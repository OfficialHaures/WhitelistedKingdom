package nl.inferno.whitelistedKingdom.models;

import org.bukkit.Location;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Kingdom {
    private int id;
    private String name;
    private UUID owner;
    private Location home;
    private Set<UUID> members;
    private Set<UUID> moderators;
    private double bank;
    private int level;

    public Kingdom(String name, UUID owner) {
        this.name = name;
        this.owner = owner;
        this.members = new HashSet<>();
        this.moderators = new HashSet<>();
        this.bank = 0;
        this.level = 1;
    }

    // Getters and setters
}
