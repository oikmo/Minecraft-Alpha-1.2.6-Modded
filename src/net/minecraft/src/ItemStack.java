package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public final class ItemStack
{

    public ItemStack(Block block)
    {
        this(block, 1);
    }

    public ItemStack(Block block, int i)
    {
        this(block.blockID, i);
    }

    public ItemStack(Item item)
    {
        this(item, 1);
    }

    public ItemStack(Item item, int i)
    {
        this(item.shiftedIndex, i);
    }

    public ItemStack(int i)
    {
        this(i, 1);
    }

    public ItemStack(int i, int j)
    {
        stackSize = 0;
        itemID = i;
        stackSize = j;
    }

    public ItemStack(int i, int j, int k)
    {
        stackSize = 0;
        itemID = i;
        stackSize = j;
        itemDamage = k;
    }

    public ItemStack(NBTTagCompound nbttagcompound)
    {
        stackSize = 0;
        readFromNBT(nbttagcompound);
    }

    public ItemStack splitStack(int i)
    {
        stackSize -= i;
        return new ItemStack(itemID, i, itemDamage);
    }

    public Item getItem()
    {
        return Item.itemsList[itemID];
    }

    public int getIconIndex()
    {
        return getItem().getIconIndex(this);
    }

    public boolean useItem(EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        return getItem().onItemUse(this, entityplayer, world, i, j, k, l);
    }

    public float getStrVsBlock(Block block)
    {
        return getItem().getStrVsBlock(this, block);
    }

    public ItemStack useItemRightClick(World world, EntityPlayer entityplayer)
    {
        return getItem().onItemRightClick(this, world, entityplayer);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("id", (short)itemID);
        nbttagcompound.setByte("Count", (byte)stackSize);
        nbttagcompound.setShort("Damage", (short)itemDamage);
        return nbttagcompound;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        itemID = nbttagcompound.getShort("id");
        stackSize = nbttagcompound.getByte("Count");
        itemDamage = nbttagcompound.getShort("Damage");
    }

    public int getMaxStackSize()
    {
        return getItem().getItemStackLimit();
    }

    public int getMaxDamage()
    {
        return Item.itemsList[itemID].getMaxDamage();
    }

    public void damageItem(int i)
    {
        itemDamage += i;
        if(itemDamage > getMaxDamage())
        {
            stackSize--;
            if(stackSize < 0)
            {
                stackSize = 0;
            }
            itemDamage = 0;
        }
    }

    public void hitEntity(EntityLiving entityliving)
    {
        Item.itemsList[itemID].hitEntity(this, entityliving);
    }

    public void hitBlock(int i, int j, int k, int l)
    {
        Item.itemsList[itemID].hitBlock(this, i, j, k, l);
    }

    public int getDamageVsEntity(Entity entity)
    {
        return Item.itemsList[itemID].getDamageVsEntity(entity);
    }

    public boolean canHarvestBlock(Block block)
    {
        return Item.itemsList[itemID].canHarvestBlock(block);
    }

    public void onItemDestroyedByUse(EntityPlayer entityplayer)
    {
    }

    public void useItemOnEntity(EntityLiving entityliving)
    {
        Item.itemsList[itemID].func_4019_b(this, entityliving);
    }

    public ItemStack copy()
    {
        return new ItemStack(itemID, stackSize, itemDamage);
    }

    public int stackSize;
    public int animationsToGo;
    public int itemID;
    public int itemDamage;
}
