package io.alerium.itemshops.shops.objects.shops;

import io.alerium.itemshops.ItemShopsPlugin;
import io.alerium.itemshops.shops.objects.GUIInfo;
import io.alerium.itemshops.shops.objects.GUIItem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

public class ShopItem extends GUIItem {
    
    @Getter private final int cost;
    @Getter private final List<String> buyCommands;

    public ShopItem(ItemStack item, int row, int column, int cost, List<String> buyCommands) {
        super(item, row, column, null);
        this.cost = cost;
        this.buyCommands = buyCommands;
    }

    @Override
    public BiConsumer<GUIInfo, InventoryClickEvent> getOnClick() {
        return (info, event) -> {
            ItemShopsPlugin plugin = ItemShopsPlugin.getInstance();
            Player player = (Player) event.getWhoClicked();
            
            if (!plugin.getCoinsManager().removeCoins(player, cost)) {
                plugin.getMessages().getMessage("shops.notEnoughCoins").format().send(player);
                return;
            }

            for (String command : buyCommands) {
                String commandExec = command.substring(command.indexOf("]")).replaceAll("%player%", player.getName()).replaceAll("%cost%", Integer.toString(cost));

                if (command.startsWith("[CONSOLE]"))
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandExec);
                else if (command.startsWith("[PLAYER]"))
                    Bukkit.dispatchCommand(player, commandExec);
            }
            
            if (info instanceof ShopGUI && ((ShopGUI) info).isCloseOnBuy())
                player.closeInventory();

            plugin.getMessages().getMessage("shops.boughtItem").addPlaceholder("price", cost).format().send(player);
        };
    }
}
