package nl.inferno.whitelistedKingdom.database;

import nl.inferno.whitelistedKingdom.models.Kingdom;
import java.sql.*;
import java.util.UUID;

public class DatabaseQueries {
    private final Connection connection;

    public DatabaseQueries(Connection connection) {
        this.connection = connection;
    }

    public void saveKingdom(Kingdom kingdom) {
        String sql = "INSERT INTO kingdoms (name, owner, bank, level) VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE bank = ?, level = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, kingdom.getName());
            stmt.setString(2, kingdom.getOwner().toString());
            stmt.setDouble(3, kingdom.getBank());
            stmt.setInt(4, kingdom.getLevel());
            stmt.setDouble(5, kingdom.getBank());
            stmt.setInt(6, kingdom.getLevel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Kingdom loadKingdom(String name) {
        String sql = "SELECT * FROM kingdoms WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Kingdom kingdom = new Kingdom(
                    rs.getString("name"),
                    UUID.fromString(rs.getString("owner"))
                );
                kingdom.setBank(rs.getDouble("bank"));
                kingdom.setLevel(rs.getInt("level"));
                return kingdom;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
