package it.xquickglare.itemshops.integrations;

import it.xquickglare.itemshops.ItemShopsPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {
    
    private final ItemShopsPlugin plugin = ItemShopsPlugin.getInstance();
    
    @Override
    public String getIdentifier() {
        return "itemshops";
    }

    @Override
    public String getAuthor() {
        return "xQuickGlare";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String name) {
        if (player == null)
            return null;
        
        if (name.equalsIgnoreCase("coins"))
            return Integer.toString(plugin.getCoinsManager().getCoins(player));
        
        return null;
    }
}
