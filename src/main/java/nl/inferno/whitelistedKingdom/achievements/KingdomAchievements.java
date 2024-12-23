package nl.inferno.whitelistedKingdom.achievements;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class KingdomAchievements {
    private final Map<String, EnumSet<Achievement>> kingdomAchievements = new HashMap<>();

    public enum Achievement {
        FIRST_CLAIM("Claim your first territory", 100),
        WAR_WINNER("Win your first kingdom war", 500),
        RICH_KINGDOM("Accumulate 1,000,000 in bank", 1000),
        POPULATION_MASTER("Reach 50 kingdom members", 750);

        private final String description;
        private final int rewardPoints;

        Achievement(String description, int rewardPoints) {
            this.description = description;
            this.rewardPoints = rewardPoints;
        }
    }

    public void awardAchievement(String kingdomName, Achievement achievement) {
        kingdomAchievements.computeIfAbsent(kingdomName, k -> EnumSet.noneOf(Achievement.class))
                          .add(achievement);
    }
}
