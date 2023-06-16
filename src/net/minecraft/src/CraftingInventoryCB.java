package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;

public class CraftingInventoryCB
{

    public CraftingInventoryCB()
    {
        unusedList = new ArrayList<Object>();
    }

    public void onCraftGuiClosed(EntityPlayer entityplayer)
    {
        InventoryPlayer inventoryplayer = entityplayer.inventory;
        if(inventoryplayer.draggingItemStack != null)
        {
            entityplayer.dropPlayerItem(inventoryplayer.draggingItemStack);
        }
    }

    public void onCraftMatrixChanged(IInventory iinventory)
    {
    }

    protected List<?> unusedList;
}
