package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class PlayerController
{

    public PlayerController(Minecraft minecraft)
    {
        field_1064_b = false;
        mc = minecraft;
    }

    public void func_717_a(World world)
    {
    }

    public void clickBlock(int i, int j, int k, int l)
    {
        sendBlockRemoved(i, j, k, l);
    }

    public boolean sendBlockRemoved(int i, int j, int k, int l)
    {
        mc.effectRenderer.func_1186_a(i, j, k);
        World world = mc.theWorld;
        Block block = Block.blocksList[world.getBlockId(i, j, k)];
        int i1 = world.getBlockMetadata(i, j, k);
        boolean flag = world.setBlockWithNotify(i, j, k, 0);
        if(block != null && flag)
        {
            mc.sndManager.playSound(block.stepSound.func_1146_a(), (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, (block.stepSound.func_1147_b() + 1.0F) / 2.0F, block.stepSound.func_1144_c() * 0.8F);
            block.onBlockDestroyedByPlayer(world, i, j, k, i1);
        }
        return flag;
    }

    public void sendBlockRemoving(int i, int j, int k, int l)
    {
    }

    public void func_6468_a()
    {
    }

    public void func_6467_a(float f)
    {
    }

    public float getBlockReachDistance()
    {
        return 5F;
    }

    public boolean sendUseItem(EntityPlayer entityplayer, World world, ItemStack itemstack)
    {
        int i = itemstack.stackSize;
        ItemStack itemstack1 = itemstack.useItemRightClick(world, entityplayer);
        if(itemstack1 != itemstack || itemstack1 != null && itemstack1.stackSize != i)
        {
            entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = itemstack1;
            if(itemstack1.stackSize == 0)
            {
                entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public void flipPlayer(EntityPlayer entityplayer)
    {
    }

    public void func_6474_c()
    {
    }

    public boolean func_6469_d()
    {
        return true;
    }

    public void func_6473_b(EntityPlayer entityplayer)
    {
    }

    public boolean sendPlaceBlock(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l)
    {
        int i1 = world.getBlockId(i, j, k);
        if(i1 > 0 && Block.blocksList[i1].blockActivated(world, i, j, k, entityplayer))
        {
            return true;
        }
        if(itemstack == null)
        {
            return false;
        } else
        {
            return itemstack.useItem(entityplayer, world, i, j, k, l);
        }
    }

    public EntityPlayer createPlayer(World world)
    {
        return new EntityPlayerSP(mc, world, mc.field_6320_i, world.worldProvider.field_4218_e);
    }

    public void func_6475_a(EntityPlayer entityplayer, Entity entity)
    {
        entityplayer.func_6415_a_(entity);
    }

    public void func_6472_b(EntityPlayer entityplayer, Entity entity)
    {
        entityplayer.attackTargetEntityWithCurrentItem(entity);
    }

    protected final Minecraft mc;
    public boolean field_1064_b;
}
