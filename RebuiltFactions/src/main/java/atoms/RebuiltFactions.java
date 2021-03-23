package atoms;

import atoms.rebuiltfactions.dev.commands.*;
import atoms.rebuiltfactions.dev.files.ConfigManager;
import atoms.rebuiltfactions.dev.files.IconDataManager;
import atoms.rebuiltfactions.dev.menus.punish.listener.LoginEvent;
import atoms.rebuiltfactions.dev.utils.banknote.BankNoteListener;
import atoms.rebuiltfactions.dev.utils.experiencebottle.ExperienceBottleListener;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import atoms.rebuiltfactions.dev.utils.menu.listener.MenuListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;

public final class RebuiltFactions extends JavaPlugin {

    //menu management
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    //
    public static IconDataManager idm;
    public static ConfigManager config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        config = new ConfigManager(this);
        idm = new IconDataManager(this);
        checkDependencies();
        loadCommands();
        loadListener();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadCommands() {

        getCommand("kit").setExecutor(new Commandkits());
        getCommand("home").setExecutor(new Commandhome());
        getCommand("deletehome").setExecutor(new Commanddelhome());
        getCommand("withdraw").setExecutor(new Commandbanknote());
        getCommand("pay").setExecutor(new Commandpay());
        getCommand("take").setExecutor(new Commandpay());
        getCommand("createnote").setExecutor(new Commandbanknote());
        getCommand("balance").setExecutor(new Commandbalance());
        getCommand("bottle").setExecutor(new Commandbottle());
        getCommand("experience").setExecutor(new Commandexperience());
        getCommand("punish").setExecutor(new Commandpunish());
        getCommand("admin").setExecutor(new Commandadmin());
        getCommand("test").setExecutor(new Commandtest());
    }

    public void loadListener() {

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new MenuListener(), this);
        pm.registerEvents(new BankNoteListener(), this);
        pm.registerEvents(new ExperienceBottleListener(), this);
        pm.registerEvents(new LoginEvent(this), this);

    }

    private void checkDependencies() {
        PluginManager pm = this.getServer().getPluginManager();

        if (!pm.isPluginEnabled("Essentials")) {
            this.getLogger().log(Level.SEVERE, "Woah! You cannot use this plugin without EssentialsX (use latest version)");
            this.getLogger().log(Level.INFO, "Disabling " + this.getServer().getName());
        }
        this.getLogger().log(Level.INFO, "Hooked into Essentials");
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player player) {

        if (playerMenuUtilityMap.containsKey(player))
            return playerMenuUtilityMap.get(player);
        PlayerMenuUtility playerMenuUtility = new PlayerMenuUtility(player);
        playerMenuUtilityMap.put(player, playerMenuUtility);
        return playerMenuUtility;

    }


}
