package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityRainFX extends EntityFX
{

    public EntityRainFX(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        motionX *= 0.30000001192092896D;
        motionY = (float)Math.random() * 0.2F + 0.1F;
        motionZ *= 0.30000001192092896D;
        field_663_i = 1.0F;
        field_662_j = 1.0F;
        field_661_k = 1.0F;
        field_670_b = 19 + rand.nextInt(4);
        setSize(0.01F, 0.01F);
        field_664_h = 0.06F;
        field_666_f = (int)(8D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D));
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
        motionY -= field_664_h;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.98000001907348633D;
        motionY *= 0.98000001907348633D;
        motionZ *= 0.98000001907348633D;
        if(field_666_f-- <= 0)
        {
            setEntityDead();
        }
        if(onGround)
        {
            if(Math.random() < 0.5D)
            {
                setEntityDead();
            }
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }
        Material material = worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
        if(material.getIsLiquid() || material.func_878_a())
        {
            double d = (float)(MathHelper.floor_double(posY) + 1) - BlockFluids.func_288_b(worldObj.getBlockMetadata(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)));
            if(posY < d)
            {
                setEntityDead();
            }
        }
    }
}
