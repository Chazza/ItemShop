package io.alerium.itemshops.shops;

import io.alerium.itemshops.ItemShopsPlugin;
import io.alerium.itemshops.shops.objects.GUIInfo;
import io.alerium.itemshops.shops.objects.GUIItem;
import io.alerium.itemshops.shops.objects.shops.ShopGUI;
import io.alerium.itemshops.shops.objects.shops.ShopItem;
import io.alerium.itemshops.utils.configuration.ItemBuilder;
import io.alerium.itemshops.utils.configuration.Message;
import io.alerium.itemshops.utils.configuration.YAMLConfiguration;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShopManager {
    
    private final ItemShopsPlugin plugin = ItemShopsPlugin.getInstance();
    private final File folder = new File(plugin.getDataFolder(), "shops");
    private final List<ShopGUI> shops = new ArrayList<>();
    
    @Getter private GUIInfo mainGui;
    
    public void enable() {
        if (!folder.exists()) {
            folder.mkdir();
            plugin.saveResource("shops/armor.yml", false);
        }

        reload();
    }

    /**
     * This method gets a ShopGUI with the specified name
     * @param name The name of the ShopGUI
     * @return The ShopGUI, if not present null
     */
    public ShopGUI getShopGui(String name) {
        for (ShopGUI shop : shops) {
            if (shop.getName().equalsIgnoreCase(name))
                return shop;
        }
        
        return null;
    }

    /**
     * This method reloads all the shops from the config files
     */
    public void reload() {
        shops.clear();
        
        for (File file : folder.listFiles())
            loadShop(file.getName().replaceAll("\\.yml", ""));

        loadMainGui();
    }

    /**
     * This method loads the main GUI
     */
    private void loadMainGui() {
        YAMLConfiguration config = plugin.getConfiguration();
        
        Message title = config.getMessage("shop-menu.inventory.title");
        int rows = config.getConfig().getInt("shop-menu.inventory.rows");

        List<GUIItem> items = new ArrayList<>();

        ConfigurationSection section = config.getConfig().getConfigurationSection("shop-menu.items");
        for (String key : section.getKeys(false)) {
            String shopName = section.getString(key + ".shop");
            items.add(new GUIItem(
                    ItemBuilder.fromConfig(config, "shop-menu.items." + key).build(),
                    section.getInt(key + ".row"),
                    section.getInt(key + ".column"),
                    (info, event) -> {
                        ShopGUI shop = getShopGui(shopName);
                        if (shop == null)
                            return;
                        
                        shop.getInventory().open((Player) event.getWhoClicked());
                    }
            ));
        }
        
        mainGui = new GUIInfo("main-gui", title.format().toString(), rows, items);
    }

    /**
     * This method loads a specific ShopGUI
     * @param name The name of the ShopGUI
     */
    private void loadShop(String name) {
        YAMLConfiguration config = new YAMLConfiguration(plugin, "shops/" + name);

        Message title = config.getMessage("inventory.title");
        int rows = config.getConfig().getInt("inventory.rows");
        boolean close = config.getConfig().getBoolean("inventory.close-on-buy");

        List<GUIItem> items = new ArrayList<>();

        items.add(new GUIItem(
                ItemBuilder.fromConfig(config, "back-button").build(),
                config.getConfig().getInt("back-button.row"),
                config.getConfig().getInt("back-button.column"),
                (info, event) -> mainGui.getInventory().open((Player) event.getWhoClicked())
        ));

        ConfigurationSection section = config.getConfig().getConfigurationSection("items");
        for (String key : section.getKeys(false)) {
            items.add(new ShopItem(
                    ItemBuilder.fromConfig(config, "items." + key).build(),
                    section.getInt(key + ".row"),
                    section.getInt(key + ".column"),
                    section.getInt(key + ".cost"),
                    section.getStringList(key + ".buy-commands")
            ));
        }
        
        shops.add(new ShopGUI(name, title.format().toString(), rows, items, close));
    }
    
}
