package atoms.rebuiltfactions.dev.menus.admin;


import atoms.rebuiltfactions.dev.menus.admin.world.Menuadminworldmanager;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;


public class Menuadminworld extends Menu {

    public Menuadminworld(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }
    World world = this.playerMenuUtility.getOwner().getWorld();
    @Override
    public String getMenuName() {
        return "Select a world...";
    }

    @Override
    public int getMenuSlots() {
        return 54;
    }

    @Override
    public Sound getMenuSound() {
        return null;
    }

    @Override
    public void handle(InventoryClickEvent event) {

        if(event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem().getItemMeta().hasEnchant(Enchantment.DURABILITY)){
            return;
        }
        World world;
        try{
            world = Bukkit.getWorld(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            if(world.equals(this.playerMenuUtility.getOwner().getWorld())){
                return;
            }
            switch(event.getClick()){
                case LEFT:
                    new Menuadminconfirm(world, this.playerMenuUtility).create();
                    break;
                case RIGHT:
                    new Menuadminworldmanager(world, this.playerMenuUtility).create();
                    break;
                default:
                    break;

            }
        }catch (Exception e){
            this.playerMenuUtility.getOwner().closeInventory();
            etin.etin("inventoryError", this.playerMenuUtility.getOwner(), "[atoms.rebuiltfactions.menus.menuadminworld.world(invalidWorld:"+ ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()) + ")]");
        }
    }
    @Override
    public void setMenuItems() {
        List<World> a = this.playerMenuUtility.getOwner().getServer().getWorlds();

        for(World w : a){
            if(w.getName().endsWith("_end")){
                if(this.playerMenuUtility.getOwner().getWorld().equals(w)){
                    this.inventory.addItem(ItemUtils.addGlow(ItemUtils.create(Material.END_STONE, "&e&o" + w.getName(), Arrays.asList("&7Available Actions:","", "&c&lNONE... &7Why?", "&7You are currently on this world."))));
                    continue;
                }
                if (w.getPlayers().size() >= 1) {
                    this.inventory.addItem(ItemUtils.addGlow(ItemUtils.create(Material.END_STONE, "&e&o" + w.getName(), Arrays.asList("&7There are currently &e" + w.getPlayers().size() + " player(s)&7 on this", "&7world. Remove them to delete this world."))));
                   continue;
                }

                this.inventory.addItem(ItemUtils.create(Material.END_STONE, "&e&o" + w.getName(), Arrays.asList("&7Available Actions:","", "&7Right-Click: &eEdit World Settings", "&7Left-Click: &c&lDELETE WORLD", "", "&7World Settings.. who may use?", "&4&lDeveloper")));
                continue;
            }
            if(w.getName().endsWith("_nether")){
                if(this.playerMenuUtility.getOwner().getWorld().equals(w)){
                    this.inventory.addItem(ItemUtils.addGlow(ItemUtils.create(Material.NETHERRACK, "&c&o" + w.getName(), Arrays.asList("&7Available Actions:","", "&c&lNONE... &7Why?", "&7You are currently on this world."))));
                    continue;
                }

                if (w.getPlayers().size() >= 1) {
                    this.inventory.addItem(ItemUtils.addGlow(ItemUtils.create(Material.NETHERRACK, "&c&o" + w.getName(), Arrays.asList("&7There are currently &e" + w.getPlayers().size() + " player(s)&7 on this", "&7world. Remove them to delete this world."))));
                    continue;
                }
                this.inventory.addItem(ItemUtils.create(Material.NETHERRACK, "&c&o" +w.getName(), Arrays.asList("&7Available Actions:","", "&7Right-Click: &eEdit World Settings", "&7Left-Click: &c&lDELETE WORLD","", "&7World Settings.. who may use?", "&4&lDeveloper")));
                continue;
            }
            if(this.playerMenuUtility.getOwner().getWorld().equals(w)){
                this.inventory.addItem(ItemUtils.addGlow(ItemUtils.create(Material.GRASS_BLOCK, "&a&o" + w.getName(), Arrays.asList("&7Available Actions:","", "&c&lNONE... &7Why?", "&7You are currently on this world."))));
                continue;
            }
            if (w.getPlayers().size() >= 1) {
                this.inventory.addItem(ItemUtils.addGlow(ItemUtils.create(Material.GRASS_BLOCK, "&e&o" + w.getName(), Arrays.asList("&7There are currently &e" + w.getPlayers().size() + " player(s)&7 on this", "&7world. Remove them to delete this world."))));
                continue;
            }
            this.inventory.addItem(ItemUtils.create(Material.GRASS_BLOCK, "&a&o" + w.getName(), Arrays.asList("&7Available Actions:","", "&7Right-Click: &eEdit World Settings", "&7Left-Click: &c&lDELETE WORLD", "", "&7World Settings.. who may use?", "&4&lDeveloper")));

        }
    }
}
