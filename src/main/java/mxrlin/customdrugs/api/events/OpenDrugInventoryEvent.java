package mxrlin.customdrugs.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OpenDrugInventoryEvent extends Event implements Cancellable {

    private final Player player;
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;

    public OpenDrugInventoryEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        if(cancelled) return true;
        return false;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
