package io.github.feliperobertosouz.minerEldoria.managers;

import io.github.feliperobertosouz.minerEldoria.entities.Miner;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MinerManager {

    private Map<Player, Miner> MinerList;

    public MinerManager() {
        this.MinerList = new HashMap<>();
    }

    public Miner getMinerDataOrStart(Player player)
    {
        if(this.MinerList.containsKey(player))
            return this.MinerList.get(player);

        this.MinerList.put(player, new Miner());
        return this.MinerList.get(player);
    }

    public void putMinerData(Player player, Miner miner)
    {
        this.MinerList.put(player, miner);
    }

    public int getMinerMinedBlocks(Player player) {
        if (this.MinerList.containsKey(player))
            return this.MinerList.get(player).MinedBlocks;

        return 0;
    }

    public void addMinerMinedBlocks(Player player, int quantity)
    {
        if(this.MinerList.containsKey(player))
        {
            var miner = this.MinerList.get(player);
            miner.MinedBlocks = miner.MinedBlocks + quantity;
            this.MinerList.put(player,miner);
        }

    }

    public void setMinerMinedBlocks(Player player, int quantity)
    {
        Miner miner;

        if(!this.MinerList.containsKey(player))
            miner = new Miner();
        else
            miner = this.MinerList.get(player);

        miner.MinedBlocks = quantity;
        this.MinerList.put(player,miner);
    }
}

