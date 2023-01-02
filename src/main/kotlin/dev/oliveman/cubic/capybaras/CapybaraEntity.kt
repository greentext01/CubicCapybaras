package dev.oliveman.cubic.capybaras

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ActiveModel
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LightningBolt
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.animal.Pig
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.phys.Vec3
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

fun createCapybara(loc: Location): AbstractCapybara {
    return if (Random.nextInt(50) == 0)
        PullUpCapybara(loc)
    else
        NormalCapybara(loc)
}

abstract class AbstractCapybara(loc: Location, modelId: String) :
    Pig(EntityType.PIG, (loc.world as CraftWorld).handle) {
    private val model: ActiveModel = ModelEngineAPI.createActiveModel(modelId)

    companion object {
        val FOOD_ITEMS: Ingredient = Ingredient.of(*arrayOf<ItemLike>(Items.MELON_SLICE))
    }

    init {
        setPos(Vec3(loc.x, loc.y, loc.z))
        val modeledEntity = ModelEngineAPI.createModeledEntity(this.bukkitEntity)
        modeledEntity.addModel(model, true)
        model.spawn()

        isSilent = true
        isInvisible = true
        persistentInvisibility = true
    }

    override fun registerGoals() {
        goalSelector.addGoal(0, FloatGoal(this))
        goalSelector.addGoal(1, PanicGoal(this, 1.25))
        goalSelector.addGoal(3, BreedGoal(this, 1.0))
        goalSelector.addGoal(4, TemptGoal(this, 1.2, Ingredient.of(*arrayOf<ItemLike>(Items.CARROT_ON_A_STICK)), false))
        goalSelector.addGoal(4, TemptGoal(this, 1.2, FOOD_ITEMS, false))
        goalSelector.addGoal(6, WaterAvoidingRandomStrollGoal(this, 1.0))
        goalSelector.addGoal(7, LookAtPlayerGoal(this, Player::class.java, 6.0f))
        goalSelector.addGoal(8, RandomLookAroundGoal(this))
    }

    override fun isFood(itemstack: ItemStack?): Boolean {
        return FOOD_ITEMS.test(itemstack)
    }

    override fun getBreedOffspring(worldserver: ServerLevel?, entityageable: AgeableMob?): AbstractCapybara {
        return createCapybara(Location(worldserver?.world, 0.0, 0.0, 0.0))
    }

    protected fun playCustomSound(sound: String) {
        val pos = position()
        val location = Location(null, pos.x, pos.y, pos.z)
        this.level.world.playSound(location, sound, 1f, 1f)
    }

    abstract override fun playAmbientSound()

    override fun playHurtSound(damagesource: DamageSource?) {
        playCustomSound("cubic_extras:capybara.hurt")
    }

    override fun getDeathSound(): SoundEvent? {
        playCustomSound("cubic_extras:capybara.death")
        return null
    }

    override fun mobInteract(entityhuman: Player, enumhand: InteractionHand?): InteractionResult? {
        val flag = isFood(entityhuman.getItemInHand(enumhand))
        return if (!flag && this.isSaddled && !this.isVehicle && !entityhuman.isSecondaryUseActive) {
            InteractionResult.sidedSuccess(level.isClientSide)
        } else {
            super.mobInteract(entityhuman, enumhand)
        }
    }

    override fun thunderHit(worldserver: ServerLevel?, entitylightning: LightningBolt?) {

    }
}

class NormalCapybara(loc: Location) : AbstractCapybara(loc, "capybara") {
    override fun playAmbientSound() {
        playCustomSound("cubic_extras:capybara.ambient")
    }
}

class PullUpCapybara(loc: Location) : AbstractCapybara(loc, "capybara_with_orange") {
    private var lastTimePlayedSong = 0.toLong()

    override fun playAmbientSound() {
        // 3 minutes in milliseconds
        if (System.currentTimeMillis() - lastTimePlayedSong >= 3 * 60 * 1000) {
            lastTimePlayedSong = System.currentTimeMillis()
            playCustomSound("cubic_extras:capybara.pullup")
        } else {
            playCustomSound("cubic_extras:capybara.ambient")
        }
    }
}
