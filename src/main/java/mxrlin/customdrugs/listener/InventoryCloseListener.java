package mxrlin.customdrugs.listener;

import mxrlin.customdrugs.commands.DrugCommand;
import mxrlin.customdrugs.drugs.Drug;
import mxrlin.customdrugs.helper.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
        if(!(e.getPlayer() instanceof Player)) return;
        Player p = (Player) e.getPlayer();
        if(e.getView().getTitle().equals(Language.getMessage("setpotioninventory"))){
            if(InventoryClickListener.slot1.containsKey(p.getUniqueId())){
                String name = DrugCommand.drugInProcess.get(p.getUniqueId()).getName();
                String desc = DrugCommand.drugInProcess.get(p.getUniqueId()).getDescription();
                int seconds = DrugCommand.drugInProcess.get(p.getUniqueId()).getDrugDurationInSeconds();
                double sellprice = DrugCommand.drugInProcess.get(p.getUniqueId()).getSellPrice();
                double buyprice = DrugCommand.drugInProcess.get(p.getUniqueId()).getBuyPrice();

                ItemStack slot1 = InventoryClickListener.slot1.get(p.getUniqueId());
                ItemStack slot2 = InventoryClickListener.slot2.get(p.getUniqueId()) != null ? InventoryClickListener.slot2.get(p.getUniqueId()) : null;
                ItemStack slot3 = InventoryClickListener.slot3.get(p.getUniqueId()) != null ? InventoryClickListener.slot3.get(p.getUniqueId()) : null;

                ItemStack[] is = {slot1, slot2, slot3};

                List<PotionEffectType> potions = a(is);

                PlayerInteractListener.desc.remove(p.getUniqueId());
                DrugCommand.drugInProcess.remove(p.getUniqueId());
                DrugCommand.drugInProcess.put(p.getUniqueId(), new Drug(name, desc, potions, seconds, sellprice, buyprice));

                p.sendMessage(Language.getMessage("setpotion"));

                DrugCommand.setPlayerDrugCreationInv(p);
            }
            InventoryClickListener.slot1.remove(p.getUniqueId());
            InventoryClickListener.slot2.remove(p.getUniqueId());
            InventoryClickListener.slot3.remove(p.getUniqueId());
        }else if(e.getView().getTitle().equals(Language.getMessage("setsellpriceinventoryname"))){
            if(InventoryClickListener.sellbuyinv.contains(p.getUniqueId())) return;
            p.sendMessage(Language.getMessage("setsellprice"));
            DrugCommand.setPlayerDrugCreationInv(p);
        }else if(e.getView().getTitle().equals(Language.getMessage("setbuypriceinventoryname"))){
            if(InventoryClickListener.sellbuyinv.contains(p.getUniqueId())) return;
            p.sendMessage(Language.getMessage("setbuyprice"));
            DrugCommand.setPlayerDrugCreationInv(p);
        }
    }
    private List<PotionEffectType> a(ItemStack[] is){
        List<PotionEffectType> potions = new ArrayList<>();
        for(int i = 0; i < is.length; i++){
            if(is[i] == null){

            }else{
                switch (is[i].getType()){
                    case GOLDEN_APPLE:
                        potions.add(PotionEffectType.ABSORPTION);
                        break;
                    case BLACK_WOOL:
                        potions.add(PotionEffectType.BLINDNESS);
                        break;
                    case DIAMOND_PICKAXE:
                        potions.add(PotionEffectType.FAST_DIGGING);
                        break;
                    case FLINT_AND_STEEL:
                        potions.add(PotionEffectType.FIRE_RESISTANCE);
                        break;
                    case ROTTEN_FLESH:
                        potions.add(PotionEffectType.HUNGER);
                        break;
                    case IRON_SWORD:
                        potions.add(PotionEffectType.INCREASE_DAMAGE);
                        break;
                    case GLASS:
                        potions.add(PotionEffectType.INVISIBILITY);
                        break;
                    case IRON_BOOTS:
                        potions.add(PotionEffectType.JUMP);
                        break;
                    case ENDER_EYE:
                        potions.add(PotionEffectType.NIGHT_VISION);
                        break;
                    case WOODEN_PICKAXE:
                        potions.add(PotionEffectType.SLOW_DIGGING);
                        break;
                    case POTION:
                        if(is[i].hasItemMeta() && is[i].getItemMeta().getDisplayName().equals("Instant Health")){
                            potions.add(PotionEffectType.HEAL);
                        }else if(is[i].hasItemMeta() && is[i].getItemMeta().getDisplayName().equals("Poison")){
                            potions.add(PotionEffectType.POISON);
                        }else if(is[i].hasItemMeta() && is[i].getItemMeta().getDisplayName().equals("Regeneration")){
                            potions.add(PotionEffectType.REGENERATION);
                        }else if(is[i].hasItemMeta() && is[i].getItemMeta().getDisplayName().equals("Slowness")){
                            potions.add(PotionEffectType.SLOW);
                        }else if(is[i].hasItemMeta() && is[i].getItemMeta().getDisplayName().equals("Speed")){
                            potions.add(PotionEffectType.SPEED);
                        }else if(is[i].hasItemMeta() && is[i].getItemMeta().getDisplayName().equals("Water Breathing")){
                            potions.add(PotionEffectType.WATER_BREATHING);
                        }else if(is[i].hasItemMeta() && is[i].getItemMeta().getDisplayName().equals("Weakness")){
                            potions.add(PotionEffectType.WEAKNESS);
                        }
                        break;
                }
            }
        }
        return potions;
    }
}
