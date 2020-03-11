package io.alerium.itemshops.shops.objects;

import fr.minuskube.inv.SmartInventory;
import io.alerium.itemshops.ItemShopsPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GUIInfo {

    @Getter protected final String name;

    protected final String title;
    protected final int rows;
    
    @Getter protected final List<GUIItem> items;
    
    private SmartInventory inventory;

    /**
     * This method returns the SmartInventory, if not created it creates it
     * @return The SmartInventory
     */
    public SmartInventory getInventory() {
        if (inventory == null) {
            inventory = SmartInventory.builder()
                    .id(name)
                    .title(title)
                    .size(rows, 9)
                    .provider(new GUIProvider(this))
                    .manager(ItemShopsPlugin.getInstance().getInventoryManager())
                    .build();
        }

        return inventory;
    }
    
}
