package atoms.rebuiltfactions.dev.commands;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.menus.homes.Menuhomes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commandhome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(!(sender instanceof Player))
            return true;

        new Menuhomes(RebuiltFactions.getPlayerMenuUtility((Player)sender)).create();

        return true;
    }
}
