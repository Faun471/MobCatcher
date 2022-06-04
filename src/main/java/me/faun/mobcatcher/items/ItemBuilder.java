package me.faun.mobcatcher.items;

import me.faun.mobcatcher.utils.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {
    private final ItemStack item;
    private final Material type;
    private ItemMeta meta;
    private int amount = 1;
    private int model = 0;
    private String name;
    private List<String> lore = new ArrayList<>();

    public ItemBuilder(Material type) {
        this.item = new ItemStack(type);
        this.type = type;
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.type = item.getType();
        this.amount = item.getAmount();
        if (item.hasItemMeta()) {
            this.meta = item.getItemMeta();
            this.meta.displayName(Component.text(""));
            this.meta.lore(lore.stream().map(StringUtils::messageParse).collect(Collectors.toList()));
            this.meta.setCustomModelData(model);
            item.setItemMeta(this.meta);
        }
    }


    public ItemBuilder name(String name) {
        this.name = name;

        ItemMeta meta = this.item.getItemMeta();
        meta.displayName(StringUtils.messageParse(name));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder model(int model) {
        this.model = model;
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder meta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    public ItemStack build() {
        this.item.setAmount(this.amount);
        this.item.setType(this.type);

        ItemMeta meta = this.item.getItemMeta();
        if (this.item.hasItemMeta()) {
            this.meta = item.getItemMeta();
            this.meta.displayName(StringUtils.messageParse(this.name));
            this.meta.lore(lore.stream().map(StringUtils::messageParse).collect(Collectors.toList()));
            this.meta.setCustomModelData(model);
            item.setItemMeta(meta);
        }

        return this.item;
    }
}
