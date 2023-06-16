package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.*;

public class Packet60 extends Packet
{

    public Packet60()
    {
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        field_12236_a = datainputstream.readDouble();
        field_12235_b = datainputstream.readDouble();
        field_12239_c = datainputstream.readDouble();
        field_12238_d = datainputstream.readFloat();
        int i = datainputstream.readInt();
        field_12237_e = new HashSet<ChunkPosition>();
        int j = (int)field_12236_a;
        int k = (int)field_12235_b;
        int l = (int)field_12239_c;
        for(int i1 = 0; i1 < i; i1++)
        {
            int j1 = datainputstream.readByte() + j;
            int k1 = datainputstream.readByte() + k;
            int l1 = datainputstream.readByte() + l;
            field_12237_e.add(new ChunkPosition(j1, k1, l1));
        }

    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeDouble(field_12236_a);
        dataoutputstream.writeDouble(field_12235_b);
        dataoutputstream.writeDouble(field_12239_c);
        dataoutputstream.writeFloat(field_12238_d);
        dataoutputstream.writeInt(field_12237_e.size());
        int i = (int)field_12236_a;
        int j = (int)field_12235_b;
        int k = (int)field_12239_c;
        int j1;
        for(Iterator<ChunkPosition> iterator = field_12237_e.iterator(); iterator.hasNext(); dataoutputstream.writeByte(j1))
        {
            ChunkPosition chunkposition = (ChunkPosition)iterator.next();
            int l = chunkposition.x - i;
            int i1 = chunkposition.y - j;
            j1 = chunkposition.z - k;
            dataoutputstream.writeByte(l);
            dataoutputstream.writeByte(i1);
        }

    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_12245_a(this);
    }

    public int getPacketSize()
    {
        return 32 + field_12237_e.size() * 3;
    }

    public double field_12236_a;
    public double field_12235_b;
    public double field_12239_c;
    public float field_12238_d;
    public Set<ChunkPosition> field_12237_e;
}
