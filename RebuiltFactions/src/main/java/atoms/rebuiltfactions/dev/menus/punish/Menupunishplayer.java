package atoms.rebuiltfactions.dev.menus.punish;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

public class Menupunishplayer extends Menu {

    RebuiltFactions plugin = RebuiltFactions.getPlugin(RebuiltFactions.class);

    private Player player;

    public Menupunishplayer(Player player, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.player = player;
    }

    @Override
    public String getMenuName() {
        return "Punish " + player.getName();
    }

    @Override
    public int getMenuSlots() {
        return 27;
    }

    @Override
    public Sound getMenuSound() {
        return null;
    }

    @Override
    public void handle(InventoryClickEvent event) {

        if(InventoryAction.MOVE_TO_OTHER_INVENTORY == event.getAction())
            event.setCancelled(true);

        switch (event.getSlot()) {
            case 0:
                etin.etin("warn", this.player, "Chat Spamming");
                break;
            case 9:
                etin.etin("warn", this.player, "Foul Language");
                break;
            case 18:
                etin.etins("warn", this.player);
                break;
            case 2:
                etin.kick("Warning Limit Exceed", this.player, this.playerMenuUtility.getOwner());
                break;
            case 11:
                etin.kick("Disrespect", this.player, this.playerMenuUtility.getOwner());
                break;
            case 20:
                etin.kick("Unknown", this.player, this.playerMenuUtility.getOwner());
                break;
            case 4:
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tempban " + this.player.getName() + " 1d Warning Limit Over-Exceed");
                etin.etin("playerBan", this.player, this.playerMenuUtility.getOwner());
                break;
            case 13:
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tempban " + this.player.getName() + " 3d XRay - (Ore XRAY, Material XRAY)");
                etin.etin("playerBan", this.player, this.playerMenuUtility.getOwner());
                break;
            case 22:
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tempban " + this.player.getName() + " 7d PvP - Hacks (KillAura, Reach)");
                etin.etin("playerBan", this.player, this.playerMenuUtility.getOwner());
                break;
            case 26:
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "ban " + this.player.getName() + " Perm");
                break;
            case 8:
                new Menupunishmain(this.playerMenuUtility).create();
            default:
                break;
        }
        new Menupunishmain(this.playerMenuUtility);
    }

    @Override
    public void setMenuItems() {
        this.inventory.setItem(0, ItemUtils.create(Material.BARRIER, "&fWarn", Arrays.asList("&7Reason: &eChat Spamming")));
        this.inventory.setItem(9, ItemUtils.create(Material.BARRIER, "&fWarn", Arrays.asList("&7Reason: &eFoul Language")));
        this.inventory.setItem(18, ItemUtils.create(Material.BARRIER, "&fWarn", Arrays.asList("&7Reason: &eNo Reason")));
        this.inventory.setItem(2, ItemUtils.create(Material.FEATHER, "&fKick", Arrays.asList("&7Reason: &eWarning Limit Exceed")));
        this.inventory.setItem(11, ItemUtils.create(Material.FEATHER, "&fKick", Arrays.asList("&7Reason: &eDisrespect")));
        this.inventory.setItem(20, ItemUtils.create(Material.FEATHER, "&fKick", Arrays.asList("&7Reason: &eUnknown")));
        this.inventory.setItem(4, ItemUtils.create(Material.WOODEN_AXE, "&cTempban", Arrays.asList("&7Reason: &eWarning Limit Over-Exceed", "&7Duration: &e1d")));
        this.inventory.setItem(13, ItemUtils.create(Material.WOODEN_AXE, "&cTempban", Arrays.asList("&7Reason: &eXRay - (Ore XRAY, Material XRAY)", "&7Duration: &e3d")));
        this.inventory.setItem(22, ItemUtils.create(Material.WOODEN_AXE, "&cTempban", Arrays.asList("&7Reason: &ePvP - Hacks (KillAura, Reach)", "&7Duration: &e7d")));
        this.inventory.setItem(8, ItemUtils.create(Material.ARROW, "&cGo Back", Arrays.asList("&7Return to previous menu.")));
        this.inventory.setItem(26, ItemUtils.create(Material.DIAMOND_AXE, "&4Perm Ban", Arrays.asList("&7Reason: &eNo Reason")));
    }
}
