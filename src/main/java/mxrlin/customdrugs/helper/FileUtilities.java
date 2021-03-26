package mxrlin.customdrugs.helper;

import com.google.common.io.ByteStreams;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class FileUtilities {
    public static File loadResource(Plugin plugin, String fileName) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, fileName);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try(InputStream in = plugin.getResource(fileName);
                    OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }
    public static void setThing(Plugin plugin, String path, String filename, Double input){
        //GET INSTANCES
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, filename);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(resourceFile);

        //CREATE FILE IF NOT CREATED
        loadResource(plugin,filename);

        //SET INPUT
        configuration.set(path, input);

        //SAVE FILE
        try {
            configuration.save(resourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setThing(Plugin plugin, String path, String filename, String input){
        //GET INSTANCES
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, filename);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(resourceFile);

        //CREATE FILE IF NOT CREATED
        loadResource(plugin,filename);

        //SET INPUT
        configuration.set(path, input);

        //SAVE FILE
        try {
            configuration.save(resourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setThing(Plugin plugin, String path, String filename, Integer input){
        //GET INSTANCES
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, filename);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(resourceFile);

        //CREATE FILE IF NOT CREATED
        loadResource(plugin,filename);

        //SET INPUT
        configuration.set(path, input);

        //SAVE FILE
        try {
            configuration.save(resourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setThing(Plugin plugin, String path, String filename, Float input){
        //GET INSTANCES
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, filename);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(resourceFile);

        //CREATE FILE IF NOT CREATED
        loadResource(plugin,filename);

        //SET INPUT
        configuration.set(path, input);

        //SAVE FILE
        try {
            configuration.save(resourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getString(Plugin plugin, String path, String filename){
        //GET INSTANCES
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, filename);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(resourceFile);

        //CREATE FILE IF NOT CREATED
        loadResource(plugin, filename);

        //GET STRING FROM PATH
        String string = "null";
        string = configuration.getString(path);

        //RETURNING STRING
        return string;
    }
    public static Integer getInt(Plugin plugin, String path, String filename){
        //GET INSTANCES
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, filename);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(resourceFile);

        //CREATE FILE IF NOT CREATED
        loadResource(plugin, filename);

        //GET STRING FROM PATH
        int integer = 0;
        integer = configuration.getInt(path);

        //RETURNING STRING
        return integer;
    }

    public static Double getDouble(Plugin plugin, String path, String filename){
        //GET INSTANCES
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, filename);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(resourceFile);

        //CREATE FILE IF NOT CREATED
        loadResource(plugin, filename);

        //GET STRING FROM PATH
        double doublee = 0;
        doublee = configuration.getDouble(path);

        //RETURNING STRING
        return doublee;
    }
    public static Float getFloat(Plugin plugin, String path, String filename){
        //GET INSTANCES
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, filename);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(resourceFile);

        //CREATE FILE IF NOT CREATED
        loadResource(plugin, filename);

        //GET STRING FROM PATH
        float floatt = 0;
        floatt = configuration.getInt(path);

        //RETURNING STRING
        return floatt;
    }
}
