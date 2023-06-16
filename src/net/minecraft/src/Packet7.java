package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet7 extends Packet
{

    public Packet7()
    {
    }

    public Packet7(int i, int j, int k)
    {
        field_9277_a = i;
        field_9276_b = j;
        field_9278_c = k;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        field_9277_a = datainputstream.readInt();
        field_9276_b = datainputstream.readInt();
        field_9278_c = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(field_9277_a);
        dataoutputstream.writeInt(field_9276_b);
        dataoutputstream.writeByte(field_9278_c);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_6499_a(this);
    }

    public int getPacketSize()
    {
        return 9;
    }

    public int field_9277_a;
    public int field_9276_b;
    public int field_9278_c;
}
