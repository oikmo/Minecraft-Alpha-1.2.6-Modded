package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class TexturedQuad
{

    public TexturedQuad(PositionTexureVertex apositiontexurevertex[])
    {
        field_1194_b = 0;
        field_1196_c = false;
        field_1195_a = apositiontexurevertex;
        field_1194_b = apositiontexurevertex.length;
    }

    public TexturedQuad(PositionTexureVertex apositiontexurevertex[], int i, int j, int k, int l)
    {
        this(apositiontexurevertex);
        float f = 0.0015625F;
        float f1 = 0.003125F;
        apositiontexurevertex[0] = apositiontexurevertex[0].setTexturePosition((float)k / 64F - f, (float)j / 32F + f1);
        apositiontexurevertex[1] = apositiontexurevertex[1].setTexturePosition((float)i / 64F + f, (float)j / 32F + f1);
        apositiontexurevertex[2] = apositiontexurevertex[2].setTexturePosition((float)i / 64F + f, (float)l / 32F - f1);
        apositiontexurevertex[3] = apositiontexurevertex[3].setTexturePosition((float)k / 64F - f, (float)l / 32F - f1);
    }

    public void func_809_a()
    {
        PositionTexureVertex apositiontexurevertex[] = new PositionTexureVertex[field_1195_a.length];
        for(int i = 0; i < field_1195_a.length; i++)
        {
            apositiontexurevertex[i] = field_1195_a[field_1195_a.length - i - 1];
        }

        field_1195_a = apositiontexurevertex;
    }

    public void func_808_a(Tessellator tessellator, float f)
    {
        Vec3D vec3d = field_1195_a[1].vector3D.subtract(field_1195_a[0].vector3D);
        Vec3D vec3d1 = field_1195_a[1].vector3D.subtract(field_1195_a[2].vector3D);
        Vec3D vec3d2 = vec3d1.crossProduct(vec3d).normalize();
        tessellator.startDrawingQuads();
        if(field_1196_c)
        {
            tessellator.setNormal(-(float)vec3d2.xCoord, -(float)vec3d2.yCoord, -(float)vec3d2.zCoord);
        } else
        {
            tessellator.setNormal((float)vec3d2.xCoord, (float)vec3d2.yCoord, (float)vec3d2.zCoord);
        }
        for(int i = 0; i < 4; i++)
        {
            PositionTexureVertex positiontexurevertex = field_1195_a[i];
            tessellator.addVertexWithUV((float)positiontexurevertex.vector3D.xCoord * f, (float)positiontexurevertex.vector3D.yCoord * f, (float)positiontexurevertex.vector3D.zCoord * f, positiontexurevertex.texturePositionX, positiontexurevertex.texturePositionY);
        }

        tessellator.draw();
    }

    public PositionTexureVertex field_1195_a[];
    public int field_1194_b;
    private boolean field_1196_c;
}
