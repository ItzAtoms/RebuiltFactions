package atoms.rebuiltfactions.dev.menus.homes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import com.earth2me.essentials.Essentials;
import org.bukkit.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Menudeletehomes extends Menu {
    Essentials ess = (Essentials) Essentials.getProvidingPlugin(Essentials.class);

    public Menudeletehomes(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public String getMenuName() {
        String name = "Available Homes to Delete";
        List<String> homes = this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes();
        if (homes.isEmpty() || homes == null)
            name = "No Homes Available to Delete";
        return name;
    }

    public int getMenuSlots() {
        int size = 9;
        List<String> homes = this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes();
        if (homes.size() < 10) {
            size = 9;
        } else if (homes.size() > 9 && homes.size() < 19) {
            size = 18;
        } else if (homes.size() > 18 && homes.size() < 28) {
            size = 27;
        } else if (homes.size() > 27 && homes.size() < 37) {
            size = 36;
        } else if (homes.size() > 36 && homes.size() < 46) {
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

    public void handle(InventoryClickEvent e) {
        e.setCancelled(true);
        ItemStack ci = e.getCurrentItem();
        if (e.getClick().isLeftClick() && e.getCurrentItem().getType() != Material.AIR) {
            if (!ci.hasItemMeta() || !ci.getItemMeta().hasLore() || !(ci.getItemMeta().getLore().size() == 5))
                return;
            String Iname = ChatColor.stripColor(ci.getItemMeta().getDisplayName());
            try {
                this.ess.getUser(this.playerMenuUtility.getOwner()).delHome(Iname);
                this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&7» &bSuccessfully removed '&e" + Iname + "&e'&b home."));
            } catch (Exception ex) {
                this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&7» &cFailed to remove '&e" + Iname + "&e'&b home."));
            }
        }
        if (e.getClick().isRightClick() && e.getCurrentItem().getType() != Material.AIR) {
            if (!ci.hasItemMeta() || !ci.getItemMeta().hasLore() || !(ci.getItemMeta().getLore().size() == 5))
                return;
            String Iname = ChatColor.stripColor(ci.getItemMeta().getDisplayName());
            try {
                this.ess.getUser(this.playerMenuUtility.getOwner()).delHome(Iname);
                this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&7» &bSuccessfully removed '&e" + Iname + "&e'&b home."));
            } catch (Exception ex) {
                this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&7» &cFailed to remove '&e" + Iname + "&e'&b home."));
            }
        }
        this.playerMenuUtility.getOwner().closeInventory();
    }

    public void setMenuItems() {
        List<String> lore = Arrays.asList("&7Location: %x%, %y%, %z%", "&7World: %world%", "",
                "&7Right or Left Click to delete!", "", "&8(&c&l!&8) &4&lDELETE ACTION! CANNOT BE UNDONE");
        List<String> homes = this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes();
        for (int i = 0; i < homes.size(); i++) {
            String it = null;
            Material itemname = null;
            ItemStack item = null;
            this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes();
            String n =this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes().get(i).toString().substring(0, 1).toUpperCase() + this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes().get(i).toString().substring(1);
            it = RebuiltFactions.idm.getConfig()
                    .getString("Icons." + this.playerMenuUtility.getOwner().getUniqueId() + "." + n);
            if (it != null) {
                itemname = Material.valueOf(it);
                item = new ItemStack(itemname);
            } else {
                item = new ItemStack(Material.RED_BED);
            }
            String home = ((String) this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes().get(i)).toString();
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
                    ls = ls.replace("%y%", "" + lo.getBlockY());
                    ls = ls.replace("%z%", "" + lo.getBlockZ());
                    ls = ColorUtils.color(ls);
                    lores.add(ls);
                    continue;
                }
                lores.add("");
            }
            String hn = this.ess.getUser(this.playerMenuUtility.getOwner()).getHomes().get(i);
            String hname = ChatColor.AQUA + hn.substring(0, 1).toUpperCase() + hn.substring(1);
            ItemStack itm = ItemUtils.create(item.getType(), hname, lores);
            this.inventory.setItem(i, ItemUtils.addGlow(itm));
        }
    }
}