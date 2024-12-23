package nl.inferno.whitelistedKingdom.combat;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KingdomCombatSystem {
    private final Map<UUID, CombatStats> playerStats = new HashMap<>();

    public class CombatStats {
        private int kills;
        private int deaths;
        private double damageDealt;
        private int winStreak;

        public void addKill() {
            kills++;
            winStreak++;
        }
    }

    public void handleKingdomCombat(Player attacker, Player victim) {
        CombatStats attackerStats = playerStats.computeIfAbsent(
            attacker.getUniqueId(), k -> new CombatStats()
        );
        attackerStats.addKill();
    }
}
