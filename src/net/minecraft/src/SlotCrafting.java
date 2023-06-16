package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class SlotCrafting extends SlotInventory
{

    public SlotCrafting(GuiContainer guicontainer, IInventory iinventory, IInventory iinventory1, int i, int j, int k)
    {
        super(guicontainer, iinventory1, i, j, k);
        craftMatrix = iinventory;
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }

    public void onPickupFromSlot()
    {
        for(int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
            if(craftMatrix.getStackInSlot(i) != null)
            {
                craftMatrix.decrStackSize(i, 1);
            }
        }

    }

    private final IInventory craftMatrix;
}
