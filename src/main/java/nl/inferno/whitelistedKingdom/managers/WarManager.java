package nl.inferno.whitelistedKingdom.managers;

import nl.inferno.whitelistedKingdom.models.Kingdom;
import java.util.HashMap;
import java.util.Map;

public class WarManager {
    private final Map<String, String> activeWars = new HashMap<>();

    public boolean declareWar(Kingdom attacker, Kingdom defender) {
        if (isAtWar(attacker.getName()) || isAtWar(defender.getName()))
            return false;

        activeWars.put(attacker.getName(), defender.getName());
        return true;
    }

    public boolean isAtWar(String kingdomName) {
        return activeWars.containsKey(kingdomName) || activeWars.containsValue(kingdomName);
    }
}
