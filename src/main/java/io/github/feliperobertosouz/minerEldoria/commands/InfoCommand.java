package io.github.feliperobertosouz.minerEldoria.commands;

import io.github.feliperobertosouz.minerEldoria.MinerEldoria;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jspecify.annotations.Nullable;

public class InfoCommand implements BasicCommand {
    private final MinerEldoria Plugin;

    public InfoCommand(MinerEldoria plugin) {
        Plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        var sender = commandSourceStack.getSender();
        MiniMessage mm = MiniMessage.miniMessage();

        var configStore = this.Plugin.getConfigStore();

        var baseChance = configStore.getBaseChance();
        var fairPlayBonus = configStore.getFairPlayBonus();

        sender.sendMessage(mm.deserialize("<gold>[MINERELDORIA]</gold>"));
        sender.sendMessage(mm.deserialize("Chance Base: " + baseChance));
        sender.sendMessage(mm.deserialize("Bonus a cada falha: " + fairPlayBonus));

    }

    @Override
    public @Nullable String permission() {
        return "minereldoria.admin";
    }
}
