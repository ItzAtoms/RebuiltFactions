package atoms.rebuiltfactions.dev.utils;

import atoms.RebuiltFactions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemUtils {

    private RebuiltFactions plugin;

    public static ItemStack create(Material i, String name, List<String> lore){

        ItemStack e = new ItemStack(Material.DIRT);
        ItemMeta meta = e.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ColorUtils.color("Could not set display name"));
        meta.setLore(Arrays.asList(ColorUtils.color("&7Null"), ColorUtils.color("&7Null")));

        e.setItemMeta(meta);


        try{
            e = new ItemStack(i);
            meta.setDisplayName(ColorUtils.color(name));
            List<String> l = new ArrayList<>();
            for(String s : lore){
                l.add(ColorUtils.color(s));
            }

            meta.setLore(l);

            e.setItemMeta(meta);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return e;
    }

    public static ItemStack create(Material i, String name){

        ItemStack e = new ItemStack(Material.DIRT);
        ItemMeta meta = e.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ColorUtils.color("Could not set display name"));

        e.setItemMeta(meta);


        try{
            e = new ItemStack(i);
            meta.setDisplayName(ColorUtils.color(name));
            e.setItemMeta(meta);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return e;
    }

    public static ItemStack createSkull(String name, List<String> lore){

        ItemStack e = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) e.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ColorUtils.color("Could not set display name"));
        meta.setLore(Arrays.asList(ColorUtils.color("&7Null"), ColorUtils.color("&7Null")));

        e.setItemMeta(meta);


        try{
            meta.setDisplayName(ColorUtils.color(name));
            List<String> l = new ArrayList<>();
            for(String s : lore){
                l.add(ColorUtils.color(s));
            }

            meta.setLore(l);
            meta.setOwner(ChatColor.stripColor(name));
            e.setItemMeta(meta);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return e;
    }

    public static ItemStack addGlow(ItemStack i){

        try{
            i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            ItemMeta meta = i.getItemMeta();
            assert meta != null;
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            i.setItemMeta(meta);
            return i;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Custom Mob Eggs
}
