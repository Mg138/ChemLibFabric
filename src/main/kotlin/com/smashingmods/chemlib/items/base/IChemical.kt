package com.smashingmods.chemlib.items.base

import net.minecraft.item.ItemConvertible

interface IChemical : ItemConvertible {
    val abbreviation: String
    val id: String
    val color: Int

    fun register()
}