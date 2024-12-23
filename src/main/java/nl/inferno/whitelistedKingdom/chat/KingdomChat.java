package nl.inferno.whitelistedKingdom.chat;

import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class KingdomChat {
    private final Set<UUID> kingdomChat = new HashSet<>();
    private final Set<UUID> allyChat = new HashSet<>();

    public void toggleKingdomChat(Player player) {
        UUID uuid = player.getUniqueId();
        if (kingdomChat.contains(uuid)) {
            kingdomChat.remove(uuid);
        } else {
            kingdomChat.add(uuid);
            allyChat.remove(uuid);
        }
    }

    public boolean isInKingdomChat(UUID uuid) {
        return kingdomChat.contains(uuid);
    }
}
