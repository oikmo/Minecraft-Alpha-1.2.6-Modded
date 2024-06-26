package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

public class Explosion
{

    public Explosion(World world, Entity entity, double d, double d1, double d2, float f)
    {
        field_12257_a = false;
        field_12250_h = new Random();
        field_12251_g = new HashSet<ChunkPosition>();
        field_12249_i = world;
        field_12253_e = entity;
        field_12252_f = f;
        field_12256_b = d;
        field_12255_c = d1;
        field_12254_d = d2;
    }

    public void func_12248_a()
    {
        float f = field_12252_f;
        int i = 16;
        for(int j = 0; j < i; j++)
        {
            for(int l = 0; l < i; l++)
            {
label0:
                for(int j1 = 0; j1 < i; j1++)
                {
                    if(j != 0 && j != i - 1 && l != 0 && l != i - 1 && j1 != 0 && j1 != i - 1)
                    {
                        continue;
                    }
                    double d = ((float)j / ((float)i - 1.0F)) * 2.0F - 1.0F;
                    double d1 = ((float)l / ((float)i - 1.0F)) * 2.0F - 1.0F;
                    double d2 = ((float)j1 / ((float)i - 1.0F)) * 2.0F - 1.0F;
                    double d3 = Math.sqrt(d * d + d1 * d1 + d2 * d2);
                    d /= d3;
                    d1 /= d3;
                    d2 /= d3;
                    float f1 = field_12252_f * (0.7F + field_12249_i.rand.nextFloat() * 0.6F);
                    double d5 = field_12256_b;
                    double d7 = field_12255_c;
                    double d9 = field_12254_d;
                    float f2 = 0.3F;
                    do
                    {
                        if(f1 <= 0.0F)
                        {
                            continue label0;
                        }
                        int j4 = MathHelper.floor_double(d5);
                        int k4 = MathHelper.floor_double(d7);
                        int l4 = MathHelper.floor_double(d9);
                        int i5 = field_12249_i.getBlockId(j4, k4, l4);
                        if(i5 > 0)
                        {
                            f1 -= (Block.blocksList[i5].getExplosionResistance(field_12253_e) + 0.3F) * f2;
                        }
                        if(f1 > 0.0F)
                        {
                            field_12251_g.add(new ChunkPosition(j4, k4, l4));
                        }
                        d5 += d * (double)f2;
                        d7 += d1 * (double)f2;
                        d9 += d2 * (double)f2;
                        f1 -= f2 * 0.75F;
                    } while(true);
                }

            }

        }

        field_12252_f *= 2.0F;
        int k = MathHelper.floor_double(field_12256_b - (double)field_12252_f - 1.0D);
        int i1 = MathHelper.floor_double(field_12256_b + (double)field_12252_f + 1.0D);
        int k1 = MathHelper.floor_double(field_12255_c - (double)field_12252_f - 1.0D);
        int l1 = MathHelper.floor_double(field_12255_c + (double)field_12252_f + 1.0D);
        int i2 = MathHelper.floor_double(field_12254_d - (double)field_12252_f - 1.0D);
        int j2 = MathHelper.floor_double(field_12254_d + (double)field_12252_f + 1.0D);
        List<?> list = field_12249_i.getEntitiesWithinAABBExcludingEntity(field_12253_e, AxisAlignedBB.getBoundingBoxFromPool(k, k1, i2, i1, l1, j2));
        Vec3D vec3d = Vec3D.createVector(field_12256_b, field_12255_c, field_12254_d);
        for(int k2 = 0; k2 < list.size(); k2++)
        {
            Entity entity = (Entity)list.get(k2);
            double d4 = entity.getDistance(field_12256_b, field_12255_c, field_12254_d) / (double)field_12252_f;
            if(d4 <= 1.0D)
            {
                double d6 = entity.posX - field_12256_b;
                double d8 = entity.posY - field_12255_c;
                double d10 = entity.posZ - field_12254_d;
                double d11 = MathHelper.sqrt_double(d6 * d6 + d8 * d8 + d10 * d10);
                d6 /= d11;
                d8 /= d11;
                d10 /= d11;
                double d12 = field_12249_i.func_675_a(vec3d, entity.boundingBox);
                double d13 = (1.0D - d4) * d12;
                entity.canAttackEntity(field_12253_e, (int)(((d13 * d13 + d13) / 2D) * 8D * (double)field_12252_f + 1.0D));
                double d14 = d13;
                entity.motionX += d6 * d14;
                entity.motionY += d8 * d14;
                entity.motionZ += d10 * d14;
            }
        }

        field_12252_f = f;
        ArrayList<ChunkPosition> arraylist = new ArrayList<ChunkPosition>();
        arraylist.addAll(field_12251_g);
        if(field_12257_a)
        {
            for(int l2 = arraylist.size() - 1; l2 >= 0; l2--)
            {
                ChunkPosition chunkposition = (ChunkPosition)arraylist.get(l2);
                int i3 = chunkposition.x;
                int j3 = chunkposition.y;
                int k3 = chunkposition.z;
                int l3 = field_12249_i.getBlockId(i3, j3, k3);
                int i4 = field_12249_i.getBlockId(i3, j3 - 1, k3);
                if(l3 == 0 && Block.opaqueCubeLookup[i4] && field_12250_h.nextInt(3) == 0)
                {
                    field_12249_i.setBlockWithNotify(i3, j3, k3, Block.fire.blockID);
                }
            }

        }
    }

    public void func_12247_b()
    {
        field_12249_i.playSoundEffect(field_12256_b, field_12255_c, field_12254_d, "random.explode", 4F, (1.0F + (field_12249_i.rand.nextFloat() - field_12249_i.rand.nextFloat()) * 0.2F) * 0.7F);
        ArrayList<ChunkPosition> arraylist = new ArrayList<ChunkPosition>();
        arraylist.addAll(field_12251_g);
        for(int i = arraylist.size() - 1; i >= 0; i--)
        {
            ChunkPosition chunkposition = (ChunkPosition)arraylist.get(i);
            int j = chunkposition.x;
            int k = chunkposition.y;
            int l = chunkposition.z;
            int i1 = field_12249_i.getBlockId(j, k, l);
            for(int j1 = 0; j1 < 1; j1++)
            {
                double d = (float)j + field_12249_i.rand.nextFloat();
                double d1 = (float)k + field_12249_i.rand.nextFloat();
                double d2 = (float)l + field_12249_i.rand.nextFloat();
                double d3 = d - field_12256_b;
                double d4 = d1 - field_12255_c;
                double d5 = d2 - field_12254_d;
                double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                d3 /= d6;
                d4 /= d6;
                d5 /= d6;
                double d7 = 0.5D / (d6 / (double)field_12252_f + 0.10000000000000001D);
                d7 *= field_12249_i.rand.nextFloat() * field_12249_i.rand.nextFloat() + 0.3F;
                d3 *= d7;
                d4 *= d7;
                d5 *= d7;
                field_12249_i.spawnParticle("explode", (d + field_12256_b * 1.0D) / 2D, (d1 + field_12255_c * 1.0D) / 2D, (d2 + field_12254_d * 1.0D) / 2D, d3, d4, d5);
                field_12249_i.spawnParticle("smoke", d, d1, d2, d3, d4, d5);
            }

            if(i1 > 0)
            {
                Block.blocksList[i1].dropBlockAsItemWithChance(field_12249_i, j, k, l, field_12249_i.getBlockMetadata(j, k, l), 0.3F);
                field_12249_i.setBlockWithNotify(j, k, l, 0);
                Block.blocksList[i1].onBlockDestroyedByExplosion(field_12249_i, j, k, l);
            }
        }

    }

    public boolean field_12257_a;
    private Random field_12250_h;
    private World field_12249_i;
    public double field_12256_b;
    public double field_12255_c;
    public double field_12254_d;
    public Entity field_12253_e;
    public float field_12252_f;
    public Set<ChunkPosition> field_12251_g;
}
