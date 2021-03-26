package mxrlin.customdrugs.helper;

import mxrlin.customdrugs.CustomDrug;
import mxrlin.customdrugs.helper.items.ItemBuilder;
import mxrlin.customdrugs.helper.items.PlayerHead;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;

public class RecipeLoader {

    public void registerRecipes(){
        Bukkit.addRecipe(registerComputerRecipe());
        Bukkit.addRecipe(registerBedrockRecipe());
    }

    private ShapedRecipe registerComputerRecipe(){
        // Getting the instances
        ItemStack phone = new ItemBuilder(PlayerHead.createIDSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM3MDFiMjEyNjk5MmUzMTdhNzkwYWFjZDlkNjk1MWQ4NzhkNjViMzA3MGNlZjgxZTNmMDAwNzZiNmZlZjBiZCJ9fX0==="))
                .setDisplayName(Language.getMessage("phonename")).setLore(Language.getMessageList("phonelore")).build();
        NamespacedKey key = new NamespacedKey(CustomDrug.instance, "1");
        ShapedRecipe shapedRecipe = new ShapedRecipe(key, phone);

        // Set the Shape
        shapedRecipe.shape("BBB", "BBB", "KKK");

        // Set the Ingredient (Set 'B' to Bedrock...)
        shapedRecipe.setIngredient('B', Material.BEDROCK);
        shapedRecipe.setIngredient('K', Material.STONE_BUTTON);

        return shapedRecipe;
    }

    private ShapedRecipe registerBedrockRecipe(){
        // Getting the instances
        ItemStack bedrock = new ItemBuilder(Material.BEDROCK, 1, (short) 0).build();
        NamespacedKey key = new NamespacedKey(CustomDrug.instance, "2");
        ShapedRecipe shapedRecipe = new ShapedRecipe(key, bedrock);

        // Set the shape
        shapedRecipe.shape("OOO", "OOO", "OOO");

        // Set the Ingredient (Set 'B' to Bedrock...)
        shapedRecipe.setIngredient('O', Material.OBSIDIAN);

        return shapedRecipe;
    }

}
