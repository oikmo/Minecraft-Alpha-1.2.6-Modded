package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityFX extends Entity
{

    public EntityFX(World world, double d, double d1, double d2, 
            double d3, double d4, double d5)
    {
        super(world);
        field_667_e = 0;
        field_666_f = 0;
        setSize(0.2F, 0.2F);
        yOffset = height / 2.0F;
        setPosition(d, d1, d2);
        field_663_i = field_662_j = field_661_k = 1.0F;
        motionX = d3 + (double)((float)(Math.random() * 2D - 1.0D) * 0.4F);
        motionY = d4 + (double)((float)(Math.random() * 2D - 1.0D) * 0.4F);
        motionZ = d5 + (double)((float)(Math.random() * 2D - 1.0D) * 0.4F);
        float f = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
        float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX = (motionX / (double)f1) * (double)f * 0.40000000596046448D;
        motionY = (motionY / (double)f1) * (double)f * 0.40000000596046448D + 0.10000000149011612D;
        motionZ = (motionZ / (double)f1) * (double)f * 0.40000000596046448D;
        field_669_c = rand.nextFloat() * 3F;
        field_668_d = rand.nextFloat() * 3F;
        field_665_g = (rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
        field_666_f = (int)(4F / (rand.nextFloat() * 0.9F + 0.1F));
        field_667_e = 0;
        entityWalks = false;
    }

    public EntityFX func_407_b(float f)
    {
        motionX *= f;
        motionY = (motionY - 0.10000000149011612D) * (double)f + 0.10000000149011612D;
        motionZ *= f;
        return this;
    }

    public EntityFX func_405_d(float f)
    {
        setSize(0.2F * f, 0.2F * f);
        field_665_g *= f;
        return this;
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
        motionY -= 0.040000000000000001D * (double)field_664_h;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.98000001907348633D;
        motionY *= 0.98000001907348633D;
        motionZ *= 0.98000001907348633D;
        if(onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }
    }

    public void func_406_a(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
        float f6 = (float)(field_670_b % 16) / 16F;
        float f7 = f6 + 0.0624375F;
        float f8 = (float)(field_670_b / 16) / 16F;
        float f9 = f8 + 0.0624375F;
        float f10 = 0.1F * field_665_g;
        float f11 = (float)((prevPosX + (posX - prevPosX) * (double)f) - field_660_l);
        float f12 = (float)((prevPosY + (posY - prevPosY) * (double)f) - field_659_m);
        float f13 = (float)((prevPosZ + (posZ - prevPosZ) * (double)f) - field_658_n);
        float f14 = getEntityBrightness(f);
        tessellator.setColorOpaque_F(field_663_i * f14, field_662_j * f14, field_661_k * f14);
        tessellator.addVertexWithUV(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f6, f9);
        tessellator.addVertexWithUV((f11 - f1 * f10) + f4 * f10, f12 + f2 * f10, (f13 - f3 * f10) + f5 * f10, f6, f8);
        tessellator.addVertexWithUV(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f7, f8);
        tessellator.addVertexWithUV((f11 + f1 * f10) - f4 * f10, f12 - f2 * f10, (f13 + f3 * f10) - f5 * f10, f7, f9);
    }

    public int func_404_c()
    {
        return 0;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }

    protected int field_670_b;
    protected float field_669_c;
    protected float field_668_d;
    protected int field_667_e;
    protected int field_666_f;
    protected float field_665_g;
    protected float field_664_h;
    protected float field_663_i;
    protected float field_662_j;
    protected float field_661_k;
    public static double field_660_l;
    public static double field_659_m;
    public static double field_658_n;
}
