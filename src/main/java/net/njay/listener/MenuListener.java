package net.njay.listener;

import net.njay.Menu;
import net.njay.MenuFramework;
import net.njay.MenuManager;
import net.njay.annotation.IgnoreSlots;
import net.njay.annotation.MenuInventory;
import net.njay.annotation.MenuItem;
import net.njay.player.MenuPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        MenuPlayer player = MenuFramework.getPlayerManager().getPlayer((Player) e.getWhoClicked());
        MenuManager manager = player.getMenuManager();
        boolean cancel = true;
        if (manager.getCurrentMenu() != null && manager.getCurrentMenu().getInventory().getName().equals(e.getInventory().getName())) {
            MenuInventory menuInventory = player.getMenuManager().getCurrentMenu().getClass().getAnnotation(MenuInventory.class);
            if (e.getRawSlot() < e.getInventory().getSize()) {
                if (player.getMenuManager().getCurrentMenu().getClass().isAnnotationPresent(IgnoreSlots.class)) {
                    IgnoreSlots ignoreSlots = player.getMenuManager().getCurrentMenu().getClass().getAnnotation(IgnoreSlots.class);
                    for (int i : ignoreSlots.slots())
                        if (i == e.getRawSlot())
                            cancel = false;
                }
            } else
                cancel = false;
            if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT || e.getClick() == ClickType.DOUBLE_CLICK)
                cancel = true;
            e.setCancelled(cancel);
            for (Method m : MenuFramework.getRegistry().getLoadedMenus().get(manager.getCurrentMenu().getClass())) {
                MenuItem menuItem = m.getAnnotation(MenuItem.class);
                if (e.getRawSlot() == menuItem.slot()) try {
                    m.invoke(manager.getCurrentMenu(), player);
                    return;
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        MenuPlayer player = MenuFramework.getPlayerManager().getPlayer((Player) e.getPlayer());
        if (player.getMenuManager().getCurrentMenu() == null) return;
        if (!player.getMenuManager().getCurrentMenu().getInventory().getName().equals(e.getInventory().getName())) return;
        MenuInventory menuInventory = player.getMenuManager().getCurrentMenu().getClass().getAnnotation(MenuInventory.class);
        if (menuInventory == null || menuInventory.onClose() == null) return;
        if (menuInventory.onClose() != Menu.class) new MenuOpener(player, menuInventory.onClose());
    }

    public static class MenuOpener extends BukkitRunnable {

        private MenuPlayer player;
        private Class menuClass;

        public MenuOpener(MenuPlayer player, Class menuClass) {
            this.player = player;
            this.menuClass = menuClass;
            runTaskLater(MenuFramework.getRegistry().getPlugin(), 1);
        }

        @Override
        public void run() {
            for (HumanEntity h : player.getActiveMenu().getInventory().getViewers()) {
                if (h.getName().equalsIgnoreCase(player.getBukkit().getName())) return;
            }
            player.setActiveMenu(menuClass);
        }
    }
}
