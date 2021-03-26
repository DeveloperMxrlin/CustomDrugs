package mxrlin.customdrugs.listener;

import mxrlin.customdrugs.CustomDrug;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreProcessListener implements Listener {
    @EventHandler
    public void onPlayerPreCommand(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String cmd = e.getMessage().replace("/", "");
        String[] args = cmd.split(" ");

        CustomDrug.instance.getAliases().forEach(current ->  {

            if(args[0].equalsIgnoreCase(current)){

                e.setCancelled(true);
                p.performCommand(cmd.replace(current, "customdrugs"));

            }

        });

    }
}
