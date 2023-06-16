package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ItemPickaxe extends ItemTool
{

    public ItemPickaxe(int i, int j)
    {
        super(i, 2, j, blocksEffectiveAgainst);
        field_328_aY = j;
    }

    public boolean canHarvestBlock(Block block)
    {
        if(block == Block.obsidian)
        {
            return field_328_aY == 3;
        }
        if(block == Block.blockDiamond || block == Block.oreDiamond)
        {
            return field_328_aY >= 2;
        }
        if(block == Block.blockGold || block == Block.oreGold)
        {
            return field_328_aY >= 2;
        }
        if(block == Block.blockSteel || block == Block.oreIron)
        {
            return field_328_aY >= 1;
        }
        if(block == Block.oreRedstone || block == Block.oreRedstoneGlowing)
        {
            return field_328_aY >= 2;
        }
        if(block.blockMaterial == Material.rock)
        {
            return true;
        }
        return block.blockMaterial == Material.iron;
    }

    private static Block blocksEffectiveAgainst[];
    private int field_328_aY;

    static 
    {
        blocksEffectiveAgainst = (new Block[] {
            Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, 
            Block.oreDiamond, Block.blockDiamond, Block.blockIce, Block.bloodStone
        });
    }
}
