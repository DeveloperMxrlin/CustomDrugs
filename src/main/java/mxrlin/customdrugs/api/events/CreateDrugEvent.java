package mxrlin.customdrugs.api.events;

import mxrlin.customdrugs.drugs.Drug;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CreateDrugEvent extends Event implements Cancellable {

    private final Drug drug;
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player creator;
    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public CreateDrugEvent(Drug drug, Player creator){
        this.drug = drug;
        this.creator = creator;
    }

    public Drug getDrug() {
        return drug;
    }

    public Player getCreator() {
        return creator;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
