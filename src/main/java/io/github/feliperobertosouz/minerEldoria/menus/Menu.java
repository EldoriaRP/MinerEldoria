package io.github.feliperobertosouz.minerEldoria.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Menu {
    Inventory getMenu();
    void openMenu(Player player);
}
