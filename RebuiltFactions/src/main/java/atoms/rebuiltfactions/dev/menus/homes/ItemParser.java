package atoms.rebuiltfactions.dev.menus.homes;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ColorUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemParser {
    public List<ItemStack> ItemParsers(Player player) {
        List<ItemStack> items = new ArrayList<>();
        for (String i : RebuiltFactions.config.getConfig().getStringList("config.homegui.icons")) {
            Material itemname = Material.valueOf(i);
            String in = ChatColor.WHITE + WordUtils.capitalize(itemname.toString().replace("_", " ").toLowerCase());
            ItemStack item = nameItem(new ItemStack(itemname), in, ChatColor.GRAY + "Click to select icon for" + ChatColor.AQUA + " ");
            items.add(item);
        }
        return items;
    }

    private ItemStack nameItem(ItemStack item, String name, String lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColorUtils.color("&b" + name));
        meta.setLore(Lists.newArrayList(lore));
        item.setItemMeta(meta);
        return item;
    }
}