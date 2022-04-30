package com.smashingmods.chemlib.items

import com.smashingmods.chemlib.items.base.BaseItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.world.World

class ElementItem(
    val name: String,
    override val abbreviation: String,
    private val atomicNumber: Int,
    color: Int
) : BaseItem(
    "element_$name",
    color,
    FabricItemSettings()
) {
    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext,
    ) {
        super.appendTooltip(stack, world, tooltip, context)
        tooltip.add(LiteralText(atomicNumber.toString()))
    }
}