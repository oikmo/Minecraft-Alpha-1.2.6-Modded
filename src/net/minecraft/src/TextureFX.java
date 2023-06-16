package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class TextureFX
{

    public TextureFX(int i)
    {
        field_1127_a = new byte[1024 /*GL_FRONT_LEFT*/];
        field_1131_c = false;
        field_1130_d = 0;
        field_1129_e = 1;
        field_1128_f = 0;
        field_1126_b = i;
    }

    public void func_783_a()
    {
    }

    public void func_782_a(RenderEngine renderengine)
    {
        if(field_1128_f == 0)
        {
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderengine.getTexture("/terrain.png"));
        } else
        if(field_1128_f == 1)
        {
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderengine.getTexture("/gui/items.png"));
        }
    }

    public byte field_1127_a[];
    public int field_1126_b;
    public boolean field_1131_c;
    public int field_1130_d;
    public int field_1129_e;
    public int field_1128_f;
}
