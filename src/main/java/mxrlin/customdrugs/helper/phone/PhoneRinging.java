package mxrlin.customdrugs.helper.phone;

import mxrlin.customdrugs.CustomDrug;
import mxrlin.customdrugs.helper.Language;
import mxrlin.customdrugs.listener.PlayerInteractListener;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PhoneRinging {
    int task;
    int count = 5;
    int playerringstask;
    int playerringscount = 3;
    private void PhoneRing(Player p){
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(CustomDrug.instance, () -> {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 0.01f);
            if(count == 0){
                count = 5;
                Bukkit.getScheduler().cancelTask(task);
            }
            count--;
        }, 0,2);
    }
    public void PlayerRingsPhone(Player p){
        playerringstask = Bukkit.getScheduler().scheduleSyncRepeatingTask(CustomDrug.instance, () -> {
            if(playerringscount == 0){
                playerringscount = 3;
                Bukkit.getScheduler().cancelTask(playerringstask);
                PlayerInteractListener.ringing.remove(p.getUniqueId());
                List<String> answer = Language.getMessageList("phoneanswers");
                int i = new Random().nextInt(answer.size());
                TextComponent text = new net.md_5.bungee.api.chat.TextComponent();
                text.setText(answer.get(i).replace("%telephone%", "✆"));
                TextComponent extra = new TextComponent();
                extra.setText(" §2§l[OPEN MENU]");
                extra.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to open the Menu!").create()));
                PlayerInteractListener.waitingforanswer.add(p.getUniqueId());
                extra.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/customdrugs telephone accept"));
                text.addExtra(extra);
                p.spigot().sendMessage(text);
                schedulerAnswer(p);
            }else if(playerringscount == 3){
                p.sendMessage("§e✆ Ring...");
                PhoneRing(p);
            }else if(playerringscount == 2){
                p.sendMessage("§e✆ Ring... Ring...");
                PhoneRing(p);
            }else if(playerringscount == 1){
                p.sendMessage("§e✆ Ring... Ring... Ring...");
                PhoneRing(p);
            }
            playerringscount--;
        }, 0, 20);
    }
    private void schedulerAnswer(Player p){
        Bukkit.getScheduler().scheduleSyncDelayedTask(CustomDrug.instance, () -> {
            PlayerInteractListener.waitingforanswer.remove(p.getUniqueId());
        }, CustomDrug.instance.getWaitphone() * 20L);
    }
}
