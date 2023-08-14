package uk.fuby.skyecarpetaddons

import carpet.api.settings.Rule
import carpet.api.settings.RuleCategory

object Options {

    @JvmField
    @Rule(
        categories = [RuleCategory.FEATURE]
    )
    var thunderRitual = false

    @JvmField
    @Rule(
        categories = [RuleCategory.FEATURE]
    )
    var traderRitual = false

}