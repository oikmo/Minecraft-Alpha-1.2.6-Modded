package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class RenderLiving extends Render
{

    public RenderLiving(ModelBase modelbase, float f)
    {
        unusedRenderBlocks = modelbase;
        field_9246_c = f;
    }

    public void func_4013_a(ModelBase modelbase)
    {
        field_6332_f = modelbase;
    }

    public void func_171_a(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        GL11.glPushMatrix();
        GL11.glDisable(2884 /*GL_CULL_FACE*/);
        unusedRenderBlocks.field_1244_k = func_167_c(entityliving, f1);
        unusedRenderBlocks.field_1243_l = entityliving.ridingEntity != null || entityliving.field_9300_bu;
        if(field_6332_f != null)
        {
            field_6332_f.field_1243_l = unusedRenderBlocks.field_1243_l;
        }
        try
        {
            float f2 = entityliving.prevRenderYawOffset + (entityliving.renderYawOffset - entityliving.prevRenderYawOffset) * f1;
            float f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
            float f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
            GL11.glTranslatef((float)d, (float)d1, (float)d2);
            float f5 = func_170_d(entityliving, f1);
            GL11.glRotatef(180F - f2, 0.0F, 1.0F, 0.0F);
            if(entityliving.deathTime > 0)
            {
                float f6 = ((((float)entityliving.deathTime + f1) - 1.0F) / 20F) * 1.6F;
                f6 = MathHelper.sqrt_float(f6);
                if(f6 > 1.0F)
                {
                    f6 = 1.0F;
                }
                GL11.glRotatef(f6 * func_172_a(entityliving), 0.0F, 0.0F, 1.0F);
            }
            float f7 = 0.0625F;
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            GL11.glScalef(-1F, -1F, 1.0F);
            func_6330_a(entityliving, f1);
            GL11.glTranslatef(0.0F, -24F * f7 - 0.0078125F, 0.0F);
            float f8 = entityliving.field_705_Q + (entityliving.field_704_R - entityliving.field_705_Q) * f1;
            float f9 = entityliving.field_703_S - entityliving.field_704_R * (1.0F - f1);
            if(f8 > 1.0F)
            {
                f8 = 1.0F;
            }
            func_140_a(entityliving.skinUrl, entityliving.getEntityTexture());
            GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
            unusedRenderBlocks.render(f9, f8, f5, f3 - f2, f4, f7);
            for(int i = 0; i < 4; i++)
            {
                if(func_166_a(entityliving, i))
                {
                    field_6332_f.render(f9, f8, f5, f3 - f2, f4, f7);
                    GL11.glDisable(3042 /*GL_BLEND*/);
                    GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
                }
            }

            func_6331_b(entityliving, f1);
            float f10 = entityliving.getEntityBrightness(f1);
            int j = func_173_a(entityliving, f10, f1);
            if((j >> 24 & 0xff) > 0 || entityliving.hurtTime > 0 || entityliving.deathTime > 0)
            {
                GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
                GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
                GL11.glEnable(3042 /*GL_BLEND*/);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthFunc(514);
                if(entityliving.hurtTime > 0 || entityliving.deathTime > 0)
                {
                    GL11.glColor4f(f10, 0.0F, 0.0F, 0.4F);
                    unusedRenderBlocks.render(f9, f8, f5, f3 - f2, f4, f7);
                    for(int k = 0; k < 4; k++)
                    {
                        if(func_166_a(entityliving, k))
                        {
                            GL11.glColor4f(f10, 0.0F, 0.0F, 0.4F);
                            field_6332_f.render(f9, f8, f5, f3 - f2, f4, f7);
                        }
                    }

                }
                if((j >> 24 & 0xff) > 0)
                {
                    float f11 = (float)(j >> 16 & 0xff) / 255F;
                    float f12 = (float)(j >> 8 & 0xff) / 255F;
                    float f13 = (float)(j & 0xff) / 255F;
                    float f14 = (float)(j >> 24 & 0xff) / 255F;
                    GL11.glColor4f(f11, f12, f13, f14);
                    unusedRenderBlocks.render(f9, f8, f5, f3 - f2, f4, f7);
                    for(int l = 0; l < 4; l++)
                    {
                        if(func_166_a(entityliving, l))
                        {
                            GL11.glColor4f(f11, f12, f13, f14);
                            field_6332_f.render(f9, f8, f5, f3 - f2, f4, f7);
                        }
                    }

                }
                GL11.glDepthFunc(515);
                GL11.glDisable(3042 /*GL_BLEND*/);
                GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
                GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            }
            GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
        GL11.glPopMatrix();
    }

    protected float func_167_c(EntityLiving entityliving, float f)
    {
        return entityliving.getSwingProgress(f);
    }

    protected float func_170_d(EntityLiving entityliving, float f)
    {
        return (float)entityliving.ticksExisted + f;
    }

    protected void func_6331_b(EntityLiving entityliving, float f)
    {
    }

    protected boolean func_166_a(EntityLiving entityliving, int i)
    {
        return false;
    }

    protected float func_172_a(EntityLiving entityliving)
    {
        return 90F;
    }

    protected int func_173_a(EntityLiving entityliving, float f, float f1)
    {
        return 0;
    }

    protected void func_6330_a(EntityLiving entityliving, float f)
    {
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        func_171_a((EntityLiving)entity, d, d1, d2, f, f1);
    }

    protected ModelBase unusedRenderBlocks;
    protected ModelBase field_6332_f;
}
