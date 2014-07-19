package net.njay;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.njay.annotation.*;
import net.njay.listener.MenuListener;
import net.njay.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class MenuRegistry {

    private Map<Class<? extends Menu>, List<Method>> loadedMenus = Maps.newHashMap();
    private Map<Class<? extends Menu>, List<Method>> loadedPreprocessors = Maps.newHashMap();
    private Plugin plugin;

    /**
     * Gets the Bukkit Plugin
     *
     * @return the Plugin
     */
    public Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * Initializes the framework
     *
     * @param plugin the plugin to initialize framework for
     * @param menus  array of menus to register
     */
    public MenuRegistry(Plugin plugin, Class... menus) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new MenuListener(), plugin);
        for (Class menu : menus)
            addMenu(menu);
    }

    /**
     * Adds the Menu to the loaded list of Menus
     *
     * @param clazz a class which extends Menu
     */
    public void addMenu(Class<? extends Menu> clazz) {
        List<Method> methods = Lists.newArrayList();
        List<Method> preProcessors = Lists.newArrayList();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(MenuItem.class)) {
                methods.add(m);
            }else if (m.isAnnotationPresent(PreProcessor.class)){
                preProcessors.add(m);
            }
        }

        // If the class to add has a nested annotation
        if (clazz.isAnnotationPresent(NestedMenu.class)) {
            Annotation annotation = clazz.getAnnotation(NestedMenu.class);
            NestedMenu nestedAnnotation = (NestedMenu) annotation;
            // iterate the array of classes and register them as well
            for (Class clazz0 : nestedAnnotation.value()) {
                addMenu(clazz0);
            }
        }
        loadedMenus.put(clazz, methods);
        loadedPreprocessors.put(clazz, preProcessors);
    }

    /**
     * Gets the loaded Menus
     *
     * @return a Map of Menus and their callback methods
     */
    public Map<Class<? extends Menu>, List<Method>> getLoadedMenus() {
        return loadedMenus;
    }

    /**
     * Creates a Bukkit Inventory for a Menu class
     *
     * @param clazz
     * @return
     */
    public Inventory generateFreshMenu(Menu menu, Class clazz) {
        MenuInventory menuInv = (MenuInventory) clazz.getAnnotation(MenuInventory.class);
        Inventory inv = Bukkit.createInventory(null, menuInv.slots(), menuInv.name());
        for (int i = 0; i < inv.getSize(); i++)
            inv.setItem(i, ItemUtils.annotationToItemStack(menuInv.filler()));
        for (Method m : loadedMenus.get(clazz)) {
            MenuItem menuItem = m.getAnnotation(MenuItem.class);
            ItemStack item = ItemUtils.annotationToItemStack(menuItem.item());
            inv.setItem(menuItem.slot(), item);
        }
        if (clazz.isAnnotationPresent(IgnoreSlots.class)) {
            IgnoreSlots ignoreSlots = (IgnoreSlots) clazz.getAnnotation(IgnoreSlots.class);
            if (ignoreSlots.slots().length == ignoreSlots.items().length) {
                for (int i = 0; i < ignoreSlots.slots().length; i++) {
                    inv.setItem(ignoreSlots.slots()[i], ItemUtils.annotationToItemStack(ignoreSlots.items()[i]));
                }
            }
        }
        for (Method m : loadedPreprocessors.get(clazz)){
            try {
                m.invoke(menu, inv);
            } catch (IllegalAccessException e) {
                System.out.println("All @PreProcessor methods must be static!");
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                System.out.println("All @PreProcessor methods must take 1 Inventory as a parameter!");
                e.printStackTrace();
            }
        }
        return inv;
    }

}
