package io.github.feliperobertosouz.minerEldoria.listeners;

import io.github.feliperobertosouz.minerEldoria.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MenuClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getInventory();
        var topInventory = player.getOpenInventory().getTopInventory();

        if (!(event.getInventory().getHolder() instanceof Menu))
            return;

        event.setCancelled(true);
    }
}
