package nl.inferno.whitelistedKingdom.managers;

import nl.inferno.whitelistedKingdom.models.Kingdom;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KingdomManager {
    private final Map<String, Kingdom> kingdoms = new HashMap<>();
    private final Map<UUID, Kingdom> playerKingdoms = new HashMap<>();
    

    public boolean createKingdom(String name, UUID owner) {
        if (kingdoms.containsKey(name)) return false;
        Kingdom kingdom = new Kingdom(name, owner);
        kingdoms.put(name, kingdom);
        playerKingdoms.put(owner, kingdom);
        return true;
    }

    public Kingdom getKingdom(String name) {
        return kingdoms.get(name);
    }

    public Kingdom getPlayerKingdom(UUID player) {
        return playerKingdoms.get(player);
    }

    public void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        String sql = "SELECT k.* FROM kingdoms k " +
                    "JOIN kingdom_members km ON k.id = km.kingdom_id " +
                    "WHERE km.player_uuid = ?";

        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Kingdom kingdom = new Kingdom(
                    rs.getString("name"),
                    UUID.fromString(rs.getString("owner"))
                );
                kingdom.setId(rs.getInt("id"));
                kingdom.setBank(rs.getDouble("bank"));
                kingdom.setLevel(rs.getInt("level"));

                // Load additional kingdom data
                loadKingdomMembers(kingdom);
                loadKingdomClaims(kingdom);

                // Cache the loaded kingdom
                kingdoms.put(kingdom.getName(), kingdom);
                playerKingdoms.put(uuid, kingdom);
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to load player data for " + player.getName() + ": " + e.getMessage());
        }
    }
}
