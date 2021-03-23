package atoms.rebuiltfactions.dev.menus.punish.listener;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.KitUtils;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginEvent implements Listener {

    RebuiltFactions plugin;

    public LoginEvent(RebuiltFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void preLogin(AsyncPlayerPreLoginEvent event) {
        if(Bukkit.hasWhitelist()){
            OfflinePlayer p = Bukkit.getOfflinePlayer(event.getUniqueId());
            if(p.isWhitelisted()){
                event.allow();
                return;
            }
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ColorUtils.color("&c&lServer Maintenance Active!" + "\n" + "&7You are not whitelisted thus cannot join" + "\n" + "the server. If you believe this is a" + "\n" + "a mistake contact staff on our discord."));
        }
        boolean isBanned = this.plugin.getServer().getBanList(BanList.Type.NAME).isBanned(event.getName());
        if (isBanned) {
            BanEntry entry = this.plugin.getServer().getBanList(BanList.Type.NAME).getBanEntry(event.getName());
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
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ColorUtils.color("&c&lYou've Been Banned" + "\n" + "" + "\n" + "&7Reason: &e" + reason + "\n" + "&7Duration: &e" + ds + "\n" + "\n" + "&7You may appeal on our discord in #ban-appeal " + "\n" + "or you can purchase a ban reset on our store." + "\n" + "\n" + "&d&lDiscord: " + "\n" + "&d&lWebstore:"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void kickedEvent(PlayerKickEvent e) {
        boolean isBanned = this.plugin.getServer().getBanList(BanList.Type.NAME).isBanned(e.getPlayer().getName());
        if (isBanned) {
            BanEntry entry = this.plugin.getServer().getBanList(BanList.Type.NAME).getBanEntry(e.getPlayer().getName());
            String ds = "Indefinitely";
            Date date = entry.getExpiration();
            String reason = entry.getReason();
            if (reason == null)
                reason = "No reason was provided.";
            if (date != null) {
                ds = "";
                Iterator<Map.Entry<TimeUnit, Long>> it = KitUtils.computeDiff(new Date(System.currentTimeMillis()), date).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = it.next();
                    System.out.println((new StringBuilder()).append(pair.getKey()).append(" = ").append(pair.getValue()).toString());
                    ds = String.valueOf(ds) + pair.getValue() + " " + pair.getKey() + " ";
                    it.remove();
                }
            }
            e.setReason(ColorUtils.color("&c&lYou've Been Banned" + "\n" + "" + "\n" + "&7Reason: &e" + reason + "\n" + "&7Duration: &e" + ds + "\n" + "\n" + "&7You may appeal on our discord in #ban-appeal " + "\n" + "or you can purchase a ban reset on our store." + "\n" + "\n" + "&d&lDiscord: " + "\n" + "&d&lWebstore:"));
        }
    }

}
