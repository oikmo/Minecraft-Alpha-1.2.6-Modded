package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer
{

    public TileEntityMobSpawnerRenderer()
    {
        field_1412_b = new HashMap<String, Entity>();
    }

    public void func_931_a(TileEntityMobSpawner tileentitymobspawner, double d, double d1, double d2, 
            float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d + 0.5F, (float)d1, (float)d2 + 0.5F);
        Entity entity = (Entity)field_1412_b.get(tileentitymobspawner.entityID);
        if(entity == null)
        {
            entity = EntityList.createEntityInWorld(tileentitymobspawner.entityID, null);
            field_1412_b.put(tileentitymobspawner.entityID, entity);
        }
        if(entity != null)
        {
            entity.setWorld(tileentitymobspawner.worldObj);
            float f1 = 0.4375F;
            GL11.glTranslatef(0.0F, 0.4F, 0.0F);
            GL11.glRotatef((float)(tileentitymobspawner.field_830_d + (tileentitymobspawner.field_831_c - tileentitymobspawner.field_830_d) * (double)f) * 10F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.4F, 0.0F);
            GL11.glScalef(f1, f1, f1);
            entity.setLocationAndAngles(d, d1, d2, 0.0F, 0.0F);
            RenderManager.instance.func_853_a(entity, 0.0D, 0.0D, 0.0D, 0.0F, f);
        }
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, 
            float f)
    {
        func_931_a((TileEntityMobSpawner)tileentity, d, d1, d2, f);
    }

    private Map<String, Entity> field_1412_b;
}
