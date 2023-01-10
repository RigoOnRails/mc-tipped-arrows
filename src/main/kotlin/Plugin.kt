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
            PotionType.WATER_BREATHING,
            PotionType.WEAKNESS,
        )
    }

    override fun onEnable() {
        for (potionType in TIPPED_ARROW_POTION_TYPES) {
            // Add regular recipe
            addTippedArrowRecipe(PotionData(potionType))

            // Add redstone potion recipe, if possible
            if (potionType.isExtendable) {
                addTippedArrowRecipe(PotionData(potionType, true, false))
            }

            // Add glowstone potion recipe, if possible
            if (potionType.isUpgradeable) {
                addTippedArrowRecipe(PotionData(potionType, false, true))
            }
        }
    }

    private fun addTippedArrowRecipe(potionData: PotionData) {
        // Crafting result
        val tippedArrows = ItemStack(Material.TIPPED_ARROW, 8)
        val tippedArrowsMeta = tippedArrows.itemMeta as PotionMeta
        tippedArrowsMeta.basePotionData = potionData
        tippedArrows.itemMeta = tippedArrowsMeta

        // Recipe
        val recipeKeyName = fun(): String {
            val segments = mutableListOf(potionData.type.name, "tipped_arrow")
            if (potionData.isExtended) segments.add("extended")
            if (potionData.isUpgraded) segments.add("upgraded")
            return segments.joinToString("_")
        }()

        val recipe = ShapedRecipe(NamespacedKey(this, recipeKeyName), tippedArrows)

        // Require 8 arrows around 1 potion for the recipe.
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