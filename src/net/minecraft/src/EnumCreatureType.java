package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public enum EnumCreatureType
{
    monster(IMobs.class, 100),
    creature(EntityAnimals.class, 20);
/*
    public static EnumCreatureType[] func_4145_values()
    {
        return (EnumCreatureType[])field_6518_e.clone();
    }

    public static EnumCreatureType valueOf(String s)
    {
        return (EnumCreatureType)Enum.valueOf(EnumCreatureType.class, s);
    }
*/
    private EnumCreatureType(Class<?> class1, int j)
    {
        field_4278_c = class1;
        maxNumberOfEntityType = j;
    }
/*
    public static final EnumCreatureType monster;
    public static final EnumCreatureType creature;
*/
    public final Class<?> field_4278_c;
    public final int maxNumberOfEntityType;
/*
    private static final EnumCreatureType field_6518_e[]; /* synthetic field */
/*
    static 
    {
        monster = new EnumCreatureType("monster", 0, IMobs.class, 100);
        creature = new EnumCreatureType("creature", 1, EntityAnimals.class, 20);
        field_6518_e = (new EnumCreatureType[] {
            monster, creature
        });
    }
*/
}
