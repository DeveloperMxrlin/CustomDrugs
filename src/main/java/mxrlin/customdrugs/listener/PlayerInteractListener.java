package mxrlin.customdrugs.listener;

import mxrlin.customdrugs.CustomDrug;
import mxrlin.customdrugs.api.events.CreateDrugEvent;
import mxrlin.customdrugs.commands.DrugCommand;
import mxrlin.customdrugs.drugs.Drug;
import mxrlin.customdrugs.helper.items.ItemBuilder;
import mxrlin.customdrugs.helper.Language;
import mxrlin.customdrugs.helper.phone.PhoneRinging;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInteractListener implements Listener {
    public static List<UUID> desc = new ArrayList<>();
    public static List<UUID> ringing = new ArrayList<>();
    public static List<UUID> waitingforanswer = new ArrayList<>();
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getItem() == null) return;
        if(!e.getItem().hasItemMeta()) return;
        if(DrugCommand.drugInProcess.containsKey(p.getUniqueId())){
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                if(e.getItem() != null && e.getItem().hasItemMeta()){
                    if(e.getItem().getItemMeta().getDisplayName().equals(Language.getMessage("setdescriptionname"))){
                        if(desc.contains(p.getUniqueId())){
                            p.sendMessage(Language.getMessage("cancelddescription"));
                            desc.remove(p.getUniqueId());
                            return;
                        }
                        p.sendMessage(Language.getMessage("setdescriptionnow"));
                        desc.add(p.getUniqueId());
                    }else if(e.getItem().getItemMeta().getDisplayName().equals(Language.getMessage("setpotionsname"))){

                        if(desc.contains(p.getUniqueId())){
                            p.sendMessage(Language.getMessage("cantchangeother"));
                            return;
                        }

                        Inventory inventory = Bukkit.createInventory(null, 9*4, Language.getMessage("setpotioninventory"));
                        /*
                        ABSORPTION
                        BLINDNESS
                        CONFUSION N
                        DAMAGE_RESISTANCE (N)
                        FAST_DIGGING
                        FIRE_RESISTANCE
                        HARM N
                        HEAL
                        HEALTH_BOOST N
                        HUNGER
                        INCREASE_DAMAGE
                        INVISIBILITY
                        JUMP
                        NIGHT_VISION
                        POISON
                        REGENERATION
                        SATURATION N
                        SLOW
                        SLOW_DIGGING
                        SPEED
                        WATER_BREATHING
                        WEAKNESS
                        WITHER
                         */

                        for(int i = 0; i < inventory.getSize(); i++){
                            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (short) 15).setDisplayName(" ").build());
                        }

                        inventory.setItem(0, new ItemBuilder(Material.GOLDEN_APPLE, 1, (short) 0).setDisplayName("Absorption").build());
                        inventory.setItem(1, new ItemBuilder(Material.BLACK_WOOL, 1, (short) 0).setDisplayName("Blindness").build());
                        inventory.setItem(2, new ItemBuilder(Material.DIAMOND_PICKAXE, 1, (short) 0).setDisplayName("Haste").build());
                        inventory.setItem(3, new ItemBuilder(Material.FLINT_AND_STEEL, 1, (short) 0).setDisplayName("Fire Resistance").build());
                        inventory.setItem(14, new ItemBuilder(Material.POTION, 1, (short) 8261).setDisplayName("Instant Health").hideEnchs().build());
                        inventory.setItem(6, new ItemBuilder(Material.ROTTEN_FLESH, 1, (short) 0).setDisplayName("Hunger").build());
                        inventory.setItem(7, new ItemBuilder(Material.IRON_SWORD, 1, (short) 0).setDisplayName("Strength").build());
                        inventory.setItem(8, new ItemBuilder(Material.GLASS, 1, (short) 0).setDisplayName("Invisibility").build());
                        inventory.setItem(9, new ItemBuilder(Material.IRON_BOOTS, 1, (short) 0).setDisplayName("Jump Boost").build());
                        inventory.setItem(10, new ItemBuilder(Material.ENDER_EYE, 1, (short) 0).setDisplayName("Night Vision").build());
                        inventory.setItem(11, new ItemBuilder(Material.POTION, 1, (short) 16388).setDisplayName("Poison").hideEnchs().build());
                        inventory.setItem(12, new ItemBuilder(Material.POTION, 1, (short) 8257).setDisplayName("Regeneration").hideEnchs().build());
                        inventory.setItem(4, new ItemBuilder(Material.POTION, 1, (short) 8234).setDisplayName("Slowness").hideEnchs().build());
                        inventory.setItem(5, new ItemBuilder(Material.WOODEN_PICKAXE, 1, (short) 0).setDisplayName("Slow Digging").build());
                        inventory.setItem(15, new ItemBuilder(Material.POTION, 1, (short) 8194).setDisplayName("Speed").hideEnchs().build());
                        inventory.setItem(16, new ItemBuilder(Material.POTION, 1, (short) 8237).setDisplayName("Water Breathing").hideEnchs().build());
                        inventory.setItem(17, new ItemBuilder(Material.POTION, 1, (short) 8232).setDisplayName("Weakness").hideEnchs().build());


                        inventory.setItem(29, new ItemBuilder(Material.BARRIER, 1, (short) 0).setDisplayName(Language.getMessage("setpotioninvbarriername")).setLore(Language.getMessageList("setpotioninvbarrierlore")).build());
                        inventory.setItem(31, new ItemBuilder(Material.BARRIER, 1, (short) 0).setDisplayName(Language.getMessage("setpotioninvbarriername")).setLore(Language.getMessageList("setpotioninvbarrierlore")).build());
                        inventory.setItem(33, new ItemBuilder(Material.BARRIER, 1, (short) 0).setDisplayName(Language.getMessage("setpotioninvbarriername")).setLore(Language.getMessageList("setpotioninvbarrierlore")).build());

                        p.openInventory(inventory);
                    }else if(e.getItem().getItemMeta().getDisplayName().equals(Language.getMessage("finishname"))){

                        if(desc.contains(p.getUniqueId())){
                            p.sendMessage(Language.getMessage("cantchangeother"));
                            return;
                        }

                        if(CustomDrug.instance.getHandler().drugExists(DrugCommand.drugInProcess.get(p.getUniqueId()).getName())){
                            p.sendMessage(Language.getMessage("drugexist"));
                            return;
                        }

                        if(DrugCommand.drugInProcess.get(p.getUniqueId()) == null) return;

                        Drug d = DrugCommand.drugInProcess.get(p.getUniqueId());

                        DrugCommand.drugInProcess.remove(p.getUniqueId());
                        CreateDrugEvent event = new CreateDrugEvent(d, p);
                        Bukkit.getPluginManager().callEvent(event);
                        if(event.isCancelled()) return;

                        CustomDrug.instance.getHandler().createNewDrug(d.getName(), d.getDrugEffectTypes(), d.getDrugDurationInSeconds(), d.getDescription(), d.getSellPrice(), d.getBuyPrice());
                        p.sendMessage(Language.getMessage("finishcreation"));

                        Bukkit.getScheduler().scheduleSyncDelayedTask(CustomDrug.instance, () -> p.getInventory().clear(), 2);
                    }else if(e.getItem().getItemMeta().getDisplayName().equals(Language.getMessage("cancelname"))){

                        if(desc.contains(p.getUniqueId())){
                            p.sendMessage(Language.getMessage("cantchangeother"));
                            return;
                        }

                        p.sendMessage(Language.getMessage("caneldcreation"));
                        DrugCommand.drugInProcess.remove(p.getUniqueId());
                        Bukkit.getScheduler().scheduleSyncDelayedTask(CustomDrug.instance, () -> p.getInventory().clear(), 2);
                    }else if(e.getItem().getItemMeta().getDisplayName().equals(Language.getMessage("setpricename"))){

                        Inventory inv = Bukkit.createInventory(null, 9*3, Language.getMessage("setpriceinventoryname"));

                        for(int i = 0; i < inv.getSize(); i++){
                            inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (short) 15).setDisplayName(" ").build());
                        }

                        inv.setItem(11, new ItemBuilder(Material.LIME_CONCRETE, 1, (short) 0).setDisplayName(Language.getMessage("selectbuypricename")).setLore(Language.getMessageList("selectbuypricelore")).build());
                        inv.setItem(15, new ItemBuilder(Material.RED_CONCRETE, 1, (short) 0).setDisplayName(Language.getMessage("selectsellpricename")).setLore(Language.getMessageList("selectsellpricelore")).build());

                        p.openInventory(inv);

                    }
                }
            }
        }else if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals(Language.getMessage("phonename"))){

            if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){

                if(ringing.contains(p.getUniqueId())) return;
                ringing.add(p.getUniqueId());

                new PhoneRinging().PlayerRingsPhone(p);

            }

        }else if(CustomDrug.instance.getHandler().getDrugObjects().get(e.getItem().getItemMeta().getDisplayName()) != null){

            Drug d = CustomDrug.instance.getHandler().getDrugObjects().get(e.getItem().getItemMeta().getDisplayName());
            List<PotionEffectType> types = d.getDrugEffectTypes();

            for(PotionEffectType type : types){
                PotionEffect pot = type.createEffect((d.getDrugDurationInSeconds() * 20), 1);
                pot.apply(p);
            }

            int slot = p.getInventory().getHeldItemSlot();
            int amt = p.getItemInHand().getAmount();

            p.getInventory().setItem(slot, new ItemBuilder(p.getItemInHand()).setAmount(amt - 1).build());

            p.sendMessage(Language.getMessage("applieddrug"));
        }
    }
}
