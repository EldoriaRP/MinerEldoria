package io.github.feliperobertosouz.minerEldoria.commands;

import io.github.feliperobertosouz.minerEldoria.MinerEldoria;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.Nullable;

public class ReloadCommand implements BasicCommand {

    private final MinerEldoria Plugin;

    public ReloadCommand(MinerEldoria plugin){
        this.Plugin = plugin;
    }


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        this.Plugin.reloadConfig();
        this.Plugin.loadConfiguration();
        commandSourceStack.getSender().sendMessage("Config recarregada com sucesso");
    }

    @Override
    public @Nullable String permission(){
        return "minereldoria.reload";
    }
}
