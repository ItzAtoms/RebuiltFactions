package atoms.rebuiltfactions.dev.commands;

import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.player.User;
import com.earth2me.essentials.Essentials;
import net.ess3.api.Economy;
import net.ess3.api.IEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Commandbanknote implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){



        NumberFormat format = NumberFormat.getInstance();
        User user = new User((Player)sender);
        if(sender instanceof ConsoleCommandSender)
            return true;
        if(label.equalsIgnoreCase("withdraw")) {
            if (args.length != 1)
                return true;

            int amount = 0;
            try {
                BigDecimal old = Economy.getMoneyExact(((Player) sender).getUniqueId());
                try {
                    amount = (int) Double.parseDouble(args[0]);
                } catch (NumberFormatException nfe) {
                    etin.etins("invalidNumberFormat", user.getBase());
                    return true;
                }
                if (amount > old.intValue()) {
                    etin.etin("noMoney", (Player) sender);
                    return true;
                }
                int remainder = old.intValue() - amount;
                try {
                    Economy.setMoney(((Player) sender).getUniqueId(), BigDecimal.valueOf(remainder));
                    user.giveBankNote(amount);
                    ((Player) sender).sendMessage(ColorUtils.color("&c&l-" + format.format(amount)));
                    return true;
                } catch (Exception a) {
                    a.printStackTrace();
                }
            } catch (Exception e) {
                return true;
            }
        }
        if(label.equalsIgnoreCase("createnote")){
            if (args.length != 1)
                return true;
            if(!user.hasPermission("dc.createnote")){
                etin.etins("permissionError", (Player)sender);return true;}
            int amount = 0;
            try {
                try {
                    amount = (int) Double.parseDouble(args[0]);
                    if(amount > 999999999){
                        etin.etins("noteCreateLimitReach", user.getBase());
                        return true;
                    }
                } catch (NumberFormatException nfe) {
                    etin.etins("invalidNumberFormat", user.getBase());
                    return true;
                }
                try {
                    user.giveBankNote(amount);
                    ((Player) sender).sendMessage(ColorUtils.color("&7<&c?&7> &a$" + format.format(amount)));
                    return true;
                } catch (Exception a) {
                    a.printStackTrace();
                }
            } catch (Exception e) {
                return true;
            }
        }
        return true;
    }

}
