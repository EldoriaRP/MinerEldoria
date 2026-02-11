package io.github.feliperobertosouz.minerEldoria.utils;

import io.github.feliperobertosouz.minerEldoria.entities.LootTable;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class LootTableLoader {

    public static LootTable load(ConfigurationSection section) {

        LootTable table = new LootTable();

        for (String key : section.getKeys(false)) {

            ConfigurationSection drop = section.getConfigurationSection(key);
            if (drop == null) continue;

            double weight = drop.getDouble("weight", 0);

            // entrada "nada"
            if (!drop.contains("material")) {
                table.add(null, weight);
                continue;
            }

            Material material = Material.matchMaterial(drop.getString("material"));
            if (material == null) continue;

            int amount = drop.getInt("amount", 1);

            ItemStack item = new ItemStack(material, amount);

            table.add(item, weight);
        }

        return table;
    }
}
