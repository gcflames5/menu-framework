package net.njay.annotation;

import net.njay.Menu;
import org.bukkit.Material;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MenuInventory {

    /**
     * @return Number of slots in the menu inventory (must be divisible by 9)
     */
    int slots();

    /**
     * @return Name of the inventory (default "Menu")
     */
    String name() default "Menu";

    /**
     * @return Class of the menu to open once this menu has been closed (default none)
     */
    Class<? extends Menu> onClose() default Menu.class;

    /**
     * @return ItemStackAnnotation of the items that fill the unused spaces of inventory
     */
    ItemStackAnnotation filler() default @ItemStackAnnotation(material = Material.ENDER_PORTAL, name = " ");

}
