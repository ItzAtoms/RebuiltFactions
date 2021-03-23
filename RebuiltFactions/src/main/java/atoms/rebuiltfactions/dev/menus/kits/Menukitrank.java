package atoms.rebuiltfactions.dev.menus.kits;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.KitUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Kit;
import net.ess3.api.IEssentials;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

public class Menukitrank extends Menu {

    private Essentials ess;

    private LuckPerms luckPerms;

    private User user;

    private List<String> kits;

    private boolean hasKits = true;

    public Menukitrank(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);

        this.ess = Essentials.getPlugin(Essentials.class);
        this.luckPerms = LuckPermsProvider.get();
        this.user = this.luckPerms.getPlayerAdapter(Player.class).getUser(this.playerMenuUtility.getOwner());
        try {
            this.kits = KitUtils.listKits(ess.getUser(this.playerMenuUtility.getOwner().getUniqueId()));
        } catch (Exception e) {
            this.hasKits = false;
        }
    }

    @Override
    public String getMenuName() {
        return "Available Rank Kits";
    }

    @Override
    public int getMenuSlots() {
        return 9;
    }

    @Override
    public Sound getMenuSound() {
        return null;
    }

    @Override
    public void handle(InventoryClickEvent event) {
        String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase(Locale.ENGLISH));
        Kit kit = null;
        if ("improper kit format".equalsIgnoreCase(name) || "no kits found".equalsIgnoreCase(name)) {
            RebuiltFactions.getPlugin(RebuiltFactions.class).getLogger().log(Level.SEVERE, "[" + this.playerMenuUtility.getOwner().getName() + "] Tried to get a kit whilst the kits are badly formatted. Fix this issue!");
            return;
        }
        try {
            kit = new Kit(name, (IEssentials) this.ess);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (event.getClick()) {
            case LEFT:
                if (this.playerMenuUtility.getOwner().isOp()) {
                    try {
                        this.playerMenuUtility.getOwner().sendMessage(ColorUtils.color("&6Recevied kit &c" + name + "&6."));
                        kit.expandItems(this.ess.getUser(this.playerMenuUtility.getOwner().getUniqueId()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                this.playerMenuUtility.getOwner().performCommand("ekits " + name);
                new Menukitrank(this.playerMenuUtility).create();
                break;
            case RIGHT:
                new Menukitcontents(this.playerMenuUtility, name).create();
                break;
            default:
                break;
        }
    }

    @Override
    public void setMenuItems() {
        File file = new File("plugins/Essentials/kits.yml");
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (Exception e) {
            RebuiltFactions.getPlugin(RebuiltFactions.class).getLogger().log(Level.SEVERE, "Could not load essentials kits file! Kits will not work as intended");
        }
        try {
            if (hasKits) {
                for (String k : this.kits) {
                    if (config.contains("kits." + k.toLowerCase() + ".displayname")) {

                        Kit kit = new Kit(k.toLowerCase(Locale.ENGLISH), this.ess);
                        if (this.user.getCachedData().getPermissionData().checkPermission("essentials.kits." + k.toLowerCase()).asBoolean()) {
                            if (kit.getNextUse(this.ess.getUser(this.playerMenuUtility.getOwner().getUniqueId())) < 1L ||
                                    this.playerMenuUtility.getOwner().isOp()) {
                                inventory.addItem(ItemUtils.create(Material.GREEN_STAINED_GLASS_PANE, config.getString("kits." + k.toLowerCase() + ".displayname"), Arrays.asList("&7[&a✓&7] &a&lUnlocked","", "&7Left-Click to redeem", "&7Right-Click to preview")));
                                continue;
                            }
                            inventory.addItem(ItemUtils.create(Material.YELLOW_STAINED_GLASS_PANE, config.getString("kits." + k.toLowerCase() + ".displayname"), Arrays.asList("&cCooldown active for &e" + KitUtils.fromDateDiff(kit.getNextUse(this.ess.getUser(this.playerMenuUtility.getOwner().getUniqueId()))), "", "&7Left-Click to redeem", "&7Right-Click to preview")));
                            continue;
                        }

                        inventory.addItem(ItemUtils.create(Material.RED_STAINED_GLASS_PANE, config.getString("kits." + k.toLowerCase() + ".displayname"), Arrays.asList("&7[&c✘&7] &c&lLocked", "&cPurchase a higher rank to access this kit.", "", "&7Left-Click to redeem", "&7Right-Click to preview")));
                    } else {
                        for (int i = 0; i < inventory.getSize(); i++) {
                            if (this.playerMenuUtility.getOwner().isOp()) {

                                inventory.setItem(i, ItemUtils.create(Material.RED_STAINED_GLASS_PANE, "&c&lImproper Kit Format", Arrays.asList("&7Hey! Kits were found but were badly", "&7formatted please go into kits.yml and", "&7add '&edisplayname&7' configuration", "&7section to properly configure kits.", "", "&c&lEXAMPLE", "", "&7mykit:", "  &7delay: 86400", "  &7displayname: '&d&lMy Kit&7'",
                                        "  &7items:", "&7     - iron_spade", "&7     - iron_pickaxe", "&7     - iron_chestplate", "&7     - golden_apple:1")));
                                continue;
                            }
                            inventory.setItem(i, ItemUtils.create(Material.RED_STAINED_GLASS_PANE, "&c&lImproper Kit Format", Arrays.asList("&7Kits were not formatted correctly!", "&7Contact Staff!")));
                        }
                        etin.etin("kitErrorFormat", true, RebuiltFactions.getPlugin(RebuiltFactions.class));
                        break;
                    }
                }
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isOp()) {
                        etin.etin("kitErrorNull", true, RebuiltFactions.getPlugin(RebuiltFactions.class));
                    }
                }
                for (int i = 0; i < inventory.getSize(); i++) {

                    if (this.playerMenuUtility.getOwner().isOp()) {
                        inventory.setItem(i, ItemUtils.create(Material.RED_STAINED_GLASS_PANE, "&c&lNo Kits Found", Arrays.asList("&7Hey! No kits were found in config", "&7please enter the kits.yml file", "&7add kits with '&edisplayname&7' configuration", "&7section to properly configure kits.", "", "&c&lEXAMPLE", "", "&7mykit:", "  &7delay: 86400", "  &7displayname: '&d&lMy Kit&7'",
                                "  &7items:", "&7     - iron_spade", "&7     - iron_pickaxe", "&7     - iron_chestplate", "&7     - golden_apple:1")));
                        continue;
                    }
                    inventory.setItem(i, ItemUtils.create(Material.RED_STAINED_GLASS_PANE, "&c&lNo Kits Found", Arrays.asList("&7No kits were found!", "&7Contact Staff!")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
