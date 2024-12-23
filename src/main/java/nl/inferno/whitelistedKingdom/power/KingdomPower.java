package nl.inferno.whitelistedKingdom.power;

import nl.inferno.whitelistedKingdom.models.Kingdom;

import java.util.HashMap;
import java.util.Map;

public class KingdomPower {
    private final Map<String, Double> kingdomPower = new HashMap<>();
    private static final double BASE_POWER = 10.0;
    private static final double MAX_POWER = 100.0;

    public void calculatePower(Kingdom kingdom) {
        double power = BASE_POWER;
        power += kingdom.getMembers().size() * 2;
        power += kingdom.getClaimedChunks().size();
        power = Math.min(power, MAX_POWER);
        kingdomPower.put(kingdom.getName(), power);
    }
}
