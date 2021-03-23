package atoms.rebuiltfactions.dev.commands;

import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.player.User;
import net.ess3.api.Economy;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Commandexperience implements CommandExecutor {
    NumberFormat format = NumberFormat.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player))
            return true;
        if(args.length > 1)
            return true;
        User user = new User((Player) sender);
        try {
            int exp = 0;
            ComponentBuilder message = new ComponentBuilder();
            TextComponent p = new TextComponent("» ");
            p.setColor(ChatColor.GRAY);
            message.append(p);
            if (args.length == 1) {
                try {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player == null) {
                        etin.etins("nullPlayer", user.getBase());
                        return true;
                    }
                    User t = new User(player);
                    exp = t.getTotalExperience();
                    TextComponent n = new TextComponent(ColorUtils.color("&e" + t.getBase().getName() + "'s &7current exp is &a" + format.format(exp)));
                    message.append(n);
                    user.getBase().spigot().sendMessage(message.create());
                    return true;
                } catch (NullPointerException h) {
                    h.printStackTrace();
                    return true;
                }
            }

            exp = user.getTotalExperience();
            TextComponent n = new TextComponent(ColorUtils.color("&7Your current exp is &a" + format.format(exp)));
            message.append(n);
            user.getBase().spigot().sendMessage(message.create());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
