package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ModelBiped extends ModelBase
{

    public ModelBiped()
    {
        this(0.0F);
    }

    public ModelBiped(float f)
    {
        this(f, 0.0F);
    }

    public ModelBiped(float f, float f1)
    {
        field_1279_h = false;
        field_1278_i = false;
        field_1277_j = false;
        bipedHead = new ModelRenderer(0, 0);
        bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, f);
        bipedHead.setPosition(0.0F, 0.0F + f1, 0.0F);
        field_1285_b = new ModelRenderer(32, 0);
        field_1285_b.addBox(-4F, -8F, -4F, 8, 8, 8, f + 0.5F);
        field_1285_b.setPosition(0.0F, 0.0F + f1, 0.0F);
        field_1284_c = new ModelRenderer(16, 16);
        field_1284_c.addBox(-4F, 0.0F, -2F, 8, 12, 4, f);
        field_1284_c.setPosition(0.0F, 0.0F + f1, 0.0F);
        bipedRightArm = new ModelRenderer(40, 16);
        bipedRightArm.addBox(-3F, -2F, -2F, 4, 12, 4, f);
        bipedRightArm.setPosition(-5F, 2.0F + f1, 0.0F);
        bipedLeftArm = new ModelRenderer(40, 16);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1F, -2F, -2F, 4, 12, 4, f);
        bipedLeftArm.setPosition(5F, 2.0F + f1, 0.0F);
        bipedRightLeg = new ModelRenderer(0, 16);
        bipedRightLeg.addBox(-2F, 0.0F, -2F, 4, 12, 4, f);
        bipedRightLeg.setPosition(-2F, 12F + f1, 0.0F);
        bipedLeftLeg = new ModelRenderer(0, 16);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-2F, 0.0F, -2F, 4, 12, 4, f);
        bipedLeftLeg.setPosition(2.0F, 12F + f1, 0.0F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        bipedHead.render(f5);
        field_1284_c.render(f5);
        bipedRightArm.render(f5);
        bipedLeftArm.render(f5);
        bipedRightLeg.render(f5);
        bipedLeftLeg.render(f5);
        field_1285_b.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        bipedHead.rotateAngleY = f3 / 57.29578F;
        bipedHead.rotateAngleX = f4 / 57.29578F;
        field_1285_b.rotateAngleY = bipedHead.rotateAngleY;
        field_1285_b.rotateAngleX = bipedHead.rotateAngleX;
        bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
        bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;
        if(field_1243_l)
        {
            bipedRightArm.rotateAngleX += -0.6283185F;
            bipedLeftArm.rotateAngleX += -0.6283185F;
            bipedRightLeg.rotateAngleX = -1.256637F;
            bipedLeftLeg.rotateAngleX = -1.256637F;
            bipedRightLeg.rotateAngleY = 0.3141593F;
            bipedLeftLeg.rotateAngleY = -0.3141593F;
        }
        if(field_1279_h)
        {
            bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5F - 0.3141593F;
        }
        if(field_1278_i)
        {
            bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5F - 0.3141593F;
        }
        bipedRightArm.rotateAngleY = 0.0F;
        bipedLeftArm.rotateAngleY = 0.0F;
        if(field_1244_k > -9990F)
        {
            float f6 = field_1244_k;
            field_1284_c.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.2F;
            bipedRightArm.offsetZ = MathHelper.sin(field_1284_c.rotateAngleY) * 5F;
            bipedRightArm.offsetX = -MathHelper.cos(field_1284_c.rotateAngleY) * 5F;
            bipedLeftArm.offsetZ = -MathHelper.sin(field_1284_c.rotateAngleY) * 5F;
            bipedLeftArm.offsetX = MathHelper.cos(field_1284_c.rotateAngleY) * 5F;
            bipedRightArm.rotateAngleY += field_1284_c.rotateAngleY;
            bipedLeftArm.rotateAngleY += field_1284_c.rotateAngleY;
            bipedLeftArm.rotateAngleX += field_1284_c.rotateAngleY;
            f6 = 1.0F - field_1244_k;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * 3.141593F);
            float f8 = MathHelper.sin(field_1244_k * 3.141593F) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
            bipedRightArm.rotateAngleX -= (double)f7 * 1.2D + (double)f8;
            bipedRightArm.rotateAngleY += field_1284_c.rotateAngleY * 2.0F;
            bipedRightArm.rotateAngleZ = MathHelper.sin(field_1244_k * 3.141593F) * -0.4F;
        }
        if(field_1277_j)
        {
            field_1284_c.rotateAngleX = 0.5F;
            bipedRightLeg.rotateAngleX -= 0.0F;
            bipedLeftLeg.rotateAngleX -= 0.0F;
            bipedRightArm.rotateAngleX += 0.4F;
            bipedLeftArm.rotateAngleX += 0.4F;
            bipedRightLeg.offsetZ = 4F;
            bipedLeftLeg.offsetZ = 4F;
            bipedRightLeg.offsetY = 9F;
            bipedLeftLeg.offsetY = 9F;
            bipedHead.offsetY = 1.0F;
        } else
        {
            field_1284_c.rotateAngleX = 0.0F;
            bipedRightLeg.offsetZ = 0.0F;
            bipedLeftLeg.offsetZ = 0.0F;
            bipedRightLeg.offsetY = 12F;
            bipedLeftLeg.offsetY = 12F;
            bipedHead.offsetY = 0.0F;
        }
        bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
    }

    public ModelRenderer bipedHead;
    public ModelRenderer field_1285_b;
    public ModelRenderer field_1284_c;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public boolean field_1279_h;
    public boolean field_1278_i;
    public boolean field_1277_j;
}
