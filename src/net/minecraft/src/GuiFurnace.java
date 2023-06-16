package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class GuiFurnace extends GuiContainer
{

    public GuiFurnace(InventoryPlayer inventoryplayer, TileEntityFurnace tileentityfurnace)
    {
        field_978_j = tileentityfurnace;
        inventorySlots.add(new SlotInventory(this, tileentityfurnace, 0, 56, 17));
        inventorySlots.add(new SlotInventory(this, tileentityfurnace, 1, 56, 53));
        inventorySlots.add(new SlotInventory(this, tileentityfurnace, 2, 116, 35));
        for(int i = 0; i < 3; i++)
        {
            for(int k = 0; k < 9; k++)
            {
                inventorySlots.add(new SlotInventory(this, inventoryplayer, k + (i + 1) * 9, 8 + k * 18, 84 + i * 18));
            }

        }

        for(int j = 0; j < 9; j++)
        {
            inventorySlots.add(new SlotInventory(this, inventoryplayer, j, 8 + j * 18, 142));
        }

    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Furnace", 60, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float f)
    {
        int i = mc.renderEngine.getTexture("/gui/furnace.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        if(field_978_j.isBurning())
        {
            int l = field_978_j.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(j + 56, (k + 36 + 12) - l, 176, 12 - l, 14, l + 2);
        }
        int i1 = field_978_j.getCookProgressScaled(24);
        drawTexturedModalRect(j + 79, k + 34, 176, 14, i1 + 1, 16);
    }

    private TileEntityFurnace field_978_j;
}
