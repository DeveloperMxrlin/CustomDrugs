package mxrlin.customdrugs.drugs;

import mxrlin.customdrugs.CustomDrug;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Drug {

    private String name;
    private String description;
    private List<PotionEffectType> drugEffectTypes;
    private int drugDurationInSeconds;
    private double sellPrice;
    private double buyPrice;

    public Drug(String name, String description, List<PotionEffectType> drugEffectTypes,
            int drugDurationInSeconds, double sellPrice, double buyPrice) {
        this.name = name;
        this.description = description;
        this.drugEffectTypes = drugEffectTypes;
        this.drugDurationInSeconds = drugDurationInSeconds;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    public void saveDrug(){
        final File drugfile = this.getDrugFile();
        final File drugFolder = CustomDrug.instance.getHandler().getDrugFolder();
        if(!drugFolder.exists()){
            drugFolder.mkdir();
        }
        if(!drugfile.exists()){
            try {
                drugfile.createNewFile();
            } catch (IOException e ) {
                e.printStackTrace();
            }
        }

        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(drugfile);

        configuration.set("drug.name", name);
        configuration.set("drug.desc", description);
        configuration.set("drug.duration", drugDurationInSeconds);
        configuration.set("drug.sell", sellPrice);
        configuration.set("drug.buy", buyPrice);

        final List<String> effectList = new ArrayList<>();

        for(PotionEffectType type : drugEffectTypes){
            effectList.add(type.getName());
        }

        configuration.set("effects", effectList);

        try {
            configuration.save(drugfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delDrug(){
        final File drugfile = this.getDrugFile();
        final File folder = CustomDrug.instance.getHandler().getDrugFolder();
        drugfile.delete();
    }

    public File getDrugFile(){
        return new File(CustomDrug.instance.getDataFolder() + "//drugs", this.name + ".yml");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PotionEffectType> getDrugEffectTypes() {
        return drugEffectTypes;
    }

    public void setDrugEffectTypes(List<PotionEffectType> drugEffectTypes) {
        this.drugEffectTypes = drugEffectTypes;
    }

    public int getDrugDurationInSeconds() {
        return drugDurationInSeconds;
    }

    public void setDrugDurationInSeconds(int drugDurationInSeconds) {
        this.drugDurationInSeconds = drugDurationInSeconds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }
}
