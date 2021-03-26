package mxrlin.customdrugs.citizens;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class CreateNPC {

    private String name;
    private Location loc;
    private EntityType type;
    private NPC npc;

    public CreateNPC(String name, Location loc, EntityType type) {
        this.name = name;
        this.loc = loc;
        this.type = type;
        this.npc = CitizensAPI.getNPCRegistry().createNPC(this.type, this.name);
    }
    public void spawnNPC(){
        this.npc.spawn(this.loc);
    }
    public void changeSkin(String skinname){
        // set skin properties
        npc.data().setPersistent(NPC.PLAYER_SKIN_UUID_METADATA, skinname);
        npc.data().setPersistent(NPC.PLAYER_SKIN_USE_LATEST, false);

        // send skin change to online players
        Entity npcEntity = npc.getEntity();
        if (npcEntity instanceof SkinnableEntity) {
            ((SkinnableEntity) npcEntity).getSkinTracker().notifySkinChange(npc.data().get(NPC.PLAYER_SKIN_USE_LATEST));
        }
    }
}
