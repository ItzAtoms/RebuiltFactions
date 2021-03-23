package atoms.rebuiltfactions.dev.menus.admin;

import atoms.rebuiltfactions.dev.menus.punish.Menupunishbannedplayers;
import atoms.rebuiltfactions.dev.menus.punish.Menupunishmain;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

public class Menuadminpunish extends Menu{

    public Menuadminpunish(PlayerMenuUtility playerMenuUtility) {

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
                new Menupunishmain(this.playerMenuUtility).create();
                break;
            case 5:
                new Menupunishbannedplayers(this.playerMenuUtility).create();
                break;
            default:
                break;
        }
    }

    @Override
    public void setMenuItems() {

        this.inventory.setItem(3, ItemUtils.create(Material.DIAMOND_AXE, "&7Punish an Online Player", Arrays.asList("&7(Click to access)")));
        this.inventory.setItem(5, ItemUtils.create(Material.WOODEN_AXE, "&7View punished players.", Arrays.asList("&7(Click to access)")));
    }
}