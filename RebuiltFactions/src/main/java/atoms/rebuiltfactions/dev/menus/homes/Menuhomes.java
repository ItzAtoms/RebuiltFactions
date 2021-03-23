package atoms.rebuiltfactions.dev.menus.homes;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import com.earth2me.essentials.Essentials;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menuhomes extends Menu {

    protected Essentials ess = Essentials.getPlugin(Essentials.class);

    List<String> homes;

    public Menuhomes(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.homes = ess.getUser(this.playerMenuUtility.getOwner()).getHomes();
    }

    @Override
    public String getMenuName() {
        if(homes.isEmpty())
            return "No Homes Available";
        return "Available Homes";
    }

    @Override
    public int getMenuSlots() {
        int size = 9;
        if (homes.size() < 10) {
            size = 9;
        } else if (homes.size() < 19) {
            size = 18;
        } else if (homes.size() < 28) {
            size = 27;
        } else if (homes.size() < 37) {
            size = 36;
        } else if (homes.size() < 46) {
            size = 45;
        } else if (homes.size() > 47 && homes.size() < 57) {
            size = 57;
        }
        return size;
    }

    @Override
    public Sound getMenuSound() {
        return null;
    }

    @Override
    public void handle(InventoryClickEvent event) {
        event.setCancelled(true);
        ItemStack ci = event.getCurrentItem();
        if (event.getClick().isLeftClick() && event.getCurrentItem().getType() != Material.AIR) {
            if (!ci.hasItemMeta() || !ci.getItemMeta().hasLore())
                return;
            String Iname = ChatColor.stripColor(ci.getItemMeta().getDisplayName());
            this.playerMenuUtility.getOwner().performCommand("ehome " + Iname);
            this.playerMenuUtility.getOwner().closeInventory();
        }
        if (event.getClick().isRightClick() && event.getCurrentItem().getType() != Material.AIR) {
            if (!ci.hasItemMeta())
                return;
            String Iname = ChatColor.stripColor(ci.getItemMeta().getDisplayName());
            (new Menuicon(Iname, this.playerMenuUtility)).create();
        }
    }

    @Override
    public void setMenuItems() {
        List<String> lore = Arrays.asList("&7Location: %x%, %y%, %z%", "&7World: %world%", "",
                "&7Left-Click to teleport", "&7Right-Click to edit icon");
        List<String> homes = this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes();
        for (int i = 0; i < homes.size(); i++) {
            String it = null;
            Material itemname = null;
            ItemStack item = null;
            this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes();
            String n = this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes().get(i).toString().substring(0, 1).toUpperCase() + this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes().get(i).toString().substring(1);
            it = RebuiltFactions.idm.getConfig()
                    .getString("Icons." + this.playerMenuUtility.getOwner().getUniqueId() + "." + n);
            if (it != null) {
                itemname = Material.valueOf(it);
                item = new ItemStack(itemname);
            } else {
                item = new ItemStack(Material.RED_BED);
            }
            String home = ((String)this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes().get(i)).toString();
            Location lo = null;
            try {
                lo = this.ess.getUser(this.playerMenuUtility.getOwner()).getHome(home);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<String> lores = new ArrayList<>();
            for (String ls : lore) {
                if (ls != null) {
                    ls = ls.replace("%world%", lo.getWorld().getName());
                    ls = ls.replace("%x%", "" + lo.getBlockX());
                    ls = ls.replace("%y%", "" +lo.getBlockY());
                    ls = ls.replace("%z%", "" +lo.getBlockZ());
                    ls = ColorUtils.color(ls);
                    lores.add(ls);
                    continue;
                }
                lores.add("");
            }
            String hn = this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes().get(i);
            String hname = ChatColor.AQUA + hn.substring(0, 1).toUpperCase() + hn.substring(1);
            ItemStack itm = ItemUtils.create(item.getType(), hname, lores);
            this.inventory.setItem(i, itm);
        }
    }
}
