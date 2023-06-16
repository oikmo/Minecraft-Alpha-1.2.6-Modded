package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class SlotInventory extends Slot
{

    public SlotInventory(GuiContainer guicontainer, IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i);
        guiHandler = guicontainer;
        xPos = j;
        yPos = k;
    }

    public boolean isAtCursorPos(int i, int j)
    {
        int k = (guiHandler.width - guiHandler.xSize) / 2;
        int l = (guiHandler.height - guiHandler.ySize) / 2;
        i -= k;
        j -= l;
        return i >= xPos - 1 && i < xPos + 16 + 1 && j >= yPos - 1 && j < yPos + 16 + 1;
    }

    private final GuiContainer guiHandler;
    public final int xPos;
    public final int yPos;
}
