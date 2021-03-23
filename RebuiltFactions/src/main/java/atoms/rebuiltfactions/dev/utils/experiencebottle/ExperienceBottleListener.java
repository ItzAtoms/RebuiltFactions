package atoms.rebuiltfactions.dev.utils.experiencebottle;


import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.player.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.List;

public class ExperienceBottleListener implements Listener {

    @EventHandler
    public void onRedeem(PlayerInteractEvent event) {
        NumberFormat format = NumberFormat.getInstance();
        Player p = event.getPlayer();
        Action action = event.getAction();
        ItemStack i = p.getInventory().getItemInMainHand();
        User user = new User(p);
        if (!action.equals(Action.RIGHT_CLICK_BLOCK) && !action.equals(Action.RIGHT_CLICK_AIR))
            return;
        if (!i.getType().equals(Material.EXPERIENCE_BOTTLE) || i.getType().equals(Material.AIR))
            return;
        ItemMeta m = i.getItemMeta();
        assert m != null;
        if (!ChatColor.stripColor(m.getDisplayName()).contains("EXP Bottle"))
            return;
        if (!m.hasLore())
            return;
        int amount = 0;
        List<String> l = m.getLore();
        assert l != null;
        if(l.size() != 2){return;}
        String a = ChatColor.stripColor(l.get(0));
        a = a.replaceAll("[a-zA-Z]", "");
        a = a.replaceAll("[,:]", "");
        a = a.replaceFirst(" ", "");
        event.setCancelled(true);
        try {
            amount = Integer.parseInt(a);
            if(i.getAmount() <= 1){
                user.setTotalExperience(user.getTotalExperience() + amount);
                user.getBase().playSound(user.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float) 2, (float) 2);
                p.getInventory().setItemInMainHand(null);
                user.getBase().sendMessage(ColorUtils.color("&a&l+" + format.format(amount) + " &a&nEXP"));
                return;
            }
            p.getInventory().setItemInMainHand(null);
            user.setTotalExperience(user.getTotalExperience() + (amount * i.getAmount()));
            amount = amount * i.getAmount();
            user.getBase().playSound(user.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float) 2, (float) 2);
            user.getBase().sendMessage(ColorUtils.color("&a&l+" + format.format(amount) + " &a&nEXP"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
