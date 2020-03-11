package io.alerium.itemshops.shops.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

@RequiredArgsConstructor @Getter
public class GUIItem {

    protected final ItemStack item;
    protected final int row;
    protected final int column;
    
    protected final BiConsumer<GUIInfo, InventoryClickEvent> onClick;
    
}
