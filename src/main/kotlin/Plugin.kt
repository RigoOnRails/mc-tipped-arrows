import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType
import java.lang.Exception

/*
//Arrow of Fire Resistance
//Arrow of Harming
//Arrow of Healing
//Arrow of Invisibility
//Arrow of Leaping
//Arrow of slow falling
//Arrow of Night Vision
//Arrow of Poison
//Arrow of Regeneration
//Arrow of Slowness
//Arrow of Strength
//Arrow of Swiftness
//Arrow of the Turtle Master
Arrow of Water Breathing
Arrow of Weakness
 */

class Plugin: JavaPlugin() {
    companion object {
        val TIPPED_ARROW_POTION_TYPES = arrayOf(
            PotionType.FIRE_RESISTANCE,
            PotionType.INSTANT_DAMAGE,
            PotionType.INSTANT_HEAL,
            PotionType.INVISIBILITY,
            PotionType.JUMP,
            PotionType.SLOW_FALLING,
            PotionType.NIGHT_VISION,
            PotionType.POISON,
            PotionType.REGEN,
            PotionType.SLOWNESS,
            PotionType.STRENGTH,
            PotionType.SPEED,
            PotionType.TURTLE_MASTER,
        )
    }

    override fun onEnable() {
        for (potionType in TIPPED_ARROW_POTION_TYPES) {
            // Display names courtesy of @UnicornFortune1 🤪
            val displayName = when (potionType) {
                PotionType.FIRE_RESISTANCE -> "boom chakalakalaka"
                PotionType.INSTANT_DAMAGE -> "begone thot"
                PotionType.INSTANT_HEAL -> "hurts if ur already dead"
                PotionType.INVISIBILITY -> "john cena"
                PotionType.JUMP -> "bad bunny"
                PotionType.SLOW_FALLING -> "i believe i can fly"
                PotionType.NIGHT_VISION -> "these mushrooms go crazy"
                PotionType.POISON -> "die slowly."
                PotionType.REGEN -> "u be aight"
                PotionType.SLOWNESS -> "hold ya horses"
                PotionType.STRENGTH -> "steroid arrows"
                PotionType.SPEED -> "DO YOU WANT TO DO SOME FUCKING COCAINE"
                PotionType.TURTLE_MASTER -> "now u a turtle"
                else -> throw Exception("Unexpected potion type")
            }

            // Add regular recipe
            addTippedArrowRecipe(PotionData(potionType), displayName)

            // Add redstone potion recipe, if possible
            if (potionType.isExtendable) {
                addTippedArrowRecipe(PotionData(potionType, true, false), displayName)
            }

            // Add glowstone potion recipe, if possible
            if (potionType.isUpgradeable) {
                addTippedArrowRecipe(PotionData(potionType, false, true), displayName)
            }
        }
    }

    private fun addTippedArrowRecipe(potionData: PotionData, displayName: String) {
        // Crafting result
        val tippedArrows = ItemStack(Material.TIPPED_ARROW, 8)
        val tippedArrowsMeta = tippedArrows.itemMeta as PotionMeta
        tippedArrowsMeta.displayName(Component.text(displayName))
        tippedArrowsMeta.basePotionData = potionData
        tippedArrows.itemMeta = tippedArrowsMeta

        // Require 8 arrows around 1 potion for the recipe.
        val recipe = ShapedRecipe(NamespacedKey(this, potionData.type.name + "_tipped_arrow"), tippedArrows)
        recipe.shape(
            "AAA",
            "APA",
            "AAA",
        )

        val potion = ItemStack(Material.POTION)
        val potionMeta = potion.itemMeta as PotionMeta
        potionMeta.basePotionData = potionData
        potion.itemMeta = potionMeta

        recipe.setIngredient('A', Material.ARROW)
        recipe.setIngredient('P', potion)

        server.addRecipe(recipe)
    }
}