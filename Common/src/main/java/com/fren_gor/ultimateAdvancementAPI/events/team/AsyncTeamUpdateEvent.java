package com.fren_gor.ultimateAdvancementAPI.events.team;

import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

import static com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils.validateTeamProgression;

/**
 * Called before a team member leaves or after they join a team.
 * <p>May be called asynchronously.
 *
 * @since 2.2.0
 */
public class AsyncTeamUpdateEvent extends Event {

    /**
     * The action that occurred.
     * <p>When a player moves in another team, an {@link Action#LEAVE} is always called before an {@link Action#JOIN}.
     */
    public enum Action {
        JOIN, LEAVE;
    }

    private final TeamProgression team;
    private final UUID playerUUID;
    private final Action action;

    /**
     * Creates a new {@code AsyncTeamUpdateEvent}.
     *
     * @param team The {@link TeamProgression} of the player's team. It must be valid (see {@link TeamProgression#isValid()}).
     * @param playerUUID The {@link UUID} of the player.
     * @param action The {@link Action} of the update.
     */
    public AsyncTeamUpdateEvent(@NotNull TeamProgression team, @NotNull UUID playerUUID, @NotNull Action action) {
        super(!Bukkit.isPrimaryThread());
        this.team = validateTeamProgression(team);
        this.playerUUID = Objects.requireNonNull(playerUUID, "UUID is null.");
        this.action = Objects.requireNonNull(action, "Action is null.");
    }

    /**
     * Gets the {@link TeamProgression} of the player's team.
     *
     * @return The {@link TeamProgression} of the player's team.
     */
    @NotNull
    public TeamProgression getTeamProgression() {
        return team;
    }

    /**
     * Gets the {@link UUID} of the player.
     *
     * @return The {@link UUID} of the player.
     */
    @NotNull
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    /**
     * Gets the {@link Action} of the update.
     *
     * @return The {@link Action} of the update.
     */
    @NotNull
    public Action getAction() {
        return action;
    }

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public String toString() {
        return "AsyncTeamUpdateEvent{" +
                "team=" + team +
                ", playerUUID=" + playerUUID +
                ", action=" + action +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AsyncTeamUpdateEvent that = (AsyncTeamUpdateEvent) o;

        if (!team.equals(that.team)) return false;
        if (!playerUUID.equals(that.playerUUID)) return false;
        return action == that.action;
    }

    @Override
    public int hashCode() {
        int result = team.hashCode();
        result = 31 * result + playerUUID.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }
}