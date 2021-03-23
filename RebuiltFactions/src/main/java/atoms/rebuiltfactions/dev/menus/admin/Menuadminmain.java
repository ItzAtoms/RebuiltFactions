package atoms.rebuiltfactions.dev.menus.admin;

import atoms.rebuiltfactions.dev.menus.admin.world.Menuadminplayer;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;

public class Menuadminmain extends Menu{


    public Menuadminmain(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Available Admin Settings";
    }

    @Override
    public int getMenuSlots() {
        return 9;
    }

    @Override
    public Sound getMenuSound() {
        return Sound.BLOCK_BELL_RESONATE;
    }

    @Override
    public void handle(InventoryClickEvent event) {

        switch (event.getSlot()){
            case 0:
                new Menuadminplayer(this.playerMenuUtility).create();
                break;
            case 2:
                new Menuadminworld(this.playerMenuUtility).create();
                break;
            case 4:
                new Menuadminpunish(this.playerMenuUtility).create();
                break;
            case 6:
                new Menuadminserver(this.playerMenuUtility).create();
                break;
            default:
                break;
        }
    }

    @Override
    public void setMenuItems() {

        this.inventory.setItem(0,ItemUtils.create(Material.DIAMOND_SWORD, "&7Player", Arrays.asList("&7(Click to access)")));
        this.inventory.setItem(2,ItemUtils.create(Material.GRASS_BLOCK, "&7World", Arrays.asList("&7(Click to access)")));
        this.inventory.setItem(4,ItemUtils.create(Material.DIAMOND_AXE, "&7Punish", Arrays.asList("&7(Click to access)")));
        this.inventory.setItem(6,ItemUtils.create(Material.BARRIER, "&7Server", Arrays.asList("&7(Click to access)")));


    }
}
