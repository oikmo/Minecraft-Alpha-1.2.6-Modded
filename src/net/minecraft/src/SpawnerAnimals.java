package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

public final class SpawnerAnimals
{

    public SpawnerAnimals()
    {
    }

    protected static ChunkPosition getRandomSpawningPointInChunk(World world, int i, int j)
    {
        int k = i + world.rand.nextInt(16);
        int l = world.rand.nextInt(128);
        int i1 = j + world.rand.nextInt(16);
        return new ChunkPosition(k, l, i1);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static final int performSpawning(World var0) {
        eligibleChunksForSpawning.clear();

        int var1;
        for(var1 = 0; var1 < var0.playerEntities.size(); ++var1) {
           EntityPlayer var2 = (EntityPlayer)var0.playerEntities.get(var1);
           int var3 = MathHelper.floor_double(var2.posX / 16.0D);
           int var4 = MathHelper.floor_double(var2.posZ / 16.0D);
           byte var5 = 8;

           for(int var6 = -var5; var6 <= var5; ++var6) {
              for(int var7 = -var5; var7 <= var5; ++var7) {
                 eligibleChunksForSpawning.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
              }
           }
        }

        var1 = 0;

        for(int var28 = 0; var28 < EnumCreatureType.values().length; ++var28) {
           EnumCreatureType var29 = EnumCreatureType.values()[var28];
           if(var0.countEntities(var29.field_4278_c) <= var29.maxNumberOfEntityType * eligibleChunksForSpawning.size() / 256) {
              Iterator<ChunkCoordIntPair> var30 = eligibleChunksForSpawning.iterator();

              label90:
              while(var30.hasNext()) {
                 ChunkCoordIntPair var31 = (ChunkCoordIntPair)var30.next();
                 if(var0.rand.nextInt(50) == 0) {
                    MobSpawnerBase var32 = var0.func_4075_a().func_4074_a(var31);
                    Class[] var33 = var32.getEntitiesForType(var29);
                    if(var33 != null && var33.length != 0) {
                       int var8 = var0.rand.nextInt(var33.length);
                       ChunkPosition var9 = getRandomSpawningPointInChunk(var0, var31.chunkXPos * 16, var31.chunkZPos * 16);
                       int var10 = var9.x;
                       int var11 = var9.y;
                       int var12 = var9.z;
                       if(!var0.isBlockOpaqueCube(var10, var11, var12) && var0.getBlockMaterial(var10, var11, var12) == Material.air) {
                          int var13 = 0;

                          for(int var14 = 0; var14 < 3; ++var14) {
                             int var15 = var10;
                             int var16 = var11;
                             int var17 = var12;
                             byte var18 = 6;

                             for(int var19 = 0; var19 < 4; ++var19) {
                                var15 += var0.rand.nextInt(var18) - var0.rand.nextInt(var18);
                                var16 += var0.rand.nextInt(1) - var0.rand.nextInt(1);
                                var17 += var0.rand.nextInt(var18) - var0.rand.nextInt(var18);
                                if(var0.isBlockOpaqueCube(var15, var16 - 1, var17) && !var0.isBlockOpaqueCube(var15, var16, var17) && !var0.getBlockMaterial(var15, var16, var17).getIsLiquid() && !var0.isBlockOpaqueCube(var15, var16 + 1, var17)) {
                                   float var20 = (float)var15 + 0.5F;
                                   float var21 = (float)var16;
                                   float var22 = (float)var17 + 0.5F;
                                   if(var0.getClosestPlayer((double)var20, (double)var21, (double)var22, 24.0D) == null) {
                                      float var23 = var20 - (float)var0.spawnX;
                                      float var24 = var21 - (float)var0.spawnY;
                                      float var25 = var22 - (float)var0.spawnZ;
                                      float var26 = var23 * var23 + var24 * var24 + var25 * var25;
                                      if(var26 >= 576.0F) {
                                         EntityLiving var34;
                                         try {
                                            var34 = (EntityLiving)var33[var8].getConstructor(new Class[]{World.class}).newInstance(new Object[]{var0});
                                         } catch (Exception var27) {
                                            var27.printStackTrace();
                                            return var1;
                                         }

                                         var34.setLocationAndAngles((double)var20, (double)var21, (double)var22, var0.rand.nextFloat() * 360.0F, 0.0F);
                                         if(var34.getCanSpawnHere()) {
                                            ++var13;
                                            var0.entityJoinedWorld(var34);
                                            if(var34 instanceof EntitySpider && var0.rand.nextInt(100) == 0) {
                                               EntitySkeleton var35 = new EntitySkeleton(var0);
                                               var35.setLocationAndAngles((double)var20, (double)var21, (double)var22, var34.rotationYaw, 0.0F);
                                               var0.entityJoinedWorld(var35);
                                               var35.mountEntity(var34);
                                            }

                                            if(var13 >= var34.func_6391_i()) {
                                               continue label90;
                                            }
                                         }

                                         var1 += var13;
                                      }
                                   }
                                }
                             }
                          }
                       }
                    }
                 }
              }
           }
        }

        return var1;
     }

    private static Set<ChunkCoordIntPair> eligibleChunksForSpawning = new HashSet<ChunkCoordIntPair>();

}
