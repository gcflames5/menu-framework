package net.njay.annotation;

import org.bukkit.Material;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A slots ItemStack details
 * </p>
 * As Java annotation members are restricted, an ItemStack's details are encapsulated here
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemStackAnnotation {
    /**
     * The Material type for this slot
     *
     * @return the Material type
     */
    Material material();

    /**
     * The amount of Material for this slot
     *
     * @return the amount of Material for this slot, defaults to 1
     */
    int amount() default 1;

    /**
     * The (display) name of this item
     *
     * @return the display name
     */
    String name();

    /**
     * The lore of the item
     *
     * @return the lore
     */
    String[] lore() default {};

    /**
     * The durability of the item
     * </p>
     * Can be used to color materials like wool
     *
     * @return the durability
     */
    short durability() default 0;

    /**
     * Any enchantments to be applied to the item.
     *
     * @return A string representation of an Enchantment (Needs to be parsed)
     */
    String[] enchantments() default {};

}
