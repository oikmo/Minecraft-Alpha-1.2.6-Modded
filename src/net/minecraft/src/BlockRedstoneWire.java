package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockRedstoneWire extends Block
{

    public BlockRedstoneWire(int i, int j)
    {
        super(i, j, Material.circuits);
        wiresProvidePower = true;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        return blockIndexInTexture + (j <= 0 ? 0 : 16);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return 5;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return world.isBlockOpaqueCube(i, j - 1, k);
    }

    private void updateAndPropagateCurrentStrength(World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        int i1 = 0;
        wiresProvidePower = false;
        boolean flag = world.isBlockIndirectlyGettingPowered(i, j, k);
        wiresProvidePower = true;
        if(flag)
        {
            i1 = 15;
        } else
        {
            for(int j1 = 0; j1 < 4; j1++)
            {
                int l1 = i;
                int j2 = k;
                if(j1 == 0)
                {
                    l1--;
                }
                if(j1 == 1)
                {
                    l1++;
                }
                if(j1 == 2)
                {
                    j2--;
                }
                if(j1 == 3)
                {
                    j2++;
                }
                i1 = getMaxCurrentStrength(world, l1, j, j2, i1);
                if(world.isBlockOpaqueCube(l1, j, j2) && !world.isBlockOpaqueCube(i, j + 1, k))
                {
                    i1 = getMaxCurrentStrength(world, l1, j + 1, j2, i1);
                    continue;
                }
                if(!world.isBlockOpaqueCube(l1, j, j2))
                {
                    i1 = getMaxCurrentStrength(world, l1, j - 1, j2, i1);
                }
            }

            if(i1 > 0)
            {
                i1--;
            } else
            {
                i1 = 0;
            }
        }
        if(l != i1)
        {
            world.setBlockMetadataWithNotify(i, j, k, i1);
            world.func_701_b(i, j, k, i, j, k);
            if(i1 > 0)
            {
                i1--;
            }
            for(int k1 = 0; k1 < 4; k1++)
            {
                int i2 = i;
                int k2 = k;
                int l2 = j - 1;
                if(k1 == 0)
                {
                    i2--;
                }
                if(k1 == 1)
                {
                    i2++;
                }
                if(k1 == 2)
                {
                    k2--;
                }
                if(k1 == 3)
                {
                    k2++;
                }
                if(world.isBlockOpaqueCube(i2, j, k2))
                {
                    l2 += 2;
                }
                int i3 = getMaxCurrentStrength(world, i2, j, k2, -1);
                if(i3 >= 0 && i3 != i1)
                {
                    updateAndPropagateCurrentStrength(world, i2, j, k2);
                }
                i3 = getMaxCurrentStrength(world, i2, l2, k2, -1);
                if(i3 >= 0 && i3 != i1)
                {
                    updateAndPropagateCurrentStrength(world, i2, l2, k2);
                }
            }

            if(l == 0 || i1 == 0)
            {
                world.notifyBlocksOfNeighborChange(i, j, k, blockID);
                world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
                world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
                world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
                world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
                world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
                world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
            }
        }
    }

    private void notifyWireNeighborsOfNeighborChange(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j, k) != blockID)
        {
            return;
        } else
        {
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
            return;
        }
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        if(world.multiplayerWorld)
        {
            return;
        }
        updateAndPropagateCurrentStrength(world, i, j, k);
        world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        notifyWireNeighborsOfNeighborChange(world, i - 1, j, k);
        notifyWireNeighborsOfNeighborChange(world, i + 1, j, k);
        notifyWireNeighborsOfNeighborChange(world, i, j, k - 1);
        notifyWireNeighborsOfNeighborChange(world, i, j, k + 1);
        if(world.isBlockOpaqueCube(i - 1, j, k))
        {
            notifyWireNeighborsOfNeighborChange(world, i - 1, j + 1, k);
        } else
        {
            notifyWireNeighborsOfNeighborChange(world, i - 1, j - 1, k);
        }
        if(world.isBlockOpaqueCube(i + 1, j, k))
        {
            notifyWireNeighborsOfNeighborChange(world, i + 1, j + 1, k);
        } else
        {
            notifyWireNeighborsOfNeighborChange(world, i + 1, j - 1, k);
        }
        if(world.isBlockOpaqueCube(i, j, k - 1))
        {
            notifyWireNeighborsOfNeighborChange(world, i, j + 1, k - 1);
        } else
        {
            notifyWireNeighborsOfNeighborChange(world, i, j - 1, k - 1);
        }
        if(world.isBlockOpaqueCube(i, j, k + 1))
        {
            notifyWireNeighborsOfNeighborChange(world, i, j + 1, k + 1);
        } else
        {
            notifyWireNeighborsOfNeighborChange(world, i, j - 1, k + 1);
        }
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        super.onBlockRemoval(world, i, j, k);
        if(world.multiplayerWorld)
        {
            return;
        }
        world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        updateAndPropagateCurrentStrength(world, i, j, k);
        notifyWireNeighborsOfNeighborChange(world, i - 1, j, k);
        notifyWireNeighborsOfNeighborChange(world, i + 1, j, k);
        notifyWireNeighborsOfNeighborChange(world, i, j, k - 1);
        notifyWireNeighborsOfNeighborChange(world, i, j, k + 1);
        if(world.isBlockOpaqueCube(i - 1, j, k))
        {
            notifyWireNeighborsOfNeighborChange(world, i - 1, j + 1, k);
        } else
        {
            notifyWireNeighborsOfNeighborChange(world, i - 1, j - 1, k);
        }
        if(world.isBlockOpaqueCube(i + 1, j, k))
        {
            notifyWireNeighborsOfNeighborChange(world, i + 1, j + 1, k);
        } else
        {
            notifyWireNeighborsOfNeighborChange(world, i + 1, j - 1, k);
        }
        if(world.isBlockOpaqueCube(i, j, k - 1))
        {
            notifyWireNeighborsOfNeighborChange(world, i, j + 1, k - 1);
        } else
        {
            notifyWireNeighborsOfNeighborChange(world, i, j - 1, k - 1);
        }
        if(world.isBlockOpaqueCube(i, j, k + 1))
        {
            notifyWireNeighborsOfNeighborChange(world, i, j + 1, k + 1);
        } else
        {
            notifyWireNeighborsOfNeighborChange(world, i, j - 1, k + 1);
        }
    }

    private int getMaxCurrentStrength(World world, int i, int j, int k, int l)
    {
        if(world.getBlockId(i, j, k) != blockID)
        {
            return l;
        }
        int i1 = world.getBlockMetadata(i, j, k);
        if(i1 > l)
        {
            return i1;
        } else
        {
            return l;
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(world.multiplayerWorld)
        {
            return;
        }
        int i1 = world.getBlockMetadata(i, j, k);
        boolean flag = canPlaceBlockAt(world, i, j, k);
        if(!flag)
        {
            dropBlockAsItem(world, i, j, k, i1);
            world.setBlockWithNotify(i, j, k, 0);
        } else
        {
            updateAndPropagateCurrentStrength(world, i, j, k);
        }
        super.onNeighborBlockChange(world, i, j, k, l);
    }

    public int idDropped(int i, Random random)
    {
        return Item.redstone.shiftedIndex;
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l)
    {
        if(!wiresProvidePower)
        {
            return false;
        } else
        {
            return isPoweringTo(world, i, j, k, l);
        }
    }

    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(!wiresProvidePower)
        {
            return false;
        }
        if(iblockaccess.getBlockMetadata(i, j, k) == 0)
        {
            return false;
        }
        if(l == 1)
        {
            return true;
        }
        boolean flag = isPowerProviderOrWire(iblockaccess, i - 1, j, k) || !iblockaccess.isBlockOpaqueCube(i - 1, j, k) && isPowerProviderOrWire(iblockaccess, i - 1, j - 1, k);
        boolean flag1 = isPowerProviderOrWire(iblockaccess, i + 1, j, k) || !iblockaccess.isBlockOpaqueCube(i + 1, j, k) && isPowerProviderOrWire(iblockaccess, i + 1, j - 1, k);
        boolean flag2 = isPowerProviderOrWire(iblockaccess, i, j, k - 1) || !iblockaccess.isBlockOpaqueCube(i, j, k - 1) && isPowerProviderOrWire(iblockaccess, i, j - 1, k - 1);
        boolean flag3 = isPowerProviderOrWire(iblockaccess, i, j, k + 1) || !iblockaccess.isBlockOpaqueCube(i, j, k + 1) && isPowerProviderOrWire(iblockaccess, i, j - 1, k + 1);
        if(!iblockaccess.isBlockOpaqueCube(i, j + 1, k))
        {
            if(iblockaccess.isBlockOpaqueCube(i - 1, j, k) && isPowerProviderOrWire(iblockaccess, i - 1, j + 1, k))
            {
                flag = true;
            }
            if(iblockaccess.isBlockOpaqueCube(i + 1, j, k) && isPowerProviderOrWire(iblockaccess, i + 1, j + 1, k))
            {
                flag1 = true;
            }
            if(iblockaccess.isBlockOpaqueCube(i, j, k - 1) && isPowerProviderOrWire(iblockaccess, i, j + 1, k - 1))
            {
                flag2 = true;
            }
            if(iblockaccess.isBlockOpaqueCube(i, j, k + 1) && isPowerProviderOrWire(iblockaccess, i, j + 1, k + 1))
            {
                flag3 = true;
            }
        }
        if(!flag2 && !flag1 && !flag && !flag3 && l >= 2 && l <= 5)
        {
            return true;
        }
        if(l == 2 && flag2 && !flag && !flag1)
        {
            return true;
        }
        if(l == 3 && flag3 && !flag && !flag1)
        {
            return true;
        }
        if(l == 4 && flag && !flag2 && !flag3)
        {
            return true;
        }
        return l == 5 && flag1 && !flag2 && !flag3;
    }

    public boolean canProvidePower()
    {
        return wiresProvidePower;
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if(world.getBlockMetadata(i, j, k) > 0)
        {
            double d = (double)i + 0.5D + ((double)random.nextFloat() - 0.5D) * 0.20000000000000001D;
            double d1 = (float)j + 0.0625F;
            double d2 = (double)k + 0.5D + ((double)random.nextFloat() - 0.5D) * 0.20000000000000001D;
            world.spawnParticle("reddust", d, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    public static boolean isPowerProviderOrWire(IBlockAccess iblockaccess, int i, int j, int k)
    {
        int l = iblockaccess.getBlockId(i, j, k);
        if(l == Block.redstoneWire.blockID)
        {
            return true;
        }
        if(l == 0)
        {
            return false;
        }
        return Block.blocksList[l].canProvidePower();
    }

    private boolean wiresProvidePower;
}
