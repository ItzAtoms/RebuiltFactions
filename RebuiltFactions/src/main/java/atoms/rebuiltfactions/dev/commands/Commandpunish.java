package atoms.rebuiltfactions.dev.commands;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.menus.punish.Menupunishbannedplayers;
import atoms.rebuiltfactions.dev.menus.punish.Menupunishmain;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commandpunish implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(!(sender instanceof Player))
            return true;

        if(label.equalsIgnoreCase("punish") && args.length < 1) {
            if (!(sender.hasPermission("dc.punish"))) {
                etin.etins("permissionError", (Player) sender);
                return true;
            }

            new Menupunishmain(RebuiltFactions.getPlayerMenuUtility((Player) sender)).create();
            return true;
        }

        if(label.equalsIgnoreCase("punish") && args.length == 1 && args[0].equalsIgnoreCase("unban")){
            if (!(sender.hasPermission("dc.punish.unban"))) {
                etin.etins("permissionError", (Player) sender);
                return true;
            }
            new Menupunishbannedplayers(RebuiltFactions.getPlayerMenuUtility((Player)sender)).create();
            return true;
        }
        return true;
    }
}
