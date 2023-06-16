package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet38 extends Packet
{

    public Packet38()
    {
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        field_9274_a = datainputstream.readInt();
        field_9273_b = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(field_9274_a);
        dataoutputstream.writeByte(field_9273_b);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_9447_a(this);
    }

    public int getPacketSize()
    {
        return 5;
    }

    public int field_9274_a;
    public byte field_9273_b;
}
