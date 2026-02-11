package io.github.feliperobertosouz.minerEldoria.menus;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class LootMenu implements  Menu, InventoryHolder {

    public Inventory Inventory;

    public LootMenu()
    {
        this.Inventory = Bukkit.createInventory(
                this,
                54,
                Component.text("Drops de minerador")
        );
    }

    @Override
    public Inventory getMenu() {
        return this.Inventory;
    }

    @Override
    public void openMenu(Player player) {
        player.openInventory(this.Inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.Inventory;
    }
}
