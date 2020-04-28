package io.alerium.itemshops;

import fr.minuskube.inv.InventoryManager;
import io.alerium.itemshops.coins.CoinsManager;
import io.alerium.itemshops.commands.GiveAllCommand;
import io.alerium.itemshops.commands.GiveCommand;
import io.alerium.itemshops.commands.OpenCommand;
import io.alerium.itemshops.commands.ReloadCommand;
import io.alerium.itemshops.integrations.PlaceholderAPI;
import io.alerium.itemshops.shops.ShopManager;
import io.alerium.itemshops.utils.commands.Command;
import io.alerium.itemshops.utils.configuration.YAMLConfiguration;
import io.samdev.actionutil.ActionUtil;
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
    
    @Getter private ActionUtil actionUtil;
    
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
        
        actionUtil = ActionUtil.init(this);
    }
    
    private void registerCommands() {
        getCommand("itemshop").setExecutor(new Command(Arrays.asList(
                new GiveAllCommand(),
                new GiveCommand(),
                new OpenCommand(),
                new ReloadCommand()
        )));
    }
    
    private void registerIntegrations() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new PlaceholderAPI().register();
    }
    
}
