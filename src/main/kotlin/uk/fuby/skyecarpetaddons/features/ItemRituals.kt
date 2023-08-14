package uk.fuby.skyecarpetaddons.features

import net.minecraft.entity.EntityType
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.world.Heightmap
import net.minecraft.world.poi.PointOfInterestStorage
import net.minecraft.world.poi.PointOfInterestTypes
import uk.fuby.skyecarpetaddons.Options
import kotlin.jvm.optionals.getOrElse

object ItemRituals {

    private val thunderConditions = ItemStack(Items.LIGHTNING_ROD, 64)
    private fun thunderRitual(item : ItemEntity) {
        val world = item.world
        if (!Options.thunderRitual) return
        if (world.isClient) return
        val serverWorld = item.server?.getWorld(world.registryKey) ?: return

        serverWorld.setWeather(0, 12000, true, true)
    }

    private val traderConditions = ItemStack(Items.EMERALD_BLOCK, 16)

    private fun traderRitual(item : ItemEntity) {
        val world = item.world
        if (!Options.traderRitual) return
        if (world.isClient) return
        val serverWorld = item.server?.getWorld(world.registryKey) ?: return

        val height = world.getChunk(item.blockPos).sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, item.blockX, item.blockZ)
        val pos = item.blockPos.mutableCopy()
        pos.y = height + 1

        val wanderTarget = serverWorld.pointOfInterestStorage.getPosition(
            { it.matchesKey(
                    PointOfInterestTypes.MEETING
                )
            },
            { true }, pos, 48, PointOfInterestStorage.OccupationStatus.ANY
        ).getOrElse{pos}

        val wanderingTrader = EntityType.WANDERING_TRADER.spawn(serverWorld, pos, SpawnReason.EVENT) ?: return
        wanderingTrader.despawnDelay = 48000
        wanderingTrader.setWanderTarget(wanderTarget)
        wanderingTrader.setPositionTarget(wanderTarget, 16)
    }

    fun detectItemRitual(item : ItemEntity) {
        val itemStack = item.stack
        when {
            itemStack.matches(thunderConditions) -> {
                thunderRitual(item)
            }
            itemStack.matches(traderConditions) -> {
                traderRitual(item)
            }
        }
    }

    private fun ItemStack.matches(item : ItemStack) : Boolean {
        return (this.item == item.item && this.count == item.count)
    }
}