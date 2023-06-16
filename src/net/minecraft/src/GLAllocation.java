package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.nio.*;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class GLAllocation
{

    public GLAllocation()
    {
    }

    public static synchronized int generateDisplayLists(int i)
    {
        int j = GL11.glGenLists(i);
        displayLists.add(Integer.valueOf(j));
        displayLists.add(Integer.valueOf(i));
        return j;
    }

    public static synchronized void generateTextureNames(IntBuffer intbuffer)
    {
        GL11.glGenTextures(intbuffer);
        for(int i = intbuffer.position(); i < intbuffer.limit(); i++)
        {
            textureNames.add(Integer.valueOf(intbuffer.get(i)));
        }

    }

    public static synchronized void deleteTexturesAndDisplayLists()
    {
        for(int i = 0; i < displayLists.size(); i += 2)
        {
            GL11.glDeleteLists(((Integer)displayLists.get(i)).intValue(), ((Integer)displayLists.get(i + 1)).intValue());
        }

        IntBuffer intbuffer = createDirectIntBuffer(textureNames.size());
        intbuffer.flip();
        GL11.glDeleteTextures(intbuffer);
        for(int j = 0; j < textureNames.size(); j++)
        {
            intbuffer.put(((Integer)textureNames.get(j)).intValue());
        }

        intbuffer.flip();
        GL11.glDeleteTextures(intbuffer);
        displayLists.clear();
        textureNames.clear();
    }

    public static synchronized ByteBuffer createDirectByteBuffer(int i)
    {
        ByteBuffer bytebuffer = ByteBuffer.allocateDirect(i).order(ByteOrder.nativeOrder());
        return bytebuffer;
    }

    public static IntBuffer createDirectIntBuffer(int i)
    {
        return createDirectByteBuffer(i << 2).asIntBuffer();
    }

    public static FloatBuffer createDirectFloatBuffer(int i)
    {
        return createDirectByteBuffer(i << 2).asFloatBuffer();
    }

    private static List<Integer> displayLists = new ArrayList<Integer>();
    private static List<Integer> textureNames = new ArrayList<Integer>();

}
