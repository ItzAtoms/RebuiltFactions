package atoms.rebuiltfactions.dev.utils.menu.listener;

import atoms.rebuiltfactions.dev.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import javax.swing.*;

public class MenuListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || event.getSlotType() == InventoryType.SlotType.OUTSIDE)
            return;

        InventoryHolder holder = event.getClickedInventory().getHolder();
        if(holder instanceof Menu){
            event.setCancelled(true);
            Menu menu = (Menu) holder;
            menu.handle(event);
        }

    }
}
