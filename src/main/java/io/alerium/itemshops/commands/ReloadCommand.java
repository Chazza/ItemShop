package io.alerium.itemshops.commands;

import io.alerium.itemshops.ItemShopsPlugin;
import io.alerium.itemshops.utils.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {
    
    private final ItemShopsPlugin plugin = ItemShopsPlugin.getInstance();
    
    public ReloadCommand() {
        super("reload", "itemshops.reload", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.getConfiguration().reload();
        plugin.getMessages().reload();
        
        plugin.getShopManager().reload();
        plugin.getCoinsManager().enable();
        
        plugin.getMessages().getMessage("commands.reload").format().send(sender);
    }
}
