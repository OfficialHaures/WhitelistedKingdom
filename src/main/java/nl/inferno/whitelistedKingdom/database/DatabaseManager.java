package nl.inferno.whitelistedKingdom.database;

import nl.inferno.whitelistedKingdom.WhitelistedKingdom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private final WhitelistedKingdom plugin;

    public DatabaseManager(WhitelistedKingdom plugin) {
        this.plugin = plugin;
        initTables();
    }

    private void initTables() {
        createKingdomsTable();
        createMembersTable();
        createClaimsTable();
        createWarTable();
        createAlliancesTable();
    }

    private void createKingdomsTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS kingdoms (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(32) UNIQUE NOT NULL,
                owner VARCHAR(36) NOT NULL,
                home_world VARCHAR(32),
                home_x DOUBLE,
                home_y DOUBLE,
                home_z DOUBLE,
                bank DOUBLE DEFAULT 0,
                level INT DEFAULT 1,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;
        executeUpdate(sql);
    }

    // Additional table creation methods
}
