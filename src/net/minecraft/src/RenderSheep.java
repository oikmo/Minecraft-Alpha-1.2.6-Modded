package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class RenderSheep extends RenderLiving
{

    public RenderSheep(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(modelbase, f);
        func_4013_a(modelbase1);
    }

    protected boolean func_176_a(EntitySheep entitysheep, int i)
    {
        loadTexture("/mob/sheep_fur.png");
        return i == 0 && !entitysheep.sheared;
    }

    protected boolean func_166_a(EntityLiving entityliving, int i)
    {
        return func_176_a((EntitySheep)entityliving, i);
    }
}
