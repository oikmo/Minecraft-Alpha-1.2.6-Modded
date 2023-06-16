package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class RenderPig extends RenderLiving
{

    public RenderPig(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(modelbase, f);
        func_4013_a(modelbase1);
    }

    protected boolean func_180_a(EntityPig entitypig, int i)
    {
        loadTexture("/mob/saddle.png");
        return i == 0 && entitypig.rideable;
    }

    protected boolean func_166_a(EntityLiving entityliving, int i)
    {
        return func_180_a((EntityPig)entityliving, i);
    }
}
