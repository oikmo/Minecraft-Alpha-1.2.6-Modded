package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class GuiChest extends GuiContainer
{

    public GuiChest(IInventory iinventory, IInventory iinventory1)
    {
        field_980_m = 0;
        field_982_j = iinventory;
        field_981_l = iinventory1;
        field_948_f = false;
        char c = '\336';
        int i = c - 108;
        field_980_m = iinventory1.getSizeInventory() / 9;
        ySize = i + field_980_m * 18;
        int j = (field_980_m - 4) * 18;
        for(int k = 0; k < field_980_m; k++)
        {
            for(int j1 = 0; j1 < 9; j1++)
            {
                inventorySlots.add(new SlotInventory(this, iinventory1, j1 + k * 9, 8 + j1 * 18, 18 + k * 18));
            }

        }

        for(int l = 0; l < 3; l++)
        {
            for(int k1 = 0; k1 < 9; k1++)
            {
                inventorySlots.add(new SlotInventory(this, iinventory, k1 + (l + 1) * 9, 8 + k1 * 18, 103 + l * 18 + j));
            }

        }

        for(int i1 = 0; i1 < 9; i1++)
        {
            inventorySlots.add(new SlotInventory(this, iinventory, i1, 8 + i1 * 18, 161 + j));
        }

    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(field_981_l.getInvName(), 8, 6, 0x404040);
        fontRenderer.drawString(field_982_j.getInvName(), 8, (ySize - 96) + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float f)
    {
        int i = mc.renderEngine.getTexture("/gui/container.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, field_980_m * 18 + 17);
        drawTexturedModalRect(j, k + field_980_m * 18 + 17, 0, 126, xSize, 96);
    }

    private IInventory field_982_j;
    private IInventory field_981_l;
    private int field_980_m;
}
