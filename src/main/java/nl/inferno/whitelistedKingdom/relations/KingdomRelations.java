package nl.inferno.whitelistedKingdom.relations;

import java.util.HashMap;
import java.util.Map;

public class KingdomRelations {
    private final Map<String, Map<String, Relation>> kingdomRelations = new HashMap<>();

    public enum Relation {
        ALLY,
        ENEMY,
        NEUTRAL,
        TRUCE
    }

    public void setRelation(String kingdom1, String kingdom2, Relation relation) {
        kingdomRelations.computeIfAbsent(kingdom1, k -> new HashMap<>())
                       .put(kingdom2, relation);
        kingdomRelations.computeIfAbsent(kingdom2, k -> new HashMap<>())
                       .put(kingdom1, relation);
    }
}
