package nl.inferno.whitelistedKingdom.tasks;

import nl.inferno.whitelistedKingdom.WhitelistedKingdom;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSaveTask extends BukkitRunnable {
    private final WhitelistedKingdom plugin;

    public AutoSaveTask(WhitelistedKingdom plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getKingdomManager().saveAllKingdoms();
    }
}
