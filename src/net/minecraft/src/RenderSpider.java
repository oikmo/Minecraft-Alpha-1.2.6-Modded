package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class RenderSpider extends RenderLiving
{

    public RenderSpider()
    {
        super(new ModelSpider(), 1.0F);
        func_4013_a(new ModelSpider());
    }

    protected float func_191_a(EntitySpider entityspider)
    {
        return 180F;
    }

    protected boolean func_190_a(EntitySpider entityspider, int i)
    {
        if(i != 0)
        {
            return false;
        }
        if(i != 0)
        {
            return false;
        } else
        {
            loadTexture("/mob/spider_eyes.png");
            float f = (1.0F - entityspider.getEntityBrightness(1.0F)) * 0.5F;
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
            return true;
        }
    }

    protected float func_172_a(EntityLiving entityliving)
    {
        return func_191_a((EntitySpider)entityliving);
    }

    protected boolean func_166_a(EntityLiving entityliving, int i)
    {
        return func_190_a((EntitySpider)entityliving, i);
    }
}
