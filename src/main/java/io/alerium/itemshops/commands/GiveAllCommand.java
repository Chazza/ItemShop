package io.alerium.itemshops.commands;

import io.alerium.itemshops.ItemShopsPlugin;
import io.alerium.itemshops.utils.NumberUtil;
import io.alerium.itemshops.utils.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveAllCommand extends SubCommand {
    
    private final ItemShopsPlugin plugin = ItemShopsPlugin.getInstance();
    
    public GiveAllCommand() {
        super("giveall", "itemshops.giveall", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            plugin.getMessages().getMessage("commands.giveall.usage").format().send(sender);
            return;
        }
        
        Integer amount = NumberUtil.parseInteger(args[0]);
        if (amount == null) {
            plugin.getMessages().getMessage("commands.giveall.usage").format().send(sender);
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers())
            plugin.getCoinsManager().addCoins(player, amount);
        
        plugin.getMessages().getMessage("commands.giveall.done")
                .addPlaceholder("coins", amount)
                .addPlaceholder("players", Bukkit.getOnlinePlayers().size())
                .format()
                .send(sender);
    }
    
}
