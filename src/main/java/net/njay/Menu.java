package net.njay;

import org.bukkit.inventory.Inventory;

/**
 * Class to represent a Menu
 */
public class Menu {
    /**
     * The Bukkit Inventory for this Menu
     */
    protected Inventory inv;

    /**
     * The MenuManager for this Menu
     */
    protected MenuManager manager;

    public Menu(MenuManager manager, Inventory inv) {
        this.manager = manager;
        this.inv = inv;
    }

    /**
     * Sets this Menu's Bukkit Inventory
     *
     * @param inv Bukkit Inventory
     */
    public void setInventory(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Gets this Menu's Bukkit Inventory
     *
     * @return Bukkit Inventory
     */
    public Inventory getInventory() {
        return this.inv;
    }
}
