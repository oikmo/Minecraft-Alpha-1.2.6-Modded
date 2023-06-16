package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;
import org.lwjgl.opengl.GL11;

public class EffectRenderer
{

    @SuppressWarnings("unchecked")
	public EffectRenderer(World world, RenderEngine renderengine)
    {
        field_1728_b = new List[4];
        rand = new Random();
        if(world != null)
        {
            worldObj = world;
        }
        field_1731_c = renderengine;
        for(int i = 0; i < 4; i++)
        {
            field_1728_b[i] = new ArrayList<EntityFX>();
        }

    }

    public void func_1192_a(EntityFX entityfx)
    {
        int i = entityfx.func_404_c();
        field_1728_b[i].add(entityfx);
    }

    public void func_1193_a()
    {
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < field_1728_b[i].size(); j++)
            {
                EntityFX entityfx = (EntityFX)field_1728_b[i].get(j);
                entityfx.onUpdate();
                if(entityfx.isDead)
                {
                    field_1728_b[i].remove(j--);
                }
            }

        }

    }

    public void func_1189_a(Entity entity, float f)
    {
        float f1 = MathHelper.cos((entity.rotationYaw * 3.141593F) / 180F);
        float f2 = MathHelper.sin((entity.rotationYaw * 3.141593F) / 180F);
        float f3 = -f2 * MathHelper.sin((entity.rotationPitch * 3.141593F) / 180F);
        float f4 = f1 * MathHelper.sin((entity.rotationPitch * 3.141593F) / 180F);
        float f5 = MathHelper.cos((entity.rotationPitch * 3.141593F) / 180F);
        EntityFX.field_660_l = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f;
        EntityFX.field_659_m = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f;
        EntityFX.field_658_n = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f;
        for(int i = 0; i < 3; i++)
        {
            if(field_1728_b[i].size() == 0)
            {
                continue;
            }
            int j = 0;
            if(i == 0)
            {
                j = field_1731_c.getTexture("/particles.png");
            }
            if(i == 1)
            {
                j = field_1731_c.getTexture("/terrain.png");
            }
            if(i == 2)
            {
                j = field_1731_c.getTexture("/gui/items.png");
            }
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, j);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            for(int k = 0; k < field_1728_b[i].size(); k++)
            {
                EntityFX entityfx = (EntityFX)field_1728_b[i].get(k);
                entityfx.func_406_a(tessellator, f, f1, f5, f2, f3, f4);
            }

            tessellator.draw();
        }

    }

    public void func_1187_b(Entity entity, float f)
    {
        byte byte0 = 3;
        if(field_1728_b[byte0].size() == 0)
        {
            return;
        }
        Tessellator tessellator = Tessellator.instance;
        for(int i = 0; i < field_1728_b[byte0].size(); i++)
        {
            EntityFX entityfx = (EntityFX)field_1728_b[byte0].get(i);
            entityfx.func_406_a(tessellator, f, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        }

    }

    public void func_1188_a(World world)
    {
        worldObj = world;
        for(int i = 0; i < 4; i++)
        {
            field_1728_b[i].clear();
        }

    }

    public void func_1186_a(int i, int j, int k)
    {
        int l = worldObj.getBlockId(i, j, k);
        if(l == 0)
        {
            return;
        }
        Block block = Block.blocksList[l];
        int i1 = 4;
        for(int j1 = 0; j1 < i1; j1++)
        {
            for(int k1 = 0; k1 < i1; k1++)
            {
                for(int l1 = 0; l1 < i1; l1++)
                {
                    double d = (double)i + ((double)j1 + 0.5D) / (double)i1;
                    double d1 = (double)j + ((double)k1 + 0.5D) / (double)i1;
                    double d2 = (double)k + ((double)l1 + 0.5D) / (double)i1;
                    func_1192_a((new EntityDiggingFX(worldObj, d, d1, d2, d - (double)i - 0.5D, d1 - (double)j - 0.5D, d2 - (double)k - 0.5D, block)).func_4041_a(i, j, k));
                }

            }

        }

    }

    public void func_1191_a(int i, int j, int k, int l)
    {
        int i1 = worldObj.getBlockId(i, j, k);
        if(i1 == 0)
        {
            return;
        }
        Block block = Block.blocksList[i1];
        float f = 0.1F;
        double d = (double)i + rand.nextDouble() * (block.maxX - block.minX - (double)(f * 2.0F)) + (double)f + block.minX;
        double d1 = (double)j + rand.nextDouble() * (block.maxY - block.minY - (double)(f * 2.0F)) + (double)f + block.minY;
        double d2 = (double)k + rand.nextDouble() * (block.maxZ - block.minZ - (double)(f * 2.0F)) + (double)f + block.minZ;
        if(l == 0)
        {
            d1 = ((double)j + block.minY) - (double)f;
        }
        if(l == 1)
        {
            d1 = (double)j + block.maxY + (double)f;
        }
        if(l == 2)
        {
            d2 = ((double)k + block.minZ) - (double)f;
        }
        if(l == 3)
        {
            d2 = (double)k + block.maxZ + (double)f;
        }
        if(l == 4)
        {
            d = ((double)i + block.minX) - (double)f;
        }
        if(l == 5)
        {
            d = (double)i + block.maxX + (double)f;
        }
        func_1192_a((new EntityDiggingFX(worldObj, d, d1, d2, 0.0D, 0.0D, 0.0D, block)).func_4041_a(i, j, k).func_407_b(0.2F).func_405_d(0.6F));
    }

    public String func_1190_b()
    {
        return (new StringBuilder()).append("").append(field_1728_b[0].size() + field_1728_b[1].size() + field_1728_b[2].size()).toString();
    }

    protected World worldObj;
    private List<EntityFX> field_1728_b[];
    private RenderEngine field_1731_c;
    private Random rand;
}
