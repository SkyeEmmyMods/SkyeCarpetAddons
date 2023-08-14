package uk.fuby.skyecarpetaddons.features

import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import uk.fuby.skyecarpetaddons.Options

object ItemRituals {

    private val thunderConditions = ItemStack(Items.LIGHTNING_ROD, 64)
    private fun thunderRitual(item : ItemEntity) {
        if (!Options.thunderRitual) return
        if (item.world.isClient) return
        item.server?.getWorld(item.world.registryKey)?.setWeather(0, 12000, true, true)
    }

    fun detectItemRitual(item : ItemEntity) {
        val itemStack = item.stack
        when {
            itemStack.matches(thunderConditions) -> {
                thunderRitual(item)
            }
        }
    }

    private fun ItemStack.matches(item : ItemStack) : Boolean {
        return (this.item == item.item && this.count == item.count)
    }
}