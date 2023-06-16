package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockIce extends BlockBreakable
{

    public BlockIce(int i, int j)
    {
        super(i, j, Material.ice, false);
        slipperiness = 0.98F;
        setTickOnLoad(true);
    }

    public int func_234_g()
    {
        return 1;
    }

    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return super.shouldSideBeRendered(iblockaccess, i, j, k, 1 - l);
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        Material material = world.getBlockMaterial(i, j - 1, k);
        if(material.func_880_c() || material.getIsLiquid())
        {
            world.setBlockWithNotify(i, j, k, Block.waterStill.blockID);
        }
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) > 11 - Block.lightOpacity[blockID])
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, Block.waterMoving.blockID);
        }
    }
}
