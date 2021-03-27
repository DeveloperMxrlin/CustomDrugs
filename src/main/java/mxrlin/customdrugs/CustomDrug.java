package mxrlin.customdrugs;

import mxrlin.customdrugs.citizens.listener.NPCRightClickListener;
import mxrlin.customdrugs.commands.DrugCommand;
import mxrlin.customdrugs.drugs.DrugHandler;
import mxrlin.customdrugs.helper.FileUtilities;
import mxrlin.customdrugs.helper.RecipeLoader;
import mxrlin.customdrugs.helper.UpdateChecker;
import mxrlin.customdrugs.helper.mysql.DrugMySQL;
import mxrlin.customdrugs.helper.mysql.MySQL;
import mxrlin.customdrugs.helper.mysql.MySQLFile;
import mxrlin.customdrugs.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomDrug extends JavaPlugin {

    public static CustomDrug instance;

    // Strings
    private final String DrugHandlerName = "§c§lDrughandler";

    // Ints
    private int waitphone = 15;
    private double minusop1 = 0.1D;
    private double minusop2 = 1.0D;
    private double minusop3 = 10.0D;
    private double plusop1 = 10.0D;
    private double plusop2 = 1.0D;
    private double plusop3 = 0.1D;

    // Booleans
    private boolean Citizensloaded = false;
    private boolean Essentialsloaded = false;

    // Lists
    private final List<String> aliases = new ArrayList<>();

    // Class instances
    private final DrugHandler handler = new DrugHandler();
    private final RecipeLoader recipe = new RecipeLoader();

    @Override
    public void onEnable() {
        instance = this;

        if(Bukkit.getPluginManager().getPlugin("Citizens") != null){
            Citizensloaded = true;
        }
        if(Bukkit.getPluginManager().getPlugin("Essentials") != null){
            Essentialsloaded = true;
        }

        recipe.registerRecipes();
        handler.importDrugs();

        loadConfig();

        Bukkit.getPluginManager().registerEvents(new NPCRightClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreProcessListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);

        getCommand("customdrugs").setExecutor(new DrugCommand());

        if(getConfig().getBoolean("update-checker")){
            new UpdateChecker(this, 90338).getVersion(version -> {
                if(this.getDescription().getVersion().equalsIgnoreCase(version)){
                    System.out.println("[CustomDrugs] There is no new update available.");
                }else{
                    System.out.println("[CustomDrugs] There is a new update! Download it now here:");
                    System.out.println("[CustomDrugs] https://www.spigotmc.org/resources/customdrugs-99-editable.90338/");
                }
            });
        }

        Bukkit.getConsoleSender().sendMessage("§8§m--- §c§lCUSTOMDRUGS §8§m---");

        Bukkit.getConsoleSender().sendMessage("");

        Bukkit.getConsoleSender().sendMessage("§c§lCUSTOMDRUGS LOADED");
        Bukkit.getConsoleSender().sendMessage("§cVersion: §c§l" + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("§cAuthor: Mxrlin");

        Bukkit.getConsoleSender().sendMessage("");
        if(!Citizensloaded){
            Bukkit.getConsoleSender().sendMessage("§e§lPlease install Citizens if you want to use the Handler NPC.");
        }else Bukkit.getConsoleSender().sendMessage("§e§lCitizens successfully loaded with CustomDrugs.");
        if(!Essentialsloaded){
            Bukkit.getConsoleSender().sendMessage("§e§lPlease install Essentials if you want to buy/sell Drugs.");
        }else Bukkit.getConsoleSender().sendMessage("§e§lEssentials successfully loaded with CustomDrugs.");

        Bukkit.getConsoleSender().sendMessage("");

        if(MySQLFile.loadMySQL(getConfig())){
            Bukkit.getConsoleSender().sendMessage("§aMySQL Connected!");
        }else if(MySQLFile.useMySQL){
            Bukkit.getConsoleSender().sendMessage("§cSomething went wrong whilst connecting to MySQL. Please check your entered data again!");
        }else Bukkit.getConsoleSender().sendMessage("§eMySQL isn't connected, because you disabled it!");

        Bukkit.getConsoleSender().sendMessage("");

        Bukkit.getConsoleSender().sendMessage("§8§m--- §c§lCUSTOMDRUGS §8§m---");
    }

    @Override
    public void onDisable() {

        DrugMySQL.saveDrugs();

        Bukkit.getConsoleSender().sendMessage("§8§m--- §c§lCUSTOMDRUGS §8§m---");

        Bukkit.getConsoleSender().sendMessage("");

        Bukkit.getConsoleSender().sendMessage("§c§lCUSTOMDRUGS DEACTIVATED");
        Bukkit.getConsoleSender().sendMessage("§cVersion: §c§l" + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("§cAuthor: Mxrlin");

        Bukkit.getConsoleSender().sendMessage("");

        if(MySQLFile.disconnectMySQL()){
            Bukkit.getConsoleSender().sendMessage("§aMySQL disconnected!");
        }else if(MySQLFile.useMySQL){
            Bukkit.getConsoleSender().sendMessage("§cSomething went wrong whilst disconnecting MySQL. Please check your entered data again!");
        }else Bukkit.getConsoleSender().sendMessage("§eMySQL cant disconnect because you disabled MySQL.");

        Bukkit.getConsoleSender().sendMessage("");

        Bukkit.getConsoleSender().sendMessage("§8§m--- §c§lCUSTOMDRUGS §8§m---");
    }

    public DrugHandler getHandler() {
        return handler;
    }

    public String getDrugHandlerName() {
        return DrugHandlerName;
    }

    private void loadConfig(){
        File configFile = new File(CustomDrug.instance.getDataFolder(), "config.yml");
        if (!(configFile.exists())){
            FileUtilities.loadResource(CustomDrug.instance, "config.yml");
        }
        FileConfiguration config = CustomDrug.instance.getConfig();
        this.aliases.addAll(config.getStringList("aliases"));
        this.waitphone = config.getInt("waitphoneinsecs");
        this.minusop1 = config.getDouble("minusoption1");
        this.minusop2 = config.getDouble("minusoption2");
        this.minusop3 = config.getDouble("minusoption3");

        this.plusop1 = config.getDouble("plusoption1");
        this.plusop2 = config.getDouble("plusoption2");
        this.plusop3 = config.getDouble("plusoption3");
    }

    public List<String> getAliases() {
        return aliases;
    }

    public int getWaitphone() {
        return waitphone;
    }

    public boolean isCitizensloaded() {
        return Citizensloaded;
    }

    public RecipeLoader getRecipe() {
        return recipe;
    }

    public double getMinusop1() {
        return minusop1;
    }

    public double getMinusop2() {
        return minusop2;
    }

    public double getMinusop3() {
        return minusop3;
    }

    public double getPlusop1() {
        return plusop1;
    }

    public double getPlusop2() {
        return plusop2;
    }

    public double getPlusop3() {
        return plusop3;
    }

    public boolean isEssentialsloaded() {
        return Essentialsloaded;
    }
}
