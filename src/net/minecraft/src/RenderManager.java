package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;
import org.lwjgl.opengl.GL11;

public class RenderManager
{

    private RenderManager()
    {
        entityRenderMap = new HashMap<Class<?>, Render>();
        entityRenderMap.put(EntitySpider.class, new RenderSpider());
        entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
        entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7F));
        entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7F));
        entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3F));
        entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
        entityRenderMap.put(EntitySkeleton.class, new RenderBiped(new ModelSkeleton(), 0.5F));
        entityRenderMap.put(EntityZombie.class, new RenderBiped(new ModelZombie(), 0.5F));
        entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
        entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
        entityRenderMap.put(EntityZombieSimple.class, new RenderZombieSimple(new ModelZombie(), 0.5F, 6F));
        entityRenderMap.put(EntityGhast.class, new RenderGhast());
        entityRenderMap.put(EntityLiving.class, new RenderLiving(new ModelBiped(), 0.5F));
        entityRenderMap.put(Entity.class, new RenderEntity());
        entityRenderMap.put(EntityPainting.class, new RenderPainting());
        entityRenderMap.put(EntityArrow.class, new RenderArrow());
        entityRenderMap.put(EntitySnowball.class, new RenderSnowball());
        entityRenderMap.put(EntityFireball.class, new RenderFireball());
        entityRenderMap.put(EntityItem.class, new RenderItem());
        entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
        entityRenderMap.put(EntityFallingSand.class, new RenderFallingSand());
        entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
        entityRenderMap.put(EntityBoat.class, new RenderBoat());
        entityRenderMap.put(EntityFish.class, new RenderFish());
        Render render;
        for(Iterator<Render> iterator = entityRenderMap.values().iterator(); iterator.hasNext(); render.setRenderManager(this))
        {
            render = (Render)iterator.next();
        }

    }

    public Render func_4117_a(Class<?> class1)
    {
        Render render = (Render)entityRenderMap.get(class1);
        if(render == null && class1 != (Entity.class))
        {
            render = func_4117_a(class1.getSuperclass());
            entityRenderMap.put(class1, render);
        }
        return render;
    }

    public Render func_855_a(Entity entity)
    {
        return func_4117_a(entity.getClass());
    }

    public void func_857_a(World world, RenderEngine renderengine, FontRenderer fontrenderer, EntityPlayer entityplayer, GameSettings gamesettings, float f)
    {
        worldObj = world;
        renderEngine = renderengine;
        options = gamesettings;
        field_1226_h = entityplayer;
        field_1218_p = fontrenderer;
        field_1225_i = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        field_1224_j = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        field_1222_l = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double)f;
        field_1221_m = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double)f;
        field_1220_n = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double)f;
    }

    public void func_854_a(Entity entity, float f)
    {
        double d = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f;
        float f1 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f;
        float f2 = entity.getEntityBrightness(f);
        GL11.glColor3f(f2, f2, f2);
        func_853_a(entity, d - field_1232_b, d1 - field_1231_c, d2 - field_1230_d, f1, f);
    }

    public void func_853_a(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        Render render = func_855_a(entity);
        if(render != null)
        {
            render.doRender(entity, d, d1, d2, f, f1);
            render.doRenderShadowAndFire(entity, d, d1, d2, f, f1);
        }
    }

    public void func_852_a(World world)
    {
        worldObj = world;
    }

    public double func_851_a(double d, double d1, double d2)
    {
        double d3 = d - field_1222_l;
        double d4 = d1 - field_1221_m;
        double d5 = d2 - field_1220_n;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public FontRenderer getFontRenderer()
    {
        return field_1218_p;
    }

    private Map<Class<?>, Render> entityRenderMap;
    public static RenderManager instance = new RenderManager();
    private FontRenderer field_1218_p;
    public static double field_1232_b;
    public static double field_1231_c;
    public static double field_1230_d;
    public RenderEngine renderEngine;
    public ItemRenderer field_4236_f;
    public World worldObj;
    public EntityPlayer field_1226_h;
    public float field_1225_i;
    public float field_1224_j;
    public GameSettings options;
    public double field_1222_l;
    public double field_1221_m;
    public double field_1220_n;

}
