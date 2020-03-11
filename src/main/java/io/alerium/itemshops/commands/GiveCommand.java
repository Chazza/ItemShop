package io.alerium.itemshops.commands;

import io.alerium.itemshops.ItemShopsPlugin;
import io.alerium.itemshops.utils.NumberUtil;
import io.alerium.itemshops.utils.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand extends SubCommand {
    
    private final ItemShopsPlugin plugin = ItemShopsPlugin.getInstance();
    
    public GiveCommand() {
        super("give", "itemshops.give", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            plugin.getMessages().getMessage("commands.give.usage").format().send(sender);
            return;
        }
        
        String playerName = args[0];
        Integer amount = NumberUtil.parseInteger(args[1]);
        if (amount == null) {
            plugin.getMessages().getMessage("commands.give.usage").format().send(sender);
            return;
        }

        Player player = Bukkit.getPlayer(playerName);
        if (player == null || !player.isOnline()) {
            plugin.getMessages().getMessage("commands.give.offlinePlayer").format().send(sender);
            return;
        }
        
        plugin.getCoinsManager().addCoins(player, amount);
        plugin.getMessages().getMessage("commands.give.done")
                .addPlaceholder("coins", amount)
                .addPlaceholder("player", player.getName())
                .format()
                .send(player);
    }
    
}
