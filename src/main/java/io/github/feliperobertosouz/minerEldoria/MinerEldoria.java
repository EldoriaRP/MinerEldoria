package io.github.feliperobertosouz.minerEldoria;

import io.github.feliperobertosouz.minerEldoria.commands.ListCommand;
import io.github.feliperobertosouz.minerEldoria.commands.ReloadCommand;
import io.github.feliperobertosouz.minerEldoria.listeners.BlockBreakListener;
import io.github.feliperobertosouz.minerEldoria.entities.LootTable;
import io.github.feliperobertosouz.minerEldoria.listeners.MenuClickListener;
import io.github.feliperobertosouz.minerEldoria.managers.MinerManager;
import io.github.feliperobertosouz.minerEldoria.utils.LootTableLoader;
import io.papermc.paper.command.brigadier.BasicCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinerEldoria extends JavaPlugin {

    private MinerManager MinerManager;
    private LootTable LootTable;
    private int Quantity = 20;
    private String PlayerPermission = "minerador";
    private boolean Enabled = true;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        loadConfiguration();
        if (!this.Enabled) {
            this.getLogger().warning("O plugin esta desativado por meio de configurações, altere no config.yml para habilitar o plugin novamente");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.MinerManager = new MinerManager();

        loadListeners();

        loadCommands();

        this.getLogger().info("PLUGIN INICIADO COM SUCESSO");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("PLUGIN ENCERRADO COM SUCESSO");
    }

    public void loadCommands()
    {
        BasicCommand reloadCommand = new ReloadCommand(this);
        registerCommand("minereldoria-reload", reloadCommand);

        BasicCommand listCommand = new ListCommand(this);
        registerCommand("minereldoria-list", listCommand);
    }

    public void loadListeners()
    {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this.MinerManager, this), this);
        getServer().getPluginManager().registerEvents(new MenuClickListener(),this);
    }

    public void loadLootTable()
    {
        ConfigurationSection section = getConfig().getConfigurationSection("drops");
        this.LootTable = LootTableLoader.load(section);
        this.getLogger().info("LootTable reiniciada com sucesso");
    }

    public void loadConfiguration()
    {
        loadLootTable();
        this.Quantity = getConfig().getInt("quantity",20);
        this.PlayerPermission = getConfig().getString("permission","minerador");
        this.Enabled = getConfig().getBoolean("enabled",true);
    }

    public LootTable getLootTable()
    {
        return this.LootTable;
    }

    public String getPlayerPermission()
    {
        return this.PlayerPermission;
    }

    public int getBlocksQuantity()
    {
        return this.Quantity;
    }
}
