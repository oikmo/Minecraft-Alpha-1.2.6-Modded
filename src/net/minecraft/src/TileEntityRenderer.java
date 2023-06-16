package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;
import org.lwjgl.opengl.GL11;

public class TileEntityRenderer
{

    private TileEntityRenderer()
    {
        specialRendererMap = new HashMap<Class<?>, TileEntitySpecialRenderer>();
        specialRendererMap.put(TileEntitySign.class, new TileEntitySignRenderer());
        specialRendererMap.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        TileEntitySpecialRenderer tileentityspecialrenderer;
        for(Iterator<TileEntitySpecialRenderer> iterator = specialRendererMap.values().iterator(); iterator.hasNext(); tileentityspecialrenderer.setTileEntityRenderer(this))
        {
            tileentityspecialrenderer = (TileEntitySpecialRenderer)iterator.next();
        }

    }

    public TileEntitySpecialRenderer getSpecialRendererForClass(Class<?> class1)
    {
        TileEntitySpecialRenderer tileentityspecialrenderer = (TileEntitySpecialRenderer)specialRendererMap.get(class1);
        if(tileentityspecialrenderer == null && class1 != (TileEntity.class))
        {
            tileentityspecialrenderer = getSpecialRendererForClass(class1.getSuperclass());
            specialRendererMap.put(class1, tileentityspecialrenderer);
        }
        return tileentityspecialrenderer;
    }

    public boolean hasSpecialRenderer(TileEntity tileentity)
    {
        return getSpecialRendererForEntity(tileentity) != null;
    }

    public TileEntitySpecialRenderer getSpecialRendererForEntity(TileEntity tileentity)
    {
        if(tileentity == null)
        {
            return null;
        } else
        {
            return getSpecialRendererForClass(tileentity.getClass());
        }
    }

    public void setRenderingContext(World world, RenderEngine renderengine, FontRenderer fontrenderer, EntityPlayer entityplayer, float f)
    {
        worldObj = world;
        renderEngine = renderengine;
        entityPlayer = entityplayer;
        fontRenderer = fontrenderer;
        playerYaw = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        playerPitch = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        playerX = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double)f;
        playerY = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double)f;
        playerZ = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double)f;
    }

    public void renderTileEntity(TileEntity tileentity, float f)
    {
        if(tileentity.getDistanceFrom(playerX, playerY, playerZ) < 4096D)
        {
            float f1 = worldObj.getLightBrightness(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
            GL11.glColor3f(f1, f1, f1);
            renderTileEntityAt(tileentity, (double)tileentity.xCoord - staticPlayerX, (double)tileentity.yCoord - staticPlayerY, (double)tileentity.zCoord - staticPlayerZ, f);
        }
    }

    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, 
            float f)
    {
        TileEntitySpecialRenderer tileentityspecialrenderer = getSpecialRendererForEntity(tileentity);
        if(tileentityspecialrenderer != null)
        {
            tileentityspecialrenderer.renderTileEntityAt(tileentity, d, d1, d2, f);
        }
    }

    public FontRenderer getFontRenderer()
    {
        return fontRenderer;
    }

    private Map<Class<?>, TileEntitySpecialRenderer> specialRendererMap;
    public static TileEntityRenderer instance = new TileEntityRenderer();
    private FontRenderer fontRenderer;
    public static double staticPlayerX;
    public static double staticPlayerY;
    public static double staticPlayerZ;
    public RenderEngine renderEngine;
    public World worldObj;
    public EntityPlayer entityPlayer;
    public float playerYaw;
    public float playerPitch;
    public double playerX;
    public double playerY;
    public double playerZ;

}
