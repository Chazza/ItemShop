package it.xquickglare.itemshops.commands;

import it.xquickglare.itemshops.ItemShopsPlugin;
import it.xquickglare.itemshops.shops.objects.shops.ShopGUI;
import it.xquickglare.itemshops.utils.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenCommand extends SubCommand {
    
    private final ItemShopsPlugin plugin = ItemShopsPlugin.getInstance();
    
    public OpenCommand() {
        super("open", "itemshops.open", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            plugin.getShopManager().getMainGui().getInventory().open(player);
            return;
        }
        
        String shopName = args[0];
        ShopGUI shop = plugin.getShopManager().getShopGui(shopName);
        if (shop == null) {
            plugin.getMessages().getMessage("commands.open.shopNotFound").format().send(player);
            return;
        }
        
        shop.getInventory().open(player);
    }
    
}
