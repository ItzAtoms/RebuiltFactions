package atoms.rebuiltfactions.dev.commands;

import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
public class Commandpay implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;


        try {
            if (args.length != 2)
                return true;
            Player target = Bukkit.getPlayer(args[0]);
            if(target.getName().equalsIgnoreCase(player.getName())){player.sendMessage(ColorUtils.color("&7You cannot pay yourself."));}
            if (label.equalsIgnoreCase("pay")) {
                try {
                    int amount = Integer.parseInt(args[1]);
                    Essentials.getPlugin(Essentials.class).getUser(player.getUniqueId()).payUser(Essentials.getPlugin(Essentials.class).getUser(target.getUniqueId()), BigDecimal.valueOf(amount));
                    return true;
                } catch (Exception e) {
                    etin.etin("invalidNumberFormat", player);
                    return true;
                }
            }
            if (label.equalsIgnoreCase("take")) {
                try {
                    int amount = Integer.parseInt(args[1]);
                    try {
                        if (amount > Integer.parseInt(Essentials.getPlugin(Essentials.class).getUser(target.getUniqueId()).getMoney().toString())) {
                            player.sendMessage(ColorUtils.color("&cYou are trying to take an amount this player does not have."));
                            return true;
                        }
                        Essentials.getPlugin(Essentials.class).getUser(target.getUniqueId()).takeMoney(BigDecimal.valueOf(amount));
                        Essentials.getPlugin(Essentials.class).getUser(player.getUniqueId()).giveMoney(BigDecimal.valueOf(amount));
                        return true;
                    } catch (Exception e) {

                    }
                } catch (Exception e) {
                    etin.etin("invalidNumberFormat", player);
                    return true;
                }
            }
        } catch (Exception e) {
            etin.etins("nullPlayer", player);
            return true;
        }
        return true;
    }

}
