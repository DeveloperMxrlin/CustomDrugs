package mxrlin.customdrugs.drugs;

import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class DrugRecipe {
    private ShapedRecipe shapedRecipe;
    private ShapelessRecipe shapelessRecipe;

    public DrugRecipe(ShapedRecipe shapedRecipe) {
        this.shapedRecipe = shapedRecipe;
    }

    public DrugRecipe(ShapelessRecipe shapelessRecipe) {
        this.shapelessRecipe = shapelessRecipe;
    }

    public boolean isShapedRecipe(){
        if(shapedRecipe != null) return true;
        return false;
    }

    public ShapedRecipe getShapedRecipe() {
        return shapedRecipe;
    }

    public void setShapedRecipe(ShapedRecipe shapedRecipe) {
        this.shapedRecipe = shapedRecipe;
    }

    public ShapelessRecipe getShapelessRecipe() {
        return shapelessRecipe;
    }

    public void setShapelessRecipe(ShapelessRecipe shapelessRecipe) {
        this.shapelessRecipe = shapelessRecipe;
    }
}
