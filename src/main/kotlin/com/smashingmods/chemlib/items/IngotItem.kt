package com.smashingmods.chemlib.items

import com.smashingmods.chemlib.items.base.BaseItem
import com.smashingmods.chemlib.items.base.IChemical
import net.fabricmc.fabric.api.item.v1.FabricItemSettings

class IngotItem(
    id: String,
    chemical: IChemical,
    color: Int,
) : BaseItem(
    "ingot_$id",
    color,
    FabricItemSettings()
) {
    override val abbreviation = chemical.abbreviation
}