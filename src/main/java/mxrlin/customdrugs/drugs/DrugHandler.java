package mxrlin.customdrugs.drugs;

import mxrlin.customdrugs.CustomDrug;
import mxrlin.customdrugs.drugs.Drug;
import mxrlin.customdrugs.helper.Language;
import mxrlin.customdrugs.helper.infinv.ScrollerInventory;
import mxrlin.customdrugs.helper.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrugHandler {
    private final Map<String, Drug> drugObjects = new HashMap<>();
    public void loadHandlerFile(){
        final File file = getHandlerFile();
        final File folder = new File(CustomDrug.instance.getDataFolder() + "//drugs");
    }
    public void importDrugs(){
        final File handlerfile = getHandlerFile();
        final YamlConfiguration yaml = YamlConfiguration.loadConfiguration(handlerfile);
        final List<String> drugnames = yaml.getStringList("DrugNames");

        for(String drugname : drugnames){
            File drugfile = new File(CustomDrug.instance.getDataFolder() + "//drugs", drugname + ".yml");
            if(drugfile.exists()){
                YamlConfiguration drugyaml = YamlConfiguration.loadConfiguration(drugfile);
                int duration = drugyaml.getInt("drug.duration");
                double sellprice = drugyaml.getDouble("drug.sell");
                double buyprice = drugyaml.getDouble("drug.buy");
                String desc = drugyaml.getString("drug.desc");
                List<String> effectname = drugyaml.getStringList("effects");
                List<PotionEffectType> effect = new ArrayList<>();
                for(String s  : effectname){
                    effect.add(PotionEffectType.getByName(s));
                }
                drugObjects.put(drugname, new Drug(drugname,desc,effect,duration,sellprice,buyprice));
            }
        }
    }
    public void createNewDrug(final String name, final List<PotionEffectType> drugEffectType, final int drugDuration, final String description, final double sellPrice, final double buyPrice){
        final Drug drug = new Drug(name, description, drugEffectType, drugDuration, sellPrice, buyPrice);
        final File handlerfile = getHandlerFile();
        final YamlConfiguration yaml = YamlConfiguration.loadConfiguration(handlerfile);

        final List<String> drugnames = yaml.getStringList("DrugNames");
        if(!drugnames.contains(name)) drugnames.add(name);
        this.drugObjects.put(name, drug);

        drug.saveDrug();

        yaml.set("DrugNames", drugnames);
        try {
            yaml.save(handlerfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void delDrug(final String name){
        final Drug drug = this.drugObjects.get(name);
        final File handlerfile = getHandlerFile();
        final YamlConfiguration yaml = YamlConfiguration.loadConfiguration(handlerfile);

        final List<String> drugnames = yaml.getStringList("DrugNames");
        if(!drugnames.contains(name)) drugnames.remove(name);

        drug.delDrug();
        this.drugObjects.remove(name);

        yaml.set("DrugNames", drugnames);
        try {
            yaml.save(handlerfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean drugExists(final String name){
        return this.drugObjects.containsKey(name);
    }
    public File getDrugFolder(){
        File f = new File(CustomDrug.instance.getDataFolder() + "//drugs");
        if(!f.exists()){
            f.mkdirs();
        }
        return f;
    }
    public File getHandlerFile(){
        File f = new File(CustomDrug.instance.getDataFolder(), "drughandler.yml");
        if(!(f.exists())){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new File(CustomDrug.instance.getDataFolder(), "drughandler.yml");
    }

    public static void openHandlerMenu(Player p){
        ArrayList<ItemStack> list = new ArrayList<>();

        for(Drug d : CustomDrug.instance.getHandler().getDrugObjects().values()){

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
            p.sendMessage(Language.getMessage("handlerhavenothing"));
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 10f, 0.1f);
            return;
        }

        new ScrollerInventory(list, CustomDrug.instance.getDrugHandlerName(), p);
    }

    public Map<String, Drug> getDrugObjects() {
        return drugObjects;
    }
}
