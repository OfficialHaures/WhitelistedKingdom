package nl.inferno.whitelistedKingdom.shop;

import nl.inferno.whitelistedKingdom.WhitelistedKingdom;
import nl.inferno.whitelistedKingdom.managers.KingdomManager;
import nl.inferno.whitelistedKingdom.models.Kingdom;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class KingdomShop {
    private WhitelistedKingdom plugin;
    private final Map<String, ShopItem> shopItems = new HashMap<>();

    public class ShopItem {
        private final ItemStack item;
        private final double price;
        private final int level;

        public ShopItem(ItemStack item, double price, int level) {
            this.item = item;
            this.price = price;
            this.level = level;
        }
    }

    public boolean purchaseItem(String kingdomName, String itemId) {
        ShopItem item = shopItems.get(itemId);
        KingdomManager kingdom = plugin.getKingdomManager();
        if (kingdom.getBank() >= item.price && kingdom.getLevel() >= item.level) {
            kingdom.setBank(kingdom.getBank() - item.price);
            return true;
        }
        return false;
    }
}
