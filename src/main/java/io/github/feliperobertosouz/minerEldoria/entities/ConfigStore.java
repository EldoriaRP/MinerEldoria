package io.github.feliperobertosouz.minerEldoria.entities;

public class ConfigStore {

    private String permission;
    private int MinedBlocksQuantity;
    private double BaseChance;
    private double FairPlayBonus;

    public ConfigStore(String permission, int minedBlocks, double baseChance, double fairPlayBonus) {
        this.permission = permission;
        this.MinedBlocksQuantity = minedBlocks;
        this.BaseChance = baseChance;
        this.FairPlayBonus = fairPlayBonus;
    }

    public String getPermission() {
        return this.permission;
    }

    public int getMinedBlocksQuantity()
    {
        return this.MinedBlocksQuantity;
    }

    public double getBaseChance()
    {
        return this.BaseChance;
    }

    public double getFairPlayBonus()
    {
        return this.FairPlayBonus;
    }
}
