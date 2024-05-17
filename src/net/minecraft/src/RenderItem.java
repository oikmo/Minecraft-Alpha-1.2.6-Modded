package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;
import org.lwjgl.opengl.GL11;

public class RenderItem extends Render
{

    public RenderItem()
    {
        renderBlocks = new RenderBlocks();
        random = new Random();
        shadowSize = 0.15F;
        shadowOpaque = 0.75F;
    }

    public void func_165_a(EntityItem entityitem, double d, double d1, double d2, 
            float f, float f1)
    {
        random.setSeed(187L);
        ItemStack itemstack = entityitem.item;
        GL11.glPushMatrix();
        float f2 = MathHelper.sin(((float)entityitem.age + f1) / 10F + entityitem.field_804_d) * 0.1F + 0.1F;
        float f3 = (((float)entityitem.age + f1) / 20F + entityitem.field_804_d) * 57.29578F;
        byte byte0 = 1;
        if(entityitem.item.stackSize > 1)
        {
            byte0 = 2;
        }
        if(entityitem.item.stackSize > 5)
        {
            byte0 = 3;
        }
        if(entityitem.item.stackSize > 20)
        {
            byte0 = 4;
        }
        GL11.glTranslatef((float)d, (float)d1 + f2, (float)d2);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        if(itemstack.itemID < 256 && RenderBlocks.func_1219_a(Block.blocksList[itemstack.itemID].getRenderType()))
        {
            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
            loadTexture("/terrain.png");
            float f4 = 0.25F;
            if(!Block.blocksList[itemstack.itemID].renderAsNormalBlock() && itemstack.itemID != Block.stairSingle.blockID)
            {
                f4 = 0.5F;
            }
            GL11.glScalef(f4, f4, f4);
            for(int j = 0; j < byte0; j++)
            {
                GL11.glPushMatrix();
                if(j > 0)
                {
                    float f5 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
                    float f7 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
                    float f9 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
                    GL11.glTranslatef(f5, f7, f9);
                }
                renderBlocks.func_1227_a(Block.blocksList[itemstack.itemID]);
                GL11.glPopMatrix();
            }

        } else
        {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            int i = itemstack.getIconIndex();
            if(itemstack.itemID < 256)
            {
                loadTexture("/terrain.png");
            } else
            {
                loadTexture("/gui/items.png");
            }
            Tessellator tessellator = Tessellator.instance;
            float f6 = (float)((i % 16) * 16 + 0) / 256F;
            float f8 = (float)((i % 16) * 16 + 16) / 256F;
            float f10 = (float)((i / 16) * 16 + 0) / 256F;
            float f11 = (float)((i / 16) * 16 + 16) / 256F;
            float f12 = 1.0F;
            float f13 = 0.5F;
            float f14 = 0.25F;
            for(int k = 0; k < byte0; k++)
            {
                GL11.glPushMatrix();
                if(k > 0)
                {
                    float f15 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f16 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f17 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    GL11.glTranslatef(f15, f16, f17);
                }
                GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                tessellator.addVertexWithUV(0.0F - f13, 0.0F - f14, 0.0D, f6, f11);
                tessellator.addVertexWithUV(f12 - f13, 0.0F - f14, 0.0D, f8, f11);
                tessellator.addVertexWithUV(f12 - f13, 1.0F - f14, 0.0D, f8, f10);
                tessellator.addVertexWithUV(0.0F - f13, 1.0F - f14, 0.0D, f6, f10);
                tessellator.draw();
                GL11.glPopMatrix();
            }

        }
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
    }

    public void renderItemIntoGUI(FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int i, int j)
    {
        if(itemstack == null)
        {
            return;
        }
        if(itemstack.itemID < 256 && RenderBlocks.func_1219_a(Block.blocksList[itemstack.itemID].getRenderType()))
        {
            int k = itemstack.itemID;
            renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
            Block block = Block.blocksList[k];
            GL11.glPushMatrix();
            GL11.glTranslatef(i - 2, j + 3, 0.0F);
            GL11.glScalef(10F, 10F, 10F);
            GL11.glTranslatef(1.0F, 0.5F, 8F);
            GL11.glRotatef(210F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            renderBlocks.func_1227_a(block);
            GL11.glPopMatrix();
        } else
        if(itemstack.getIconIndex() >= 0)
        {
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            if(itemstack.itemID < 256)
            {
                renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
            } else
            {
                renderengine.bindTexture(renderengine.getTexture("/gui/items.png"));
            }
            renderTexturedQuad(i, j, (itemstack.getIconIndex() % 16) * 16, (itemstack.getIconIndex() / 16) * 16, 16, 16);
            GL11.glEnable(2896 /*GL_LIGHTING*/);
        }
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
    }

    public void renderItemOverlayIntoGUI(FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int i, int j)
    {
        if(itemstack == null)
        {
            return;
        }
        if(itemstack.stackSize > 1)
        {
            String s = (new StringBuilder()).append("").append(itemstack.stackSize).toString();
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
            fontrenderer.drawStringWithShadow(s, (i + 19) - 2 - fontrenderer.getStringWidth(s), j + 6 + 3, 0xffffff);
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        }
        if(itemstack.itemDamage > 0)
        {
            int k = 13 - (itemstack.itemDamage * 13) / itemstack.getMaxDamage();
            int l = 255 - (itemstack.itemDamage * 255) / itemstack.getMaxDamage();
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
            Tessellator tessellator = Tessellator.instance;
            int i1 = 255 - l << 16 | l << 8;
            int j1 = (255 - l) / 4 << 16 | 0x3f00;
            renderQuad(tessellator, i + 2, j + 13, 13, 2, 0);
            renderQuad(tessellator, i + 2, j + 13, 12, 1, j1);
            renderQuad(tessellator, i + 2, j + 13, k, 1, i1);
            GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void renderQuad(Tessellator tessellator, int i, int j, int k, int l, int i1)
    {
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(i1);
        tessellator.addVertex(i + 0, j + 0, 0.0D);
        tessellator.addVertex(i + 0, j + l, 0.0D);
        tessellator.addVertex(i + k, j + l, 0.0D);
        tessellator.addVertex(i + k, j + 0, 0.0D);
        tessellator.draw();
    }

    public void renderTexturedQuad(int i, int j, int k, int l, int i1, int j1)
    {
        float f = 0.0F;
        float f1 = 0.00390625F;
        float f2 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(i + 0, j + j1, f, (float)(k + 0) * f1, (float)(l + j1) * f2);
        tessellator.addVertexWithUV(i + i1, j + j1, f, (float)(k + i1) * f1, (float)(l + j1) * f2);
        tessellator.addVertexWithUV(i + i1, j + 0, f, (float)(k + i1) * f1, (float)(l + 0) * f2);
        tessellator.addVertexWithUV(i + 0, j + 0, f, (float)(k + 0) * f1, (float)(l + 0) * f2);
        tessellator.draw();
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        func_165_a((EntityItem)entity, d, d1, d2, f, f1);
    }

    private RenderBlocks renderBlocks;
    private Random random;
}
