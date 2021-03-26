package mxrlin.customdrugs.api.events;

import mxrlin.customdrugs.drugs.Drug;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SellDrugEvent extends Event implements Cancellable {

    private final Drug drug;
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player seller;
    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public SellDrugEvent(Drug drug, Player seller) {
        this.drug = drug;
        this.seller = seller;
    }

    public Drug getDrug() {
        return drug;
    }

    public Player getSeller() {
        return seller;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
