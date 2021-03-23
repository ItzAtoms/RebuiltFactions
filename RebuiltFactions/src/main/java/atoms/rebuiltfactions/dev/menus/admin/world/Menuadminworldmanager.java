package atoms.rebuiltfactions.dev.menus.admin.world;

import atoms.rebuiltfactions.dev.menus.admin.Menuadminworld;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;


public class Menuadminworldmanager extends Menu {

    private World w;

    public Menuadminworldmanager(World w, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.w = w;
    }

    @Override
    public String getMenuName() {
        return "'" + this.w.getName() + "' Settings...";
    }

    @Override
    public int getMenuSlots() {
        return 9;
    }

    @Override
    public Sound getMenuSound() {
        if (this.w.getName().endsWith("_end")) {
            return Sound.ENTITY_ENDER_DRAGON_GROWL;
        } else if (this.w.getName().endsWith("_nether")) {
            return Sound.ENTITY_BLAZE_DEATH;
        }
        return Sound.BLOCK_GRASS_BREAK;
    }

    @Override
    public void handle(InventoryClickEvent event) {

        switch (event.getSlot()){
            case 0:
                new Menuadminworldgamerule(this.w, this.playerMenuUtility).create();
                break;
            default:
                break;
        }

    }

    @Override
    public void setMenuItems() {

        this.inventory.addItem(ItemUtils.create(Material.PAPER, "&a&oGamerule", Arrays.asList("&8(&c&l!&8) &4&lBETA! NOT FOR COMMON USE.", "", "&7Who may use this?", "&4&lDeveloper")));

    }

}
