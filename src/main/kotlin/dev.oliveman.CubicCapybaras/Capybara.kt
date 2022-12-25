package dev.oliveman.CubicCapybaras

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ActiveModel
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class Capybara : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true;
        }
        
        val player = sender as Player
        val mob = player.world.spawnEntity(player.location, EntityType.PIG)
        val model: ActiveModel? = ModelEngineAPI.createActiveModel("capybara")
        val modeledEntity = ModelEngineAPI.createModeledEntity(mob)
        
        modeledEntity.addModel(model, false)
        return true
    }
}