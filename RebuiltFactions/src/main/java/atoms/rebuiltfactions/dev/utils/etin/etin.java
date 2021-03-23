package atoms.rebuiltfactions.dev.utils.etin;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.KitUtils;
import atoms.rebuiltfactions.dev.utils.player.User;
import net.ess3.api.Economy;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class etin {
    private static ComponentBuilder message;
    private static TextComponent kit = new TextComponent("Kit ");
    private static TextComponent s = new TextComponent("Server ");
    private static TextComponent a = new TextComponent("» ");

    public static void etin(String object, boolean sound, RebuiltFactions rebuiltFactions) {
        kit.setBold(true);
        kit.setColor(ChatColor.RED);

        a.setColor(ChatColor.GRAY);
        a.setBold(false);

        if (sound) {
            for (Player a : Bukkit.getOnlinePlayers())
                a.playSound(a.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, (float) 2, (float) 2);
        }

        switch (object) {
            case "kitErrorNull":
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isOp()) {
                        TextComponent n = new TextComponent("Kit issue! Please redirect this error code to the developer(s)");
                        n.setColor(ChatColor.GRAY);
                        n.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("[atoms.rebuiltfactions.dev.Mainkitrank.hasKits(null)]")));
                        message = new ComponentBuilder();
                        message.append(kit);
                        message.append(a);
                        message.append(n);
                        p.spigot().sendMessage(message.create());
                    }
                }
                break;
            case "kitErrorFormat":
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isOp()) {
                        TextComponent n = new TextComponent("Kit issue! Please redirect this error code to the developer(s)");
                        n.setColor(ChatColor.GRAY);
                        n.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("[atoms.rebuiltfactions.dev.Mainkitrank.setMenuItems(displayname)]")));
                        message = new ComponentBuilder();
                        message.append(kit);
                        message.append(a);
                        message.append(n);
                        p.spigot().sendMessage(message.create());
                    }
                }
                break;
            case "noteTooMuch":
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isOp()) {
                        TextComponent n = new TextComponent("Hey! there's a player that tried to redeem a banknote worth 1 trillion or more! They will reach out to for you to create smaller banknotes.");
                        n.setColor(ChatColor.GRAY);
                        n.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("[atoms.rebuiltfactions.dev.banknote.redeem(maxLimitReach)]")));
                        message = new ComponentBuilder();
                        message.append(kit);
                        message.append(a);
                        message.append(n);
                        p.spigot().sendMessage(message.create());
                    }
                }
                break;

            default:
                break;
        }
    }

    public static void etin(String object, Player user) {
        TextComponent n;
        s.setBold(true);
        s.setColor(ChatColor.RED);
        a.setBold(false);
        a.setColor(ChatColor.GRAY);
        switch (object) {
            case "noPermission":
                n = new TextComponent("Insufficient permission to use this command.");
                message = new ComponentBuilder();
                message.append(s);
                message.append(a);
                message.append(n);
                user.spigot().sendMessage(message.create());
                break;
            case "noMoney":
                n = new TextComponent("You do not have enough money.");
                n.setColor(ChatColor.RED);
                message = new ComponentBuilder();
                try {
                    message.append(n);
                    TextComponent a = new TextComponent(" (Hover me)");
                    a.setColor(ChatColor.GRAY);
                    a.setItalic(true);
                    a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ColorUtils.color("&7Your current balance &a$" + NumberFormat.getInstance().format(Economy.getMoneyExact(user.getUniqueId()))))));
                    message.append(a);
                    user.spigot().sendMessage(message.create());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "noExp":
                n = new TextComponent("You do not have enough exp.");
                n.setColor(ChatColor.RED);
                message = new ComponentBuilder();
                try {
                    message.append(n);
                    TextComponent a = new TextComponent(" (Hover me)");
                    a.setColor(ChatColor.GRAY);
                    a.setItalic(true);
                    a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ColorUtils.color("&7Your current exp &a" + NumberFormat.getInstance().format((new User(user)).getTotalExperience())))));
                    message.append(a);
                    user.spigot().sendMessage(message.create());
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    public static void etin(String object, Player user, Player operator) {
        switch (object) {
            case "playerBan":
                BanEntry entry = RebuiltFactions.getPlugin(RebuiltFactions.class).getServer().getBanList(BanList.Type.NAME).getBanEntry(user.getName());
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
                        ds = String.valueOf(ds) + pair.getValue() + " " + pair.getKey() + " ";
                    }
                }
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isOp()) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, (float) 2, (float) 2);
                        p.sendMessage(ColorUtils.color("            &c&lPLAYER BAN WARNING!"));
                        p.sendMessage(ColorUtils.color("&7Player: &e" + user.getName()));
                        p.sendMessage(ColorUtils.color("&7Reason: &e" + reason));
                        p.sendMessage(ColorUtils.color("&7Duration: &e" + ds));
                        p.sendMessage(ColorUtils.color("&7Operator: &e" + operator.getName()));
                    }
                }
                break;
            default:
                break;

        }
    }

    public static void etin(String object, Player user, String object1){

        switch (object){
            case "warn":
                user.sendMessage(ColorUtils.color("&cYou have been given a warning for: &e" + object1));
                break;
            case "inventoryError":
                        TextComponent e = new TextComponent("Inventory Error. ");
                        TextComponent n = new TextComponent("(Hover me)");
                        e.setColor(ChatColor.RED);
                        n.setItalic(true);
                        n.setColor(ChatColor.GRAY);
                        n.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(object1)));
                        message = new ComponentBuilder();
                        message.append(e);
                        message.append(n);
                        user.spigot().sendMessage(message.create());
                        break;
            default:
                break;
        }



    }

    public static void settingsChange(String setting, Player user, boolean state){
        user.sendMessage(ColorUtils.color("&8(&c&l!&8) &7Setting &e" + setting + "&7 has been changed to &d" + state));
    }

    public static void etins(String object, Player user) {
        switch (object) {
            case "purchaseError":
            case "permissionError":
                user.sendMessage(ColorUtils.color("&cYou do not have permission for this command."));
                user.playSound(user.getLocation(), Sound.ENTITY_VILLAGER_NO, (float) 2, (float) 2);
                break;
            case "successfulRedeem":
                user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float) 2, (float) 2);
                break;
            case "givenItem":
                user.playSound(user.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, (float) 2, (float) 2);
                break;
            case "nullPlayer":
                user.sendMessage(ColorUtils.color("&cGiven player was not found. Try again."));
                break;
            case "invalidNumberFormat":
                user.sendMessage(ColorUtils.color("&cThat wasn't a number :| Try again!"));
                break;
            case "tooMuchMoney":
                user.sendMessage(ColorUtils.color("&c&lFATAL: &7This note is worth too much to process! Contact staff to give you smaller notes!"));
                break;
            case "noteCreateLimitReach":
                user.sendMessage(ColorUtils.color("&c&lFATAL: &7Woah there buddy... you're tring to create a note with an amount that exceeds banknote limit! Create a note < 1T"));
                break;
            case "isOp":
                user.sendMessage(ColorUtils.color("&cHey buddy! You're already opped!"));
                break;
            case "warn":
                user.sendMessage(ColorUtils.color("&cYou have been given a warning."));
                break;

            default:
                break;
        }
    }


    public static void kick(String reason, Player user, Player operator) {

        user.kickPlayer(ColorUtils.color("&c&lKICKED" + "\n" + "&7Reason: &e" + reason + "\n" + "&7Staff: &e" + operator.getName()));
    }
    public static void restart() {
        for(Player p : Bukkit.getOnlinePlayers()){
            p.kickPlayer(ColorUtils.color("&c&lKICKED" + "\n" + "&7Reason: &eServer Restart" + "\n" + "&7Sorry to interrupt! Server needs a refresh."));
        }
        RebuiltFactions.getPlugin(RebuiltFactions.class).getServer().dispatchCommand(Bukkit.getConsoleSender(), "stop");
        RebuiltFactions.getPlugin(RebuiltFactions.class).getServer().dispatchCommand(Bukkit.getConsoleSender(), "end");
    }
    public static void maintenance() {
        if(!RebuiltFactions.getPlugin(RebuiltFactions.class).getServer().hasWhitelist()) {
            RebuiltFactions.getPlugin(RebuiltFactions.class).getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist on");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isWhitelisted())
                    continue;
                p.kickPlayer(ColorUtils.color("&c&lServer Maintenance Active!" + "\n" + "&7You are not whitelisted thus cannot join" + "\n" + "the server. If you believe this is a" + "\n" + "a mistake contact staff on our discord."));
            }
        }else{
            RebuiltFactions.getPlugin(RebuiltFactions.class).getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist off");
        }

    }
}
