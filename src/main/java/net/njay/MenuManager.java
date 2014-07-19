package net.njay;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

public class MenuManager {

    private
    @Nullable
    Menu currentMenu = null; //what is this horrific formatting
    private
    @Nullable
    Menu previousMenu = null;

    private List<Menu> menus;

    public MenuManager() {
        menus = Lists.newArrayList();
    }

    /**
     * Sets a menu instance as the current menu, if the menu has not been loaded before, it is sent to the MenuRegistry to be generated
     *
     * @param menu Menu instance that you which to set as the current menu
     */
    public void setActiveMenu(Menu menu) {
        previousMenu = currentMenu;
        if (hasMenu(menu.getClass())) {
            currentMenu = getMenu(menu.getClass());
        } else {
            currentMenu = menu;
            currentMenu.setInventory(MenuFramework.getRegistry().generateFreshMenu(menu, menu.getClass()));
            menus.add(currentMenu);
        }
    }

    /**
     * Sets a menu instance as the current menu and removes any other saved instances
     *
     * @param menu Menu instance that you which to set as the current menu
     * @param regenerate Whether or not to regenerate the menu from scratch
     */
    public void setActiveMenuAndReplace(Menu menu, boolean regenerate) {
        previousMenu = currentMenu;
        currentMenu = menu;
        if (regenerate)
            currentMenu.setInventory(MenuFramework.getRegistry().generateFreshMenu(menu, menu.getClass()));
        Menu toRemove = getMenu(menu.getClass());
        if (toRemove != null) menus.remove(toRemove);
        menus.add(currentMenu);
    }

    /**
     * Set the instance of a previously opened menu as the current active menu
     * <p/>
     * </p>(You must be sure that the menu has previously been opened or else the method does not perform anything)
     *
     * @param clazz Class of the menu that you which to set as the active menus
     */
    public void setPreviouslyOpenedActiveMenu(Class clazz) {
        if (hasMenu(clazz)) setActiveMenu(getMenu(clazz));
    }

    public Menu getMenu(Class clazz) {
        for (Menu m : menus) {
            if (m.getClass().equals(clazz)) return m;
        }
        return null;
    }

    public boolean hasMenu(Class clazz) {
        for (Menu m : menus) {
            if (clazz.equals(m.getClass())) return true;
        }
        return false;
    }

    /**
     * Gets the currently opened menu
     *
     * @return Menu instance
     */
    public Menu getCurrentMenu() {
        return this.currentMenu;
    }

    /**
     * Gets the previously opened menu
     *
     * @return Previous menu
     */
    public Menu getPreviousMenu() {
        return this.previousMenu;
    }
}
