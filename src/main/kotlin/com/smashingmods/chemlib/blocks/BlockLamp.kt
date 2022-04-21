package com.smashingmods.chemlib.blocks

import com.smashingmods.chemlib.ChemLib
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.registry.Registry

class BlockLamp(val name: String) : Block(
    FabricBlockSettings
        .of(Material.METAL)
        .strength(2.0f)
        .sounds(BlockSoundGroup.GLASS)
        .luminance(15)
) {
    fun register() {
        Registry.register(Registry.BLOCK, ChemLib.id("lamp_$name"), this)
        Registry.register(Registry.ITEM, ChemLib.id("lamp_$name"),
            BlockItem(this, FabricItemSettings().group(ChemLib.ITEM_GROUP))
        )
    }
}