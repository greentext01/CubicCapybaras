package dev.oliveman.cubic.capybaras

import org.bukkit.craftbukkit.v1_19_R2.CraftWorld
import org.bukkit.entity.Animals
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason
import org.bukkit.event.world.ChunkLoadEvent
import kotlin.random.Random

class CapybaraSpawnHandler : Listener {
    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        if (!event.isNewChunk) return
        for (entity in event.chunk.entities) {
            if (entity is Animals &&
                !entity.location.block.isLiquid &&
                Random.nextInt(10) == 0
            ) {
                entity.remove()
                val craftWorld = entity.location.world as CraftWorld
                val world = craftWorld.handle
                val capybara = createCapybara(entity.location)
                world.addFreshEntity(capybara)
            }
        }

    }
}