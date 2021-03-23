package atoms.rebuiltfactions.dev.commands;

import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.player.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commandbottle implements CommandExecutor {

    private boolean isNum(String e){
        try{
            Integer.parseInt(e);
        }catch(NumberFormatException nfe){
            nfe.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(!(sender instanceof Player))
            return true;
        User user = new User((Player)sender);
        try {
            if (!(args.length == 1))
                return true;
            if (!isNum(args[0]))
                return true;
            int exp = Integer.parseInt(args[0]);
            if (exp > user.getTotalExperience()) {
                etin.etin("noExp", user.getBase());
                return true;
            }
            user.giveExperienceBottle(exp);
            user.setTotalExperience(user.getTotalExperience() - exp);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


}
