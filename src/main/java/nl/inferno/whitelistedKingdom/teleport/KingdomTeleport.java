package nl.inferno.whitelistedKingdom.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KingdomTeleport {
    private final Map<UUID, Long> teleportCooldowns = new HashMap<>();
    private static final int COOLDOWN_SECONDS = 300;

    public void teleportToKingdom(Player player, Kingdom kingdom) {
        if (isOnCooldown(player)) {
            player.sendMessage("§cYou must wait before teleporting again!");
            return;
        }

        Location home = kingdom.getHome();
        player.teleport(home);
        setTeleportCooldown(player);
    }
}
