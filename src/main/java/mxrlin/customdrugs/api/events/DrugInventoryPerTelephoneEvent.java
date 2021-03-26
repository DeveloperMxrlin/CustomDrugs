package mxrlin.customdrugs.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class DrugInventoryPerTelephoneEvent extends OpenDrugInventoryEvent{
    private final Player player;
    private static final HandlerList HANDLERS = new HandlerList();
    public DrugInventoryPerTelephoneEvent(Player player) {
        super(player);
        this.player = player;
    }

    @Override
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
}
