package io.github.feliperobertosouz.minerEldoria.entities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LootTable {
    private static class Entry {
        ItemStack item;
        double weight;

        Entry(ItemStack item, double weight) {
            this.item = item;
            this.weight = weight;
        }
    }

    private final List<Entry> entries = new ArrayList<>();
    private double totalWeight = 0;

    public LootTable add(ItemStack item, double weight) {
        if (weight <= 0) return this;

        entries.add(new Entry(item.clone(), weight));
        totalWeight += weight;
        return this;
    }

    public ItemStack roll() {

        if (entries.isEmpty())
            return null;

        double r = ThreadLocalRandom.current().nextDouble() * totalWeight;
        double cumulative = 0;

        for (Entry entry : entries) {
            cumulative += entry.weight;

            if (r <= cumulative) {
                return entry.item == null ? null : entry.item.clone();
            }
        }

        return null;
    }

    public List<ItemStack> getDisplayItems() {

        List<ItemStack> display = new ArrayList<>();

        for (Entry entry : entries) {

            if (entry.item == null) continue;

            ItemStack clone = entry.item.clone();
            ItemMeta meta = clone.getItemMeta();

            if (meta == null) continue;

            List<Component> lore = new ArrayList<>();

            lore.add(Component.text("Quantidade: " + clone.getAmount(), NamedTextColor.GRAY));
            lore.add(Component.text("Peso: " + entry.weight, NamedTextColor.YELLOW));

            meta.lore(lore);
            clone.setItemMeta(meta);

            display.add(clone);
        }

        return display;
    }
}
