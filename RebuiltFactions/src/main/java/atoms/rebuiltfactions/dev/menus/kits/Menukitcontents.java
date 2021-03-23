package atoms.rebuiltfactions.dev.menus.kits;

import atoms.rebuiltfactions.dev.utils.ItemUtils;
import atoms.rebuiltfactions.dev.utils.KitUtils;
import atoms.rebuiltfactions.dev.utils.menu.Menu;
import atoms.rebuiltfactions.dev.utils.menu.PlayerMenuUtility;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Kit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Menukitcontents extends Menu {

    protected String kitName;

    private Essentials ess;

    protected Kit kit;

    public Menukitcontents(PlayerMenuUtility playerMenuUtility, String kit) {
        super(playerMenuUtility);
        this.kitName = kit;
        this.ess = Essentials.getPlugin(Essentials.class);
        try {
            this.kit = new Kit(this.kitName, this.ess);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public String getMenuName() {
        return "Kit " + this.kitName.substring(0, 1).toUpperCase() + this.kitName.substring(1, this.kitName.length());
    }

    @Override
    public int getMenuSlots() {
        try {
            List<ItemStack> itemList = KitUtils.getKitItems(ess.getUser(this.playerMenuUtility.getOwner().getUniqueId()), this.kit.getItems(), Essentials.getPlugin(Essentials.class));
            if (itemList.size() > 18 && itemList.size() < 26) {
                return 27;
            } else if (itemList.size() > 26 && itemList.size() < 35) {
                return 36;
            } else if (itemList.size() > 35 && itemList.size() < 44) {
                return 45;
            } else if (itemList.size() > 44 && itemList.size() < 53) {
                return 54;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 18;
    }

    @Override
    public Sound getMenuSound() {
        return Sound.ENTITY_ENDER_DRAGON_FLAP;
    }

    @Override
    public void handle(InventoryClickEvent event) {

        int size = this.inventory.getSize() - 1;
        if(!(event.getSlot() == size)){return;}
        new Menukitrank(this.playerMenuUtility).create();
    }

    @Override
    public void setMenuItems() {
        try {
            List<ItemStack> itemList = KitUtils.getKitItems(ess.getUser(this.playerMenuUtility.getOwner().getUniqueId()), this.kit.getItems(), Essentials.getPlugin(Essentials.class));
            for (ItemStack i : itemList) {
                this.inventory.addItem(i);
            }

            this.inventory.setItem(this.inventory.getSize() - 1, ItemUtils.create(Material.ARROW, "&7Back"));

        } catch (Exception e) {
            return;
        }
    }
}
