package atoms.rebuiltfactions.dev.menus.admin.world;

import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryClickEvent;

import atoms.rebuiltfactions.dev.utils.menu.Menu;

import java.util.Arrays;


public class Menuadminworldgamerule extends Menu {
    private World w;
    private String gamerule = null;
    public Menuadminworldgamerule(World w, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.w = w;
    }

    @Override
    public String getMenuName() {
        return "'" + this.w.getName() + "' Gamerules";
    }

    @Override
    public int getMenuSlots() {
        return 54;
    }

    @Override
    public Sound getMenuSound() {
        return null;
    }

    @Override
    public void handle(InventoryClickEvent event) {
            try{
                if(setGamerule(this.gamerule)){
                    this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&7Successfully enabled &e" + this.gamerule));
                    return;
                }
                this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&7Successfully disabled &e" + this.gamerule));
            }catch (Exception e){
                this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&7Could not find gamerule - &e" + this.gamerule));
                return;
            }
    }

    @Override
    public void setMenuItems() {

        for(String s : this.w.getGameRules()){
            this.gamerule = s.toUpperCase();
            if(!w.isGameRule(s)){
                this.inventory.addItem(ItemUtils.create(Material.GREEN_WOOL, "&e" + s.toUpperCase(), Arrays.asList("&7(Click to disable)")));
                continue;
            }
            this.inventory.addItem(ItemUtils.addGlow(ItemUtils.create(Material.RED_WOOL, "&e" + s.toUpperCase(), Arrays.asList("&7(Click to enable)"))));
        }

    }

    public boolean setGamerule(String gamerule) {

        switch (gamerule) {
            case "KEEP_INVENTORY":
                try {
                    this.w.setGameRule(GameRule.KEEP_INVENTORY, true);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            default:
                break;
        }

        return false;
    }
}
