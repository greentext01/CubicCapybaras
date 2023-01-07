package dev.oliveman.cubic.capybaras

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class CubicCapybaras : JavaPlugin() {
    override fun onEnable() {
        logger.info("""
     ___           ___           ___           ___           ___           ___           ___           ___
    /\  \         /\  \         /\  \         |\__\         /\  \         /\  \         /\  \         /\  \
   /::\  \       /::\  \       /::\  \        |:|  |       /::\  \       /::\  \       /::\  \       /::\  \
  /:/\:\  \     /:/\:\  \     /:/\:\  \       |:|  |      /:/\:\  \     /:/\:\  \     /:/\:\  \     /:/\:\  \
 /:/  \:\  \   /::\~\:\  \   /::\~\:\  \      |:|__|__   /::\~\:\__\   /::\~\:\  \   /::\~\:\  \   /::\~\:\  \
/:/__/ \:\__\ /:/\:\ \:\__\ /:/\:\ \:\__\     /::::\__\ /:/\:\ \:|__| /:/\:\ \:\__\ /:/\:\ \:\__\ /:/\:\ \:\__\
\:\  \  \/__/ \/__\:\/:/  / \/__\:\/:/  /    /:/~~/~    \:\~\:\/:/  / \/__\:\/:/  / \/_|::\/:/  / \/__\:\/:/  /
 \:\  \            \::/  /       \::/  /    /:/  /       \:\ \::/  /       \::/  /     |:|::/  /       \::/  /
  \:\  \           /:/  /         \/__/     \/__/         \:\/:/  /        /:/  /      |:|\/__/        /:/  /
   \:\__\         /:/  /                                   \::/__/        /:/  /       |:|  |         /:/  /
    \/__/         \/__/                                     ~~            \/__/         \|__|         \/__/
Is starting up!""")
        
        server.pluginManager.registerEvents(RecipeDisabler(), this)
        server.pluginManager.registerEvents(CapybaraSpawnHandler(), this)
        server.pluginManager.registerEvents(CopperMinedHandler(), this)
    }
}

class RecipeDisabler : Listener {
    private val forbiddenItems = listOf(
        Material.STONE_SWORD,
        Material.STONE_AXE,
        Material.STONE_PICKAXE,
        Material.STONE_SHOVEL,
        Material.STONE_HOE,
        
        Material.WOODEN_SWORD,
        Material.WOODEN_AXE,
        Material.WOODEN_PICKAXE,
        Material.WOODEN_HOE,
        Material.WOODEN_SHOVEL,
        
        Material.DIAMOND_SWORD,
        Material.DIAMOND_PICKAXE,
        Material.DIAMOND_AXE,
        Material.DIAMOND_SHOVEL,
        Material.DIAMOND_HOE,
        
        Material.DIAMOND_HELMET,
        Material.DIAMOND_CHESTPLATE,
        Material.DIAMOND_LEGGINGS,
        Material.DIAMOND_BOOTS,
    )
    
    @EventHandler
    fun onPrepareItemCraft(event: PrepareItemCraftEvent) {
        if ((event.recipe?.result?.type ?: false) in forbiddenItems) {
            event.inventory.result = ItemStack(Material.AIR)
        }
    }
}
