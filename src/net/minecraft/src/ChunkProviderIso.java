package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;

public class ChunkProviderIso
    implements IChunkProvider
{

    public ChunkProviderIso(World world, IChunkLoader ichunkloader)
    {
        chunks = new Chunk[256];
        field_899_a = new byte[32768];
        worldObj = world;
        chunkLoader = ichunkloader;
    }

    public boolean chunkExists(int i, int j)
    {
        int k = i & 0xf | (j & 0xf) * 16;
        return chunks[k] != null && chunks[k].isAtLocation(i, j);
    }

    public Chunk provideChunk(int i, int j)
    {
        int k = i & 0xf | (j & 0xf) * 16;
        try
        {
            if(!chunkExists(i, j))
            {
                Chunk chunk = func_543_c(i, j);
                if(chunk == null)
                {
                    chunk = new Chunk(worldObj, field_899_a, i, j);
                    chunk.field_1524_q = true;
                    chunk.neverSave = true;
                }
                chunks[k] = chunk;
            }
            return chunks[k];
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }

    private synchronized Chunk func_543_c(int i, int j)
    {
        try
        {
            return chunkLoader.loadChunk(worldObj, i, j);
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        return null;
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j)
    {
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate)
    {
        return true;
    }

    public boolean func_532_a()
    {
        return false;
    }

    public boolean func_536_b()
    {
        return false;
    }

    private Chunk chunks[];
    private World worldObj;
    private IChunkLoader chunkLoader;
    byte field_899_a[];
}
