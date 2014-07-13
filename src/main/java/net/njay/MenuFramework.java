package net.njay;

import net.njay.player.MenuPlayerManager;

public class MenuFramework {

    private static MenuRegistry registry;
    private static MenuPlayerManager playerManager;

    /**
     * Enable the Menu Framework with a default player manager using MenuPlayer
     *
     * @param registry Registry to initialize MenuFramework
     */
    public static void enable(MenuRegistry registry){
        MenuFramework.registry = registry;
        MenuFramework.playerManager = new MenuPlayerManager();
    }

    /**
     * Enable the Menu Framework with a custom player manager using a extension of MenuPlayer
     *
     * @param registry Registry to initialize MenuFramework
     * @param playerManager Custom Player Manager
     */
    public static void enable(MenuRegistry registry, MenuPlayerManager playerManager){
        MenuFramework.registry = registry;
        MenuFramework.playerManager = playerManager;
    }

    /**
     * @return Current menu registry
     */
    public static MenuRegistry getRegistry(){ return registry; }

    /**
     * @return Current player manager
     */
    public static MenuPlayerManager getPlayerManager(){ return playerManager; }

}
