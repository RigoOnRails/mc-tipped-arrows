import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType

// TODO: Add support for extended or upgraded potions

class Plugin: JavaPlugin() {
    override fun onEnable() {
        // Display names courtesy of @UnicornFortune1 😜
        addTippedArrowRecipe(PotionData(PotionType.FIRE_RESISTANCE),  "boom chakalakalaka")
        addTippedArrowRecipe(PotionData(PotionType.INSTANT_DAMAGE),  "wassup hoe")
        addTippedArrowRecipe(PotionData(PotionType.INSTANT_HEAL), "hurts if ur already dead")
        addTippedArrowRecipe(PotionData(PotionType.INVISIBILITY), "john cena")
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