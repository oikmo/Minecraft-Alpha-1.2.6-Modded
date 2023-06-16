package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public enum EnumSkyBlock
{
    Sky(15),
    Block(0);
/*
    public static EnumSkyBlock[] func_4151_values()
    {
        return (EnumSkyBlock[])field_1721_d.clone();
    }

    public static EnumSkyBlock valueOf(String s)
    {
        return (EnumSkyBlock)Enum.valueOf(EnumSkyBlock.class, s);
    }
*/
    private EnumSkyBlock(int j)
    {
        field_1722_c = j;
    }
/*
    public static final EnumSkyBlock Sky;
    public static final EnumSkyBlock Block;
*/
    public final int field_1722_c;
/*
    private static final EnumSkyBlock field_1721_d[]; /* synthetic field */
/*
    static 
    {
        Sky = new EnumSkyBlock("Sky", 0, 15);
        Block = new EnumSkyBlock("Block", 1, 0);
        field_1721_d = (new EnumSkyBlock[] {
            Sky, Block
        });
    }
*/
}
