package com.smashingmods.chemlib

import com.smashingmods.chemlib.items.CompoundItem
import com.smashingmods.chemlib.items.IngotItem
import com.smashingmods.chemlib.items.base.IChemical
import com.smashingmods.chemlib.resource.ChemicalParser
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry

@Suppress("UNUSED")
object ChemLibClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register {
            ColorProviderRegistry.ITEM.register(
                { itemStack, tintColor ->
                    if (tintColor == 0) {
                        (itemStack.item as? IChemical)?.color ?: 0xFFFFFF
                    } else {
                        0xFFFFFF
                    }
                }, *ChemicalParser.map.values.filter { it is IngotItem || it is CompoundItem }.toTypedArray()
            )
        }
    }
}