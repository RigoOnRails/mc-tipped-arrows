import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType

class Plugin: JavaPlugin() {
    override fun onEnable() {
        val tippedArrows = ItemStack(Material.TIPPED_ARROW, 8)
        val tippedArrowsMeta = tippedArrows.itemMeta as PotionMeta
        tippedArrowsMeta.displayName(Component.text("boom chakalakalaka"))
        tippedArrowsMeta.basePotionData = PotionData(PotionType.FIRE_RESISTANCE)
        tippedArrows.itemMeta = tippedArrowsMeta

        val recipeKey = NamespacedKey(this, "fire_resistance_tipped_arrow")
        val recipe = ShapedRecipe(recipeKey, tippedArrows)
        recipe.shape(
            "AAA",
            "APA",
            "AAA",
        )
        recipe.setIngredient('A', Material.ARROW)

        val potion = ItemStack(Material.POTION)
        val potionMeta = potion.itemMeta as PotionMeta
        potionMeta.basePotionData = PotionData(PotionType.FIRE_RESISTANCE)
        potion.itemMeta = potionMeta
        recipe.setIngredient('P', potion)

        server.addRecipe(recipe)
    }
}