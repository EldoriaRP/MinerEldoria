package io.github.feliperobertosouz.minerEldoria.listeners;

import io.github.feliperobertosouz.minerEldoria.MinerEldoria;
import io.github.feliperobertosouz.minerEldoria.managers.MinerManager;
import io.github.feliperobertosouz.minerEldoria.entities.LootTable;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakListener implements Listener {

    private final MinerManager MinerManager;
    private final MinerEldoria Plugin;

    public static final Set<Material> ALLOWED = Set.of(
            Material.IRON_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE
    );

    public BlockBreakListener(MinerManager minerManager, MinerEldoria plugin)
    {
        this.MinerManager = minerManager;
        this.Plugin = plugin;
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(!player.hasPermission(this.Plugin.getConfigStore().getPermission()))
            return;

        if(!ALLOWED.contains(block.getType()))
            return;

        var miner = this.MinerManager.getMinerDataOrStart(player);
        miner.MinedBlocks += 1;

        var counter = miner.MinedBlocks;

        if(counter >= this.Plugin.getConfigStore().getMinedBlocksQuantity())
        {
            miner.MinedBlocks = 0;
            var table = this.Plugin.getLootTable();
            var item = table.roll();
            var bonus = sortear(item,miner.SuccessRate);
            if (bonus != null)
            {
                block.getWorld().dropItemNaturally(block.getLocation(), bonus);

                miner.SuccessRate = 0.05;
            }
            else{
                miner.SuccessRate += 0.02;
                miner.Fails += 1;
            }
        }
        this.MinerManager.putMinerData(player,miner);
    }

    public static ItemStack sortear(ItemStack item, double chance) {

        if (chance <= 0) return null;
        if (chance >= 1) return item.clone();

        double roll = ThreadLocalRandom.current().nextDouble();

        if (roll <= chance) {
            return item.clone();
        }

        return null;
    }
}
