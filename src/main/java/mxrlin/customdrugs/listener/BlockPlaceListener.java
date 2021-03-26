package mxrlin.customdrugs.listener;

import mxrlin.customdrugs.helper.Language;
import mxrlin.customdrugs.helper.phone.PhoneRinging;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if(e.getBlockPlaced().getType().equals(Material.SKELETON_SKULL)){
            if(p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().equals(Language.getMessage("phonename"))){
                e.setCancelled(true);

                if(PlayerInteractListener.ringing.contains(p.getUniqueId())) return;
                PlayerInteractListener.ringing.add(p.getUniqueId());

                new PhoneRinging().PlayerRingsPhone(p);

            }
        }
    }
}
