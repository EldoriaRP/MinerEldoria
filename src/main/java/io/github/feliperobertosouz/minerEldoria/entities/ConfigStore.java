package io.github.feliperobertosouz.minerEldoria.entities;

public class ConfigStore {

    private String permission;
    private int MinedBlocksQuantity;

    public ConfigStore(String permission, int minedBlocks) {
        this.permission = permission;
        MinedBlocksQuantity = minedBlocks;
    }

    public String getPermission() {
        return this.permission;
    }

    public int getMinedBlocksQuantity()
    {
        return this.MinedBlocksQuantity;
    }
}
