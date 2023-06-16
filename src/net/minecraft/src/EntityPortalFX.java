package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityPortalFX extends EntityFX
{

    public EntityPortalFX(World world, double d, double d1, double d2, 
            double d3, double d4, double d5)
    {
        super(world, d, d1, d2, d3, d4, d5);
        motionX = d3;
        motionY = d4;
        motionZ = d5;
        field_4086_p = posX = d;
        field_4085_q = posY = d1;
        field_4084_r = posZ = d2;
        float f = rand.nextFloat() * 0.6F + 0.4F;
        field_4083_a = field_665_g = rand.nextFloat() * 0.2F + 0.5F;
        field_663_i = field_662_j = field_661_k = 1.0F * f;
        field_662_j *= 0.3F;
        field_663_i *= 0.9F;
        field_666_f = (int)(Math.random() * 10D) + 40;
        field_9314_ba = true;
        field_670_b = (int)(Math.random() * 8D);
    }

    public void func_406_a(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
        float f6 = ((float)field_667_e + f) / (float)field_666_f;
        f6 = 1.0F - f6;
        f6 *= f6;
        f6 = 1.0F - f6;
        field_665_g = field_4083_a * f6;
        super.func_406_a(tessellator, f, f1, f2, f3, f4, f5);
    }

    public float getEntityBrightness(float f)
    {
        float f1 = super.getEntityBrightness(f);
        float f2 = (float)field_667_e / (float)field_666_f;
        f2 *= f2;
        f2 *= f2;
        return f1 * (1.0F - f2) + f2;
    }

    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        float f = (float)field_667_e / (float)field_666_f;
        float f1 = f;
        f = -f + f * f * 2.0F;
        f = 1.0F - f;
        posX = field_4086_p + motionX * (double)f;
        posY = field_4085_q + motionY * (double)f + (double)(1.0F - f1);
        posZ = field_4084_r + motionZ * (double)f;
        if(field_667_e++ >= field_666_f)
        {
            setEntityDead();
        }
    }

    private float field_4083_a;
    private double field_4086_p;
    private double field_4085_q;
    private double field_4084_r;
}
