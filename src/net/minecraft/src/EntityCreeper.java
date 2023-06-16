package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityCreeper extends EntityMobs
{

    public EntityCreeper(World world)
    {
        super(world);
        fuseTime = 30;
        creeperState = -1;
        field_12241_e = -1;
        texture = "/mob/creeper.png";
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    public void func_9282_a(byte byte0)
    {
        super.func_9282_a(byte0);
        if(byte0 == 4)
        {
            if(timeSinceIgnited == 0)
            {
                worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
            }
            creeperState = 1;
        }
        if(byte0 == 5)
        {
            creeperState = -1;
        }
    }

    public void onUpdate()
    {
        lastActiveTime = timeSinceIgnited;
        if(worldObj.multiplayerWorld)
        {
            timeSinceIgnited += creeperState;
            if(timeSinceIgnited < 0)
            {
                timeSinceIgnited = 0;
            }
            if(timeSinceIgnited >= fuseTime)
            {
                timeSinceIgnited = fuseTime;
            }
        }
        super.onUpdate();
    }

    protected void updateEntityActionState()
    {
        if(field_12241_e != creeperState)
        {
            field_12241_e = creeperState;
            if(creeperState > 0)
            {
                worldObj.func_9425_a(this, (byte)4);
            } else
            {
                worldObj.func_9425_a(this, (byte)5);
            }
        }
        lastActiveTime = timeSinceIgnited;
        if(worldObj.multiplayerWorld)
        {
            super.updateEntityActionState();
        } else
        {
            if(timeSinceIgnited > 0 && creeperState < 0)
            {
                timeSinceIgnited--;
            }
            if(creeperState >= 0)
            {
                creeperState = 2;
            }
            super.updateEntityActionState();
            if(creeperState != 1)
            {
                creeperState = -1;
            }
        }
    }

    protected String getHurtSound()
    {
        return "mob.creeper";
    }

    protected String getDeathSound()
    {
        return "mob.creeperdeath";
    }

    public void onDeath(Entity entity)
    {
        super.onDeath(entity);
        if(entity instanceof EntitySkeleton)
        {
            dropItem(Item.record13.shiftedIndex + rand.nextInt(2), 1);
        }
    }

    protected void attackEntity(Entity entity, float f)
    {
        if(creeperState <= 0 && f < 3F || creeperState > 0 && f < 7F)
        {
            if(timeSinceIgnited == 0)
            {
                worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
            }
            creeperState = 1;
            timeSinceIgnited++;
            if(timeSinceIgnited == fuseTime)
            {
                worldObj.func_12243_a(this, posX, posY, posZ, 3F);
                setEntityDead();
            }
            hasAttacked = true;
        }
    }

    public float func_440_b(float f)
    {
        return ((float)lastActiveTime + (float)(timeSinceIgnited - lastActiveTime) * f) / (float)(fuseTime - 2);
    }

    protected int getDropItemId()
    {
        return Item.gunpowder.shiftedIndex;
    }

    int timeSinceIgnited;
    int lastActiveTime;
    int fuseTime;
    int creeperState;
    int field_12241_e;
}
