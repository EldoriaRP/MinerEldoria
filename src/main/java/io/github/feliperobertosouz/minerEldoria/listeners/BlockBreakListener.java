package io.github.feliperobertosouz.minerEldoria.listeners;

import io.github.feliperobertosouz.minerEldoria.MinerEldoria;
import io.github.feliperobertosouz.minerEldoria.managers.MinerManager;
import io.github.feliperobertosouz.minerEldoria.entities.LootTable;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

        MiniMessage mm = MiniMessage.miniMessage();

        if(counter >= this.Plugin.getConfigStore().getMinedBlocksQuantity())
        {
            miner.MinedBlocks = 0;
            var table = this.Plugin.getLootTable();
            var item = table.roll();
            var bonus = sortear(item,miner.SuccessRate);
            if (bonus != null)
            {
                block.getWorld().dropItemNaturally(block.getLocation(), bonus);
                player.playSound(
                        player.getLocation(),
                        Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                        1f,
                        1f
                );
                player.sendMessage(mm.deserialize("<b><green>Que sorte!</green> Você encontrou um minério extra."));

                player.spawnParticle(
                        Particle.END_ROD,
                        event.getBlock().getLocation().add(0,1,0),
                        20,
                        0.3,0.3,0.3,
                        0.1
                );

                miner.SuccessRate = this.Plugin.getConfigStore().getBaseChance();
            }
            else{
                miner.SuccessRate += this.Plugin.getConfigStore().getFairPlayBonus();
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
