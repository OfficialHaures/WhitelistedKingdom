package nl.inferno.whitelistedKingdom.events;

import nl.inferno.whitelistedKingdom.models.Kingdom;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class KingdomEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    protected final Kingdom kingdom;

    public KingdomEvent(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
