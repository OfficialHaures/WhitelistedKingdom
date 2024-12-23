package nl.inferno.whitelistedKingdom.api;

import nl.inferno.whitelistedKingdom.models.Kingdom;
import nl.inferno.whitelistedKingdom.WhitelistedKingdom;

public class KingdomAPI {
    private final WhitelistedKingdom plugin;

    public KingdomAPI(WhitelistedKingdom plugin) {
        this.plugin = plugin;
    }

    public Kingdom getKingdom(String name) {
        return plugin.getKingdomManager().getKingdom(name);
    }

    public boolean isKingdomOwner(UUID player, String kingdomName) {
        Kingdom kingdom = getKingdom(kingdomName);
        return kingdom != null && kingdom.getOwner().equals(player);
    }
}
