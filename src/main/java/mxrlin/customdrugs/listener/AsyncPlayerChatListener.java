package mxrlin.customdrugs.listener;

import mxrlin.customdrugs.commands.DrugCommand;
import mxrlin.customdrugs.drugs.Drug;
import mxrlin.customdrugs.helper.Language;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AsyncPlayerChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(PlayerInteractListener.desc.contains(p.getUniqueId()) && DrugCommand.drugInProcess.containsKey(p.getUniqueId())){
            e.setCancelled(true);

            if(e.getMessage().length() > 24){
                p.sendMessage(Language.getMessage("tolongdescription"));
                return;
            }

            String name = DrugCommand.drugInProcess.get(p.getUniqueId()).getName();
            String desc = e.getMessage();
            int seconds = DrugCommand.drugInProcess.get(p.getUniqueId()).getDrugDurationInSeconds();
            double sellprice = DrugCommand.drugInProcess.get(p.getUniqueId()).getSellPrice();
            double buyprice = DrugCommand.drugInProcess.get(p.getUniqueId()).getBuyPrice();
            List<PotionEffectType> potions = DrugCommand.drugInProcess.get(p.getUniqueId()).getDrugEffectTypes();

            PlayerInteractListener.desc.remove(p.getUniqueId());
            DrugCommand.drugInProcess.remove(p.getUniqueId());
            DrugCommand.drugInProcess.put(p.getUniqueId(), new Drug(name, desc, potions, seconds, sellprice, buyprice));

            p.sendMessage(Language.getMessage("setdescription"));

            DrugCommand.setPlayerDrugCreationInv(p);
        }
    }

}
