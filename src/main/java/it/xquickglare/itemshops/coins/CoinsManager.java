package it.xquickglare.itemshops.coins;

import it.xquickglare.itemshops.ItemShopsPlugin;
import it.xquickglare.itemshops.utils.configuration.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CoinsManager {
    
    private final ItemShopsPlugin plugin = ItemShopsPlugin.getInstance();
    
    private ItemStack coinItem;
    
    public void enable() {
        coinItem = ItemBuilder.fromConfig(plugin.getConfiguration(), "coinItem").build();
    }

    /**
     * This method gets the amount of coins of a Player
     * @param player The Player
     * @return The amount of coins
     */
    public int getCoins(Player player) {
        PlayerInventory inventory = player.getInventory();
        int amount = 0;

        for (ItemStack item : inventory) {
            if (item == null || item.getType() == Material.AIR)
                continue;
            
            if (item.isSimilar(coinItem))
                amount += item.getAmount();
        }
        
        return amount;
    }

    /**
     * This method removes the specified amount of coins from a Player
     * @param player The Player
     * @param remove The amount of coins to remove
     * @return True if successfully removed
     */
    public boolean removeCoins(Player player, int remove) {
        int coins = getCoins(player);
        if (coins < remove)
            return false;
        
        PlayerInventory inventory = player.getInventory();
        int removedAmount = 0;
        for (ItemStack item : inventory) {
            if (item == null || item.getType() == Material.AIR)
                continue;
            
            if (!item.isSimilar(coinItem)) 
                continue;
            
            int amount = item.getAmount();
            if (removedAmount + amount >= remove) {
                item.subtract(remove - removedAmount);
                break;
            }
            
            removedAmount += amount;
            item.setAmount(0);
        }
        
        return true;
    }

    /**
     * This method adds a specified amount of coins to a Player
     * @param player The Player
     * @param amount The amount of coins to add
     */
    public void addCoins(Player player, int amount) {
        player.getInventory().addItem(coinItem.asQuantity(amount));
    }
    
}
