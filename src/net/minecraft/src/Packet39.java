package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet39 extends Packet
{

    public Packet39()
    {
    }

    public int getPacketSize()
    {
        return 8;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        field_6365_a = datainputstream.readInt();
        field_6364_b = datainputstream.readInt();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(field_6365_a);
        dataoutputstream.writeInt(field_6364_b);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_6497_a(this);
    }

    public int field_6365_a;
    public int field_6364_b;
}
