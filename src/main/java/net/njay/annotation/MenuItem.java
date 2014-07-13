package net.njay.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to define a slot click callback and to set a slots details
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuItem {

    /**
     * The slot number (in the Bukkit Inventory)
     *
     * @return the slot number in the Bukkit Inventory
     */
    int slot();

    /**
     * The slots ItemStack details
     *
     * @return the slots ItemStack details
     */
    ItemStackAnnotation item();

}
