package nl.inferno.whitelistedKingdom.upgrades;

import nl.inferno.whitelistedKingdom.models.Kingdom;
import java.util.HashMap;
import java.util.Map;

public class KingdomUpgrades {
    private final Map<String, Map<UpgradeType, Integer>> kingdomUpgrades = new HashMap<>();

    public enum UpgradeType {
        MEMBER_LIMIT(5000, 5),
        CLAIM_LIMIT(10000, 10),
        POWER_BOOST(15000, 3),
        BANK_CAPACITY(20000, 5);

        private final int cost;
        private final int maxLevel;

        UpgradeType(int cost, int maxLevel) {
            this.cost = cost;
            this.maxLevel = maxLevel;
        }
    }

    public boolean purchaseUpgrade(Kingdom kingdom, UpgradeType type) {
        Map<UpgradeType, Integer> upgrades = kingdomUpgrades.computeIfAbsent(
            kingdom.getName(), k -> new HashMap<>()
        );

        int currentLevel = upgrades.getOrDefault(type, 0);
        if (currentLevel >= type.maxLevel) return false;

        if (kingdom.getBank() >= type.cost) {
            kingdom.setBank(kingdom.getBank() - type.cost);
            upgrades.put(type, currentLevel + 1);
            return true;
        }
        return false;
    }
}
