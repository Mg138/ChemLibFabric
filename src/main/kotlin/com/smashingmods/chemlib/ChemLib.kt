package com.smashingmods.chemlib

import com.smashingmods.chemlib.blocks.BlockLamp
import com.smashingmods.chemlib.blocks.ModBlocks
import com.smashingmods.chemlib.resource.ChemicalParser
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.ItemGroup
import net.minecraft.text.Style
import net.minecraft.text.TextColor
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.nio.file.Path
import kotlin.io.path.copyTo
import kotlin.io.path.createDirectories
import kotlin.io.path.forEachDirectoryEntry
import kotlin.io.path.name

@Suppress("UNUSED")
object ChemLib : ModInitializer {
    private const val MOD_ID = "chemlib"
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun id(name: String) = Identifier(MOD_ID, name)

    val CHEM_TOOLTIP_COLOR: Style = Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.DARK_AQUA))

    val ITEM_GROUP: ItemGroup =
        FabricItemGroupBuilder.build(id("chemlib")) { Registry.ITEM.get(id("element_hydrogen")).defaultStack }

    val DATA_DIR: Path = FabricLoader.getInstance().configDir.resolve("chemlib/chemicals").createDirectories()

    init {
        FabricLoader.getInstance().getModContainer(MOD_ID).get()
            .findPath("data/chemlib/chemicals").get()
            .forEachDirectoryEntry { it.copyTo(DATA_DIR.resolve(it.name), overwrite = true) }
    }

    override fun onInitialize() {
        ChemicalParser.load()

        ModBlocks.BLOCKS.forEach(BlockLamp::register)
    }
}