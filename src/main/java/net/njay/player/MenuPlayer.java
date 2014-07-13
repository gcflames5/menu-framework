package net.njay.player;

import net.njay.Menu;
import net.njay.MenuManager;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * Class to represent a custom Player wrapper
 */
public class MenuPlayer {

    /**
     * The MenuManager
     */
    protected
    @Nullable
    MenuManager menuManager;

    /**
     * The Bukkit Player
     */
    protected Player bukkit;

    /**
     * Default constructor for MenuPlayer
     *
     * @param bukkit the Bukkit Player
     */
    public MenuPlayer(Player bukkit) {
        this.bukkit = bukkit;
        resetManager();
    }

    /**
     * Gets the Bukkit player
     *
     * @return the Bukkit player
     */
    public Player getBukkit() {
        return this.bukkit;
    }

    /**
     * Gets the menu manager instance associated with the player
     *
     * @return the MenuManager instance
     */
    public MenuManager getMenuManager() {
        return menuManager;
    }

    /**
     * Show a menu to this player
     *
     * @param menu Menu you wish to show
     */
    public void setActiveMenu(Menu menu) {
        menuManager.setActiveMenu(menu);
        getBukkit().openInventory(menuManager.getCurrentMenu().getInventory());
    }

    /**
     * Sets the active menu for this player
     *
     * @param clazz class of the Menu
     */
    public void setActiveMenu(Class<? extends Menu> clazz) {
        menuManager.setPreviouslyOpenedActiveMenu(clazz);
        getBukkit().openInventory(menuManager.getCurrentMenu().getInventory());
    }

    /**
     * Reset the menu manager, and erases all menu history
     */
    public void resetManager() {
        menuManager = new MenuManager();
    }

}