package atoms.rebuiltfactions.dev.menus.admin;

import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.logging.Level;

public class Menuadminserver extends Menu {

    public Menuadminserver(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Please select...";
    }

    @Override
    public int getMenuSlots() {
        return 9;
    }

    @Override
    public Sound getMenuSound() {
        return null;
    }

    @Override
    public void handle(InventoryClickEvent event) {

        switch (event.getSlot()) {
            case 3:
                etin.maintenance();
                new Menuadminserver(this.playerMenuUtility).create();
                break;
            case 5:
                this.playerMenuUtility.getOwner().getServer().getLogger().log(Level.SEVERE, "[" + this.playerMenuUtility.getOwner().getUniqueId() + "]" +this.playerMenuUtility.getOwner() + " dispatched command 'restart' from admin menu.");
                etin.restart();
                break;
            default:
                break;
        }
    }

    @Override
    public void setMenuItems() {
        boolean isWhitelisted = this.playerMenuUtility.getOwner().getServer().hasWhitelist();
        this.inventory.setItem(3, ItemUtils.addGlow(ItemUtils.create(Material.RED_WOOL, "&7Disable Maintenance Mode", Arrays.asList("&7(Click to disable)"))));
        if (!isWhitelisted) {
            this.inventory.setItem(3, ItemUtils.create(Material.GREEN_WOOL, "&7Enable Maintenance Mode", Arrays.asList("&7(Click to enable)")));
        }
        this.inventory.setItem(5, ItemUtils.create(Material.TOTEM_OF_UNDYING, "&7Restart Server", Arrays.asList("&7(Click to run)", "&8(&c&l!&8) &4&lBETA! DEV USE ONLY!")));
    }
}
