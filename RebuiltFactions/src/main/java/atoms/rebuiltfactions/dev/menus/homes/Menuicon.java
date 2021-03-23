package atoms.rebuiltfactions.dev.menus.homes;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Menuicon extends Menu {
    private String home = "";

    public Menuicon(String home, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.home = home;
    }

    public String getMenuName() {
        return "Change Icon for Home: " + home;
    }

    public int getMenuSlots() {
        return 54;
    }
    public Sound getMenuSound() {
        return null;
    }
    public void handle(InventoryClickEvent e) {
        String ci = e.getCurrentItem().getType().toString();
        String Inm = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
        String Iname = Inm.substring(Inm.lastIndexOf(" ") + 1);
        RebuiltFactions.idm.getConfig().set("Icons." + this.playerMenuUtility.getOwner().getUniqueId() + "." + Iname, ci);
        RebuiltFactions.idm.saveConfig();
        (new Menuhomes(this.playerMenuUtility)).create();
    }

    public void setMenuItems() {
        ItemParser ip = new ItemParser();
        List<ItemStack> icons = ip.ItemParsers(this.playerMenuUtility.getOwner());
        for (ItemStack i : icons) {
            ItemStack is = ItemUtils.create(i.getType(), i.getItemMeta().getLore().get(0) + this.home);
            this.inventory.addItem(is);
        }
    }

}
