package com.smashingmods.chemlib.items.base

import com.smashingmods.chemlib.ChemLib
import com.smashingmods.chemlib.ChemLib.id
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.UseAction
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

abstract class BaseItem(
    override val id: String,
    override val color: Int,
    properties: FabricItemSettings,
) : Item(properties.group(ChemLib.ITEM_GROUP)), IChemical {
    override fun register() {
        Registry.register(Registry.ITEM, id(id), this)
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext,
    ) {
        super.appendTooltip(stack, world, tooltip, context)

        tooltip.add(LiteralText(abbreviation).styled { ChemLib.CHEM_TOOLTIP_COLOR })
    }
}