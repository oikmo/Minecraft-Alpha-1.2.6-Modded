package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


final class ChunkCoordinates
{

    public ChunkCoordinates(int i, int j)
    {
        field_1518_a = i;
        field_1517_b = j;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof ChunkCoordinates)
        {
            ChunkCoordinates chunkcoordinates = (ChunkCoordinates)obj;
            return field_1518_a == chunkcoordinates.field_1518_a && field_1517_b == chunkcoordinates.field_1517_b;
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return field_1518_a << 16 ^ field_1517_b;
    }

    public final int field_1518_a;
    public final int field_1517_b;
}
