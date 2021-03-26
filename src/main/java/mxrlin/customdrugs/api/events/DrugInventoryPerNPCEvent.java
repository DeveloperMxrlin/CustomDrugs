package mxrlin.customdrugs.api.events;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class DrugInventoryPerNPCEvent extends OpenDrugInventoryEvent{

    private final Player player;
    private final NPC npc;
    private static final HandlerList HANDLERS = new HandlerList();

    public DrugInventoryPerNPCEvent(Player player, NPC npc) {
        super(player);
        this.player = player;
        this.npc = npc;
    }

    public NPC getNpc() {
        return npc;
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
