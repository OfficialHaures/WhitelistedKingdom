package nl.inferno.whitelistedKingdom.chat;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KingdomChatChannels {
    private final Map<UUID, ChatChannel> playerChannels = new HashMap<>();

    public enum ChatChannel {
        KINGDOM,
        ALLY,
        WAR,
        GLOBAL
    }

    public void sendKingdomMessage(Player player, String message) {
        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom != null) {
            kingdom.broadcastMessage("§6[Kingdom] " + player.getName() + ": §f" + message);
        }
    }
}
