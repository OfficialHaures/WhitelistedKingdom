package nl.inferno.whitelistedKingdom.visualization;

import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class TerritoryVisualizer {
    public void showTerritory(Player player, Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (x == 0 || x == 15 || z == 0 || z == 15) {
                    player.spawnParticle(
                        Particle.FLAME,
                        chunk.getBlock(x, player.getLocation().getBlockY(), z).getLocation(),
                        1, 0, 0, 0, 0
                    );
                }
            }
        }
    }
}
