package mxrlin.customdrugs.helper.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class PlayerHead {
    public static ItemStack getHead(String owner, byte skulltype){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 0);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if(skulltype == 3){
            skullMeta.setOwner(owner);
        }
        skull.setItemMeta(skullMeta);
        return skull;

        // 0 SKELETON SKULL
        // 1 WITHER SKELLI SKULL
        // 2 ZOMBIE HEAD
        // 3 HEAD
        // 4 CREEPER HEAD
    }


    public static ItemStack createIDSkull(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 0);
        if(url.isEmpty())
            return head;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch(IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

}
