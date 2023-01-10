import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType

class Plugin: JavaPlugin() {
    override fun onEnable() {
        data class TippedArrowRecipe(
            val potionType: PotionType,
            val extendable: Boolean,
            val upgradable: Boolean,
            val displayName: String,
        )

        // Display names courtesy of @UnicornFortune1 😜
        val recipes = arrayOf(
            TippedArrowRecipe(
                PotionType.FIRE_RESISTANCE,
                extendable = true,
                upgradable = false,
                displayName = "boom chakalakalaka",
            ),
            TippedArrowRecipe(
                PotionType.INSTANT_DAMAGE,
                extendable = false,
                upgradable = true,
                displayName = "wassup hoe",
            ),
            TippedArrowRecipe(
                PotionType.INSTANT_HEAL,
                extendable = false,
                upgradable = true,
                displayName = "hurts if ur already dead",
            ),
            TippedArrowRecipe(
                PotionType.INVISIBILITY,
                extendable = true,
                upgradable = false,
                displayName = "john cena",
            ),
        )

        for (recipe in recipes) {
            addTippedArrowRecipe(PotionData(recipe.potionType), recipe.displayName)

            if (recipe.extendable) {
                addTippedArrowRecipe(
                    PotionData(recipe.potionType, true, false),
                    recipe.displayName,
                )
            }

            if (recipe.upgradable) {
                addTippedArrowRecipe(
                    PotionData(recipe.potionType, false, true),
                    recipe.displayName,
                )
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