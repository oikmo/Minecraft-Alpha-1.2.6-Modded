package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

class SlotArmor extends SlotInventory
{

    SlotArmor(GuiInventory guiinventory, GuiContainer guicontainer, IInventory iinventory, int i, int j, int k, int l)
    {
        super(guicontainer, iinventory, i, j, k);
        field_1123_d = guiinventory;
        field_1124_c = l;
    }

    public int getSlotStackLimit()
    {
        return 1;
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        if(itemstack.getItem() instanceof ItemArmor)
        {
            return ((ItemArmor)itemstack.getItem()).armorType == field_1124_c;
        }
        System.out.println((new StringBuilder()).append(itemstack.getItem().shiftedIndex).append(", ").append(field_1124_c).toString());
        if(itemstack.getItem().shiftedIndex == Block.pumpkin.blockID)
        {
            return field_1124_c == 0;
        } else
        {
            return false;
        }
    }

    public int func_775_c()
    {
        return 15 + field_1124_c * 16;
    }

    final int field_1124_c; /* synthetic field */
    final GuiInventory field_1123_d; /* synthetic field */
}
