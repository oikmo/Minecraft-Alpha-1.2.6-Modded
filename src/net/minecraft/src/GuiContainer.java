package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

public abstract class GuiContainer extends GuiScreen
{
	public GuiContainer()
    {
        xSize = 176;
        ySize = 166;
        inventorySlots = new ArrayList<Slot>();
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawGuiContainerBackgroundLayer(f);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(k, l, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        for(int i1 = 0; i1 < inventorySlots.size(); i1++)
        {
            SlotInventory slotinventory = (SlotInventory)inventorySlots.get(i1);
            drawSlotInventory(slotinventory);
            if(slotinventory.isAtCursorPos(i, j))
            {
                GL11.glDisable(2896 /*GL_LIGHTING*/);
                GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
                int j1 = slotinventory.xPos;
                int k1 = slotinventory.yPos;
                drawGradientRect(j1, k1, j1 + 16, k1 + 16, 0x80ffffff, 0x80ffffff);
                GL11.glEnable(2896 /*GL_LIGHTING*/);
                GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
            }
        }

        InventoryPlayer inventoryplayer = mc.thePlayer.inventory;
        if(inventoryplayer.draggingItemStack != null)
        {
            GL11.glTranslatef(0.0F, 0.0F, 32F);
            itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, inventoryplayer.draggingItemStack, i - k - 8, j - l - 8);
            itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, inventoryplayer.draggingItemStack, i - k - 8, j - l - 8);
        }
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        drawGuiContainerForegroundLayer();
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        GL11.glPopMatrix();
    }

    protected void drawGuiContainerForegroundLayer()
    {
    }

    protected abstract void drawGuiContainerBackgroundLayer(float f);

    private void drawSlotInventory(SlotInventory slotinventory)
    {
        IInventory iinventory = slotinventory.inventory;
        int i = slotinventory.slotIndex;
        int j = slotinventory.xPos;
        int k = slotinventory.yPos;
        ItemStack itemstack = iinventory.getStackInSlot(i);
        if(itemstack == null)
        {
            int l = slotinventory.func_775_c();
            if(l >= 0)
            {
                GL11.glDisable(2896 /*GL_LIGHTING*/);
                mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/items.png"));
                drawTexturedModalRect(j, k, (l % 16) * 16, (l / 16) * 16, 16, 16);
                GL11.glEnable(2896 /*GL_LIGHTING*/);
                return;
            }
        }
        itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack, j, k);
        itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack, j, k);
    }

    private Slot getSlotAtPosition(int i, int j)
    {
        for(int k = 0; k < inventorySlots.size(); k++)
        {
            SlotInventory slotinventory = (SlotInventory)inventorySlots.get(k);
            if(slotinventory.isAtCursorPos(i, j))
            {
                return slotinventory;
            }
        }

        return null;
    }

    protected void mouseClicked(int i, int j, int k)
    {
        if(k == 0 || k == 1)
        {
            Slot slot = getSlotAtPosition(i, j);
            InventoryPlayer inventoryplayer = mc.thePlayer.inventory;
            if(slot != null)
            {
                ItemStack itemstack = slot.getStack();
                if(itemstack != null || inventoryplayer.draggingItemStack != null)
                {
                    if(itemstack != null && inventoryplayer.draggingItemStack == null)
                    {
                        int i1 = k != 0 ? (itemstack.stackSize + 1) / 2 : itemstack.stackSize;
                        inventoryplayer.draggingItemStack = slot.inventory.decrStackSize(slot.slotIndex, i1);
                        if(itemstack.stackSize == 0)
                        {
                            slot.putStack(null);
                        }
                        slot.onPickupFromSlot();
                    } else
                    if(itemstack == null && inventoryplayer.draggingItemStack != null && slot.isItemValid(inventoryplayer.draggingItemStack))
                    {
                        int j1 = k != 0 ? 1 : inventoryplayer.draggingItemStack.stackSize;
                        if(j1 > slot.getSlotStackLimit())
                        {
                            j1 = slot.getSlotStackLimit();
                        }
                        slot.putStack(inventoryplayer.draggingItemStack.splitStack(j1));
                        if(inventoryplayer.draggingItemStack.stackSize == 0)
                        {
                            inventoryplayer.draggingItemStack = null;
                        }
                    } else
                    if(itemstack != null && inventoryplayer.draggingItemStack != null)
                    {
                        if(slot.isItemValid(inventoryplayer.draggingItemStack))
                        {
                            if(itemstack.itemID != inventoryplayer.draggingItemStack.itemID)
                            {
                                if(inventoryplayer.draggingItemStack.stackSize <= slot.getSlotStackLimit())
                                {
                                    ItemStack itemstack1 = itemstack;
                                    slot.putStack(inventoryplayer.draggingItemStack);
                                    inventoryplayer.draggingItemStack = itemstack1;
                                }
                            } else
                            if(itemstack.itemID == inventoryplayer.draggingItemStack.itemID)
                            {
                                if(k == 0)
                                {
                                    int k1 = inventoryplayer.draggingItemStack.stackSize;
                                    if(k1 > slot.getSlotStackLimit() - itemstack.stackSize)
                                    {
                                        k1 = slot.getSlotStackLimit() - itemstack.stackSize;
                                    }
                                    if(k1 > inventoryplayer.draggingItemStack.getMaxStackSize() - itemstack.stackSize)
                                    {
                                        k1 = inventoryplayer.draggingItemStack.getMaxStackSize() - itemstack.stackSize;
                                    }
                                    inventoryplayer.draggingItemStack.splitStack(k1);
                                    if(inventoryplayer.draggingItemStack.stackSize == 0)
                                    {
                                        inventoryplayer.draggingItemStack = null;
                                    }
                                    itemstack.stackSize += k1;
                                } else
                                if(k == 1)
                                {
                                    int l1 = 1;
                                    if(l1 > slot.getSlotStackLimit() - itemstack.stackSize)
                                    {
                                        l1 = slot.getSlotStackLimit() - itemstack.stackSize;
                                    }
                                    if(l1 > inventoryplayer.draggingItemStack.getMaxStackSize() - itemstack.stackSize)
                                    {
                                        l1 = inventoryplayer.draggingItemStack.getMaxStackSize() - itemstack.stackSize;
                                    }
                                    inventoryplayer.draggingItemStack.splitStack(l1);
                                    if(inventoryplayer.draggingItemStack.stackSize == 0)
                                    {
                                        inventoryplayer.draggingItemStack = null;
                                    }
                                    itemstack.stackSize += l1;
                                }
                            }
                        } else
                        if(itemstack.itemID == inventoryplayer.draggingItemStack.itemID && inventoryplayer.draggingItemStack.getMaxStackSize() > 1)
                        {
                            int i2 = itemstack.stackSize;
                            if(i2 > 0 && i2 + inventoryplayer.draggingItemStack.stackSize <= inventoryplayer.draggingItemStack.getMaxStackSize())
                            {
                                inventoryplayer.draggingItemStack.stackSize += i2;
                                itemstack.splitStack(i2);
                                if(itemstack.stackSize == 0)
                                {
                                    slot.putStack(null);
                                }
                                slot.onPickupFromSlot();
                            }
                        }
                    }
                }
                slot.onSlotChanged();
            } else
            if(inventoryplayer.draggingItemStack != null)
            {
                int l = (width - xSize) / 2;
                int j2 = (height - ySize) / 2;
                if(i < l || j < j2 || i >= l + xSize || j >= j2 + xSize)
                {
                    EntityPlayerSP entityplayersp = mc.thePlayer;
                    if(k == 0)
                    {
                        entityplayersp.dropPlayerItem(inventoryplayer.draggingItemStack);
                        inventoryplayer.draggingItemStack = null;
                    }
                    if(k == 1)
                    {
                        entityplayersp.dropPlayerItem(inventoryplayer.draggingItemStack.splitStack(1));
                        if(inventoryplayer.draggingItemStack.stackSize == 0)
                        {
                            inventoryplayer.draggingItemStack = null;
                        }
                    }
                }
            }
        }
    }

    protected void mouseMovedOrUp(int i, int j, int k)
    {
        if(k != 0);
    }

    protected void keyTyped(char c, int i)
    {
        if(i == 1 || i == mc.gameSettings.keyBindInventory.keyCode)
        {
            mc.displayGuiScreen(null);
        }
    }

    public void onGuiClosed()
    {
        if(mc.thePlayer == null)
        {
            return;
        }
        InventoryPlayer inventoryplayer = mc.thePlayer.inventory;
        if(inventoryplayer.draggingItemStack != null)
        {
            mc.thePlayer.dropPlayerItem(inventoryplayer.draggingItemStack);
            inventoryplayer.draggingItemStack = null;
        }
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    private static RenderItem itemRenderer = new RenderItem();
    protected int xSize;
    protected int ySize;
    protected List<Slot> inventorySlots;

}
