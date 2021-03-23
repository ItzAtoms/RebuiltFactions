package atoms.rebuiltfactions.dev.commands;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.menus.admin.Menuadminmain;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commandadmin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(!(sender instanceof Player))
            return true;
        if(!(sender).hasPermission("dc.admin")){
            etin.etins("permissionError", (Player)sender);
            return true;
        }
        new Menuadminmain(RebuiltFactions.getPlayerMenuUtility((Player)sender)).create();

        return true;
    }
}
