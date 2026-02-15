package io.github.feliperobertosouz.minerEldoria;

import io.github.feliperobertosouz.minerEldoria.commands.InfoCommand;
import io.github.feliperobertosouz.minerEldoria.commands.ListCommand;
import io.github.feliperobertosouz.minerEldoria.commands.ReloadCommand;
import io.github.feliperobertosouz.minerEldoria.entities.ConfigStore;
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
    private ConfigStore ConfigStore;

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

        BasicCommand infoCommand = new InfoCommand(this);
        registerCommand("minereldoria-info", infoCommand);
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
        var quantity = getConfig().getInt("quantity",20);
        var permission = getConfig().getString("permission","minerador");
        var baseChance = getConfig().getDouble("baseChance",0.05);
        var fairPlayBonus = getConfig().getDouble("fairPlayBonus",0.02);
        var enabled = getConfig().getBoolean("enabled",true);

        this.ConfigStore = new ConfigStore(permission,quantity,baseChance,fairPlayBonus);
    }

    public LootTable getLootTable()
    {
        return this.LootTable;
    }

    public ConfigStore getConfigStore()
    {
        return this.ConfigStore;
    }
}
