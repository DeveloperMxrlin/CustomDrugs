package mxrlin.customdrugs.commands;

import mxrlin.customdrugs.CustomDrug;
import mxrlin.customdrugs.citizens.CreateNPC;
import mxrlin.customdrugs.drugs.Drug;
import mxrlin.customdrugs.drugs.DrugHandler;
import mxrlin.customdrugs.drugs.DrugRecipe;
import mxrlin.customdrugs.helper.items.ItemBuilder;
import mxrlin.customdrugs.helper.Language;
import mxrlin.customdrugs.helper.items.PlayerHead;
import mxrlin.customdrugs.helper.infinv.ScrollerInventory;
import mxrlin.customdrugs.listener.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DrugCommand implements CommandExecutor {
    public static HashMap<UUID, Drug> drugInProcess = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) {
            s.sendMessage(Language.getMessage("noplayer"));
            return false;
        }
        Player p = (Player) s;
        if(args.length == 0){
            if(!p.hasPermission("customdrugs.user.help") || !p.hasPermission("customdrugs.user.*") || !p.hasPermission("customdrugs.*")){
                p.sendMessage(Language.getMessage("noperm"));
                return false;
            }
            p.sendMessage("§8§m-----§c§l Drugs §8§m-----");
            p.sendMessage(" ");
            p.sendMessage("§cCustomDrugs Version " + CustomDrug.instance.getDescription().getVersion() + " loaded!");
            p.sendMessage("§cDeveloped by Mxrlin");
            p.sendMessage(" ");
            p.sendMessage(Language.getMessage("help").replace("%point%", "/customdrugs").replace("%help%", "The basic command of CustomDrugs."));
            if(p.hasPermission("customdrugs.admin.create") || p.hasPermission("customdrugs.admin.*") || p.hasPermission("customdrugs.*")){
                p.sendMessage(Language.getMessage("help").replace("%point%", "/customdrugs create <Name> <DurationInSeconds>").replace("%help%", "Create a drug."));
            }
            if(p.hasPermission("customdrugs.admin.delete") || p.hasPermission("customdrugs.admin.*") || p.hasPermission("customdrugs.*")){
                p.sendMessage(Language.getMessage("help").replace("%point%", "/customdrugs delete <Name>").replace("%help%", "Delete a drug."));
            }
            if(p.hasPermission("customdrugs.admin.getdrug") || p.hasPermission("customdrugs.admin.*") || p.hasPermission("customdrugs.*")){
                p.sendMessage(Language.getMessage("help").replace("%point%", "/customdrugs get <Name>").replace("%help%", "Get a drug for free."));
            }
            if(p.hasPermission("customdrugs.admin.handler") || p.hasPermission("customdrugs.admin.*") || p.hasPermission("customdrugs.*")){
                p.sendMessage(Language.getMessage("help").replace("%point%", "/customdrugs handler").replace("%help%", "Sets the Handler NPC."));
            }
            p.sendMessage(" ");
            p.sendMessage("§8§m-----§c§l Drugs §8§m-----");
        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("create")){
                if(!p.hasPermission("customdrugs.admin.create") || !p.hasPermission("customdrugs.admin.*") || !p.hasPermission("customdrugs.*")){
                    p.sendMessage(Language.getMessage("noperm"));
                    return false;
                }
                if(drugInProcess.containsKey(p.getUniqueId())){
                    p.sendMessage(Language.getMessage("alreadyindrugcreation"));
                    return false;
                }
                String name = args[1];
                if(CustomDrug.instance.getHandler().drugExists(name)){
                    p.sendMessage(Language.getMessage("drugexist"));
                    return false;
                }
                try{
                    int seconds = Integer.parseInt(args[2]);
                    p.sendMessage(Language.getMessage("drugcreation"));
                    List<PotionEffectType> pot = new ArrayList<>(); pot.add(PotionEffectType.SPEED);

                    List<String> potsname = new ArrayList<>();
                    for(PotionEffectType type : pot){
                        potsname.add(type.getName());
                    }

                    List<String> druglore = Language.getMessageList("druglore");

                    druglore.replaceAll(e1 -> e1.replace("%potions%", potsname.toString().replace("[[[", "").replace("]]]", "").replace("[", "")
                            .replace("]", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "").replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace(",", "").replace("0", "").replaceFirst(" ", "")));
                    druglore.replaceAll(e1 -> e1.replace("%duration%", "" + seconds));

                    drugInProcess.put(p.getUniqueId(), new Drug(name,"A drug.", pot, seconds, 10, 50));

                    setPlayerDrugCreationInv(p);
                }catch (NumberFormatException e){
                    p.sendMessage(Language.getMessage("usenumber"));
                    e.printStackTrace();
                }
            }else p.sendMessage(Language.getMessage("commanddoesntexist"));
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("delete")){
                if(!p.hasPermission("customdrugs.admin.delete") || !p.hasPermission("customdrugs.admin.*") || !p.hasPermission("customdrugs.*")){
                    p.sendMessage(Language.getMessage("noperm"));
                    return false;
                }
                String drug = args[1];
                if(!CustomDrug.instance.getHandler().drugExists(drug)){
                    p.sendMessage(Language.getMessage("drugdoesntexist"));
                    return false;
                }
                CustomDrug.instance.getHandler().delDrug(drug);
                p.sendMessage(Language.getMessage("drugdeleted"));
            }else if(args[0].equalsIgnoreCase("get")){
                if(!p.hasPermission("customdrugs.admin.getdrug") || !p.hasPermission("customdrugs.admin.*") || !p.hasPermission("customdrugs.*")){
                    p.sendMessage(Language.getMessage("noperm"));
                    return false;
                }
                String drugname = args[1];
                if(!CustomDrug.instance.getHandler().drugExists(drugname)){
                    p.sendMessage(Language.getMessage("drugdoesntexist"));
                    return false;
                }

                Drug d = CustomDrug.instance.getHandler().getDrugObjects().get(drugname);

                List<String> potsname = new ArrayList<>();
                for(PotionEffectType type : d.getDrugEffectTypes()){
                    potsname.add(type.getName());
                }

                List<String> druglore = Language.getMessageList("druglore");

                druglore.replaceAll(e1 -> e1.replace("%potions%", potsname.toString().replace("[[[", "").replace("]]]", "").replace("[", "")
                        .replace("]", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "").replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace(",", "").replace("0", "").replaceFirst(" ", "")));
                druglore.replaceAll(e1 -> e1.replace("%duration%", "" + d.getDrugDurationInSeconds()));

                p.getInventory().addItem(new ItemBuilder(Material.SLIME_BALL, 1, (short) 0).setDisplayName(d.getName()).setLore(druglore).build());
                p.sendMessage(Language.getMessage("getdrug"));

            }else if(args[0].equalsIgnoreCase("telephone") && args[1].equalsIgnoreCase("accept")){
                if(PlayerInteractListener.waitingforanswer.contains(p.getUniqueId())){

                    PlayerInteractListener.waitingforanswer.remove(p.getUniqueId());
                    DrugHandler.openHandlerMenu(p);

                }else p.sendMessage(Language.getMessage("toolate").replace("%telephone%", "✆"));
            }else p.sendMessage(Language.getMessage("commanddoesntexist"));
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("handler")){

                if(!p.hasPermission("customdrugs.admin.handler") || !p.hasPermission("customdrugs.admin.*") || !p.hasPermission("customdrugs.*")){
                    p.sendMessage(Language.getMessage("noperm"));
                    return false;
                }

                if(!CustomDrug.instance.isCitizensloaded()){
                    p.sendMessage(Language.getMessage("citizensnotloaded"));
                    return false;
                }

                CreateNPC npc = new CreateNPC(CustomDrug.instance.getDrugHandlerName(), p.getLocation(), EntityType.PLAYER);
                npc.changeSkin("gestor");
                npc.spawnNPC();

                p.sendMessage(Language.getMessage("handlerspawned"));
            }else p.sendMessage(Language.getMessage("commanddoesntexist"));
        }else p.sendMessage(Language.getMessage("commanddoesntexist"));
        return false;
    }
    public static void setPlayerDrugCreationInv(Player p){
        p.getInventory().clear();

        List<PotionEffectType> pots = drugInProcess.get(p.getUniqueId()).getDrugEffectTypes();
        List<String> potsname = new ArrayList<>();
        for(PotionEffectType potname : pots){
            potsname.add(potname.getName().replace("[PotionEffectType[1,", "").replace("]]", ""));
        }

        List<String> druglore = Language.getMessageList("currentdruglore");

        druglore.replaceAll(e -> e.replace("%drugname%", drugInProcess.get(p.getUniqueId()).getName()));
        druglore.replaceAll(e -> e.replace("%description%", drugInProcess.get(p.getUniqueId()).getDescription()));
        druglore.replaceAll(e -> e.replace("%potions%", potsname.toString()).replace("[", "").replace("]", ""));
        druglore.replaceAll(e -> e.replace("%duration%", "" + drugInProcess.get(p.getUniqueId()).getDrugDurationInSeconds()));
        druglore.replaceAll(e -> e.replace("%sellprice%", "" + drugInProcess.get(p.getUniqueId()).getSellPrice()));
        druglore.replaceAll(e -> e.replace("%buyprice%", "" + drugInProcess.get(p.getUniqueId()).getBuyPrice()));

        p.getInventory().setItem(0, new ItemBuilder(Material.NAME_TAG, 1, (short) 0).setDisplayName(Language.getMessage("setdescriptionname")).setLore(Language.getMessageList("setdescriptionlore")).build());
        p.getInventory().setItem(1, new ItemBuilder(Material.GOLD_NUGGET, 1, (short) 0).setDisplayName(Language.getMessage("setpricename")).setLore(Language.getMessageList("setpricelore")).build());
        p.getInventory().setItem(2, new ItemBuilder(Material.POTION, 1, (short) 0).setDisplayName(Language.getMessage("setpotionsname")).setLore(Language.getMessageList("setpotionslore")).build());
        p.getInventory().setItem(6, new ItemBuilder(Material.SLIME_BALL, 1, (short) 0).setDisplayName(Language.getMessage("currentdrugname")).setLore(druglore).build());
        p.getInventory().setItem(7, new ItemBuilder(PlayerHead.createIDSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGE5OTM0MmUyYzczYTlmMzgyMjYyOGU3OTY0ODgyMzRmMjU4NDQ2ZjVhMmQ0ZDU5ZGRlNGFhODdkYjk4In19fQ==")).setDisplayName(Language.getMessage("finishname")).setLore(Language.getMessageList("finishlore")).build());
        p.getInventory().setItem(8, new ItemBuilder(Material.BARRIER, 1, (short) 0).setDisplayName(Language.getMessage("cancelname")).setLore(Language.getMessageList("cancellore")).build());
    }
}
