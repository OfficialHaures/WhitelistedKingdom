package nl.inferno.whitelistedKingdom.economy;

import nl.inferno.whitelistedKingdom.models.Kingdom;
import org.bukkit.plugin.RegisteredServiceProvider;
import net.milkbowl.vault.economy.Economy;

public class KingdomEconomy {
    private Economy economy;
    private final Map<String, Double> kingdomTaxes = new HashMap<>();

    public boolean depositToKingdom(Kingdom kingdom, double amount) {
        kingdom.setBank(kingdom.getBank() + amount);
        return true;
    }

    public boolean withdrawFromKingdom(Kingdom kingdom, double amount) {
        if (kingdom.getBank() < amount) return false;
        kingdom.setBank(kingdom.getBank() - amount);
        return true;
    }
}
