package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class RenderZombieSimple extends RenderLiving
{

    public RenderZombieSimple(ModelBase modelbase, float f, float f1)
    {
        super(modelbase, f * f1);
        field_204_f = f1;
    }

    protected void func_175_a(EntityZombieSimple entityzombiesimple, float f)
    {
        GL11.glScalef(field_204_f, field_204_f, field_204_f);
    }

    protected void func_6330_a(EntityLiving entityliving, float f)
    {
        func_175_a((EntityZombieSimple)entityliving, f);
    }

    private float field_204_f;
}
