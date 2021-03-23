package atoms.rebuiltfactions.dev.menus.kits;

import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Menukitmain extends Menu {

    public Menukitmain(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Server Kits";
    }

    @Override
    public int getMenuSlots() {
        return 9;
    }

    @Override
    public Sound getMenuSound() {
        return Sound.ENTITY_ENDER_DRAGON_FLAP;
    }

    @Override
    public void handle(InventoryClickEvent event) {

        switch (event.getSlot()) {
            case 2:
                new Menukitrank(playerMenuUtility).create();
                break;
            default:
                break;
        }

    }

    @Override
    public void setMenuItems() {

        inventory.setItem(2, ItemUtils.create(Material.RED_BED, "&e&lServer Kits"));
        inventory.setItem(4, ItemUtils.create(Material.DIAMOND_CHESTPLATE, "&d&lBuffed Kits"));
        inventory.setItem(6, ItemUtils.create(Material.DIAMOND_SWORD, "&c&lAdmin Kits"));
    }
}
