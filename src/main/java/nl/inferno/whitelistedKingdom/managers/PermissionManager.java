package nl.inferno.whitelistedKingdom.managers;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PermissionManager {
    private final Map<String, Map<UUID, EnumSet<KingdomPermission>>> permissions = new HashMap<>();

    public enum KingdomPermission {
        INVITE_MEMBERS,
        KICK_MEMBERS,
        CLAIM_LAND,
        ACCESS_BANK,
        DECLARE_WAR,
        MAKE_ALLIANCE,
        MANAGE_RANKS
    }

    public void setPermission(String kingdom, UUID player, KingdomPermission permission) {
        permissions.computeIfAbsent(kingdom, k -> new HashMap<>())
                  .computeIfAbsent(player, p -> EnumSet.noneOf(KingdomPermission.class))
                  .add(permission);
    }
}
