package mxrlin.customdrugs.api;

import mxrlin.customdrugs.CustomDrug;
import mxrlin.customdrugs.drugs.Drug;
import mxrlin.customdrugs.drugs.DrugHandler;
import mxrlin.customdrugs.helper.Language;
import mxrlin.customdrugs.helper.infinv.ScrollerInventory;
import mxrlin.customdrugs.helper.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomDrugsAPI {

    private static DrugHandler handler = CustomDrug.instance.getHandler();

    /**
     * This Method creates a drug and add it to the pool. This Method don't fire the 'CreateDrugEvent'.
     * @param name Set the name of the drug
     * @param drugEffectType Set a list of Effect Types, the consumer gets when he consumes a drug.
     * @param drugDuration Set the time in seconds on how long the Drug should last.
     * @param description Set a description of the Drug. (Max 24 Characters.)
     * @param sellPrice Set the Sell price of the Drug. Essentials need to be loaded in.
     * @param buyPrice Set the Buy price of the Drug. Essentials need to be loaded in.
     * @return Returns a boolean, that is false if something went wrong.
     */
    public static boolean createDrug(final String name, final List<PotionEffectType> drugEffectType, final int drugDuration, final String description, final double sellPrice, final double buyPrice){

        if(handler.drugExists(name)) return false;

        if(description.length() > 24) return false;

        if(drugEffectType.size() > 3) return false;

        handler.createNewDrug(name, drugEffectType, drugDuration, description, sellPrice, buyPrice);
        return true;

    }

    /**
     * This Method deletes a drug with that name. This Method don't fire the 'DeleteDrugEvent'.
     * @param name The Name of the Drug you want to delete.
     * @return Returns a boolean, that is false if something went wrong.
     */
    public static boolean deleteDrug(final String name){

        if(!handler.drugExists(name)) return false;

        handler.delDrug(name);
        return true;
    }

    /**
     * Checks if a drug with the name exists.
     * @param name Checks if that name exist.
     * @return Returns a boolean if the drug exists.
     */
    public static boolean drugExists(final String name){
        return handler.drugExists(name);
    }

    /**
     * Get the Drug Folder as a File.
     * @return Returns the Drug Folder as a File.
     */
    public static File getDrugFolder(){
        return new File(CustomDrug.instance.getDataFolder() + "//drugs");
    }

    /**
     * Get the handler file.
     * @return Returns the Handler File as File.
     */
    public static File getHandlerFile(){
        return new File(CustomDrug.instance.getDataFolder(), "drughandler.yml");
    }

    /**
     * Opens the Handler Menu for the Player. This Method doesn't fire 'DrugInventoryPerNPCEvent' or 'DrugInventoryPerTelephoneEvent'.
     * @param player The Handler Menu will open for THAT Player.
     * @return Returns a boolean, that is false if something went wrong.
     */
    public static boolean openHandlerMenu(Player player){
        ArrayList<ItemStack> list = new ArrayList<>();

        for(Drug d : handler.getDrugObjects().values()){

            List<String> potsname = new ArrayList<>();
            for(PotionEffectType type : d.getDrugEffectTypes()){
                potsname.add(type.getName());
            }

            List<String> druglore = Language.getMessageList("drugslore");

            druglore.replaceAll(e1 -> e1.replace("%drugname%", d.getName()));
            druglore.replaceAll(e1 -> e1.replace("%description%", d.getDescription()));
            druglore.replaceAll(e1 -> e1.replace("%potions%", potsname.toString().replace("[", "").replace("]", "")));
            druglore.replaceAll(e1 -> e1.replace("%duration%", "" + d.getDrugDurationInSeconds()));
            druglore.replaceAll(e1 -> e1.replace("%sellprice%", "" + d.getSellPrice()));
            druglore.replaceAll(e1 -> e1.replace("%buyprice%", "" + d.getBuyPrice()));

            list.add(new ItemBuilder(Material.SLIME_BALL, 1, (short) 0).setDisplayName(d.getName()).setLore(druglore).build());
        }

        if(list.size() == 0){
            player.sendMessage(Language.getMessage("handlerhavenothing"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10f, 0.1f);
            return false;
        }

        new ScrollerInventory(list, CustomDrug.instance.getDrugHandlerName(), player);
        return true;
    }

    /**
     * Loads The Handler File and The Drug Folder, and creates the File/Folder if it doesn't exist.
     * @return Returns a boolean, that is false if something went wrong.
     */
    public static boolean loadHandlerFile(){

        handler.loadHandlerFile();
        return true;

    }

}
