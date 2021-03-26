package mxrlin.customdrugs.api.events;

import mxrlin.customdrugs.drugs.Drug;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeleteDrugEvent extends Event implements Cancellable {

    private final Drug drug;
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player deletor;
    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public DeleteDrugEvent(Drug drug, Player deletor) {
        this.drug = drug;
        this.deletor = deletor;
    }

    public Drug getDrug() {
        return drug;
    }

    public Player getDeletor() {
        return deletor;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
