package nl.inferno.whitelistedKingdom;

import nl.inferno.whitelistedKingdom.commands.*;
import nl.inferno.whitelistedKingdom.database.DatabaseManager;
import nl.inferno.whitelistedKingdom.economy.KingdomEconomy;
import nl.inferno.whitelistedKingdom.managers.*;
import nl.inferno.whitelistedKingdom.chat.KingdomChatChannels;
import nl.inferno.whitelistedKingdom.gui.KingdomGUI;
import nl.inferno.whitelistedKingdom.power.KingdomPower;
import nl.inferno.whitelistedKingdom.tasks.AutoSaveTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class WhitelistedKingdom extends JavaPlugin {
    private DatabaseManager databaseManager;
    private KingdomManager kingdomManager;
    private TerritoryManager territoryManager;
    private WarManager warManager;
    private KingdomChatChannels chatManager;
    private KingdomGUI guiManager;
    private PermissionManager permissionManager;
    private KingdomEconomy economyManager;
    private KingdomPower powerManager;

    @Override
    public void onEnable() {
        // Config setup
        saveDefaultConfig();

        // Initialize managers
        initializeManagers();

        // Register commands
        registerCommands();

        // Register listeners
        registerListeners();

        // Start tasks
        startTasks();

        getLogger().info("WhitelistedKingdom has been enabled!");
    }

    @Override
    public void onDisable() {
        // Save all kingdom data
        kingdomManager.saveAllKingdoms();

        // Close database connection
        databaseManager.closeConnection();

        getLogger().info("WhitelistedKingdom has been disabled!");
    }

    private void initializeManagers() {
        databaseManager = new DatabaseManager(this);
        kingdomManager = new KingdomManager();
        territoryManager = new TerritoryManager();
        warManager = new WarManager();
        chatManager = new KingdomChatChannels();
        guiManager = new KingdomGUI();
        permissionManager = new PermissionManager();
        economyManager = new KingdomEconomy();
        powerManager = new KingdomPower();
    }

    private void registerCommands() {
        getCommand("kingdom").setExecutor(new KingdomCommand(this));
        getCommand("kingdomadmin").setExecutor(new KingdomAdminCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new KingdomListener(this), this);
    }

    private void startTasks() {
        // Auto-save task (every 15 minutes)
        new AutoSaveTask(this).runTaskTimer(this, 18000L, 18000L);

        // Power update task (every 5 minutes)
        new PowerUpdateTask(this).runTaskTimer(this, 6000L, 6000L);
    }

    // Getters for managers
    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public KingdomManager getKingdomManager() { return kingdomManager; }
    public TerritoryManager getTerritoryManager() { return territoryManager; }
    public WarManager getWarManager() { return warManager; }
    public KingdomChatChannels getChatManager() { return chatManager; }
    public KingdomGUI getGuiManager() { return guiManager; }
    public PermissionManager getPermissionManager() { return permissionManager; }
    public KingdomEconomy getEconomyManager() { return economyManager; }
    public KingdomPower getPowerManager() { return powerManager; }
    public KingdomGUI getKingdomGUI() {
        return guiManager;
    }
    public Object getKingdomPower() {
        return powerManager;
    }

}