package com.smashingmods.chemlib.items

import com.smashingmods.chemlib.Utils
import com.smashingmods.chemlib.items.base.BaseItem
import com.smashingmods.chemlib.items.base.IChemical
import net.fabricmc.fabric.api.item.v1.FabricItemSettings

class CompoundItem(
    val name: String,
    color: Int,
    private val chemicals: List<Pair<Int, IChemical>>,
) : BaseItem(
    "compound_$name",
    color,
    FabricItemSettings()
), IChemical {
    override val abbreviation by lazy { Utils.getAbbreviation(chemicals) }
}