package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityFallingSand extends Entity
{

    public EntityFallingSand(World world)
    {
        super(world);
        field_798_b = 0;
    }

    public EntityFallingSand(World world, float f, float f1, float f2, int i)
    {
        super(world);
        field_798_b = 0;
        field_799_a = i;
        field_618_ad = true;
        setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
        setPosition(f, f1, f2);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        entityWalks = false;
        prevPosX = f;
        prevPosY = f1;
        prevPosZ = f2;
    }

    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    public void onUpdate()
    {
        if(field_799_a == 0)
        {
            setEntityDead();
            return;
        }
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        field_798_b++;
        motionY -= 0.039999999105930328D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.98000001907348633D;
        motionY *= 0.98000001907348633D;
        motionZ *= 0.98000001907348633D;
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);
        if(worldObj.getBlockId(i, j, k) == field_799_a)
        {
            worldObj.setBlockWithNotify(i, j, k, 0);
        }
        if(onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
            motionY *= -0.5D;
            setEntityDead();
            if(!worldObj.canBlockBePlacedAt(field_799_a, i, j, k, true) || !worldObj.setBlockWithNotify(i, j, k, field_799_a))
            {
                dropItem(field_799_a, 1);
            }
        } else
        if(field_798_b > 100)
        {
            dropItem(field_799_a, 1);
            setEntityDead();
        }
    }

    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("Tile", (byte)field_799_a);
    }

    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        field_799_a = nbttagcompound.getByte("Tile") & 0xff;
    }

    public float func_392_h_()
    {
        return 0.0F;
    }

    public World func_465_i()
    {
        return worldObj;
    }

    public int field_799_a;
    public int field_798_b;
}
