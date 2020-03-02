package it.xquickglare.itemshops.shops.objects;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GUIProvider implements InventoryProvider {
    
    private final GUIInfo gui;
    
    @Override
    public void init(Player player, InventoryContents contents) {
        for (GUIItem item : gui.getItems())
            contents.set(item.getRow(), item.getColumn(), ClickableItem.of(item.getItem(), event -> item.getOnClick().accept(gui, event)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
    
}
