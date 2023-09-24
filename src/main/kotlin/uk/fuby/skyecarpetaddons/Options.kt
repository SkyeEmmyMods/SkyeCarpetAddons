package uk.fuby.skyecarpetaddons

import carpet.api.settings.CarpetRule
import carpet.api.settings.Rule
import carpet.api.settings.RuleCategory
import carpet.api.settings.Validator
import carpet.utils.Messenger
import net.minecraft.server.command.ServerCommandSource

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

    @JvmField
    @Rule(
        categories = [RuleCategory.FEATURE]
    )
    var fastMinecarts = false

    @JvmField
    @Rule(
        categories = [RuleCategory.CREATIVE],
        validators = [ItemRngValidator::class],
        options = ["false", "0.0 0.0 0.0", "-0.1 -0.1 -0.1", "0.1 0.1 0.1"],
        strict = false
    )
    var hardcodedItemRNG = "false"

    class ItemRngValidator : Validator<String>() {
        override fun validate(
            source: ServerCommandSource?,
            changingRule: CarpetRule<String>?,
            newValue: String?,
            userInput: String?
        ) : String? {
            newValue ?: return null
            if (newValue == "false") {
                return newValue
            }

            newValue.split(" ").forEach {
                try {
                    if (it.toDouble() in -0.1..0.1) {
                        return newValue
                    }
                    Messenger.m(source, "r Values must be in range -0.1 to 0.1")
                } catch (_: NumberFormatException) {}
            }
            return null
        }
    }

    @JvmField
    @Rule(
        categories = [RuleCategory.CREATIVE],
        validators = [DispenserItemRngValidator::class],
        options = ["false", "0.0 0.0 0.0", "-0.2033650 -0.1033650 -0.1033650", "0.2033650 0.1033650 0.1033650"],
        strict = false
    )
    var hardcodedDispenserItemRNG = "false"

    class DispenserItemRngValidator : Validator<String>() {
        override fun validate(
            source: ServerCommandSource?,
            changingRule: CarpetRule<String>?,
            newValue: String?,
            userInput: String?
        ) : String? {
            newValue ?: return null
            if (newValue == "false") {
                return newValue
            }

            val values = newValue.split(" ")

            for ((index, value) in values.withIndex()) {
                if (index == 0) {
                    try {
                        if (value.toDouble() !in -0.2033650..0.2033650) {
                            Messenger.m(source, "r First value must be in range -0.2033650 to 0.2033650")
                            return null
                        }
                    } catch (_: NumberFormatException) {
                        Messenger.m(source, "r Invalid numbers, must be in format 123.456")
                        return null
                    }
                }
                else {
                    try {
                        if (value.toDouble() !in -0.1033650..0.1033650) {
                            Messenger.m(source, "r Second and third value must be in range -0.1033650 to 0.1033650")
                            return null
                        }
                    } catch (_: NumberFormatException) {
                        Messenger.m(source, "r Invalid numbers, must be in format 123.456")
                        return null
                    }
                }
            }
            return newValue
        }
    }


}