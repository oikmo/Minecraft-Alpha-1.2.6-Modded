package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityFireball extends Entity
{

    public EntityFireball(World world)
    {
        super(world);
        field_9402_e = -1;
        field_9401_f = -1;
        field_9400_g = -1;
        field_9399_h = 0;
        field_9398_i = false;
        field_9406_a = 0;
        field_9395_l = 0;
        setSize(1.0F, 1.0F);
    }

    public boolean func_384_a(double d)
    {
        double d1 = boundingBox.getAverageEdgeLength() * 4D;
        d1 *= 64D;
        return d < d1 * d1;
    }

    public EntityFireball(World world, EntityLiving entityliving, double d, double d1, double d2)
    {
        super(world);
        field_9402_e = -1;
        field_9401_f = -1;
        field_9400_g = -1;
        field_9399_h = 0;
        field_9398_i = false;
        field_9406_a = 0;
        field_9395_l = 0;
        field_9397_j = entityliving;
        setSize(1.0F, 1.0F);
        setLocationAndAngles(entityliving.posX, entityliving.posY, entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        motionX = motionY = motionZ = 0.0D;
        d += rand.nextGaussian() * 0.40000000000000002D;
        d1 += rand.nextGaussian() * 0.40000000000000002D;
        d2 += rand.nextGaussian() * 0.40000000000000002D;
        double d3 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        field_9405_b = (d / d3) * 0.10000000000000001D;
        field_9404_c = (d1 / d3) * 0.10000000000000001D;
        field_9403_d = (d2 / d3) * 0.10000000000000001D;
    }

    public void onUpdate()
    {
        super.onUpdate();
        fire = 10;
        if(field_9406_a > 0)
        {
            field_9406_a--;
        }
        if(field_9398_i)
        {
            int i = worldObj.getBlockId(field_9402_e, field_9401_f, field_9400_g);
            if(i != field_9399_h)
            {
                field_9398_i = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                field_9396_k = 0;
                field_9395_l = 0;
            } else
            {
                field_9396_k++;
                if(field_9396_k == 1200)
                {
                    setEntityDead();
                }
                return;
            }
        } else
        {
            field_9395_l++;
        }
        Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
        Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1);
        vec3d = Vec3D.createVector(posX, posY, posZ);
        vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity entity = null;
        List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expands(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if(!entity1.canBeCollidedWith() || entity1 == field_9397_j && field_9395_l < 25)
            {
                continue;
            }
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expands(f2, f2, f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
            if(d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entityHit != null)
            {
                if(!movingobjectposition.entityHit.canAttackEntity(field_9397_j, 0));
            }
            worldObj.func_12244_a(null, posX, posY, posZ, 1.0F, true);
            setEntityDead();
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.95F;
        if(handleWaterMovement())
        {
            for(int k = 0; k < 4; k++)
            {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }

            f1 = 0.8F;
        }
        motionX += field_9405_b;
        motionY += field_9404_c;
        motionZ += field_9403_d;
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)field_9402_e);
        nbttagcompound.setShort("yTile", (short)field_9401_f);
        nbttagcompound.setShort("zTile", (short)field_9400_g);
        nbttagcompound.setByte("inTile", (byte)field_9399_h);
        nbttagcompound.setByte("shake", (byte)field_9406_a);
        nbttagcompound.setByte("inGround", (byte)(field_9398_i ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        field_9402_e = nbttagcompound.getShort("xTile");
        field_9401_f = nbttagcompound.getShort("yTile");
        field_9400_g = nbttagcompound.getShort("zTile");
        field_9399_h = nbttagcompound.getByte("inTile") & 0xff;
        field_9406_a = nbttagcompound.getByte("shake") & 0xff;
        field_9398_i = nbttagcompound.getByte("inGround") == 1;
    }

    public boolean canBeCollidedWith()
    {
        return true;
    }

    public float func_4035_j_()
    {
        return 1.0F;
    }

    public boolean canAttackEntity(Entity entity, int i)
    {
        func_9281_M();
        if(entity != null)
        {
            Vec3D vec3d = entity.func_4037_H();
            if(vec3d != null)
            {
                motionX = vec3d.xCoord;
                motionY = vec3d.yCoord;
                motionZ = vec3d.zCoord;
                field_9405_b = motionX * 0.10000000000000001D;
                field_9404_c = motionY * 0.10000000000000001D;
                field_9403_d = motionZ * 0.10000000000000001D;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public float func_392_h_()
    {
        return 0.0F;
    }

    private int field_9402_e;
    private int field_9401_f;
    private int field_9400_g;
    private int field_9399_h;
    private boolean field_9398_i;
    public int field_9406_a;
    private EntityLiving field_9397_j;
    private int field_9396_k;
    private int field_9395_l;
    public double field_9405_b;
    public double field_9404_c;
    public double field_9403_d;
}
