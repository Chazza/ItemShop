package it.xquickglare.itemshops.shops.objects.shops;

import it.xquickglare.itemshops.shops.objects.GUIInfo;
import it.xquickglare.itemshops.shops.objects.GUIItem;
import lombok.Getter;

import java.util.List;

public class ShopGUI extends GUIInfo {
    
    @Getter private final boolean closeOnBuy;

    public ShopGUI(String name, String title, int rows, List<GUIItem> items, boolean closeOnBuy) {
        super(name, title, rows, items);
        this.closeOnBuy = closeOnBuy;
    }
    
}
