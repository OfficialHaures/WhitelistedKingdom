package nl.inferno.whitelistedKingdom;

import nl.inferno.whitelistedKingdom.models.Kingdom;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.Player;

public class KingdomListener implements Listener {
    private final WhitelistedKingdom plugin;

    public KingdomListener(WhitelistedKingdom plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getKingdomManager().loadPlayerData(player);
        plugin.getKingdomPower().updatePlayerPower(player);
        plugin.getKingdomGUI().sendWelcomeMessage(player);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChatChannel channel = plugin.getChatManager().getPlayerChannel(player);

        switch (channel) {
            case KINGDOM -> handleKingdomChat(event);
            case ALLY -> handleAllyChat(event);
            case WAR -> handleWarChat(event);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location loc = event.getBlock().getLocation();

        if (!plugin.getTerritoryManager().canBuildHere(player, loc)) {
            event.setCancelled(true);
            player.sendMessage("§cThis territory belongs to another kingdom!");
            return;
        }

        // Resource collection system
        plugin.getResourceManager().handleResourceBreak(event);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker) || !(event.getEntity() instanceof Player victim)) {
            return;
        }

        Kingdom attackerKingdom = plugin.getKingdomManager().getPlayerKingdom(attacker.getUniqueId());
        Kingdom victimKingdom = plugin.getKingdomManager().getPlayerKingdom(victim.getUniqueId());

        if (attackerKingdom == null || victimKingdom == null) return;

        if (attackerKingdom.equals(victimKingdom) || plugin.getRelationManager().areAllies(attackerKingdom, victimKingdom)) {
            event.setCancelled(true);
            return;
        }

        plugin.getCombatManager().handlePvP(attacker, victim);
    }
}
