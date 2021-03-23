package atoms.rebuiltfactions.dev.menus.admin.world;

import atoms.rebuiltfactions.dev.menus.punish.Menupunishplayer;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import atoms.rebuiltfactions.dev.utils.player.User;
import com.earth2me.essentials.Essentials;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class Menuadminplayer extends Menu{
    public Menuadminplayer(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Online players";
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
        final Inventory inv;
        final Server server = Bukkit.getServer();
        if(event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE))
            return;
        try{
            Player target = Bukkit.getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            assert target != null;
            if(target.isOp())
                return;
            com.earth2me.essentials.User invUser = Essentials.getPlugin(Essentials.class).getUser(target.getUniqueId());
            com.earth2me.essentials.User user = Essentials.getPlugin(Essentials.class).getUser(this.playerMenuUtility.getOwner().getUniqueId());
            switch (event.getClick()){
                case LEFT:
                    inv = invUser.getBase().getInventory();
                    user.getBase().closeInventory();
                    user.getBase().openInventory(inv);
                    user.setInvSee(true);
                    break;
                case RIGHT:
                    new Menupunishplayer(target, this.playerMenuUtility).create();
                    break;
                case MIDDLE:
                    inv = server.createInventory(invUser.getBase(), 9, "Equipped");
                    inv.setContents(invUser.getBase().getInventory().getArmorContents());
                    user.getBase().closeInventory();
                    user.getBase().openInventory(inv);
                    user.setInvSee(true);
                    break;
                default:
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void setMenuItems() {

        for(Player p : Bukkit.getOnlinePlayers()){
            this.inventory.addItem(ItemUtils.createSkull(p.getName(), Arrays.asList("&7Health: &e" + Math.round(p.getHealth()), "&7EXP: &e" +
                    (new User(p).getTotalExperience()) ,"&7UUID: &e" + p.getUniqueId(), "&7Operator: &e" + p.isOp(), "&7Gamemode: &e" +
                    p.getGameMode(), "", "&7Available Actions", "&7Right-Click: &ePunish", "&7Middle-Click: &eView Equipped Armor", "&7Left-Click: &eView Inventory")));
        }
    }
}
