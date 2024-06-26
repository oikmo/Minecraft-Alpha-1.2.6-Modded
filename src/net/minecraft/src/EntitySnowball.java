package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntitySnowball extends Entity
{

    public EntitySnowball(World world)
    {
        super(world);
        field_816_b = -1;
        field_815_c = -1;
        field_814_d = -1;
        field_813_e = 0;
        field_812_f = false;
        field_817_a = 0;
        field_809_i = 0;
        setSize(0.25F, 0.25F);
    }

    public boolean func_384_a(double d)
    {
        double d1 = boundingBox.getAverageEdgeLength() * 4D;
        d1 *= 64D;
        return d < d1 * d1;
    }

    public EntitySnowball(World world, EntityLiving entityliving)
    {
        super(world);
        field_816_b = -1;
        field_815_c = -1;
        field_814_d = -1;
        field_813_e = 0;
        field_812_f = false;
        field_817_a = 0;
        field_809_i = 0;
        field_811_g = entityliving;
        setSize(0.25F, 0.25F);
        setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.func_373_s(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        float f = 0.4F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
        func_467_a(motionX, motionY, motionZ, 1.5F, 1.0F);
    }

    public EntitySnowball(World world, double d, double d1, double d2)
    {
        super(world);
        field_816_b = -1;
        field_815_c = -1;
        field_814_d = -1;
        field_813_e = 0;
        field_812_f = false;
        field_817_a = 0;
        field_809_i = 0;
        field_810_h = 0;
        setSize(0.25F, 0.25F);
        setPosition(d, d1, d2);
        yOffset = 0.0F;
    }

    public void func_467_a(double d, double d1, double d2, float f, 
            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        field_810_h = 0;
    }

    public void setVelocity(double d, double d1, double d2)
    {
        motionX = d;
        motionY = d1;
        motionZ = d2;
        if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(d * d + d2 * d2);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
        }
    }

    public void onUpdate()
    {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        super.onUpdate();
        if(field_817_a > 0)
        {
            field_817_a--;
        }
        if(field_812_f)
        {
            int i = worldObj.getBlockId(field_816_b, field_815_c, field_814_d);
            if(i != field_813_e)
            {
                field_812_f = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                field_810_h = 0;
                field_809_i = 0;
            } else
            {
                field_810_h++;
                if(field_810_h == 1200)
                {
                    setEntityDead();
                }
                return;
            }
        } else
        {
            field_809_i++;
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
        if(!worldObj.multiplayerWorld)
        {
            Entity entity = null;
            List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expands(1.0D, 1.0D, 1.0D));
            double d = 0.0D;
            for(int l = 0; l < list.size(); l++)
            {
                Entity entity1 = (Entity)list.get(l);
                if(!entity1.canBeCollidedWith() || entity1 == field_811_g && field_809_i < 5)
                {
                    continue;
                }
                float f4 = 0.3F;
                AxisAlignedBB axisalignedbb = entity1.boundingBox.expands(f4, f4, f4);
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
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entityHit != null)
            {
                if(!movingobjectposition.entityHit.canAttackEntity(field_811_g, 0));
            }
            for(int j = 0; j < 8; j++)
            {
                worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }

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
        float f1 = 0.99F;
        float f2 = 0.03F;
        if(handleWaterMovement())
        {
            for(int k = 0; k < 4; k++)
            {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }

            f1 = 0.8F;
        }
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        motionY -= f2;
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)field_816_b);
        nbttagcompound.setShort("yTile", (short)field_815_c);
        nbttagcompound.setShort("zTile", (short)field_814_d);
        nbttagcompound.setByte("inTile", (byte)field_813_e);
        nbttagcompound.setByte("shake", (byte)field_817_a);
        nbttagcompound.setByte("inGround", (byte)(field_812_f ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        field_816_b = nbttagcompound.getShort("xTile");
        field_815_c = nbttagcompound.getShort("yTile");
        field_814_d = nbttagcompound.getShort("zTile");
        field_813_e = nbttagcompound.getByte("inTile") & 0xff;
        field_817_a = nbttagcompound.getByte("shake") & 0xff;
        field_812_f = nbttagcompound.getByte("inGround") == 1;
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        if(field_812_f && field_811_g == entityplayer && field_817_a <= 0 && entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow.shiftedIndex, 1)))
        {
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.func_443_a_(this, 1);
            setEntityDead();
        }
    }

    public float func_392_h_()
    {
        return 0.0F;
    }

    private int field_816_b;
    private int field_815_c;
    private int field_814_d;
    private int field_813_e;
    private boolean field_812_f;
    public int field_817_a;
    private EntityLiving field_811_g;
    private int field_810_h;
    private int field_809_i;
}
