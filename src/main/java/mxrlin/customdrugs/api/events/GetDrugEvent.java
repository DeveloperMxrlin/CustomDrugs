package mxrlin.customdrugs.api.events;

import mxrlin.customdrugs.drugs.Drug;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GetDrugEvent extends Event implements Cancellable {

    private final Player player;
    private final Drug drug;
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;

    public GetDrugEvent(Player player, Drug drug) {
        this.player = player;
        this.drug = drug;
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
