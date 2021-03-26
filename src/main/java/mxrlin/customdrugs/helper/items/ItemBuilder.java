package mxrlin.customdrugs.helper.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class ItemBuilder {

    private ItemStack current;

    public ItemBuilder(ItemStack i){
        this.current = i;
    }

    public ItemBuilder(Material m, int amount, short ID){
        this(new ItemStack(m, amount, ID));
    }
    public ItemBuilder setDisplayName(String displayname){
        ItemMeta m = current.getItemMeta();
        m.setDisplayName(displayname);
        current.setItemMeta(m);
        return this;
    }
    public ItemBuilder setLore(List<String> lore){
        ItemMeta m = current.getItemMeta();
        m.setLore(lore);
        current.setItemMeta(m);
        return this;
    }
    public ItemBuilder addEnch(Enchantment ench, int level){
        ItemMeta m = current.getItemMeta();
        m.addEnchant(ench, level, true);
        current.setItemMeta(m);
        return this;
    }
    public ItemBuilder hideEnchs(){
        ItemMeta m = current.getItemMeta();
        m.addItemFlags(ItemFlag.values());
        current.setItemMeta(m);
        return this;
    }
    public ItemBuilder dyeLeather(int red, int green, int blue){
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) current.getItemMeta();
        leatherArmorMeta.setColor(Color.fromRGB(red, green, blue));
        current.setItemMeta(leatherArmorMeta);
        return this;
    }
    public ItemBuilder setUnbreakable(){
        ItemMeta im = current.getItemMeta();
        im.setUnbreakable(true);
        current.setItemMeta(im);
        return this;
    }
    public ItemBuilder setAmount(int i){
        current.setAmount(i);
        return this;
    }
    public ItemStack build(){
        return this.current;
    }
}
