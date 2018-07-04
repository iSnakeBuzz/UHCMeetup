package com.isnakebuzz.meetup.i;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemBuilder {

    @SuppressWarnings("deprecation")
    public static ItemStack crearNormal(final int n, final int n2, final int n3) {
        return new ItemStack(n, n2, (short) n3);
    }

    public static ItemStack crearArmor(final ItemStack itemStack) {
        itemStack.setItemMeta(itemStack.getItemMeta());
        return itemStack;
    }

    public static ItemStack crearItem(final int n, final int n2, final int n3) {
        final ItemStack itemStack = new ItemStack(n, n2, (short) (byte) n3);
        return itemStack;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack crearItem(final int n, final int n2, final int n3, final String s) {
        final ItemStack itemStack = new ItemStack(n, n2, (short) (byte) n3);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ItemStack crearCabeza(final String owner, final String s, final String... array) {
        final ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        itemMeta.setOwner(owner);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        final ArrayList<String> lore = new ArrayList<String>();
        for (int length = array.length, i = 0; i < length; ++i) {
            lore.add(ChatColor.translateAlternateColorCodes('&', array[i]));
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta((ItemMeta) itemMeta);
        return itemStack;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ItemStack crearCabeza(final String owner, final String s, final Integer n, final String... array) {
        final ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, (int) n, (short) 3);
        final SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        itemMeta.setOwner(owner);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        final ArrayList<String> lore = new ArrayList<String>();
        for (int length = array.length, i = 0; i < length; ++i) {
            lore.add(ChatColor.translateAlternateColorCodes('&', array[i]));
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta((ItemMeta) itemMeta);
        return itemStack;
    }

    @SuppressWarnings({"unchecked", "deprecation", "rawtypes"})
    public static ItemStack crearItem1(final int n, final int n2, final int n3, final String s, final String... array) {
        final ItemStack itemStack = new ItemStack(n, n2, (short) n3);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        final ArrayList<String> lore = new ArrayList<String>();
        for (int length = array.length, i = 0; i < length; ++i) {
            lore.add(ChatColor.translateAlternateColorCodes('&', array[i]));
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ItemStack crearItem1(final Material material, final int n, final int n2, final String s, final String... array) {
        final ItemStack itemStack = new ItemStack(material, n, (short) n2);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        final ArrayList<String> lore = new ArrayList<String>();
        for (int length = array.length, i = 0; i < length; ++i) {
            lore.add(ChatColor.translateAlternateColorCodes('&', array[i]));
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    public static ItemStack crearItem1(final int n, final int n2, final int n3, final String s, final List<String> list) {
        final ItemStack itemStack = new ItemStack(n, n2, (short) n3);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        final ArrayList<String> lore = new ArrayList<String>();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', (String) iterator.next())
            );
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ItemStack crearItem1(final Material material, final int n, final int n2, final String s, final List<String> list) {
        final ItemStack itemStack = new ItemStack(material, n, (short) n2);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        final ArrayList<String> lore = new ArrayList<String>();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', (String) iterator.next()));
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static ItemStack crearItem1Ench(final int n, final int n2, final int n3, final String s, final String... array) {
        final ItemStack itemStack = new ItemStack(n, n2, (short) n3);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        final ArrayList<String> lore = new ArrayList<String>();
        for (int length = array.length, i = 0; i < length; ++i) {
            lore.add(ChatColor.translateAlternateColorCodes('&', array[i]));
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta(itemMeta);
        EnchantGlow.addGlow(itemStack);
        return itemStack;
    }

    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    public static ItemStack crearItem1Ench(final int n, final int n2, final int n3, final String s, final List<String> list) {
        final ItemStack itemStack = new ItemStack(n, n2, (short) n3);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        final ArrayList<String> lore = new ArrayList<String>();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', (String) iterator.next()));
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta(itemMeta);
        EnchantGlow.addGlow(itemStack);
        return itemStack;
    }

    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    public static ItemStack crearItem2(final int n, final int n2, final int n3, final String displayName, final ArrayList<String> list) {
        final ItemStack itemStack = new ItemStack(n, n2, (short) n3);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        final ArrayList<String> lore = new ArrayList<String>();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', (String) iterator.next()));
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static ItemStack crearItem3(final int n, final int n2, final int n3, final String displayName, final String[] array) {
        final ItemStack itemStack = new ItemStack(n, n2, (short) n3);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        final ArrayList<String> lore = new ArrayList<String>();
        for (int length = array.length, i = 0; i < length; ++i) {
            lore.add(ChatColor.translateAlternateColorCodes('&', array[i]));
            itemMeta.setLore((List) lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
