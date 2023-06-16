package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;

class MinecartTrackLogic
{

    public MinecartTrackLogic(BlockMinecartTrack blockminecarttrack, World world, int i, int j, int k)
    {
        field_1160_a = blockminecarttrack;
        field_1161_g = new ArrayList<ChunkPosition>();
        worldObj = world;
        field_1165_c = i;
        field_1164_d = j;
        field_1163_e = k;
        field_1162_f = world.getBlockMetadata(i, j, k);
        func_789_a();
    }

    private void func_789_a()
    {
        field_1161_g.clear();
        if(field_1162_f == 0)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d, field_1163_e - 1));
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d, field_1163_e + 1));
        } else
        if(field_1162_f == 1)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c - 1, field_1164_d, field_1163_e));
            field_1161_g.add(new ChunkPosition(field_1165_c + 1, field_1164_d, field_1163_e));
        } else
        if(field_1162_f == 2)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c - 1, field_1164_d, field_1163_e));
            field_1161_g.add(new ChunkPosition(field_1165_c + 1, field_1164_d + 1, field_1163_e));
        } else
        if(field_1162_f == 3)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c - 1, field_1164_d + 1, field_1163_e));
            field_1161_g.add(new ChunkPosition(field_1165_c + 1, field_1164_d, field_1163_e));
        } else
        if(field_1162_f == 4)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d + 1, field_1163_e - 1));
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d, field_1163_e + 1));
        } else
        if(field_1162_f == 5)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d, field_1163_e - 1));
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d + 1, field_1163_e + 1));
        } else
        if(field_1162_f == 6)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c + 1, field_1164_d, field_1163_e));
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d, field_1163_e + 1));
        } else
        if(field_1162_f == 7)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c - 1, field_1164_d, field_1163_e));
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d, field_1163_e + 1));
        } else
        if(field_1162_f == 8)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c - 1, field_1164_d, field_1163_e));
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d, field_1163_e - 1));
        } else
        if(field_1162_f == 9)
        {
            field_1161_g.add(new ChunkPosition(field_1165_c + 1, field_1164_d, field_1163_e));
            field_1161_g.add(new ChunkPosition(field_1165_c, field_1164_d, field_1163_e - 1));
        }
    }

    private void func_785_b()
    {
        for(int i = 0; i < field_1161_g.size(); i++)
        {
            MinecartTrackLogic minecarttracklogic = func_795_a((ChunkPosition)field_1161_g.get(i));
            if(minecarttracklogic == null || !minecarttracklogic.func_793_b(this))
            {
                field_1161_g.remove(i--);
            } else
            {
                field_1161_g.set(i, new ChunkPosition(minecarttracklogic.field_1165_c, minecarttracklogic.field_1164_d, minecarttracklogic.field_1163_e));
            }
        }

    }

    private boolean func_784_a(int i, int j, int k)
    {
        if(worldObj.getBlockId(i, j, k) == field_1160_a.blockID)
        {
            return true;
        }
        if(worldObj.getBlockId(i, j + 1, k) == field_1160_a.blockID)
        {
            return true;
        }
        return worldObj.getBlockId(i, j - 1, k) == field_1160_a.blockID;
    }

    private MinecartTrackLogic func_795_a(ChunkPosition chunkposition)
    {
        if(worldObj.getBlockId(chunkposition.x, chunkposition.y, chunkposition.z) == field_1160_a.blockID)
        {
            return new MinecartTrackLogic(field_1160_a, worldObj, chunkposition.x, chunkposition.y, chunkposition.z);
        }
        if(worldObj.getBlockId(chunkposition.x, chunkposition.y + 1, chunkposition.z) == field_1160_a.blockID)
        {
            return new MinecartTrackLogic(field_1160_a, worldObj, chunkposition.x, chunkposition.y + 1, chunkposition.z);
        }
        if(worldObj.getBlockId(chunkposition.x, chunkposition.y - 1, chunkposition.z) == field_1160_a.blockID)
        {
            return new MinecartTrackLogic(field_1160_a, worldObj, chunkposition.x, chunkposition.y - 1, chunkposition.z);
        } else
        {
            return null;
        }
    }

    private boolean func_793_b(MinecartTrackLogic minecarttracklogic)
    {
        for(int i = 0; i < field_1161_g.size(); i++)
        {
            ChunkPosition chunkposition = (ChunkPosition)field_1161_g.get(i);
            if(chunkposition.x == minecarttracklogic.field_1165_c && chunkposition.z == minecarttracklogic.field_1163_e)
            {
                return true;
            }
        }

        return false;
    }

    private boolean func_794_b(int i, int j, int k)
    {
        for(int l = 0; l < field_1161_g.size(); l++)
        {
            ChunkPosition chunkposition = (ChunkPosition)field_1161_g.get(l);
            if(chunkposition.x == i && chunkposition.z == k)
            {
                return true;
            }
        }

        return false;
    }

    private int func_790_c()
    {
        int i = 0;
        if(func_784_a(field_1165_c, field_1164_d, field_1163_e - 1))
        {
            i++;
        }
        if(func_784_a(field_1165_c, field_1164_d, field_1163_e + 1))
        {
            i++;
        }
        if(func_784_a(field_1165_c - 1, field_1164_d, field_1163_e))
        {
            i++;
        }
        if(func_784_a(field_1165_c + 1, field_1164_d, field_1163_e))
        {
            i++;
        }
        return i;
    }

    private boolean func_787_c(MinecartTrackLogic minecarttracklogic)
    {
        if(func_793_b(minecarttracklogic))
        {
            return true;
        }
        if(field_1161_g.size() == 2)
        {
            return false;
        }
        if(field_1161_g.size() == 0)
        {
            return true;
        }
        ChunkPosition chunkposition = (ChunkPosition)field_1161_g.get(0);
        return minecarttracklogic.field_1164_d != field_1164_d || chunkposition.y != field_1164_d ? true : true;
    }

    private void func_788_d(MinecartTrackLogic minecarttracklogic)
    {
        field_1161_g.add(new ChunkPosition(minecarttracklogic.field_1165_c, minecarttracklogic.field_1164_d, minecarttracklogic.field_1163_e));
        boolean flag = func_794_b(field_1165_c, field_1164_d, field_1163_e - 1);
        boolean flag1 = func_794_b(field_1165_c, field_1164_d, field_1163_e + 1);
        boolean flag2 = func_794_b(field_1165_c - 1, field_1164_d, field_1163_e);
        boolean flag3 = func_794_b(field_1165_c + 1, field_1164_d, field_1163_e);
        byte byte0 = -1;
        if(flag || flag1)
        {
            byte0 = 0;
        }
        if(flag2 || flag3)
        {
            byte0 = 1;
        }
        if(flag1 && flag3 && !flag && !flag2)
        {
            byte0 = 6;
        }
        if(flag1 && flag2 && !flag && !flag3)
        {
            byte0 = 7;
        }
        if(flag && flag2 && !flag1 && !flag3)
        {
            byte0 = 8;
        }
        if(flag && flag3 && !flag1 && !flag2)
        {
            byte0 = 9;
        }
        if(byte0 == 0)
        {
            if(worldObj.getBlockId(field_1165_c, field_1164_d + 1, field_1163_e - 1) == field_1160_a.blockID)
            {
                byte0 = 4;
            }
            if(worldObj.getBlockId(field_1165_c, field_1164_d + 1, field_1163_e + 1) == field_1160_a.blockID)
            {
                byte0 = 5;
            }
        }
        if(byte0 == 1)
        {
            if(worldObj.getBlockId(field_1165_c + 1, field_1164_d + 1, field_1163_e) == field_1160_a.blockID)
            {
                byte0 = 2;
            }
            if(worldObj.getBlockId(field_1165_c - 1, field_1164_d + 1, field_1163_e) == field_1160_a.blockID)
            {
                byte0 = 3;
            }
        }
        if(byte0 < 0)
        {
            byte0 = 0;
        }
        worldObj.setBlockMetadataWithNotify(field_1165_c, field_1164_d, field_1163_e, byte0);
    }

    private boolean func_786_c(int i, int j, int k)
    {
        MinecartTrackLogic minecarttracklogic = func_795_a(new ChunkPosition(i, j, k));
        if(minecarttracklogic == null)
        {
            return false;
        } else
        {
            minecarttracklogic.func_785_b();
            return minecarttracklogic.func_787_c(this);
        }
    }

    public void func_792_a(boolean flag)
    {
        boolean flag1 = func_786_c(field_1165_c, field_1164_d, field_1163_e - 1);
        boolean flag2 = func_786_c(field_1165_c, field_1164_d, field_1163_e + 1);
        boolean flag3 = func_786_c(field_1165_c - 1, field_1164_d, field_1163_e);
        boolean flag4 = func_786_c(field_1165_c + 1, field_1164_d, field_1163_e);
        int i = -1;
        if((flag1 || flag2) && !flag3 && !flag4)
        {
            i = 0;
        }
        if((flag3 || flag4) && !flag1 && !flag2)
        {
            i = 1;
        }
        if(flag2 && flag4 && !flag1 && !flag3)
        {
            i = 6;
        }
        if(flag2 && flag3 && !flag1 && !flag4)
        {
            i = 7;
        }
        if(flag1 && flag3 && !flag2 && !flag4)
        {
            i = 8;
        }
        if(flag1 && flag4 && !flag2 && !flag3)
        {
            i = 9;
        }
        if(i == -1)
        {
            if(flag1 || flag2)
            {
                i = 0;
            }
            if(flag3 || flag4)
            {
                i = 1;
            }
            if(flag)
            {
                if(flag2 && flag4)
                {
                    i = 6;
                }
                if(flag3 && flag2)
                {
                    i = 7;
                }
                if(flag4 && flag1)
                {
                    i = 9;
                }
                if(flag1 && flag3)
                {
                    i = 8;
                }
            } else
            {
                if(flag1 && flag3)
                {
                    i = 8;
                }
                if(flag4 && flag1)
                {
                    i = 9;
                }
                if(flag3 && flag2)
                {
                    i = 7;
                }
                if(flag2 && flag4)
                {
                    i = 6;
                }
            }
        }
        if(i == 0)
        {
            if(worldObj.getBlockId(field_1165_c, field_1164_d + 1, field_1163_e - 1) == field_1160_a.blockID)
            {
                i = 4;
            }
            if(worldObj.getBlockId(field_1165_c, field_1164_d + 1, field_1163_e + 1) == field_1160_a.blockID)
            {
                i = 5;
            }
        }
        if(i == 1)
        {
            if(worldObj.getBlockId(field_1165_c + 1, field_1164_d + 1, field_1163_e) == field_1160_a.blockID)
            {
                i = 2;
            }
            if(worldObj.getBlockId(field_1165_c - 1, field_1164_d + 1, field_1163_e) == field_1160_a.blockID)
            {
                i = 3;
            }
        }
        if(i < 0)
        {
            i = 0;
        }
        field_1162_f = i;
        func_789_a();
        worldObj.setBlockMetadataWithNotify(field_1165_c, field_1164_d, field_1163_e, i);
        for(int j = 0; j < field_1161_g.size(); j++)
        {
            MinecartTrackLogic minecarttracklogic = func_795_a((ChunkPosition)field_1161_g.get(j));
            if(minecarttracklogic == null)
            {
                continue;
            }
            minecarttracklogic.func_785_b();
            if(minecarttracklogic.func_787_c(this))
            {
                minecarttracklogic.func_788_d(this);
            }
        }

    }

    static int func_791_a(MinecartTrackLogic minecarttracklogic)
    {
        return minecarttracklogic.func_790_c();
    }

    private World worldObj;
    private int field_1165_c;
    private int field_1164_d;
    private int field_1163_e;
    private int field_1162_f;
    private List<ChunkPosition> field_1161_g;
    final BlockMinecartTrack field_1160_a; /* synthetic field */
}
