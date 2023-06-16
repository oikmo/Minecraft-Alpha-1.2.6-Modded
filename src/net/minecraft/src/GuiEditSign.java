package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiEditSign extends GuiScreen
{

    public GuiEditSign(TileEntitySign tileentitysign)
    {
        screenTitle = "Edit sign message:";
        editLine = 0;
        entitySign = tileentitysign;
    }

    public void initGui()
    {
        controlList.clear();
        Keyboard.enableRepeatEvents(true);
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120, "Done"));
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen()
    {
        updateCounter++;
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 0)
        {
            entitySign.onInventoryChanged();
            mc.displayGuiScreen(null);
        }
    }

    protected void keyTyped(char c, int i)
    {
        if(i == 200)
        {
            editLine = editLine - 1 & 3;
        }
        if(i == 208 || i == 28)
        {
            editLine = editLine + 1 & 3;
        }
        if(i == 14 && entitySign.signText[editLine].length() > 0)
        {
            entitySign.signText[editLine] = entitySign.signText[editLine].substring(0, entitySign.signText[editLine].length() - 1);
        }
        if(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\307\374\351\342\344\340\345\347\352\353\350\357\356\354\304\305\311\346\306\364\366\362\373\371\377\326\334\370\243\330\327\u0192\341\355\363\372\361\321\252\272\277\256\254\275\274\241\253\273".indexOf(c) >= 0 && entitySign.signText[editLine].length() < 15)
        {
        	entitySign.signText[editLine] += c;
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, screenTitle, width / 2, 40, 0xffffff);
        GL11.glPushMatrix();
        GL11.glTranslatef(width / 2, height / 2, 50F);
        float f1 = 93.75F;
        GL11.glScalef(-f1, -f1, -f1);
        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
        Block block = entitySign.getBlockType();
        if(block == Block.signPost)
        {
            float f2 = (float)(entitySign.getBlockMetadata() * 360) / 16F;
            GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, 0.3125F, 0.0F);
        } else
        {
            int k = entitySign.getBlockMetadata();
            float f3 = 0.0F;
            if(k == 2)
            {
                f3 = 180F;
            }
            if(k == 4)
            {
                f3 = 90F;
            }
            if(k == 5)
            {
                f3 = -90F;
            }
            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, 0.3125F, 0.0F);
        }
        if((updateCounter / 6) % 2 == 0)
        {
            entitySign.lineBeingEdited = editLine;
        }
        TileEntityRenderer.instance.renderTileEntityAt(entitySign, -0.5D, -0.75D, -0.5D, 0.0F);
        entitySign.lineBeingEdited = -1;
        GL11.glPopMatrix();
        super.drawScreen(i, j, f);
    }

    protected String screenTitle;
    private TileEntitySign entitySign;
    private int updateCounter;
    private int editLine;
}
