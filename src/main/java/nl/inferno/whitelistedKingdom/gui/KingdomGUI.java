package nl.inferno.whitelistedKingdom.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KingdomGUI {
    private final WhitelistedKingdom plugin;

    public void openMainMenu(Player player) {
        Inventory gui = Bukkit.createInventory(null, 54, "Kingdom Menu");

        gui.setItem(11, createMenuItem("Kingdom Info"));
        gui.setItem(13, createMenuItem("Members"));
        gui.setItem(15, createMenuItem("Territory"));
        gui.setItem(29, createMenuItem("Bank"));
        gui.setItem(31, createMenuItem("Wars"));
        gui.setItem(33, createMenuItem("Alliances"));

        player.openInventory(gui);
    }
}
