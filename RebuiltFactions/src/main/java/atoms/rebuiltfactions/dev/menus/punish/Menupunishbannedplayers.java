package atoms.rebuiltfactions.dev.menus.punish;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.KitUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.*;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Menupunishbannedplayers extends Menu {
    RebuiltFactions plugin = RebuiltFactions.getPlugin(RebuiltFactions.class);

    public Menupunishbannedplayers(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Viewing Banned Players";
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
        event.setCancelled(true);
        if(event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().getType() != Material.GRAY_STAINED_GLASS_PANE){
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),"unban " + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            new Menupunishbannedplayers(this.playerMenuUtility).create();
        }
    }

    @Override
    public void setMenuItems() {
        for(OfflinePlayer p : Bukkit.getBannedPlayers()){
            BanEntry entry = RebuiltFactions.getPlugin(RebuiltFactions.class).getServer().getBanList(BanList.Type.NAME).getBanEntry(p.getName());
            String ds = "&c&lIndefinitely";
            assert entry != null;
            Date date = entry.getExpiration();
            String reason = entry.getReason();

            if (reason == null)
                reason = "None provided";
            if (date != null) {
                ds = "";
                Iterator<Map.Entry<TimeUnit, Long>> it = KitUtils.computeDiff(new Date(System.currentTimeMillis()), date).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = it.next();
                    System.out.println((new StringBuilder()).append(pair.getKey()).append(" = ").append(pair.getValue()).toString());
                    ds = String.valueOf(ds) + pair.getValue() + " " + pair.getKey() + " ";
                }
            }
            this.inventory.addItem(ItemUtils.createSkull("&7" + p.getName(), Arrays.asList("", "&7Duration: &e" + ds, "&7Reason: &e" + reason, "", "&7Click to &a&lUNBAN")));
        }
        for(int i = 0; i < this.inventory.getSize(); i++){
            if(this.inventory.getItem(i) == null)
                this.inventory.setItem(i, ItemUtils.create(Material.GRAY_STAINED_GLASS_PANE, "&cNO DATA"));
        }
    }
}
