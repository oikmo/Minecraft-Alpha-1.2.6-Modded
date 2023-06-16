package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class CraftingInventoryPlayerCB extends CraftingInventoryCB
{

    public CraftingInventoryPlayerCB(ItemStack aitemstack[])
    {
        craftResult = new InventoryCraftResult();
        craftMatrix = new InventoryCrafting(this, aitemstack);
        onCraftMatrixChanged(craftMatrix);
    }

    public void onCraftMatrixChanged(IInventory iinventory)
    {
        int ai[] = new int[9];
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                int k = -1;
                if(i < 2 && j < 2)
                {
                    ItemStack itemstack = craftMatrix.getStackInSlot(i + j * 2);
                    if(itemstack != null)
                    {
                        k = itemstack.itemID;
                    }
                }
                ai[i + j * 3] = k;
            }

        }

        craftResult.setInventorySlotContents(0, CraftingManager.getInstance().craft(ai));
    }

    public void onCraftGuiClosed(EntityPlayer entityplayer)
    {
        super.onCraftGuiClosed(entityplayer);
        for(int i = 0; i < 9; i++)
        {
            ItemStack itemstack = craftMatrix.getStackInSlot(i);
            if(itemstack != null)
            {
                entityplayer.dropPlayerItem(itemstack);
            }
        }

    }

    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
}
