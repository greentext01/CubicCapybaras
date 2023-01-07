package dev.oliveman.cubic.capybaras

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class CopperMinedHandler : Listener {
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.block.type == Material.COPPER_ORE && !event.player.inventory.itemInMainHand.type.isAir) {
            event.block.type = Material.AIR
        }
    }
}