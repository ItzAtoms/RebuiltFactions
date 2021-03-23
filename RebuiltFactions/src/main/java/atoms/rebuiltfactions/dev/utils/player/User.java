package atoms.rebuiltfactions.dev.utils.player;

import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import net.ess3.api.IEssentials;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Objects;

public class User extends com.earth2me.essentials.PlayerExtension {

    NumberFormat format = NumberFormat.getInstance();
    LuckPerms lp = LuckPermsProvider.get();
    net.luckperms.api.model.user.User u = lp.getPlayerAdapter(Player.class).getUser(Objects.requireNonNull(this.base.getPlayer()));

    public User(Player base) {
        super(base);
    }

    public boolean hasPermission(String node){
        try{
            if(u.getCachedData().getPermissionData().checkPermission(node).asBoolean()){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public int getTotalExperience(){
        return Math.round(base.getExp() * base.getExpToLevel()) + getTotalExperience(base.getLevel());
    }

    public int getTotalExperience(int level){
        int xp = 0;
        if (level >= 0 && level <= 15) {
            xp = (int)Math.round(Math.pow(level, 2.0D) + (6 * level));
        } else if (level > 15 && level <= 30) {
            xp = (int)Math.round(2.5D * Math.pow(level, 2.0D) - 40.5D * level + 360.0D);
        } else if (level > 30) {
            xp = (int)Math.round(4.5D * Math.pow(level, 2.0D) - 162.5D * level + 2220.0D);
        }
        return xp;
    }

    public void setTotalExperience(int amount){
        int level = 0;
        int xp = 0;
        float a = 0.0F;
        float b = 0.0F;
        float c = -amount;
        if (amount > getTotalExperience(0) && amount <= getTotalExperience(15)) {
            a = 1.0F;
            b = 6.0F;
        } else if (amount > getTotalExperience(15) && amount <= getTotalExperience(30)) {
            a = 2.5F;
            b = -40.5F;
            c += 360.0F;
        } else if (amount > getTotalExperience(30)) {
            a = 4.5F;
            b = -162.5F;
            c += 2220.0F;
        }
        level = (int)Math.floor((-b + Math.sqrt(Math.pow(b, 2.0D) - (4.0F * a * c))) / (2.0F * a));
        xp = amount - getTotalExperience(level);
        this.base.setLevel(level);
        this.base.setExp(0.0F);
        this.base.giveExp(xp);
    }

    public void giveBankNote(int amount){

        ItemStack note = ItemUtils.create(Material.PAPER, "&c&lBank Note &7(Right Click)", Arrays.asList("&f&lAmount: &a&l$" + format.format(amount), "&f&lSigned: &a&l" + base.getName(), "", "&7Right click to redeem"));
        if(base.getInventory().firstEmpty() == -1){
            base.getWorld().dropItemNaturally(base.getLocation(), note);
            return;
        }
        base.getInventory().addItem(note);
        etin.etins("givenItem", base);
    }

    public void giveExperienceBottle(int amount){
        ItemStack i = ItemUtils.create(Material.EXPERIENCE_BOTTLE, "&a&lEXP Bottle &7(Right Click)", Arrays.asList("&d&lAmount: &f" + format.format(amount), "&d&lSigned: &f" + base.getName()));
        if(base.getInventory().firstEmpty() == -1){
            base.getWorld().dropItemNaturally(base.getLocation(), i);
            return;
        }
        base.getInventory().addItem(i);
    }

    public void sendMessage(String message){
        this.base.sendMessage(ColorUtils.color(message));
    }

    public ItemStack getItemInMainHand(){
        return this.base.getInventory().getItemInMainHand();
    }

    public void setHat(ItemStack hat){
        if(hat == null || hat.getType().equals(Material.AIR))
            return;
        ItemStack old = this.base.getInventory().getHelmet();
        if(old.getType().equals(Material.AIR) || old.getType() == null) {
            this.base.getInventory().setHelmet(hat);
            return;
        }
        this.base.getInventory().addItem(old);
        this.base.getInventory().setHelmet(hat);
    }
}
