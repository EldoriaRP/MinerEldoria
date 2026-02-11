package io.github.feliperobertosouz.minerEldoria.commands;

import io.github.feliperobertosouz.minerEldoria.MinerEldoria;
import io.github.feliperobertosouz.minerEldoria.entities.LootTable;
import io.github.feliperobertosouz.minerEldoria.menus.LootMenu;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ListCommand implements BasicCommand {

    private final MinerEldoria Plugin;

    public ListCommand(MinerEldoria plugin)
    {
        this.Plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        var sender = commandSourceStack.getSender();
        if(!(sender instanceof Player))
        {
            sender.sendMessage("Apenas players podem executar este comando");
            return;
        }

        var menu = new LootMenu();
        var displayItems = Plugin.getLootTable().getDisplayItems();
        int slot = 0;
        for (ItemStack item : displayItems) {
            menu.Inventory.setItem(slot++, item);
        }

        menu.openMenu((Player) sender);
    }

    @Override
    public @Nullable String permission(){
        return "minereldoria.admin";
    }
}
