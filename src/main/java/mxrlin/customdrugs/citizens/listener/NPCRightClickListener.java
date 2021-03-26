package mxrlin.customdrugs.citizens.listener;

import mxrlin.customdrugs.CustomDrug;
import mxrlin.customdrugs.drugs.Drug;
import mxrlin.customdrugs.drugs.DrugHandler;
import mxrlin.customdrugs.helper.items.ItemBuilder;
import mxrlin.customdrugs.helper.Language;
import mxrlin.customdrugs.helper.infinv.ScrollerInventory;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NPCRightClickListener implements Listener {
    @EventHandler
    public void onNpcClick(NPCRightClickEvent e){
        Player p = e.getClicker();
        if(e.getNPC().getName().equals(CustomDrug.instance.getDrugHandlerName())){

            DrugHandler.openHandlerMenu(p);
        }
    }
}
