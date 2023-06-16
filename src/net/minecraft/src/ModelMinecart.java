package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ModelMinecart extends ModelBase
{

    public ModelMinecart()
    {
        field_1256_a = new ModelRenderer[7];
        field_1256_a[0] = new ModelRenderer(0, 10);
        field_1256_a[1] = new ModelRenderer(0, 0);
        field_1256_a[2] = new ModelRenderer(0, 0);
        field_1256_a[3] = new ModelRenderer(0, 0);
        field_1256_a[4] = new ModelRenderer(0, 0);
        field_1256_a[5] = new ModelRenderer(44, 10);
        byte byte0 = 20;
        byte byte1 = 8;
        byte byte2 = 16;
        byte byte3 = 4;
        field_1256_a[0].addBox(-byte0 / 2, -byte2 / 2, -1F, byte0, byte2, 2, 0.0F);
        field_1256_a[0].setPosition(0.0F, 0 + byte3, 0.0F);
        field_1256_a[5].addBox(-byte0 / 2 + 1, -byte2 / 2 + 1, -1F, byte0 - 2, byte2 - 2, 1, 0.0F);
        field_1256_a[5].setPosition(0.0F, 0 + byte3, 0.0F);
        field_1256_a[1].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        field_1256_a[1].setPosition(-byte0 / 2 + 1, 0 + byte3, 0.0F);
        field_1256_a[2].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        field_1256_a[2].setPosition(byte0 / 2 - 1, 0 + byte3, 0.0F);
        field_1256_a[3].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        field_1256_a[3].setPosition(0.0F, 0 + byte3, -byte2 / 2 + 1);
        field_1256_a[4].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        field_1256_a[4].setPosition(0.0F, 0 + byte3, byte2 / 2 - 1);
        field_1256_a[0].rotateAngleX = 1.570796F;
        field_1256_a[1].rotateAngleY = 4.712389F;
        field_1256_a[2].rotateAngleY = 1.570796F;
        field_1256_a[3].rotateAngleY = 3.141593F;
        field_1256_a[5].rotateAngleX = -1.570796F;
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        field_1256_a[5].offsetY = 4F - f2;
        for(int i = 0; i < 6; i++)
        {
            field_1256_a[i].render(f5);
        }

    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
    }

    public ModelRenderer field_1256_a[];
}
