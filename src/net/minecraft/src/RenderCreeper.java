package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class RenderCreeper extends RenderLiving
{

    public RenderCreeper()
    {
        super(new ModelCreeper(), 0.5F);
    }

    protected void func_184_a(EntityCreeper entitycreeper, float f)
    {
        EntityCreeper entitycreeper1 = entitycreeper;
        float f1 = entitycreeper1.func_440_b(f);
        float f2 = 1.0F + MathHelper.sin(f1 * 100F) * f1 * 0.01F;
        if(f1 < 0.0F)
        {
            f1 = 0.0F;
        }
        if(f1 > 1.0F)
        {
            f1 = 1.0F;
        }
        f1 *= f1;
        f1 *= f1;
        float f3 = (1.0F + f1 * 0.4F) * f2;
        float f4 = (1.0F + f1 * 0.1F) / f2;
        GL11.glScalef(f3, f4, f3);
    }

    protected int func_183_a(EntityCreeper entitycreeper, float f, float f1)
    {
        EntityCreeper entitycreeper1 = entitycreeper;
        float f2 = entitycreeper1.func_440_b(f1);
        if((int)(f2 * 10F) % 2 == 0)
        {
            return 0;
        }
        int i = (int)(f2 * 0.2F * 255F);
        if(i < 0)
        {
            i = 0;
        }
        if(i > 255)
        {
            i = 255;
        }
        char c = '\377';
        char c1 = '\377';
        char c2 = '\377';
        return i << 24 | c << 16 | c1 << 8 | c2;
    }

    protected void func_6330_a(EntityLiving entityliving, float f)
    {
        func_184_a((EntityCreeper)entityliving, f);
    }

    protected int func_173_a(EntityLiving entityliving, float f, float f1)
    {
        return func_183_a((EntityCreeper)entityliving, f, f1);
    }
}
