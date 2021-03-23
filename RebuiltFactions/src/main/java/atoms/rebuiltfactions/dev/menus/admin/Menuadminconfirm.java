package atoms.rebuiltfactions.dev.menus.admin;

import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.*;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.apache.commons.io.FileUtils;
import org.bukkit.util.FileUtil;

import java.io.File;

public class Menuadminconfirm extends Menu {

    World world;

    boolean isWorld = false;

    boolean isNull = false;

    public Menuadminconfirm(Object object, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        if(object instanceof World){
            this.isWorld = true;
            this.world = (World)object;
        }

    }

    @Override
    public String getMenuName() {

        if(isWorld)
            return "Confirm '" + this.world.getName() + "' deletion action.";
        this.isNull = true;
        return "You're not supposed to see this!";
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
        if(isWorld){
            switch(event.getSlot()){
                case 3:
                    try {
                        this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&7Attempting world deletion for world: &e" + world.getName()));
                        try{
                            Bukkit.unloadWorld(this.world, false);
                            FileUtils.deleteDirectory(new File(String.valueOf(this.world.getWorldFolder())));
                        }catch(Exception ignored){
                            return;
                        }
                        this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&aSuccessfully deleted world!"));
                        new Menuadminworld(this.playerMenuUtility).create();
                    }catch (Exception e){
                        this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&7Failed to delete world: &e" + world.getName()));
                        new Menuadminworld(this.playerMenuUtility).create();
                        break;
                    }
                    break;
                case 5:
                    new Menuadminworld(this.playerMenuUtility).create();
                    break;
            }
        }
    }

    @Override
    public void setMenuItems() {
        if(isNull) {
            this.playerMenuUtility.getOwner().closeInventory();
            etin.etin("inventoryError", this.playerMenuUtility.getOwner(), "[atoms.rebuiltfactions.menus.menuadminworld.world(invalidWorld:" + this.world.getName() + ")]");
            return;
        }
        this.inventory.setItem(3, ItemUtils.addGlow(ItemUtils.create(Material.GREEN_WOOL, "&a&lCONFIRM")));
        this.inventory.setItem(5, ItemUtils.create(Material.RED_WOOL, "&c&lCANCEL"));
    }
}
