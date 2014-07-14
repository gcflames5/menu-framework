package net.njay.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.njay.annotation.ItemStackAnnotation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Utils for modifying ItemMeta
 */
public class ItemUtils {

    /**
     * Gets an ItemStack with type type, durability durability amount amount, name name, and lore lore
     *
     * @param type   type of Material
     * @param amount amount of the Material
     * @param name   display name of the ItemStack
     * @param lore   lore of the ItemStack
     * @return ItemStack with params
     */
    public static ItemStack getNamedItemStack(Material type, short durability, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(type, amount);
        item.setDurability(durability);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    /**
     * Gets an ItemStack with type type, amount amount, name name, and lore lore
     *
     * @param type   type of Material
     * @param amount amount of the Material
     * @param name   display name of the ItemStack
     * @param lore   lore of the ItemStack
     * @return ItemStack with params
     */
    public static ItemStack getNamedItemStack(Material type, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    /**
     * Sets the name and lore of an ItemStack
     *
     * @param item ItemStack to modify
     * @param name new name of the ItemStack
     * @param lore new lore of the ItemStack
     * @return ItemStack with set name and lore
     */
    public static ItemStack getNamedItemStack(ItemStack item, String name, List<String> lore) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    /**
     * Gets an ItemStack with type type, amount amount, and name name
     *
     * @param type   type of Material
     * @param amount amount of the Material
     * @param name   display name of the ItemStack
     * @return ItemStack with params
     */
    public static ItemStack getNamedItemStack(Material type, int amount, String name) {
        return getNamedItemStack(type, amount, name, new ArrayList<String>());
    }

    /**
     * Sets the name of an ItemStack
     *
     * @param item ItemStack to modify
     * @param name new name of the ItemStack
     * @return ItemStack set name
     */
    public static ItemStack getNamedItemStack(ItemStack item, String name) {
        return getNamedItemStack(item, name, new ArrayList<String>());
    }

    /**
     * Parse an enchantment from a string.
     *
     * @param parse The string to parse the enchantment from.
     * @return A map of one enchantment and it's corresponding level.
     */
    public static Map<Enchantment, Integer> parseEnchantment(String parse) {
        Map<Enchantment, Integer> result = Maps.newHashMap();
        int level = 1;
        List<String> parts = Lists.newArrayList(Splitter.on(":").limit(2).split(parse));
        Enchantment enchant = Enchantment.getByName(parts.get(0).toUpperCase().replace(" ", "_"));
        if(enchant == null) {
            return null;
        }
        if(parts.size() > 1) {
            try {
                level = Integer.parseInt(parts.get(1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        result.put(enchant, level);
        return result;
    }

    /**
     * Creates an {@link org.bukkit.inventory.ItemStack} with data from an {@link ItemStackAnnotation}
     *
     * @param annotation Annotation to be converted.
     * @return A formatted {@link org.bukkit.inventory.ItemStack} with data from the {@link ItemStackAnnotation}
     */
    public static ItemStack annotationToItemStack(ItemStackAnnotation annotation) {
        ItemStack stack = new ItemStack(annotation.material());
        stack.setAmount(annotation.amount());
        stack.setDurability(annotation.durability());

        ItemMeta meta = Bukkit.getServer().getItemFactory().getItemMeta(annotation.material());
        if (!annotation.name().equals("")) meta.setDisplayName(annotation.name());
        String[] emptyArray = {}; // Can't put array initializer's in if's
        if (annotation.lore() != emptyArray) meta.setLore(Arrays.asList(annotation.lore()));
        if (annotation.enchantments() != emptyArray) {
            for (String enchantment : annotation.enchantments()) {
                Map.Entry<Enchantment, Integer> parsedEnchantment = parseEnchantment(enchantment).entrySet().iterator().next();
                Enchantment enchantment1 = parsedEnchantment.getKey();
                Integer level = parsedEnchantment.getValue();
                meta.addEnchant(enchantment1, level, true);
            }
        }
        stack.setItemMeta(meta);
        return stack;
    }
}