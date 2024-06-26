package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools
{

    public CompressedStreamTools()
    {
    }

    public static NBTTagCompound readCompressed(InputStream inputstream) throws IOException
    {
        DataInputStream datainputstream = new DataInputStream(new GZIPInputStream(inputstream));
        try
        {
            NBTTagCompound nbttagcompound = read(datainputstream);
            return nbttagcompound;
        }
        finally
        {
            datainputstream.close();
        }
    }

    public static void writeGzippedCompoundToOutputStream(NBTTagCompound nbttagcompound, OutputStream outputstream) throws IOException
    {
        DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(outputstream));
        try
        {
            write(nbttagcompound, dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
    }

    public static NBTTagCompound func_1140_a(byte abyte0[]) throws IOException
    {
        DataInputStream datainputstream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte0)));
        try
        {
            NBTTagCompound nbttagcompound = read(datainputstream);
            return nbttagcompound;
        }
        finally
        {
            datainputstream.close();
        }
    }

    public static byte[] func_1142_a(NBTTagCompound nbttagcompound) throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
        try
        {
            write(nbttagcompound, dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
        return bytearrayoutputstream.toByteArray();
    }

    public static NBTTagCompound read(DataInput datainput) throws IOException
    {
        NBTBase nbtbase = NBTBase.readTag(datainput);
        if(nbtbase instanceof NBTTagCompound)
        {
            return (NBTTagCompound)nbtbase;
        } else
        {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    public static void write(NBTTagCompound nbttagcompound, DataOutput dataoutput) throws IOException
    {
        NBTBase.writeTag(nbttagcompound, dataoutput);
    }
}
