package nl.inferno.whitelistedKingdom.events;

import nl.inferno.whitelistedKingdom.models.Kingdom;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class KingdomCreateEvent extends KingdomEvent implements Cancellable {
    private boolean cancelled;
    private final Player creator;

    public KingdomCreateEvent(Kingdom kingdom, Player creator) {
        super(kingdom);
        this.creator = creator;
    }

    public Player getCreator() {
        return creator;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
