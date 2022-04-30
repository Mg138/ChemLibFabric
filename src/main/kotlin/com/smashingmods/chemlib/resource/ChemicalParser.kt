package com.smashingmods.chemlib.resource

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.smashingmods.chemlib.ChemLib
import com.smashingmods.chemlib.ChemLib.id
import com.smashingmods.chemlib.items.CompoundItem
import com.smashingmods.chemlib.items.ElementItem
import com.smashingmods.chemlib.items.IngotItem
import com.smashingmods.chemlib.items.base.IChemical
import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.json.tags.JTag.tag
import net.minecraft.util.Identifier
import kotlin.io.path.forEachDirectoryEntry
import kotlin.io.path.reader


object ChemicalParser {
    private val INGOT_TAGS: RuntimeResourcePack = RuntimeResourcePack.create("chemlib:ingots")
    
    data class TempCompoundChemical(val color: Int, val compounds: List<Pair<Int, String>>)

    val map: MutableMap<String, IChemical> = mutableMapOf()
    private val compoundCache: MutableMap<String, TempCompoundChemical> = mutableMapOf()

    private fun convertColor(array: JsonArray): Int {
        return (array[0].asInt shl 16) or (array[1].asInt shl 8) or (array[2].asInt)
    }

    private fun convertCompounds(array: JsonArray): List<Pair<Int, String>> {
        return array.map {
            val obj = it.asJsonObject
            val id = obj["id"].asString
            val count = if (obj.has("count")) {
                obj["count"].asInt
            } else {
                1
            }
            count to id
        }
    }

    private fun loadChemical(chemical: JsonObject) {
        val id = chemical["id"].asString
        val color = convertColor(chemical["color"].asJsonArray)

        when (chemical["type"].asString) {
            "element" -> {
                val name = chemical["name"].asString
                val atomicNumber = chemical["atomic_number"].asInt
                val ingot = chemical["ingot"].asBoolean

                val element = ElementItem(id, name, atomicNumber, color)
                map[id] = element
                if (ingot) {
                    map["ingot_$id"] = IngotItem(id, element, color)
                }
            }
            "compound" -> {
                val compounds = convertCompounds(chemical["compounds"].asJsonArray)

                compoundCache[id] = TempCompoundChemical(color, compounds)
            }
        }
    }

    private fun illegalChemical(id: String) =
        IllegalArgumentException("somehow, chemical $id has chemicals that aren't present while they should be!")

    fun load() {
        if (map.isNotEmpty()) return

        ChemLib.DATA_DIR.forEachDirectoryEntry("**.json") { file ->
            val jsonParser = JsonParser.parseReader(file.reader())
            val jsonObject = jsonParser.asJsonObject
            jsonObject["chemicals"].asJsonArray.map { it.asJsonObject }.forEach { chemical ->
                loadChemical(chemical)
            }
        }

        while (compoundCache.isNotEmpty()) {
            val toRemove: MutableList<String> = mutableListOf()

            compoundCache.forEach { (id, temp) ->
                val color = temp.color
                val compounds = temp.compounds
                compounds
                    .map { it.first to map[it.second] }
                    .takeIf { it.all { e -> e.second != null } }
                    ?.map { it.first to (it.second ?: throw illegalChemical(id)) }
                    ?.let {
                        map[id] = CompoundItem(id, color, it)
                        toRemove += id
                    }
            }
            toRemove.forEach { compoundCache.remove(it) }
        }

        map.forEach { it.value.register() }

        map.values
            .filterIsInstance<IngotItem>()
            .forEach {
                INGOT_TAGS.addTag(Identifier("c", "items/${it.name}_ingots"),
                    tag().add(id(it.id))
                )
            }

        RRPCallback.AFTER_VANILLA.register {
            it.add(INGOT_TAGS)
        }
    }
}

