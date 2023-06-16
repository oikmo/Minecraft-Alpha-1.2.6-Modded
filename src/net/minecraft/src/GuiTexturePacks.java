package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.File;
import java.util.List;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiTexturePacks extends GuiScreen
{

    public GuiTexturePacks(GuiScreen guiscreen)
    {
        field_6460_h = 0;
        field_6459_i = 32;
        field_6458_j = (height - 55) + 4;
        field_6457_l = 0;
        field_6456_m = width;
        field_6455_n = -2;
        field_6454_o = -1;
        field_6453_p = "";
        field_6461_a = guiscreen;
    }

    public void initGui()
    {
        controlList.add(new GuiSmallButton(5, width / 2 - 154, height - 48, "Open texture pack folder"));
        controlList.add(new GuiSmallButton(6, width / 2 + 4, height - 48, "Done"));
        mc.texturePackList.func_6532_a();
        field_6453_p = (new File(mc.field_6297_D, "texturepacks")).getAbsolutePath();
        field_6459_i = 32;
        field_6458_j = (height - 58) + 4;
        field_6457_l = 0;
        field_6456_m = width;
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 5)
        {
            Sys.openURL((new StringBuilder()).append("file://").append(field_6453_p).toString());
        }
        if(guibutton.id == 6)
        {
            mc.renderEngine.refreshTextures();
            mc.displayGuiScreen(field_6461_a);
        }
    }

    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
    }

    protected void mouseMovedOrUp(int i, int j, int k)
    {
        super.mouseMovedOrUp(i, j, k);
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        if(field_6454_o <= 0)
        {
            mc.texturePackList.func_6532_a();
            field_6454_o += 20;
        }
        List<?> list = mc.texturePackList.availableTexturePacks();
        if(Mouse.isButtonDown(0))
        {
            if(field_6455_n == -1)
            {
                if(j >= field_6459_i && j <= field_6458_j)
                {
                    int k = width / 2 - 110;
                    int i1 = width / 2 + 110;
                    int j1 = (((j - field_6459_i) + field_6460_h) - 2) / 36;
                    if(i >= k && i <= i1 && j1 >= 0 && j1 < list.size() && mc.texturePackList.setTexturePack((TexturePackBase)list.get(j1)))
                    {
                        mc.renderEngine.refreshTextures();
                    }
                    field_6455_n = j;
                } else
                {
                    field_6455_n = -2;
                }
            } else
            if(field_6455_n >= 0)
            {
                field_6460_h -= j - field_6455_n;
                field_6455_n = j;
            }
        } else
        {
            if(field_6455_n >= 0)
            {
                if(field_6455_n != j);
            }
            field_6455_n = -1;
        }
        int l = list.size() * 36 - (field_6458_j - field_6459_i - 4);
        if(l < 0)
        {
            l /= 2;
        }
        if(field_6460_h < 0)
        {
            field_6460_h = 0;
        }
        if(field_6460_h > l)
        {
            field_6460_h = l;
        }
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2912 /*GL_FOG*/);
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f1 = 32F;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(0x202020);
        tessellator.addVertexWithUV(field_6457_l, field_6458_j, 0.0D, (float)field_6457_l / f1, (float)(field_6458_j + field_6460_h) / f1);
        tessellator.addVertexWithUV(field_6456_m, field_6458_j, 0.0D, (float)field_6456_m / f1, (float)(field_6458_j + field_6460_h) / f1);
        tessellator.addVertexWithUV(field_6456_m, field_6459_i, 0.0D, (float)field_6456_m / f1, (float)(field_6459_i + field_6460_h) / f1);
        tessellator.addVertexWithUV(field_6457_l, field_6459_i, 0.0D, (float)field_6457_l / f1, (float)(field_6459_i + field_6460_h) / f1);
        tessellator.draw();
        for(int k1 = 0; k1 < list.size(); k1++)
        {
            TexturePackBase texturepackbase = (TexturePackBase)list.get(k1);
            int l1 = width / 2 - 92 - 16;
            int i2 = (36 + k1 * 36) - field_6460_h;
            byte byte1 = 32;
            byte byte2 = 32;
            if(texturepackbase == mc.texturePackList.selectedTexturePack)
            {
                int j2 = width / 2 - 110;
                int k2 = width / 2 + 110;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(0x808080);
                tessellator.addVertexWithUV(j2, i2 + byte1 + 2, 0.0D, 0.0D, 1.0D);
                tessellator.addVertexWithUV(k2, i2 + byte1 + 2, 0.0D, 1.0D, 1.0D);
                tessellator.addVertexWithUV(k2, i2 - 2, 0.0D, 1.0D, 0.0D);
                tessellator.addVertexWithUV(j2, i2 - 2, 0.0D, 0.0D, 0.0D);
                tessellator.setColorOpaque_I(0);
                tessellator.addVertexWithUV(j2 + 1, i2 + byte1 + 1, 0.0D, 0.0D, 1.0D);
                tessellator.addVertexWithUV(k2 - 1, i2 + byte1 + 1, 0.0D, 1.0D, 1.0D);
                tessellator.addVertexWithUV(k2 - 1, i2 - 1, 0.0D, 1.0D, 0.0D);
                tessellator.addVertexWithUV(j2 + 1, i2 - 1, 0.0D, 0.0D, 0.0D);
                tessellator.draw();
                GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            }
            texturepackbase.func_6483_c(mc);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            tessellator.startDrawingQuads();
            tessellator.setColorOpaque_I(0xffffff);
            tessellator.addVertexWithUV(l1, i2 + byte1, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(l1 + byte2, i2 + byte1, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(l1 + byte2, i2, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(l1, i2, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
            drawString(fontRenderer, texturepackbase.texturePackFileName, l1 + byte2 + 2, i2 + 1, 0xffffff);
            drawString(fontRenderer, texturepackbase.firstDescriptionLine, l1 + byte2 + 2, i2 + 12, 0x808080);
            drawString(fontRenderer, texturepackbase.secondDescriptionLine, l1 + byte2 + 2, i2 + 12 + 10, 0x808080);
        }

        byte byte0 = 4;
        func_6452_a(0, field_6459_i, 255, 255);
        func_6452_a(field_6458_j, height, 255, 255);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glShadeModel(7425 /*GL_SMOOTH*/);
        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 0);
        tessellator.addVertexWithUV(field_6457_l, field_6459_i + byte0, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(field_6456_m, field_6459_i + byte0, 0.0D, 1.0D, 1.0D);
        tessellator.setColorRGBA_I(0, 255);
        tessellator.addVertexWithUV(field_6456_m, field_6459_i, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(field_6457_l, field_6459_i, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 255);
        tessellator.addVertexWithUV(field_6457_l, field_6458_j, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(field_6456_m, field_6458_j, 0.0D, 1.0D, 1.0D);
        tessellator.setColorRGBA_I(0, 0);
        tessellator.addVertexWithUV(field_6456_m, field_6458_j - byte0, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(field_6457_l, field_6458_j - byte0, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        GL11.glShadeModel(7424 /*GL_FLAT*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glDisable(3042 /*GL_BLEND*/);
        drawCenteredString(fontRenderer, "Select Texture Pack", width / 2, 16, 0xffffff);
        drawCenteredString(fontRenderer, "(Place texture pack files here)", width / 2 - 77, height - 26, 0x808080);
        super.drawScreen(i, j, f);
    }

    public void updateScreen()
    {
        super.updateScreen();
        field_6454_o--;
    }

    public void func_6452_a(int i, int j, int k, int l)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32F;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0x404040, l);
        tessellator.addVertexWithUV(0.0D, j, 0.0D, 0.0D, (float)j / f);
        tessellator.addVertexWithUV(width, j, 0.0D, (float)width / f, (float)j / f);
        tessellator.setColorRGBA_I(0x404040, k);
        tessellator.addVertexWithUV(width, i, 0.0D, (float)width / f, (float)i / f);
        tessellator.addVertexWithUV(0.0D, i, 0.0D, 0.0D, (float)i / f);
        tessellator.draw();
    }

    protected GuiScreen field_6461_a;
    private int field_6460_h;
    private int field_6459_i;
    private int field_6458_j;
    private int field_6457_l;
    private int field_6456_m;
    private int field_6455_n;
    private int field_6454_o;
    private String field_6453_p;
}
