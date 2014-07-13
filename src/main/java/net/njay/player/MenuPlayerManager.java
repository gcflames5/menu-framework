package net.njay.player;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MenuPlayerManager {
    // Mapping of Bukkit players to Menu Players
    private HashMap<Player, MenuPlayer> players = Maps.newHashMap();

    /**
     * Gets the MenuPlayer by player name
     *
     * @param p The players name
     * @return The MenuPlayer
     */
    public MenuPlayer getPlayer(String p) {
        return getPlayer(Bukkit.getPlayerExact(p));
    }

    /**
     * Gets the Menu from player
     *
     * @param p The player
     * @return The MenuPlayer from Player
     */
    public MenuPlayer getPlayer(Player p) {
        if (players.containsKey(p)) return players.get(p);
        MenuPlayer pl = new MenuPlayer(p);
        players.put(p, pl);
        return pl;
    }

    /**
     * Removes the player from the list from player
     *
     * @param p The player
     */
    public void removePlayer(Player p) {
        players.remove(p);
    }
}
