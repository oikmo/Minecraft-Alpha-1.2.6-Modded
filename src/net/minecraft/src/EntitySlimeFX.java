package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntitySlimeFX extends EntityFX
{

    public EntitySlimeFX(World world, double d, double d1, double d2, 
            Item item)
    {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        field_670_b = item.getIconIndex(null);
        field_663_i = field_662_j = field_661_k = 1.0F;
        field_664_h = Block.blockSnow.field_357_bm;
        field_665_g /= 2.0F;
    }

    public int func_404_c()
    {
        return 2;
    }

    public void func_406_a(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
        float f6 = ((float)(field_670_b % 16) + field_669_c / 4F) / 16F;
        float f7 = f6 + 0.01560938F;
        float f8 = ((float)(field_670_b / 16) + field_668_d / 4F) / 16F;
        float f9 = f8 + 0.01560938F;
        float f10 = 0.1F * field_665_g;
        float f11 = (float)((prevPosX + (posX - prevPosX) * (double)f) - field_660_l);
        float f12 = (float)((prevPosY + (posY - prevPosY) * (double)f) - field_659_m);
        float f13 = (float)((prevPosZ + (posZ - prevPosZ) * (double)f) - field_658_n);
        float f14 = getEntityBrightness(f);
        tessellator.setColorOpaque_F(f14 * field_663_i, f14 * field_662_j, f14 * field_661_k);
        tessellator.addVertexWithUV(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f6, f9);
        tessellator.addVertexWithUV((f11 - f1 * f10) + f4 * f10, f12 + f2 * f10, (f13 - f3 * f10) + f5 * f10, f6, f8);
        tessellator.addVertexWithUV(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f7, f8);
        tessellator.addVertexWithUV((f11 + f1 * f10) - f4 * f10, f12 - f2 * f10, (f13 + f3 * f10) - f5 * f10, f7, f9);
    }
}
