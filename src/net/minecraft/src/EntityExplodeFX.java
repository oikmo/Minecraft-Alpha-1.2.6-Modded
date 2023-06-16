package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityExplodeFX extends EntityFX
{

    public EntityExplodeFX(World world, double d, double d1, double d2, 
            double d3, double d4, double d5)
    {
        super(world, d, d1, d2, d3, d4, d5);
        motionX = d3 + (double)((float)(Math.random() * 2D - 1.0D) * 0.05F);
        motionY = d4 + (double)((float)(Math.random() * 2D - 1.0D) * 0.05F);
        motionZ = d5 + (double)((float)(Math.random() * 2D - 1.0D) * 0.05F);
        field_663_i = field_662_j = field_661_k = rand.nextFloat() * 0.3F + 0.7F;
        field_665_g = rand.nextFloat() * rand.nextFloat() * 6F + 1.0F;
        field_666_f = (int)(16D / ((double)rand.nextFloat() * 0.80000000000000004D + 0.20000000000000001D)) + 2;
    }

    public void func_406_a(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
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
        motionY += 0.0040000000000000001D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.89999997615814209D;
        motionY *= 0.89999997615814209D;
        motionZ *= 0.89999997615814209D;
        if(onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }
    }
}
