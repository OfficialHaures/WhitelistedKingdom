package nl.inferno.whitelistedKingdom.levels;

import nl.inferno.whitelistedKingdom.models.Kingdom;

public class KingdomLevelSystem {
    private static final int MAX_LEVEL = 100;
    private final Map<Integer, LevelRequirements> levelRequirements = new HashMap<>();

    public class LevelRequirements {
        private final int experienceNeeded;
        private final double cost;
        private final int memberRequirement;

        public LevelRequirements(int exp, double cost, int members) {
            this.experienceNeeded = exp;
            this.cost = cost;
            this.memberRequirement = members;
        }
    }

    public boolean canLevelUp(Kingdom kingdom) {
        int nextLevel = kingdom.getLevel() + 1;
        if (nextLevel > MAX_LEVEL) return false;

        LevelRequirements req = levelRequirements.get(nextLevel);
        return kingdom.getExperience() >= req.experienceNeeded
            && kingdom.getBank() >= req.cost;
    }
}
