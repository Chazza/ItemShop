package it.xquickglare.itemshops;

import fr.minuskube.inv.InventoryManager;
import it.xquickglare.itemshops.coins.CoinsManager;
import it.xquickglare.itemshops.commands.GiveAllCommand;
import it.xquickglare.itemshops.commands.GiveCommand;
import it.xquickglare.itemshops.commands.OpenCommand;
import it.xquickglare.itemshops.integrations.PlaceholderAPI;
import it.xquickglare.itemshops.shops.ShopManager;
import it.xquickglare.itemshops.utils.commands.Command;
import it.xquickglare.itemshops.utils.configuration.YAMLConfiguration;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class ItemShopsPlugin extends JavaPlugin {

    @Getter private static ItemShopsPlugin instance;
    
    @Getter private YAMLConfiguration configuration;
    @Getter private YAMLConfiguration messages;
    
    @Getter private InventoryManager inventoryManager;
    
    @Getter private CoinsManager coinsManager;
    @Getter private ShopManager shopManager;
    
    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        instance = this;
        
        registerConfigs();
        registerInstances();
        registerCommands();
        registerIntegrations();
        
        getLogger().info("Plugin enabled in " + (System.currentTimeMillis()-time) + "ms");
    }

    @Override
    public void onDisable() {
        
    }
    
    private void registerConfigs() {
        configuration = new YAMLConfiguration(this, "config");
        messages = new YAMLConfiguration(this, "messages");
    }
    
    private void registerInstances() {
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        coinsManager = new CoinsManager();
        coinsManager.enable();

        shopManager = new ShopManager();
        shopManager.enable();
    }
    
    private void registerCommands() {
        getCommand("itemshop").setExecutor(new Command(messages.getMessage("commands.help").format(), Arrays.asList(
                new GiveAllCommand(),
                new GiveCommand(),
                new OpenCommand()
        )));
    }
    
    private void registerIntegrations() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new PlaceholderAPI().register();
    }
    
}
