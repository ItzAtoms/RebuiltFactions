package atoms.rebuiltfactions.dev.utils.menu;

import atoms.rebuiltfactions.dev.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;


public abstract class Menu implements InventoryHolder {

    protected PlayerMenuUtility playerMenuUtility;

    protected Inventory inventory;

    public Menu(PlayerMenuUtility playerMenuUtility){
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();
    public abstract int getMenuSlots();
    public abstract Sound getMenuSound();
    public abstract void handle(InventoryClickEvent event);
    public abstract void setMenuItems();
    public void create(){
        this.inventory = Bukkit.createInventory(this, getMenuSlots(), getMenuName());
        setMenuItems();
        for(int i = 0; i < this.inventory.getSize(); i++){
            if(this.inventory.getItem(i) == null)
                this.inventory.setItem(i, ItemUtils.create(Material.GRAY_STAINED_GLASS_PANE, "&8?"));
        }
        if(getMenuSound() != null){
            this.playerMenuUtility.getOwner().playSound(this.playerMenuUtility.getOwner().getLocation(), getMenuSound(), (float) 2, (float) 2);
        }
        this.playerMenuUtility.getOwner().openInventory(this.inventory);
    }

    public Inventory getInventory(){
        return this.inventory;
    }
}
