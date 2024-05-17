package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.GL11;

public class RenderManager {

	private Map<Class<?>, Render> entityRenderMap;
	public static RenderManager instance = new RenderManager();
	private FontRenderer fontRenderer;
	public ItemRenderer itemRenderer;
	public static double renderPosX;
	public static double renderPosY;
	public static double renderPosZ;
	public RenderEngine renderEngine;
	public World worldObj;
	public EntityPlayer entityPlayer;
	public float playerViewY;
	public float playerViewX;
	public GameSettings options;
	public double playerPosX;
	public double playerPosY;
	public double playerPosZ;
	
	private RenderManager() {
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
		for(Iterator<Render> iterator = entityRenderMap.values().iterator(); iterator.hasNext(); render.setRenderManager(this)) {
			render = (Render)iterator.next();
		}

	}

	public Render getEntityClassRenderObject(Class<?> class1) {
		Render render = (Render)entityRenderMap.get(class1);
		if(render == null && class1 != (Entity.class)) {
			render = getEntityClassRenderObject(class1.getSuperclass());
			entityRenderMap.put(class1, render);
		}
		return render;
	}
	
	public Render getEntityRenderObject(Entity entity) {
		return getEntityClassRenderObject(entity.getClass());
	}

	public void set(World world, RenderEngine renderengine, FontRenderer fontrenderer, EntityPlayer entityplayer, GameSettings gamesettings, float f) {
		worldObj = world;
		renderEngine = renderengine;
		options = gamesettings;
		entityPlayer = entityplayer;
		fontRenderer = fontrenderer;
		playerViewY = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
		playerViewX = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
		playerPosX = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double)f;
		playerPosY = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double)f;
		playerPosZ = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double)f;
	}

	public void renderEntity(Entity entity, float f) {
		double d = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f;
		double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f;
		double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f;
		float f1 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f;
		float f2 = entity.getEntityBrightness(f);
		GL11.glColor3f(f2, f2, f2);
		renderEntityWithPosYaw(entity, d - renderPosX, d1 - renderPosY, d2 - renderPosZ, f1, f);
	}

	public void renderEntityWithPosYaw(Entity entity, double d, double d1, double d2, float f, float f1) {
		Render render = getEntityRenderObject(entity);
		if(render != null) {
			render.doRender(entity, d, d1, d2, f, f1);
			render.doRenderShadowAndFire(entity, d, d1, d2, f, f1);
		}
	}

	/** World sets this RenderManager's worldObj to the world provided */
	public void set(World world) {
		worldObj = world;
	}

	public double getDistanceToCamera(double x, double y, double z) {
		double dx = x - playerPosX;
		double dy = y - playerPosY;
		double dz = z - playerPosZ;
		return dx * dx + dy * dy + dz * dz;
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
}
