package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityGhast extends EntityFlying
    implements IMobs
{

    public EntityGhast(World world)
    {
        super(world);
        field_4121_a = 0;
        field_4123_g = null;
        field_4122_h = 0;
        field_4125_e = 0;
        field_4124_f = 0;
        texture = "/mob/ghast.png";
        setSize(4F, 4F);
        isImmuneToFire = true;
    }

    protected void updateEntityActionState()
    {
        if(worldObj.difficultySetting == 0)
        {
            setEntityDead();
        }
        field_4125_e = field_4124_f;
        double d = field_4120_b - posX;
        double d1 = field_4127_c - posY;
        double d2 = field_4126_d - posZ;
        double d3 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        if(d3 < 1.0D || d3 > 60D)
        {
            field_4120_b = posX + (double)((rand.nextFloat() * 2.0F - 1.0F) * 16F);
            field_4127_c = posY + (double)((rand.nextFloat() * 2.0F - 1.0F) * 16F);
            field_4126_d = posZ + (double)((rand.nextFloat() * 2.0F - 1.0F) * 16F);
        }
        if(field_4121_a-- <= 0)
        {
            field_4121_a += rand.nextInt(5) + 2;
            if(func_4050_a(field_4120_b, field_4127_c, field_4126_d, d3))
            {
                motionX += (d / d3) * 0.10000000000000001D;
                motionY += (d1 / d3) * 0.10000000000000001D;
                motionZ += (d2 / d3) * 0.10000000000000001D;
            } else
            {
                field_4120_b = posX;
                field_4127_c = posY;
                field_4126_d = posZ;
            }
        }
        if(field_4123_g != null && field_4123_g.isDead)
        {
            field_4123_g = null;
        }
        if(field_4123_g == null || field_4122_h-- <= 0)
        {
            field_4123_g = worldObj.getClosestPlayerToEntity(this, 100D);
            if(field_4123_g != null)
            {
                field_4122_h = 20;
            }
        }
        double d4 = 64D;
        if(field_4123_g != null && field_4123_g.getDistanceSqToEntity(this) < d4 * d4)
        {
            double d5 = field_4123_g.posX - posX;
            double d6 = (field_4123_g.boundingBox.minY + (double)(field_4123_g.height / 2.0F)) - (posY + (double)(height / 2.0F));
            double d7 = field_4123_g.posZ - posZ;
            renderYawOffset = rotationYaw = (-(float)Math.atan2(d5, d7) * 180F) / 3.141593F;
            if(canEntityBeSeen(field_4123_g))
            {
                if(field_4124_f == 10)
                {
                    worldObj.playSoundAtEntity(this, "mob.ghast.charge", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
                field_4124_f++;
                if(field_4124_f == 20)
                {
                    worldObj.playSoundAtEntity(this, "mob.ghast.fireball", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    EntityFireball entityfireball = new EntityFireball(worldObj, this, d5, d6, d7);
                    double d8 = 4D;
                    Vec3D vec3d = getLook(1.0F);
                    entityfireball.posX = posX + vec3d.xCoord * d8;
                    entityfireball.posY = posY + (double)(height / 2.0F) + 0.5D;
                    entityfireball.posZ = posZ + vec3d.zCoord * d8;
                    worldObj.entityJoinedWorld(entityfireball);
                    field_4124_f = -40;
                }
            } else
            if(field_4124_f > 0)
            {
                field_4124_f--;
            }
        } else
        {
            renderYawOffset = rotationYaw = (-(float)Math.atan2(motionX, motionZ) * 180F) / 3.141593F;
            if(field_4124_f > 0)
            {
                field_4124_f--;
            }
        }
        texture = field_4124_f <= 10 ? "/mob/ghast.png" : "/mob/ghast_fire.png";
    }

    private boolean func_4050_a(double d, double d1, double d2, double d3)
    {
        double d4 = (field_4120_b - posX) / d3;
        double d5 = (field_4127_c - posY) / d3;
        double d6 = (field_4126_d - posZ) / d3;
        AxisAlignedBB axisalignedbb = boundingBox.copy();
        for(int i = 1; (double)i < d3; i++)
        {
            axisalignedbb.offset(d4, d5, d6);
            if(worldObj.getCollidingBoundingBoxes(this, axisalignedbb).size() > 0)
            {
                return false;
            }
        }

        return true;
    }

    protected String getLivingSound()
    {
        return "mob.ghast.moan";
    }

    protected String getHurtSound()
    {
        return "mob.ghast.scream";
    }

    protected String getDeathSound()
    {
        return "mob.ghast.death";
    }

    protected int getDropItemId()
    {
        return Item.gunpowder.shiftedIndex;
    }

    protected float getSoundVolume()
    {
        return 10F;
    }

    public boolean getCanSpawnHere()
    {
        return rand.nextInt(20) == 0 && super.getCanSpawnHere() && worldObj.difficultySetting > 0;
    }

    public int func_6391_i()
    {
        return 1;
    }

    public int field_4121_a;
    public double field_4120_b;
    public double field_4127_c;
    public double field_4126_d;
    private Entity field_4123_g;
    private int field_4122_h;
    public int field_4125_e;
    public int field_4124_f;
}
