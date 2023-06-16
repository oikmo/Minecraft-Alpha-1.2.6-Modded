package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityReddustFX extends EntityFX
{

    public EntityReddustFX(World world, double d, double d1, double d2)
    {
        this(world, d, d1, d2, 1.0F);
    }

    public EntityReddustFX(World world, double d, double d1, double d2, 
            float f)
    {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        motionX *= 0.10000000149011612D;
        motionY *= 0.10000000149011612D;
        motionZ *= 0.10000000149011612D;
        field_663_i = (float)(Math.random() * 0.30000001192092896D) + 0.7F;
        field_662_j = field_661_k = (float)(Math.random() * 0.10000000149011612D);
        field_665_g *= 0.75F;
        field_665_g *= f;
        field_673_a = field_665_g;
        field_666_f = (int)(8D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D));
        field_666_f *= f;
        field_9314_ba = false;
    }

    public void func_406_a(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
        float f6 = (((float)field_667_e + f) / (float)field_666_f) * 32F;
        if(f6 < 0.0F)
        {
            f6 = 0.0F;
        }
        if(f6 > 1.0F)
        {
            f6 = 1.0F;
        }
        field_665_g = field_673_a * f6;
        super.func_406_a(tessellator, f, f1, f2, f3, f4, f5);
    }

    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if(field_667_e++ >= field_666_f)
        {
            setEntityDead();
        }
        field_670_b = 7 - (field_667_e * 8) / field_666_f;
        moveEntity(motionX, motionY, motionZ);
        if(posY == prevPosY)
        {
            motionX *= 1.1000000000000001D;
            motionZ *= 1.1000000000000001D;
        }
        motionX *= 0.95999997854232788D;
        motionY *= 0.95999997854232788D;
        motionZ *= 0.95999997854232788D;
        if(onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }
    }

    float field_673_a;
}
