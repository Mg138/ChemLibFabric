package com.smashingmods.chemlib

import com.smashingmods.chemlib.items.CompoundItem
import com.smashingmods.chemlib.items.base.IChemical

object Utils {
    private val SUBSCRIPT_ZERO = Character.codePointAt("₀", 0)

    private fun getSubscript(n: String) =
        n.toCharArray().joinToString(separator = "") { Character.toString(SUBSCRIPT_ZERO + it.digitToInt()) }

    private fun quantity(quantity: Int): String {
        if (quantity > 1) {
            return getSubscript(quantity.toString())
        }
        return ""
    }

    private fun abbreviation(chemicalPair: Pair<Int, IChemical>): String {
        val chemical = chemicalPair.second
        val abbreviation = chemical.abbreviation
        val quantity = quantity(chemicalPair.first)

        if (chemical is CompoundItem) {
            return "($abbreviation)$quantity"
        }
        return abbreviation + quantity
    }

    fun getAbbreviation(chemicals: List<Pair<Int, IChemical>>) =
        chemicals.joinToString(separator = "") { abbreviation(it) }
}