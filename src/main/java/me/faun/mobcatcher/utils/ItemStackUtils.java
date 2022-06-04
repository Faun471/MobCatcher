package me.faun.mobcatcher.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackUtils {
    public static void glow(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta.getEnchants().containsKey(Enchantment.LUCK)) {
            item.addUnsafeEnchantment(Enchantment.LUCK, 1);
            item.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            item.addUnsafeEnchantment(Enchantment.LUCK, 1);
            item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);

    }
}
