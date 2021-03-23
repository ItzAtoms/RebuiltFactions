package atoms.rebuiltfactions.dev.utils.banknote;

import atoms.RebuiltFactions;
import atoms.rebuiltfactions.dev.utils.ColorUtils;
import atoms.rebuiltfactions.dev.utils.etin.etin;
import net.ess3.api.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

public class BankNoteListener implements Listener {

    @EventHandler
    public void onRedeem(PlayerInteractEvent event) {
        NumberFormat format = NumberFormat.getInstance();
        Player p = event.getPlayer();
        Action action = event.getAction();
        if(event.getItem() == null)
            return;
        if (!action.equals(Action.RIGHT_CLICK_BLOCK) && !action.equals(Action.RIGHT_CLICK_AIR))
            return;
        ItemStack a
        ItemMeta m = event.getItem().getItemMeta();
        assert m != null;
        if (!ChatColor.stripColor(m.getDisplayName()).contains("Bank Note"))
            return;
        if(m.getLore().size() != 4)
            return;
        int amount = 0;
        List<String> l = m.getLore();
        String a = ChatColor.stripColor(l.get(0));
        a = a.replaceAll("[a-zA-Z]", "");
        a = a.replaceAll("[$,:]", "");
        a = a.replaceFirst(" ", "");
        try {
            BigDecimal temp = Economy.getMoneyExact(p.getUniqueId());
            amount = ((int)Double.parseDouble(a));
            if(amount >= 1000000000){
                etin.etins("tooMuchMoney", p);
                etin.etin("noteTooMuch", false, RebuiltFactions.getPlugin(RebuiltFactions.class));
                return;
            }

            if(event.getItem().getAmount() <= 1){
                Economy.add(p.getUniqueId(), BigDecimal.valueOf(amount));
                p.sendMessage(ColorUtils.color("&a&l+" + format.format(amount)));
                p.getInventory().setItemInMainHand(null);
                return;
            }
            amount = amount * event.getItem().getAmount();
            Economy.add(p.getUniqueId(), BigDecimal.valueOf(amount));
            p.sendMessage(ColorUtils.color("&a&l+" + format.format(amount)));
            p.getInventory().setItemInMainHand(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
