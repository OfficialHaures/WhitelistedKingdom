package nl.inferno.whitelistedKingdom.managers;

import org.bukkit.Chunk;
import java.util.HashMap;
import java.util.Map;

public class TerritoryManager {
    private final Map<String, String> claimedChunks = new HashMap<>();

    public boolean claimChunk(String kingdomName, Chunk chunk) {
        String chunkKey = getChunkKey(chunk);
        if (claimedChunks.containsKey(chunkKey)) return false;

        claimedChunks.put(chunkKey, kingdomName);
        return true;
    }

    private String getChunkKey(Chunk chunk) {
        return chunk.getWorld().getName() + ":" + chunk.getX() + ":" + chunk.getZ();
    }
}
