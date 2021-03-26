package mxrlin.customdrugs.helper;

import mxrlin.customdrugs.CustomDrug;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Language {

    public static String getMessage(String path){
        File file = new File(CustomDrug.instance.getDataFolder(), "language.yml");
        if(!file.exists()) FileUtilities.loadResource(CustomDrug.instance, "language.yml");

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        return ChatColor.translateAlternateColorCodes('&', yaml.getString(path).replace("%prefix%", getPrefix()));
    }
    public static List<String> getMessageList(String path){
        File file = new File(CustomDrug.instance.getDataFolder(), "language.yml");
        if(!file.exists()) FileUtilities.loadResource(CustomDrug.instance, "language.yml");

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        List<String> messages = yaml.getStringList(path);
        List<String> translatedmsg = new ArrayList<>();

        for(String msg : messages){
            translatedmsg.add(ChatColor.translateAlternateColorCodes('&', msg));
        }

        return translatedmsg;
    }

    private static String getPrefix(){
        File configFile = new File(CustomDrug.instance.getDataFolder(), "config.yml");
        if (!(configFile.exists())){
            FileUtilities.loadResource(CustomDrug.instance, "config.yml");
        }
        FileConfiguration config = CustomDrug.instance.getConfig();
        return config.getString("prefix");
    }

}
