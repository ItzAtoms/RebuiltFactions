package atoms.rebuiltfactions.dev.menus.punish;

import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Objects;

public class Menupunishmain extends Menu {

    public Menupunishmain(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Who shall be punished?";
    }

    @Override
    public int getMenuSlots() {
        return 54;
    }

    @Override
    public Sound getMenuSound() {
        return Sound.ENTITY_ELDER_GUARDIAN_CURSE;
    }

    @Override
    public void handle(InventoryClickEvent event) {
        event.setCancelled(true);
        if(event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE))
            return;

        try {
            Player target = Bukkit.getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            if(target.isOp())
                return;
            new Menupunishplayer(target, this.playerMenuUtility).create();
        }catch (Exception e){
            e.printStackTrace();
            etin.etins("nullPlayer", this.playerMenuUtility.getOwner());
        }
    }

    @Override
    public void setMenuItems() {
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.isOp()){
                this.inventory.addItem(ItemUtils.createSkull("&c&l" + p.getName(), Arrays.asList("&7This player is an admin or", "&7above and no punishments may be", "&7applied to them!"
                , "", "&6&lWhy?", "&7Short answer... they are OP :)")));
                continue;
            }
            this.inventory.addItem(ItemUtils.createSkull("&7" + p.getName(), Arrays.asList("&7Click to view available", "&7punishments.")));
        }

    }
}
