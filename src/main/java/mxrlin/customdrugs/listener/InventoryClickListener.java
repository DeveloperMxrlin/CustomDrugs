package mxrlin.customdrugs.listener;

import mxrlin.customdrugs.CustomDrug;
import mxrlin.customdrugs.commands.DrugCommand;
import mxrlin.customdrugs.drugs.Drug;
import mxrlin.customdrugs.helper.DrugEconomy;
import mxrlin.customdrugs.helper.items.ItemBuilder;
import mxrlin.customdrugs.helper.Language;
import mxrlin.customdrugs.helper.items.PlayerHead;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InventoryClickListener implements Listener {
    public static HashMap<UUID, ItemStack> slot1 = new HashMap<>();
    public static HashMap<UUID, ItemStack> slot2 = new HashMap<>();
    public static HashMap<UUID, ItemStack> slot3 = new HashMap<>();
    static List<UUID> sellbuyinv = new ArrayList<>();
    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null) return;

        if(e.getView().getTitle().equals(Language.getMessage("setpotioninventory"))){
            e.setCancelled(true);
            if(e.getCurrentItem().hasItemMeta() && !e.getCurrentItem().getItemMeta().getDisplayName().equals(" ") && !e.getCurrentItem().getItemMeta().getDisplayName().equals(Language.getMessage("setpotioninvbarriername"))){
                if(!slot1.containsKey(p.getUniqueId())){
                    Inventory inv = e.getClickedInventory();
                    slot1.put(p.getUniqueId(), e.getCurrentItem());
                    inv.setItem(29, e.getCurrentItem());
                }else if(!slot2.containsKey(p.getUniqueId())){
                    if(slot1.get(p.getUniqueId()).equals(e.getCurrentItem())){
                        p.sendMessage(Language.getMessage("canthavesameeffects"));
                        return;
                    }
                    Inventory inv = e.getClickedInventory();
                    slot2.put(p.getUniqueId(), e.getCurrentItem());
                    inv.setItem(31, e.getCurrentItem());
                }else if(!slot3.containsKey(p.getUniqueId())){
                    if(slot1.get(p.getUniqueId()).equals(e.getCurrentItem()) || slot2.get(p.getUniqueId()).equals(e.getCurrentItem())){
                        p.sendMessage(Language.getMessage("canthavesameeffects"));
                        return;
                    }
                    Inventory inv = e.getClickedInventory();
                    slot3.put(p.getUniqueId(), e.getCurrentItem());
                    inv.setItem(33, e.getCurrentItem());
                }else{
                    p.sendMessage(Language.getMessage("alreadythreepots"));
                }
            }
        }else if(e.getView().getTitle().equals(CustomDrug.instance.getDrugHandlerName())){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta()) return;
            String drugname = e.getCurrentItem().getItemMeta().getDisplayName();
            if(CustomDrug.instance.getHandler().drugExists(drugname)){
                Drug d = CustomDrug.instance.getHandler().getDrugObjects().get(drugname);

                List<String> potsname = new ArrayList<>();
                for(PotionEffectType type : d.getDrugEffectTypes()){
                    potsname.add(type.getName());
                }

                List<String> druglore = Language.getMessageList("druglore");

                druglore.replaceAll(e1 -> e1.replace("%potions%", potsname.toString().replace("[[[", "").replace("]]]", "").replace("[", "")
                        .replace("]", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "").replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace(",", "").replace("0", "").replaceFirst(" ", "")));
                druglore.replaceAll(e1 -> e1.replace("%duration%", "" + d.getDrugDurationInSeconds()));

                if(!CustomDrug.instance.isEssentialsloaded()){
                    p.sendMessage(Language.getMessage("essentialsnotloaded"));
                    return;
                }

                if(e.getClick().equals(ClickType.LEFT)){

                    DrugEconomy eco = new DrugEconomy(p, d.getBuyPrice());

                    if(eco.hasEnoughMoney()){

                        p.getInventory().addItem(new ItemBuilder(Material.SLIME_BALL, 1, (short) 0).setDisplayName(d.getName()).setLore(druglore).build());
                        eco.remMoney();
                        p.sendMessage(Language.getMessage("boughtdrug").replace("%buyprice%", d.getBuyPrice() + ""));

                    }else p.sendMessage(Language.getMessage("notenoughmoney"));

                }else if(e.getClick().equals(ClickType.RIGHT)){

                    int slot = 1000;

                    for(int i = 0; i < p.getInventory().getSize(); i++) {
                        if(p.getInventory().getItem(i) != null){
                            if(p.getInventory().getItem(i).hasItemMeta()
                                    && p.getInventory().getItem(i).getItemMeta().getDisplayName().equals(d.getName())
                                    && p.getInventory().getItem(i).getType().equals(Material.SLIME_BALL)
                                    && p.getInventory().getItem(i).getItemMeta().getLore() != null
                                    && p.getInventory().getItem(i).getItemMeta().getLore().equals(druglore)){

                                slot = i;

                            }
                        }

                    }

                    if(slot == 1000){
                        p.sendMessage(Language.getMessage("nodrugitem"));
                        return;
                    }

                    int amt = p.getInventory().getItem(slot).getAmount();
                    ItemStack is = p.getInventory().getItem(slot);

                    p.getInventory().setItem(slot, new ItemBuilder(is).setAmount(amt - 1).build());

                    new DrugEconomy(p, d.getSellPrice()).addMoney();

                    p.sendMessage(Language.getMessage("solddrug").replace("%sellprice%", d.getSellPrice() + ""));

                }



            }
        }else if(e.getView().getTitle().equals(Language.getMessage("setpriceinventoryname"))){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta()) return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(Language.getMessage("selectbuypricename"))){

                /*
                buysellplusminusname: "%+-%%val%" # The Displayname, of the Items in the Buy/Sell Inventory GUI.
# %+-% -> If Player clicks on '+' there will be a '+', same with '-'.
# %val% -> The value on how much the price go up/down. (Can be changed in config.yml)


# The Values on how much the Sell/Buyprice should go down by clicking on it.
minusoption1: 0.1
minusoption2: 1.0
minusoption3: 10.0

# The Values on how much the Sell/Buyprice should go up by clicking on it.
plusoption1: 10.0
plusoption2: 1.0
plusoption3: 0.1
                 */

                setBuySellInv("Buy", p);

            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(Language.getMessage("selectsellpricename"))){

                setBuySellInv("Sell", p);

            }
        }else if(e.getView().getTitle().equals(Language.getMessage("setbuypriceinventoryname"))){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta()) return;
            double i = 0.0D;
            String calcWith = "";
            double current = DrugCommand.drugInProcess.get(p.getUniqueId()).getBuyPrice();
            if(e.getSlot() == 9){
                // minusop1
                i = CustomDrug.instance.getMinusop1();
                calcWith = "-";
            }else if(e.getSlot() == 10){
                // minusop2
                i = CustomDrug.instance.getMinusop2();
                calcWith = "-";
            }else if(e.getSlot() == 11){
                // minusop3
                i = CustomDrug.instance.getMinusop3();
                calcWith = "-";
            }else if(e.getSlot() == 15){
                // plusop1
                i = CustomDrug.instance.getPlusop1();
                calcWith = "+";
            }else if(e.getSlot() == 16){
                // plusop2
                i = CustomDrug.instance.getPlusop2();
                calcWith = "+";
            }else if(e.getSlot() == 17){
                // plusop3
                i = CustomDrug.instance.getPlusop3();
                calcWith = "+";
            }
            double fut = calcWith.equals("-") ? current - i : current + i;
            if(fut <= 0.0D) fut = 0.0D;
            DrugCommand.drugInProcess.get(p.getUniqueId()).setBuyPrice(fut);

            sellbuyinv.add(p.getUniqueId());
            setBuySellInv("Buy", p);
            sellbuyinv.remove(p.getUniqueId());
        }else if(e.getView().getTitle().equals(Language.getMessage("setsellpriceinventoryname"))){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta()) return;
            double i = 0.0D;
            String calcWith = "";
            double current = DrugCommand.drugInProcess.get(p.getUniqueId()).getSellPrice();
            if(e.getSlot() == 9){
                // minusop1
                i = CustomDrug.instance.getMinusop1();
                calcWith = "-";
            }else if(e.getSlot() == 10){
                // minusop2
                i = CustomDrug.instance.getMinusop2();
                calcWith = "-";
            }else if(e.getSlot() == 11){
                // minusop3
                i = CustomDrug.instance.getMinusop3();
                calcWith = "-";
            }else if(e.getSlot() == 15){
                // plusop1
                i = CustomDrug.instance.getPlusop1();
                calcWith = "+";
            }else if(e.getSlot() == 16){
                // plusop2
                i = CustomDrug.instance.getPlusop2();
                calcWith = "+";
            }else if(e.getSlot() == 17){
                // plusop3
                i = CustomDrug.instance.getPlusop3();
                calcWith = "+";
            }
            double fut = calcWith.equals("-") ? current - i : current + i;
            if(fut <= 0.0D) fut = 0.0D;
            DrugCommand.drugInProcess.get(p.getUniqueId()).setSellPrice(fut);

            sellbuyinv.add(p.getUniqueId());
            setBuySellInv("Sell", p);
            sellbuyinv.remove(p.getUniqueId());
        }
    }
    private void setBuySellInv(String s, Player p){
        Inventory inv = Bukkit.createInventory(null, 9*3, Language.getMessage("set" + (s.equalsIgnoreCase("Sell") ? "sell" : "buy") + "priceinventoryname"));

        for(int i = 0; i < inv.getSize(); i++){
            inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (short) 0).setDisplayName(" ").build());
        }

        inv.setItem(9, new ItemBuilder(PlayerHead.createIDSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJjYmRjOWQ0YzU5MGVhYzI4NWE0NTQ0ZjJiMWUwNjhiZDI3ZmQ1MjE3M2FjOGQ3Njc5MDEzODIzY2JhYjk1YSJ9fX0=="))
                .setDisplayName(Language.getMessage("buysellplusminusname").replace("%+-%", "§c-").replace("%val%", CustomDrug.instance.getMinusop1() + "")).build());
        inv.setItem(10, new ItemBuilder(PlayerHead.createIDSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0=="))
                .setDisplayName(Language.getMessage("buysellplusminusname").replace("%+-%", "§c-").replace("%val%", CustomDrug.instance.getMinusop2() + "")).build());
        inv.setItem(11, new ItemBuilder(PlayerHead.createIDSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJjYmRjOWQ0YzU5MGVhYzI4NWE0NTQ0ZjJiMWUwNjhiZDI3ZmQ1MjE3M2FjOGQ3Njc5MDEzODIzY2JhYjk1YSJ9fX0=="))
                .setDisplayName(Language.getMessage("buysellplusminusname").replace("%+-%", "§c-").replace("%val%", CustomDrug.instance.getMinusop3() + "")).build());

        inv.setItem(13, new ItemBuilder(Material.GOLD_NUGGET, 1, (short) 0).setDisplayName(Language.getMessage("currentpricename").replace("%sellbuy%", s).replace("%current%", s.equalsIgnoreCase("Sell") ? DrugCommand.drugInProcess.get(p.getUniqueId()).getSellPrice() + "" : DrugCommand.drugInProcess.get(p.getUniqueId()).getBuyPrice() + "")).build());

        inv.setItem(15, new ItemBuilder(PlayerHead.createIDSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZmMzE0MzFkNjQ1ODdmZjZlZjk4YzA2NzU4MTA2ODFmOGMxM2JmOTZmNTFkOWNiMDdlZDc4NTJiMmZmZDEifX19"))
                .setDisplayName(Language.getMessage("buysellplusminusname").replace("%+-%", "§a+").replace("%val%", CustomDrug.instance.getPlusop1() + "")).build());
        inv.setItem(16, new ItemBuilder(PlayerHead.createIDSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19"))
                .setDisplayName(Language.getMessage("buysellplusminusname").replace("%+-%", "§a+").replace("%val%", CustomDrug.instance.getPlusop2() + "")).build());
        inv.setItem(17, new ItemBuilder(PlayerHead.createIDSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZmMzE0MzFkNjQ1ODdmZjZlZjk4YzA2NzU4MTA2ODFmOGMxM2JmOTZmNTFkOWNiMDdlZDc4NTJiMmZmZDEifX19"))
                .setDisplayName(Language.getMessage("buysellplusminusname").replace("%+-%", "§a+").replace("%val%", CustomDrug.instance.getPlusop3() + "")).build());

        p.openInventory(inv);
    }
}
