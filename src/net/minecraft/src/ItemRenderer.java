package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class ItemRenderer
{

    public ItemRenderer(Minecraft minecraft)
    {
        field_9451_b = null;
        field_9453_c = 0.0F;
        field_9452_d = 0.0F;
        field_1357_e = new RenderBlocks();
        mc = minecraft;
    }

    public void renderItem(ItemStack itemstack)
    {
        GL11.glPushMatrix();
        if(itemstack.itemID < 256 && RenderBlocks.func_1219_a(Block.blocksList[itemstack.itemID].getRenderType()))
        {
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/terrain.png"));
            field_1357_e.func_1227_a(Block.blocksList[itemstack.itemID]);
        } else
        {
            if(itemstack.itemID < 256)
            {
                GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/terrain.png"));
            } else
            {
                GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/items.png"));
            }
            Tessellator tessellator = Tessellator.instance;
            float f = ((float)((itemstack.getIconIndex() % 16) * 16) + 0.0F) / 256F;
            float f1 = ((float)((itemstack.getIconIndex() % 16) * 16) + 15.99F) / 256F;
            float f2 = ((float)((itemstack.getIconIndex() / 16) * 16) + 0.0F) / 256F;
            float f3 = ((float)((itemstack.getIconIndex() / 16) * 16) + 15.99F) / 256F;
            float f4 = 1.0F;
            float f5 = 0.0F;
            float f6 = 0.3F;
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            GL11.glTranslatef(-f5, -f6, 0.0F);
            float f7 = 1.5F;
            GL11.glScalef(f7, f7, f7);
            GL11.glRotatef(50F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(335F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            float f8 = 0.0625F;
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, f1, f3);
            tessellator.addVertexWithUV(f4, 0.0D, 0.0D, f, f3);
            tessellator.addVertexWithUV(f4, 1.0D, 0.0D, f, f2);
            tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, f1, f2);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1F);
            tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - f8, f1, f2);
            tessellator.addVertexWithUV(f4, 1.0D, 0.0F - f8, f, f2);
            tessellator.addVertexWithUV(f4, 0.0D, 0.0F - f8, f, f3);
            tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - f8, f1, f3);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1F, 0.0F, 0.0F);
            for(int i = 0; i < 16; i++)
            {
                float f9 = (float)i / 16F;
                float f13 = (f1 + (f - f1) * f9) - 0.001953125F;
                float f17 = f4 * f9;
                tessellator.addVertexWithUV(f17, 0.0D, 0.0F - f8, f13, f3);
                tessellator.addVertexWithUV(f17, 0.0D, 0.0D, f13, f3);
                tessellator.addVertexWithUV(f17, 1.0D, 0.0D, f13, f2);
                tessellator.addVertexWithUV(f17, 1.0D, 0.0F - f8, f13, f2);
            }

            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            for(int j = 0; j < 16; j++)
            {
                float f10 = (float)j / 16F;
                float f14 = (f1 + (f - f1) * f10) - 0.001953125F;
                float f18 = f4 * f10 + 0.0625F;
                tessellator.addVertexWithUV(f18, 1.0D, 0.0F - f8, f14, f2);
                tessellator.addVertexWithUV(f18, 1.0D, 0.0D, f14, f2);
                tessellator.addVertexWithUV(f18, 0.0D, 0.0D, f14, f3);
                tessellator.addVertexWithUV(f18, 0.0D, 0.0F - f8, f14, f3);
            }

            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            for(int k = 0; k < 16; k++)
            {
                float f11 = (float)k / 16F;
                float f15 = (f3 + (f2 - f3) * f11) - 0.001953125F;
                float f19 = f4 * f11 + 0.0625F;
                tessellator.addVertexWithUV(0.0D, f19, 0.0D, f1, f15);
                tessellator.addVertexWithUV(f4, f19, 0.0D, f, f15);
                tessellator.addVertexWithUV(f4, f19, 0.0F - f8, f, f15);
                tessellator.addVertexWithUV(0.0D, f19, 0.0F - f8, f1, f15);
            }

            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            for(int l = 0; l < 16; l++)
            {
                float f12 = (float)l / 16F;
                float f16 = (f3 + (f2 - f3) * f12) - 0.001953125F;
                float f20 = f4 * f12;
                tessellator.addVertexWithUV(f4, f20, 0.0D, f, f16);
                tessellator.addVertexWithUV(0.0D, f20, 0.0D, f1, f16);
                tessellator.addVertexWithUV(0.0D, f20, 0.0F - f8, f1, f16);
                tessellator.addVertexWithUV(f4, f20, 0.0F - f8, f, f16);
            }

            tessellator.draw();
            GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        }
        GL11.glPopMatrix();
    }

    public void renderItemInFirstPerson(float f)
    {
        float f1 = field_9452_d + (field_9453_c - field_9452_d) * f;
        EntityPlayerSP entityplayersp = mc.thePlayer;
        GL11.glPushMatrix();
        GL11.glRotatef(((EntityPlayer) (entityplayersp)).prevRotationPitch + (((EntityPlayer) (entityplayersp)).rotationPitch - ((EntityPlayer) (entityplayersp)).prevRotationPitch) * f, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(((EntityPlayer) (entityplayersp)).prevRotationYaw + (((EntityPlayer) (entityplayersp)).rotationYaw - ((EntityPlayer) (entityplayersp)).prevRotationYaw) * f, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        float f2 = mc.theWorld.getLightBrightness(MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posX), MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posY), MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posZ));
        GL11.glColor4f(f2, f2, f2, 1.0F);
        ItemStack itemstack = field_9451_b;
        if(((EntityPlayer) (entityplayersp)).fishEntity != null)
        {
            itemstack = new ItemStack(Item.stick.shiftedIndex);
        }
        if(itemstack != null)
        {
            GL11.glPushMatrix();
            float f3 = 0.8F;
            float f5 = entityplayersp.getSwingProgress(f);
            float f7 = MathHelper.sin(f5 * 3.141593F);
            float f9 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F);
            GL11.glTranslatef(-f9 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F * 2.0F) * 0.2F, -f7 * 0.2F);
            GL11.glTranslatef(0.7F * f3, -0.65F * f3 - (1.0F - f1) * 0.6F, -0.9F * f3);
            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            f5 = entityplayersp.getSwingProgress(f);
            f7 = MathHelper.sin(f5 * f5 * 3.141593F);
            f9 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F);
            GL11.glRotatef(-f7 * 20F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f9 * 20F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f9 * 80F, 1.0F, 0.0F, 0.0F);
            f5 = 0.4F;
            GL11.glScalef(f5, f5, f5);
            if(itemstack.getItem().shouldRotateAroundWhenRendering())
            {
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            }
            renderItem(itemstack);
            GL11.glPopMatrix();
        } else
        {
            GL11.glPushMatrix();
            float f4 = 0.8F;
            float f6 = entityplayersp.getSwingProgress(f);
            float f8 = MathHelper.sin(f6 * 3.141593F);
            float f10 = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F);
            GL11.glTranslatef(-f10 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.4F, -f8 * 0.4F);
            GL11.glTranslatef(0.8F * f4, -0.75F * f4 - (1.0F - f1) * 0.6F, -0.9F * f4);
            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            f6 = entityplayersp.getSwingProgress(f);
            f8 = MathHelper.sin(f6 * f6 * 3.141593F);
            f10 = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F);
            GL11.glRotatef(f10 * 70F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f8 * 20F, 0.0F, 0.0F, 1.0F);
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTextureForDownloadableImage(mc.thePlayer.skinUrl, mc.thePlayer.getEntityTexture()));
            GL11.glTranslatef(-1F, 3.6F, 3.5F);
            GL11.glRotatef(120F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(200F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(5.6F, 0.0F, 0.0F);
            Render render = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render;
            f10 = 1.0F;
            GL11.glScalef(f10, f10, f10);
            renderplayer.drawFirstPersonHand();
            GL11.glPopMatrix();
        }
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        RenderHelper.disableStandardItemLighting();
    }

    public void renderOverlays(float f)
    {
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        if(mc.thePlayer.fire > 0 || mc.thePlayer.field_9299_bv)
        {
            int i = mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, i);
            renderFireInFirstPerson(f);
        }
        if(mc.thePlayer.func_345_I())
        {
            int j = MathHelper.floor_double(mc.thePlayer.posX);
            int l = MathHelper.floor_double(mc.thePlayer.posY);
            int i1 = MathHelper.floor_double(mc.thePlayer.posZ);
            int j1 = mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, j1);
            int k1 = mc.theWorld.getBlockId(j, l, i1);
            if(Block.blocksList[k1] != null)
            {
                renderInsideOfBlock(f, Block.blocksList[k1].getBlockTextureFromSide(2));
            }
        }
        if(mc.thePlayer.isInsideOfMaterial(Material.water))
        {
            int k = mc.renderEngine.getTexture("/misc/water.png");
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, k);
            renderWarpedTextureOverlay(f);
        }
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
    }

    private void renderInsideOfBlock(float f, int i)
    {
        Tessellator tessellator = Tessellator.instance;
        float f1 = mc.thePlayer.getEntityBrightness(f);
        f1 = 0.1F;
        GL11.glColor4f(f1, f1, f1, 0.5F);
        GL11.glPushMatrix();
        float f2 = -1F;
        float f3 = 1.0F;
        float f4 = -1F;
        float f5 = 1.0F;
        float f6 = -0.5F;
        float f7 = 0.0078125F;
        float f8 = (float)(i % 16) / 256F - f7;
        float f9 = ((float)(i % 16) + 15.99F) / 256F + f7;
        float f10 = (float)(i / 16) / 256F - f7;
        float f11 = ((float)(i / 16) + 15.99F) / 256F + f7;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(f2, f4, f6, f9, f11);
        tessellator.addVertexWithUV(f3, f4, f6, f8, f11);
        tessellator.addVertexWithUV(f3, f5, f6, f8, f10);
        tessellator.addVertexWithUV(f2, f5, f6, f9, f10);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderWarpedTextureOverlay(float f)
    {
        Tessellator tessellator = Tessellator.instance;
        float f1 = mc.thePlayer.getEntityBrightness(f);
        GL11.glColor4f(f1, f1, f1, 0.5F);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        float f2 = 4F;
        float f3 = -1F;
        float f4 = 1.0F;
        float f5 = -1F;
        float f6 = 1.0F;
        float f7 = -0.5F;
        float f8 = -mc.thePlayer.rotationYaw / 64F;
        float f9 = mc.thePlayer.rotationPitch / 64F;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(f3, f5, f7, f2 + f8, f2 + f9);
        tessellator.addVertexWithUV(f4, f5, f7, 0.0F + f8, f2 + f9);
        tessellator.addVertexWithUV(f4, f6, f7, 0.0F + f8, 0.0F + f9);
        tessellator.addVertexWithUV(f3, f6, f7, f2 + f8, 0.0F + f9);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042 /*GL_BLEND*/);
    }

    private void renderFireInFirstPerson(float f)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        float f1 = 1.0F;
        for(int i = 0; i < 2; i++)
        {
            GL11.glPushMatrix();
            int j = Block.fire.blockIndexInTexture + i * 16;
            int k = (j & 0xf) << 4;
            int l = j & 0xf0;
            float f2 = (float)k / 256F;
            float f3 = ((float)k + 15.99F) / 256F;
            float f4 = (float)l / 256F;
            float f5 = ((float)l + 15.99F) / 256F;
            float f6 = (0.0F - f1) / 2.0F;
            float f7 = f6 + f1;
            float f8 = 0.0F - f1 / 2.0F;
            float f9 = f8 + f1;
            float f10 = -0.5F;
            GL11.glTranslatef((float)(-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GL11.glRotatef((float)(i * 2 - 1) * 10F, 0.0F, 1.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(f6, f8, f10, f3, f5);
            tessellator.addVertexWithUV(f7, f8, f10, f2, f5);
            tessellator.addVertexWithUV(f7, f9, f10, f2, f4);
            tessellator.addVertexWithUV(f6, f9, f10, f3, f4);
            tessellator.draw();
            GL11.glPopMatrix();
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042 /*GL_BLEND*/);
    }

    public void func_895_a()
    {
        field_9452_d = field_9453_c;
        EntityPlayerSP entityplayersp = mc.thePlayer;
        ItemStack itemstack = ((EntityPlayer) (entityplayersp)).inventory.getCurrentItem();
        ItemStack itemstack1 = itemstack;
        float f = 0.4F;
        float f1 = itemstack1 != field_9451_b ? 0.0F : 1.0F;
        float f2 = f1 - field_9453_c;
        if(f2 < -f)
        {
            f2 = -f;
        }
        if(f2 > f)
        {
            f2 = f;
        }
        field_9453_c += f2;
        if(field_9453_c < 0.1F)
        {
            field_9451_b = itemstack1;
        }
    }

    public void func_9449_b()
    {
        field_9453_c = 0.0F;
    }

    public void func_9450_c()
    {
        field_9453_c = 0.0F;
    }

    private Minecraft mc;
    private ItemStack field_9451_b;
    private float field_9453_c;
    private float field_9452_d;
    private RenderBlocks field_1357_e;
}
