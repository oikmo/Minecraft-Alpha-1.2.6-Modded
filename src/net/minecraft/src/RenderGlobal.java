package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class RenderGlobal implements IWorldAccess {

	public RenderGlobal(Minecraft minecraft, RenderEngine renderengine) {
		tileEntities = new ArrayList<TileEntity>();
		worldRenderersToUpdate = new ArrayList<WorldRenderer>();
		occlusionEnabled = false;
		cloudOffsetX = 0;
		renderDistance = -1;
		renderEntitiesStartupCounter = 2;
		dummyBuf50k = new int[50000];
		occlusionResult = GLAllocation.createDirectIntBuffer(64);
		glRenderLists = new ArrayList<WorldRenderer>();
		prevSortX = -9999D;
		prevSortY = -9999D;
		prevSortZ = -9999D;
		frustrumCheckOffset = 0;
		mc = minecraft;
		renderEngine = renderengine;
		byte byte0 = 64;
		glRenderListBase = GLAllocation.generateDisplayLists(byte0 * byte0 * byte0 * 3);
		occlusionEnabled = minecraft.func_6251_l().checkARBOcclusion();
		if(occlusionEnabled) {
			occlusionResult.clear();
			glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(byte0 * byte0 * byte0);
			glOcclusionQueryBase.clear();
			glOcclusionQueryBase.position(0);
			glOcclusionQueryBase.limit(byte0 * byte0 * byte0);
			ARBOcclusionQuery.glGenQueriesARB(glOcclusionQueryBase);
		}
		starGLCallList = GLAllocation.generateDisplayLists(3);
		GL11.glPushMatrix();
		GL11.glNewList(starGLCallList, 4864 /*GL_COMPILE*/);
		renderStars();
		GL11.glEndList();
		GL11.glPopMatrix();
		Tessellator tessellator = Tessellator.instance;
		glSkyList = starGLCallList + 1;
		GL11.glNewList(glSkyList, 4864 /*GL_COMPILE*/);
		byte byte1 = 64;
		int i = 256 / byte1 + 2;
		float f = 16F;
		for(int j = -byte1 * i; j <= byte1 * i; j += byte1) {
			for(int l = -byte1 * i; l <= byte1 * i; l += byte1) {
				tessellator.startDrawingQuads();
				tessellator.addVertex(j + 0, f, l + 0);
				tessellator.addVertex(j + byte1, f, l + 0);
				tessellator.addVertex(j + byte1, f, l + byte1);
				tessellator.addVertex(j + 0, f, l + byte1);
				tessellator.draw();
			}

		}

		GL11.glEndList();
		glSkyList2 = starGLCallList + 2;
		GL11.glNewList(glSkyList2, 4864 /*GL_COMPILE*/);
		f = -16F;
		tessellator.startDrawingQuads();
		for(int k = -byte1 * i; k <= byte1 * i; k += byte1) {
			for(int i1 = -byte1 * i; i1 <= byte1 * i; i1 += byte1) {
				tessellator.addVertex(k + byte1, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + byte1);
				tessellator.addVertex(k + byte1, f, i1 + byte1);
			}

		}

		tessellator.draw();
		GL11.glEndList();
	}

	private void renderStars() {
		Random random = new Random(10842L);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		for(int i = 0; i < 1500; i++)
		{
			double d = random.nextFloat() * 2.0F - 1.0F;
			double d1 = random.nextFloat() * 2.0F - 1.0F;
			double d2 = random.nextFloat() * 2.0F - 1.0F;
			double d3 = 0.25F + random.nextFloat() * 0.25F;
			double d4 = d * d + d1 * d1 + d2 * d2;
			if(d4 >= 1.0D || d4 <= 0.01D)
			{
				continue;
			}
			d4 = 1.0D / Math.sqrt(d4);
			d *= d4;
			d1 *= d4;
			d2 *= d4;
			double d5 = d * 100D;
			double d6 = d1 * 100D;
			double d7 = d2 * 100D;
			double d8 = Math.atan2(d, d2);
			double d9 = Math.sin(d8);
			double d10 = Math.cos(d8);
			double d11 = Math.atan2(Math.sqrt(d * d + d2 * d2), d1);
			double d12 = Math.sin(d11);
			double d13 = Math.cos(d11);
			double d14 = random.nextDouble() * 3.1415926535897931D * 2D;
			double d15 = Math.sin(d14);
			double d16 = Math.cos(d14);
			for(int j = 0; j < 4; j++)
			{
				double d17 = 0.0D;
				double d18 = (double)((j & 2) - 1) * d3;
				double d19 = (double)((j + 1 & 2) - 1) * d3;
				double d20 = d17;
				double d21 = d18 * d16 - d19 * d15;
				double d22 = d19 * d16 + d18 * d15;
				double d23 = d22;
				double d24 = d21 * d12 + d20 * d13;
				double d25 = d20 * d12 - d21 * d13;
				double d26 = d25 * d9 - d23 * d10;
				double d27 = d24;
				double d28 = d23 * d9 + d25 * d10;
				tessellator.addVertex(d5 + d26, d6 + d27, d7 + d28);
			}

		}

		tessellator.draw();
	}

	public void changeWorld(World world)
	{
		if(worldObj != null)
		{
			worldObj.removeWorldAccess(this);
		}
		prevSortX = -9999D;
		prevSortY = -9999D;
		prevSortZ = -9999D;
		RenderManager.instance.set(world);
		worldObj = world;
		globalRenderBlocks = new RenderBlocks(world);
		if(world != null)
		{
			world.addWorldAccess(this);
			loadRenderers();
		}
	}

	public void loadRenderers()
	{
		Block.leaves.setGraphicsLevel(mc.gameSettings.fancyGraphics);
		renderDistance = mc.gameSettings.renderDistance;
		if(worldRenderers != null)
		{
			for(int i = 0; i < worldRenderers.length; i++)
			{
				worldRenderers[i].func_1204_c();
			}

		}
		int j = 64 << 3 - renderDistance;
		if(j > 400)
		{
			j = 400;
		}
		renderChunksWide = j / 16 + 1;
		renderChunksTall = 8;
		renderChunksDeep = j / 16 + 1;
		worldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
		sortedWorldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
		int k = 0;
		int l = 0;
		minBlockX = 0;
		minBlockY = 0;
		minBlockZ = 0;
		maxBlockX = renderChunksWide;
		maxBlockY = renderChunksTall;
		maxBlockZ = renderChunksDeep;
		for(int i1 = 0; i1 < worldRenderersToUpdate.size(); i1++)
		{
			((WorldRenderer)worldRenderersToUpdate.get(i1)).needsUpdate = false;
		}

		worldRenderersToUpdate.clear();
		tileEntities.clear();
		for(int j1 = 0; j1 < renderChunksWide; j1++)
		{
			for(int k1 = 0; k1 < renderChunksTall; k1++)
			{
				for(int l1 = 0; l1 < renderChunksDeep; l1++)
				{
					worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1] = new WorldRenderer(worldObj, tileEntities, j1 * 16, k1 * 16, l1 * 16, 16, glRenderListBase + k);
					if(occlusionEnabled)
					{
						worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].field_1732_z = glOcclusionQueryBase.get(l);
					}
					worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].field_1733_y = false;
					worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].field_1734_x = true;
					worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].isInFrustum = true;
					worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].field_1735_w = l++;
					worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].MarkDirty();
					sortedWorldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1] = worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1];
					worldRenderersToUpdate.add(worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1]);
					k += 3;
				}

			}

		}

		if(worldObj != null)
		{
			EntityPlayerSP entityplayersp = mc.thePlayer;
			func_956_b(MathHelper.floor_double(((Entity) (entityplayersp)).posX), MathHelper.floor_double(((Entity) (entityplayersp)).posY), MathHelper.floor_double(((Entity) (entityplayersp)).posZ));
			Arrays.sort(sortedWorldRenderers, new EntitySorter(entityplayersp));
		}
		renderEntitiesStartupCounter = 2;
	}

	public void renderEntities(Vec3D vec3d, ICamera icamera, float f) {
		if(renderEntitiesStartupCounter > 0) {
			renderEntitiesStartupCounter--;
			return;
		}
		TileEntityRenderer.instance.setRenderingContext(worldObj, renderEngine, mc.fontRenderer, mc.thePlayer, f);
		RenderManager.instance.set(worldObj, renderEngine, mc.fontRenderer, mc.thePlayer, mc.gameSettings, f);
		countEntitiesTotal = 0;
		countEntitiesRendered = 0;
		countEntitiesHidden = 0;
		EntityPlayerSP entityplayersp = mc.thePlayer;
		RenderManager.renderPosX = ((Entity) (entityplayersp)).lastTickPosX + (((Entity) (entityplayersp)).posX - ((Entity) (entityplayersp)).lastTickPosX) * (double)f;
		RenderManager.renderPosY = ((Entity) (entityplayersp)).lastTickPosY + (((Entity) (entityplayersp)).posY - ((Entity) (entityplayersp)).lastTickPosY) * (double)f;
		RenderManager.renderPosZ = ((Entity) (entityplayersp)).lastTickPosZ + (((Entity) (entityplayersp)).posZ - ((Entity) (entityplayersp)).lastTickPosZ) * (double)f;
		TileEntityRenderer.staticPlayerX = ((Entity) (entityplayersp)).lastTickPosX + (((Entity) (entityplayersp)).posX - ((Entity) (entityplayersp)).lastTickPosX) * (double)f;
		TileEntityRenderer.staticPlayerY = ((Entity) (entityplayersp)).lastTickPosY + (((Entity) (entityplayersp)).posY - ((Entity) (entityplayersp)).lastTickPosY) * (double)f;
		TileEntityRenderer.staticPlayerZ = ((Entity) (entityplayersp)).lastTickPosZ + (((Entity) (entityplayersp)).posZ - ((Entity) (entityplayersp)).lastTickPosZ) * (double)f;
		List<Entity> list = worldObj.func_658_i();
		countEntitiesTotal = list.size();
		for(int i = 0; i < list.size(); i++)
		{
			Entity entity = (Entity)list.get(i);
			if(entity.func_390_a(vec3d) && icamera.func_342_a(entity.boundingBox) && (entity != mc.thePlayer || mc.gameSettings.thirdPersonView))
			{
				countEntitiesRendered++;
				RenderManager.instance.renderEntity(entity, f);
			}
		}

		for(int j = 0; j < tileEntities.size(); j++)
		{
			TileEntityRenderer.instance.renderTileEntity((TileEntity)tileEntities.get(j), f);
		}

	}

	public String func_953_b()
	{
		return (new StringBuilder()).append("C: ").append(renderersBeingRendered).append("/").append(renderersLoaded).append(". F: ").append(renderersBeingClipped).append(", O: ").append(renderersBeingOccluded).append(", E: ").append(renderersSkippingRenderPass).toString();
	}

	public String func_957_c()
	{
		return (new StringBuilder()).append("E: ").append(countEntitiesRendered).append("/").append(countEntitiesTotal).append(". B: ").append(countEntitiesHidden).append(", I: ").append(countEntitiesTotal - countEntitiesHidden - countEntitiesRendered).toString();
	}

	private void func_956_b(int i, int j, int k)
	{
		i -= 8;
		j -= 8;
		k -= 8;
		minBlockX = 0x7fffffff;
		minBlockY = 0x7fffffff;
		minBlockZ = 0x7fffffff;
		maxBlockX = 0x80000000;
		maxBlockY = 0x80000000;
		maxBlockZ = 0x80000000;
		int l = renderChunksWide * 16;
		int i1 = l / 2;
		for(int j1 = 0; j1 < renderChunksWide; j1++)
		{
			int k1 = j1 * 16;
			int l1 = (k1 + i1) - i;
			if(l1 < 0)
			{
				l1 -= l - 1;
			}
			l1 /= l;
			k1 -= l1 * l;
			if(k1 < minBlockX)
			{
				minBlockX = k1;
			}
			if(k1 > maxBlockX)
			{
				maxBlockX = k1;
			}
			for(int i2 = 0; i2 < renderChunksDeep; i2++)
			{
				int j2 = i2 * 16;
				int k2 = (j2 + i1) - k;
				if(k2 < 0)
				{
					k2 -= l - 1;
				}
				k2 /= l;
				j2 -= k2 * l;
				if(j2 < minBlockZ)
				{
					minBlockZ = j2;
				}
				if(j2 > maxBlockZ)
				{
					maxBlockZ = j2;
				}
				for(int l2 = 0; l2 < renderChunksTall; l2++)
				{
					int i3 = l2 * 16;
					if(i3 < minBlockY)
					{
						minBlockY = i3;
					}
					if(i3 > maxBlockY)
					{
						maxBlockY = i3;
					}
					WorldRenderer worldrenderer = worldRenderers[(i2 * renderChunksTall + l2) * renderChunksWide + j1];
					boolean flag = worldrenderer.needsUpdate;
					worldrenderer.func_1197_a(k1, i3, j2);
					if(!flag && worldrenderer.needsUpdate)
					{
						worldRenderersToUpdate.add(worldrenderer);
					}
				}

			}

		}

	}

	public int func_943_a(EntityPlayer entityplayer, int i, double d)
	{
		if(mc.gameSettings.renderDistance != renderDistance)
		{
			loadRenderers();
		}
		if(i == 0)
		{
			renderersLoaded = 0;
			renderersBeingClipped = 0;
			renderersBeingOccluded = 0;
			renderersBeingRendered = 0;
			renderersSkippingRenderPass = 0;
		}
		double d1 = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * d;
		double d2 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * d;
		double d3 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * d;
		double d4 = entityplayer.posX - prevSortX;
		double d5 = entityplayer.posY - prevSortY;
		double d6 = entityplayer.posZ - prevSortZ;
		if(d4 * d4 + d5 * d5 + d6 * d6 > 16D)
		{
			prevSortX = entityplayer.posX;
			prevSortY = entityplayer.posY;
			prevSortZ = entityplayer.posZ;
			func_956_b(MathHelper.floor_double(entityplayer.posX), MathHelper.floor_double(entityplayer.posY), MathHelper.floor_double(entityplayer.posZ));
			Arrays.sort(sortedWorldRenderers, new EntitySorter(entityplayer));
		}
		int j = 0;
		if(occlusionEnabled && !mc.gameSettings.anaglyph && i == 0)
		{
			int k = 0;
			int l = 16;
			func_962_a(k, l);
			for(int i1 = k; i1 < l; i1++)
			{
				sortedWorldRenderers[i1].field_1734_x = true;
			}

			j += func_952_a(k, l, i, d);
			do
			{
				byte byte0 = (byte) l;
				l *= 2;
				if(l > sortedWorldRenderers.length)
				{
					l = sortedWorldRenderers.length;
				}
				GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
				GL11.glDisable(2896 /*GL_LIGHTING*/);
				GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
				GL11.glDisable(2912 /*GL_FOG*/);
				GL11.glColorMask(false, false, false, false);
				GL11.glDepthMask(false);
				func_962_a(byte0, l);
				GL11.glPushMatrix();
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				for(int j1 = byte0; j1 < l; j1++)
				{
					if(sortedWorldRenderers[j1].skipAllRenderPasses())
					{
						sortedWorldRenderers[j1].isInFrustum = false;
						continue;
					}
					if(!sortedWorldRenderers[j1].isInFrustum)
					{
						sortedWorldRenderers[j1].field_1734_x = true;
					}
					if(!sortedWorldRenderers[j1].isInFrustum || sortedWorldRenderers[j1].field_1733_y)
					{
						continue;
					}
					float f3 = MathHelper.sqrt_float(sortedWorldRenderers[j1].func_1202_a(entityplayer));
					int k1 = (int)(1.0F + f3 / 128F);
					if(cloudOffsetX % k1 != j1 % k1)
					{
						continue;
					}
					WorldRenderer worldrenderer = sortedWorldRenderers[j1];
					float f4 = (float)((double)worldrenderer.field_1755_i - d1);
					float f5 = (float)((double)worldrenderer.field_1754_j - d2);
					float f6 = (float)((double)worldrenderer.field_1753_k - d3);
					float f7 = f4 - f;
					float f8 = f5 - f1;
					float f9 = f6 - f2;
					if(f7 != 0.0F || f8 != 0.0F || f9 != 0.0F)
					{
						GL11.glTranslatef(f7, f8, f9);
						f += f7;
						f1 += f8;
						f2 += f9;
					}
					ARBOcclusionQuery.glBeginQueryARB(35092 /*GL_SAMPLES_PASSED_ARB*/, sortedWorldRenderers[j1].field_1732_z);
					sortedWorldRenderers[j1].func_1201_d();
					ARBOcclusionQuery.glEndQueryARB(35092 /*GL_SAMPLES_PASSED_ARB*/);
					sortedWorldRenderers[j1].field_1733_y = true;
				}

				GL11.glPopMatrix();
				GL11.glColorMask(true, true, true, true);
				GL11.glDepthMask(true);
				GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
				GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
				GL11.glEnable(2912 /*GL_FOG*/);
				j += func_952_a(byte0, l, i, d);
			} while(l < sortedWorldRenderers.length);
		} else
		{
			j += func_952_a(0, sortedWorldRenderers.length, i, d);
		}
		return j;
	}

	private void func_962_a(int i, int j)
	{
		for(int k = i; k < j; k++)
		{
			if(!sortedWorldRenderers[k].field_1733_y)
			{
				continue;
			}
			occlusionResult.clear();
			ARBOcclusionQuery.glGetQueryObjectuARB(sortedWorldRenderers[k].field_1732_z, 34919 /*GL_QUERY_RESULT_AVAILABLE_ARB*/, occlusionResult);
			if(occlusionResult.get(0) != 0)
			{
				sortedWorldRenderers[k].field_1733_y = false;
				occlusionResult.clear();
				ARBOcclusionQuery.glGetQueryObjectuARB(sortedWorldRenderers[k].field_1732_z, 34918 /*GL_QUERY_RESULT_ARB*/, occlusionResult);
				sortedWorldRenderers[k].field_1734_x = occlusionResult.get(0) != 0;
			}
		}

	}

	private int func_952_a(int i, int j, int k, double d)
	{
		glRenderLists.clear();
		int l = 0;
		for(int i1 = i; i1 < j; i1++)
		{
			if(k == 0)
			{
				renderersLoaded++;
				if(sortedWorldRenderers[i1].field_1748_p[k])
				{
					renderersSkippingRenderPass++;
				} else
					if(!sortedWorldRenderers[i1].isInFrustum)
					{
						renderersBeingClipped++;
					} else
						if(occlusionEnabled && !sortedWorldRenderers[i1].field_1734_x)
						{
							renderersBeingOccluded++;
						} else
						{
							renderersBeingRendered++;
						}
			}
			if(sortedWorldRenderers[i1].field_1748_p[k] || !sortedWorldRenderers[i1].isInFrustum || !sortedWorldRenderers[i1].field_1734_x)
			{
				continue;
			}
			int j1 = sortedWorldRenderers[i1].func_1200_a(k);
			if(j1 >= 0)
			{
				glRenderLists.add(sortedWorldRenderers[i1]);
				l++;
			}
		}

		EntityPlayerSP entityplayersp = mc.thePlayer;
		double d1 = ((EntityPlayer) (entityplayersp)).lastTickPosX + (((EntityPlayer) (entityplayersp)).posX - ((EntityPlayer) (entityplayersp)).lastTickPosX) * d;
		double d2 = ((EntityPlayer) (entityplayersp)).lastTickPosY + (((EntityPlayer) (entityplayersp)).posY - ((EntityPlayer) (entityplayersp)).lastTickPosY) * d;
		double d3 = ((EntityPlayer) (entityplayersp)).lastTickPosZ + (((EntityPlayer) (entityplayersp)).posZ - ((EntityPlayer) (entityplayersp)).lastTickPosZ) * d;
		int k1 = 0;
		for(int l1 = 0; l1 < allRenderLists.length; l1++)
		{
			allRenderLists[l1].func_859_b();
		}

		for(int i2 = 0; i2 < glRenderLists.size(); i2++)
		{
			WorldRenderer worldrenderer = (WorldRenderer)glRenderLists.get(i2);
			int j2 = -1;
			for(int k2 = 0; k2 < k1; k2++)
			{
				if(allRenderLists[k2].func_862_a(worldrenderer.field_1755_i, worldrenderer.field_1754_j, worldrenderer.field_1753_k))
				{
					j2 = k2;
				}
			}

			if(j2 < 0)
			{
				j2 = k1++;
				allRenderLists[j2].func_861_a(worldrenderer.field_1755_i, worldrenderer.field_1754_j, worldrenderer.field_1753_k, d1, d2, d3);
			}
			allRenderLists[j2].func_858_a(worldrenderer.func_1200_a(k));
		}

		func_944_a(k, d);
		return l;
	}

	public void func_944_a(int i, double d)
	{
		for(int j = 0; j < allRenderLists.length; j++)
		{
			allRenderLists[j].func_860_a();
		}

	}

	public void func_945_d()
	{
		cloudOffsetX++;
	}

	public void func_4142_a(float f)
	{
		if(mc.theWorld.worldProvider.field_4220_c)
		{
			return;
		}
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		Vec3D vec3d = worldObj.func_4079_a(mc.thePlayer, f);
		float f1 = (float)vec3d.xCoord;
		float f2 = (float)vec3d.yCoord;
		float f3 = (float)vec3d.zCoord;
		if(mc.gameSettings.anaglyph)
		{
			float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
			float f5 = (f1 * 30F + f2 * 70F) / 100F;
			float f7 = (f1 * 30F + f3 * 70F) / 100F;
			f1 = f4;
			f2 = f5;
			f3 = f7;
		}
		GL11.glColor3f(f1, f2, f3);
		Tessellator tessellator = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(2912 /*GL_FOG*/);
		GL11.glColor3f(f1, f2, f3);
		GL11.glCallList(glSkyList);
		GL11.glDisable(2912 /*GL_FOG*/);
		GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		float af[] = worldObj.worldProvider.func_4097_b(worldObj.getCelestialAngle(f), f);
		if(af != null)
		{
			GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
			GL11.glShadeModel(7425 /*GL_SMOOTH*/);
			GL11.glPushMatrix();
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
			float f8 = worldObj.getCelestialAngle(f);
			GL11.glRotatef(f8 <= 0.5F ? 0.0F : 180F, 0.0F, 0.0F, 1.0F);
			tessellator.startDrawing(6);
			tessellator.setColorRGBA_F(af[0], af[1], af[2], af[3]);
			tessellator.addVertex(0.0D, 100D, 0.0D);
			int i = 16;
			tessellator.setColorRGBA_F(af[0], af[1], af[2], 0.0F);
			for(int j = 0; j <= i; j++)
			{
				float f12 = ((float)j * 3.141593F * 2.0F) / (float)i;
				float f14 = MathHelper.sin(f12);
				float f15 = MathHelper.cos(f12);
				tessellator.addVertex(f14 * 120F, f15 * 120F, -f15 * 40F * af[3]);
			}

			tessellator.draw();
			GL11.glPopMatrix();
			GL11.glShadeModel(7424 /*GL_FLAT*/);
		}
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL11.glBlendFunc(1, 1);
		GL11.glPushMatrix();
		float f6 = 0.0F;
		float f9 = 0.0F;
		float f10 = 0.0F;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(f6, f9, f10);
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(worldObj.getCelestialAngle(f) * 360F, 1.0F, 0.0F, 0.0F);
		float f11 = 30F;
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/terrain/sun.png"));
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f11, 100D, -f11, 0.0D, 0.0D);
		tessellator.addVertexWithUV(f11, 100D, -f11, 1.0D, 0.0D);
		tessellator.addVertexWithUV(f11, 100D, f11, 1.0D, 1.0D);
		tessellator.addVertexWithUV(-f11, 100D, f11, 0.0D, 1.0D);
		tessellator.draw();
		f11 = 20F;
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/terrain/moon.png"));
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f11, -100D, f11, 1.0D, 1.0D);
		tessellator.addVertexWithUV(f11, -100D, f11, 0.0D, 1.0D);
		tessellator.addVertexWithUV(f11, -100D, -f11, 0.0D, 0.0D);
		tessellator.addVertexWithUV(-f11, -100D, -f11, 1.0D, 0.0D);
		tessellator.draw();
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		float f13 = worldObj.func_679_f(f);
		if(f13 > 0.0F)
		{
			GL11.glColor4f(f13, f13, f13, f13);
			GL11.glCallList(starGLCallList);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
		GL11.glEnable(2912 /*GL_FOG*/);
		GL11.glPopMatrix();
		GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		GL11.glCallList(glSkyList2);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL11.glDepthMask(true);
	}

	public void func_4141_b(float f)
	{
		if(mc.theWorld.worldProvider.field_4220_c)
		{
			return;
		}
		if(mc.gameSettings.fancyGraphics)
		{
			func_6510_c(f);
			return;
		}
		GL11.glDisable(2884 /*GL_CULL_FACE*/);
		float f1 = (float)(mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)f);
		byte byte0 = 32;
		int i = 256 / byte0;
		Tessellator tessellator = Tessellator.instance;
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/environment/clouds.png"));
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		Vec3D vec3d = worldObj.func_628_d(f);
		float f2 = (float)vec3d.xCoord;
		float f3 = (float)vec3d.yCoord;
		float f4 = (float)vec3d.zCoord;
		if(mc.gameSettings.anaglyph)
		{
			float f5 = (f2 * 30F + f3 * 59F + f4 * 11F) / 100F;
			float f7 = (f2 * 30F + f3 * 70F) / 100F;
			float f8 = (f2 * 30F + f4 * 70F) / 100F;
			f2 = f5;
			f3 = f7;
			f4 = f8;
		}
		float f6 = 0.0004882813F;
		double d = mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * (double)f + (double)(((float)cloudOffsetX + f) * 0.03F);
		double d1 = mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (double)f;
		int j = MathHelper.floor_double(d / 2048D);
		int k = MathHelper.floor_double(d1 / 2048D);
		d -= j * 2048 /*GL_EXP*/;
		d1 -= k * 2048 /*GL_EXP*/;
		float f9 = (120F - f1) + 0.33F;
		float f10 = (float)(d * (double)f6);
		float f11 = (float)(d1 * (double)f6);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(f2, f3, f4, 0.8F);
		for(int l = -byte0 * i; l < byte0 * i; l += byte0)
		{
			for(int i1 = -byte0 * i; i1 < byte0 * i; i1 += byte0)
			{
				tessellator.addVertexWithUV(l + 0, f9, i1 + byte0, (float)(l + 0) * f6 + f10, (float)(i1 + byte0) * f6 + f11);
				tessellator.addVertexWithUV(l + byte0, f9, i1 + byte0, (float)(l + byte0) * f6 + f10, (float)(i1 + byte0) * f6 + f11);
				tessellator.addVertexWithUV(l + byte0, f9, i1 + 0, (float)(l + byte0) * f6 + f10, (float)(i1 + 0) * f6 + f11);
				tessellator.addVertexWithUV(l + 0, f9, i1 + 0, (float)(l + 0) * f6 + f10, (float)(i1 + 0) * f6 + f11);
			}

		}

		tessellator.draw();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glEnable(2884 /*GL_CULL_FACE*/);
	}

	public void func_6510_c(float f)
	{
		GL11.glDisable(2884 /*GL_CULL_FACE*/);
		float f1 = (float)(mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)f);
		Tessellator tessellator = Tessellator.instance;
		float f2 = 12F;
		float f3 = 4F;
		double d = (mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * (double)f + (double)(((float)cloudOffsetX + f) * 0.03F)) / (double)f2;
		double d1 = (mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (double)f) / (double)f2 + 0.33000001311302185D;
		float f4 = (108F - f1) + 0.33F;
		int i = MathHelper.floor_double(d / 2048D);
		int j = MathHelper.floor_double(d1 / 2048D);
		d -= i * 2048 /*GL_EXP*/;
		d1 -= j * 2048 /*GL_EXP*/;
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/environment/clouds.png"));
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		Vec3D vec3d = worldObj.func_628_d(f);
		float f5 = (float)vec3d.xCoord;
		float f6 = (float)vec3d.yCoord;
		float f7 = (float)vec3d.zCoord;
		if(mc.gameSettings.anaglyph)
		{
			float f8 = (f5 * 30F + f6 * 59F + f7 * 11F) / 100F;
			float f10 = (f5 * 30F + f6 * 70F) / 100F;
			float f12 = (f5 * 30F + f7 * 70F) / 100F;
			f5 = f8;
			f6 = f10;
			f7 = f12;
		}
		float f9 = (float)(d * 0.0D);
		float f11 = (float)(d1 * 0.0D);
		float f13 = 0.00390625F;
		f9 = (float)MathHelper.floor_double(d) * f13;
		f11 = (float)MathHelper.floor_double(d1) * f13;
		float f14 = (float)(d - (double)MathHelper.floor_double(d));
		float f15 = (float)(d1 - (double)MathHelper.floor_double(d1));
		int k = 8;
		byte byte0 = 3;
		float f16 = 0.0009765625F;
		GL11.glScalef(f2, 1.0F, f2);
		for(int l = 0; l < 2; l++)
		{
			if(l == 0)
			{
				GL11.glColorMask(false, false, false, false);
			} else
			{
				GL11.glColorMask(true, true, true, true);
			}
			for(int i1 = -byte0 + 1; i1 <= byte0; i1++)
			{
				for(int j1 = -byte0 + 1; j1 <= byte0; j1++)
				{
					tessellator.startDrawingQuads();
					float f17 = i1 * k;
					float f18 = j1 * k;
					float f19 = f17 - f14;
					float f20 = f18 - f15;
					if(f4 > -f3 - 1.0F)
					{
						tessellator.setColorRGBA_F(f5 * 0.7F, f6 * 0.7F, f7 * 0.7F, 0.8F);
						tessellator.setNormal(0.0F, -1F, 0.0F);
						tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, f20 + (float)k, (f17 + 0.0F) * f13 + f9, (f18 + (float)k) * f13 + f11);
						tessellator.addVertexWithUV(f19 + (float)k, f4 + 0.0F, f20 + (float)k, (f17 + (float)k) * f13 + f9, (f18 + (float)k) * f13 + f11);
						tessellator.addVertexWithUV(f19 + (float)k, f4 + 0.0F, f20 + 0.0F, (f17 + (float)k) * f13 + f9, (f18 + 0.0F) * f13 + f11);
						tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, f20 + 0.0F, (f17 + 0.0F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
					}
					if(f4 <= f3 + 1.0F)
					{
						tessellator.setColorRGBA_F(f5, f6, f7, 0.8F);
						tessellator.setNormal(0.0F, 1.0F, 0.0F);
						tessellator.addVertexWithUV(f19 + 0.0F, (f4 + f3) - f16, f20 + (float)k, (f17 + 0.0F) * f13 + f9, (f18 + (float)k) * f13 + f11);
						tessellator.addVertexWithUV(f19 + (float)k, (f4 + f3) - f16, f20 + (float)k, (f17 + (float)k) * f13 + f9, (f18 + (float)k) * f13 + f11);
						tessellator.addVertexWithUV(f19 + (float)k, (f4 + f3) - f16, f20 + 0.0F, (f17 + (float)k) * f13 + f9, (f18 + 0.0F) * f13 + f11);
						tessellator.addVertexWithUV(f19 + 0.0F, (f4 + f3) - f16, f20 + 0.0F, (f17 + 0.0F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
					}
					tessellator.setColorRGBA_F(f5 * 0.9F, f6 * 0.9F, f7 * 0.9F, 0.8F);
					if(i1 > -1)
					{
						tessellator.setNormal(-1F, 0.0F, 0.0F);
						for(int k1 = 0; k1 < k; k1++)
						{
							tessellator.addVertexWithUV(f19 + (float)k1 + 0.0F, f4 + 0.0F, f20 + (float)k, (f17 + (float)k1 + 0.5F) * f13 + f9, (f18 + (float)k) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float)k1 + 0.0F, f4 + f3, f20 + (float)k, (f17 + (float)k1 + 0.5F) * f13 + f9, (f18 + (float)k) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float)k1 + 0.0F, f4 + f3, f20 + 0.0F, (f17 + (float)k1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float)k1 + 0.0F, f4 + 0.0F, f20 + 0.0F, (f17 + (float)k1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
						}

					}
					if(i1 <= 1)
					{
						tessellator.setNormal(1.0F, 0.0F, 0.0F);
						for(int l1 = 0; l1 < k; l1++)
						{
							tessellator.addVertexWithUV((f19 + (float)l1 + 1.0F) - f16, f4 + 0.0F, f20 + (float)k, (f17 + (float)l1 + 0.5F) * f13 + f9, (f18 + (float)k) * f13 + f11);
							tessellator.addVertexWithUV((f19 + (float)l1 + 1.0F) - f16, f4 + f3, f20 + (float)k, (f17 + (float)l1 + 0.5F) * f13 + f9, (f18 + (float)k) * f13 + f11);
							tessellator.addVertexWithUV((f19 + (float)l1 + 1.0F) - f16, f4 + f3, f20 + 0.0F, (f17 + (float)l1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
							tessellator.addVertexWithUV((f19 + (float)l1 + 1.0F) - f16, f4 + 0.0F, f20 + 0.0F, (f17 + (float)l1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
						}

					}
					tessellator.setColorRGBA_F(f5 * 0.8F, f6 * 0.8F, f7 * 0.8F, 0.8F);
					if(j1 > -1)
					{
						tessellator.setNormal(0.0F, 0.0F, -1F);
						for(int i2 = 0; i2 < k; i2++)
						{
							tessellator.addVertexWithUV(f19 + 0.0F, f4 + f3, f20 + (float)i2 + 0.0F, (f17 + 0.0F) * f13 + f9, (f18 + (float)i2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float)k, f4 + f3, f20 + (float)i2 + 0.0F, (f17 + (float)k) * f13 + f9, (f18 + (float)i2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float)k, f4 + 0.0F, f20 + (float)i2 + 0.0F, (f17 + (float)k) * f13 + f9, (f18 + (float)i2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, f20 + (float)i2 + 0.0F, (f17 + 0.0F) * f13 + f9, (f18 + (float)i2 + 0.5F) * f13 + f11);
						}

					}
					if(j1 <= 1)
					{
						tessellator.setNormal(0.0F, 0.0F, 1.0F);
						for(int j2 = 0; j2 < k; j2++)
						{
							tessellator.addVertexWithUV(f19 + 0.0F, f4 + f3, (f20 + (float)j2 + 1.0F) - f16, (f17 + 0.0F) * f13 + f9, (f18 + (float)j2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float)k, f4 + f3, (f20 + (float)j2 + 1.0F) - f16, (f17 + (float)k) * f13 + f9, (f18 + (float)j2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float)k, f4 + 0.0F, (f20 + (float)j2 + 1.0F) - f16, (f17 + (float)k) * f13 + f9, (f18 + (float)j2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, (f20 + (float)j2 + 1.0F) - f16, (f17 + 0.0F) * f13 + f9, (f18 + (float)j2 + 0.5F) * f13 + f11);
						}

					}
					tessellator.draw();
				}

			}

		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glEnable(2884 /*GL_CULL_FACE*/);
	}

	public boolean func_948_a(EntityPlayer entityplayer, boolean flag)
	{
		Collections.sort(worldRenderersToUpdate, new RenderSorter(entityplayer));
		int i = worldRenderersToUpdate.size() - 1;
		int j = worldRenderersToUpdate.size();
		for(int k = 0; k < j; k++)
		{
			WorldRenderer worldrenderer = (WorldRenderer)worldRenderersToUpdate.get(i - k);
			if(!flag)
			{
				if(worldrenderer.func_1202_a(entityplayer) > 1024F)
				{
					if(worldrenderer.isInFrustum)
					{
						if(k >= 3)
						{
							return false;
						}
					} else
						if(k >= 1)
						{
							return false;
						}
				}
			} else
				if(!worldrenderer.isInFrustum)
				{
					continue;
				}
			worldrenderer.func_1198_a();
			worldRenderersToUpdate.remove(worldrenderer);
			worldrenderer.needsUpdate = false;
		}

		return worldRenderersToUpdate.size() == 0;
	}

	public void func_959_a(EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i, ItemStack itemstack, float f)
	{
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
		GL11.glBlendFunc(770, 1);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathHelper.sin((float)System.currentTimeMillis() / 100F) * 0.2F + 0.4F) * 0.5F);
		if(i == 0)
		{
			if(damagePartialTime > 0.0F)
			{
				GL11.glBlendFunc(774, 768);
				int j = renderEngine.getTexture("/terrain.png");
				GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, j);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
				GL11.glPushMatrix();
				int k = worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
				Block block = k <= 0 ? null : Block.blocksList[k];
				GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
				GL11.glPolygonOffset(-3F, -3F);
				GL11.glEnable(32823 /*GL_POLYGON_OFFSET_FILL*/);
				tessellator.startDrawingQuads();
				double d = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double)f;
				double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double)f;
				double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double)f;
				tessellator.setTranslationD(-d, -d1, -d2);
				tessellator.disableColor();
				if(block == null)
				{
					block = Block.stone;
				}
				globalRenderBlocks.renderBlockUsingTexture(block, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 240 + (int)(damagePartialTime * 10F));
				tessellator.draw();
				tessellator.setTranslationD(0.0D, 0.0D, 0.0D);
				GL11.glPolygonOffset(0.0F, 0.0F);
				GL11.glDisable(32823 /*GL_POLYGON_OFFSET_FILL*/);
				GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
				GL11.glDepthMask(true);
				GL11.glPopMatrix();
			}
		} else
			if(itemstack != null)
			{
				GL11.glBlendFunc(770, 771);
				float f1 = MathHelper.sin((float)System.currentTimeMillis() / 100F) * 0.2F + 0.8F;
				GL11.glColor4f(f1, f1, f1, MathHelper.sin((float)System.currentTimeMillis() / 200F) * 0.2F + 0.5F);
				int l = renderEngine.getTexture("/terrain.png");
				GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, l);
			}
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
	}

	public void drawSelectionBox(EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i, ItemStack itemstack, float f)
	{
		if(i == 0 && movingobjectposition.typeOfHit == 0)
		{
			GL11.glEnable(3042 /*GL_BLEND*/);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
			GL11.glLineWidth(2.0F);
			GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
			GL11.glDepthMask(false);
			float f1 = 0.002F;
			int j = worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
			if(j > 0)
			{
				Block.blocksList[j].setBlockBoundsBasedOnState(worldObj, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
				double d = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double)f;
				double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double)f;
				double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double)f;
				drawOutlinedBoundingBox(Block.blocksList[j].getSelectedBoundingBoxFromPool(worldObj, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ).expands(f1, f1, f1).getOffsetBoundingBox(-d, -d1, -d2));
			}
			GL11.glDepthMask(true);
			GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
			GL11.glDisable(3042 /*GL_BLEND*/);
		}
	}

	private void drawOutlinedBoundingBox(AxisAlignedBB axisalignedbb) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(3);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.draw();
		tessellator.startDrawing(3);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.draw();
		tessellator.startDrawing(1);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.draw();
	}

	public void markBlocksForUpdate(int i, int j, int k, int l, int i1, int j1) {
		int k1 = MathHelper.bucketInt(i, 16);
		int l1 = MathHelper.bucketInt(j, 16);
		int i2 = MathHelper.bucketInt(k, 16);
		int j2 = MathHelper.bucketInt(l, 16);
		int k2 = MathHelper.bucketInt(i1, 16);
		int l2 = MathHelper.bucketInt(j1, 16);
		for(int i3 = k1; i3 <= j2; i3++) {
			int j3 = i3 % renderChunksWide;
			if(j3 < 0) {
				j3 += renderChunksWide;
			}
			for(int k3 = l1; k3 <= k2; k3++) {
				int l3 = k3 % renderChunksTall;
				if(l3 < 0) {
					l3 += renderChunksTall;
				}
				for(int i4 = i2; i4 <= l2; i4++) {
					int j4 = i4 % renderChunksDeep;
					if(j4 < 0) {
						j4 += renderChunksDeep;
					}
					int k4 = (j4 * renderChunksTall + l3) * renderChunksWide + j3;
					WorldRenderer worldrenderer = worldRenderers[k4];
					if(!worldrenderer.needsUpdate) {
						worldRenderersToUpdate.add(worldrenderer);
					}
					worldrenderer.MarkDirty();
				}
			}
		}
	}

	public void markBlockNeedsUpdate(int x, int y, int z) {
		markBlocksForUpdate(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
	}

	public void markBlockRangeNeedsUpdate(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		markBlocksForUpdate(minX - 1, minY - 1, minZ - 1, maxX + 1, maxY + 1, maxZ + 1);
	}

	public void clipRenderersByFrustum(ICamera icamera, float f) {
		for(int i = 0; i < worldRenderers.length; i++) {
			if(!worldRenderers[i].skipAllRenderPasses() && (!worldRenderers[i].isInFrustum || (i + frustrumCheckOffset & 0xf) == 0)) {
				worldRenderers[i].updateInFrustrum(icamera);
			}
		}

		frustrumCheckOffset++;
	}

	public void playRecord(String s, int x, int y, int z) {
		if(s != null) {
			mc.ingameGUI.setRecordPlayingMessage("C418 - " + s);
		}
		mc.sndManager.playStreaming(s, x, y, z, 1.0F);
	}

	public void playSound(String s, double d, double d1, double d2, 
			float f, float f1)
	{
		float f2 = 16F;
		if(f > 1.0F)
		{
			f2 *= f;
		}
		if(mc.thePlayer.getDistanceSq(d, d1, d2) < (double)(f2 * f2))
		{
			mc.sndManager.playSound(s, (float)d, (float)d1, (float)d2, f, f1);
		}
	}

	public void spawnParticle(String s, double px, double py, double pz, double d3, double d4, double d5) {
		double d6 = mc.thePlayer.posX - px;
		double d7 = mc.thePlayer.posY - py;
		double d8 = mc.thePlayer.posZ - pz;
		if(d6 * d6 + d7 * d7 + d8 * d8 > 256D) {
			return;
		}
		if(s == "bubble") {
			mc.effectRenderer.addEffect(new EntityBubbleFX(worldObj, px, py, pz, d3, d4, d5));
		} else {
			if(s == "smoke") {
				mc.effectRenderer.addEffect(new EntitySmokeFX(worldObj, px, py, pz));
			} else {
				if(s == "portal") {
					mc.effectRenderer.addEffect(new EntityPortalFX(worldObj, px, py, pz, d3, d4, d5));
				} else {
					if(s == "explode") {
						mc.effectRenderer.addEffect(new EntityExplodeFX(worldObj, px, py, pz, d3, d4, d5));
					} else {

					}
				}
			}
			if(s == "flame")
			{
				mc.effectRenderer.addEffect(new EntityFlameFX(worldObj, px, py, pz, d3, d4, d5));
			} else
				if(s == "lava")
				{
					mc.effectRenderer.addEffect(new EntityLavaFX(worldObj, px, py, pz));
				} else
					if(s == "splash")
					{
						mc.effectRenderer.addEffect(new EntitySplashFX(worldObj, px, py, pz, d3, d4, d5));
					} else
						if(s == "largesmoke")
						{
							mc.effectRenderer.addEffect(new EntitySmokeFX(worldObj, px, py, pz, 2.5F));
						} else
							if(s == "reddust")
							{
								mc.effectRenderer.addEffect(new EntityReddustFX(worldObj, px, py, pz));
							} else
								if(s == "snowballpoof")
								{
									mc.effectRenderer.addEffect(new EntitySlimeFX(worldObj, px, py, pz, Item.snowball));
								} else
									if(s == "slime")
									{
										mc.effectRenderer.addEffect(new EntitySlimeFX(worldObj, px, py, pz, Item.slimeBall));
									}
		}

	}

	public void obtainEntitySkin(Entity entity) {
		if(entity.skinUrl != null) {
			renderEngine.obtainImageData(entity.skinUrl, new ImageBufferDownload());
		}
	}

	public void releaseEntitySkin(Entity entity) {
		if(entity.skinUrl != null) {
			renderEngine.releaseImageData(entity.skinUrl);
		}
	}

	public void updateRenderers() {
		for(int i = 0; i < worldRenderers.length; i++) {
			if(!worldRenderers[i].isChunkLit) {
				continue;
			}
			if(!worldRenderers[i].needsUpdate) {
				worldRenderersToUpdate.add(worldRenderers[i]);
			}
			worldRenderers[i].MarkDirty();
		}
	}

	public void doNothingWithTileEntity(int i, int j, int k, TileEntity tileentity) {}

	public List<TileEntity> tileEntities;
	private World worldObj;
	private RenderEngine renderEngine;
	private List<WorldRenderer> worldRenderersToUpdate;
	private WorldRenderer sortedWorldRenderers[];
	private WorldRenderer worldRenderers[];
	private int renderChunksWide;
	private int renderChunksTall;
	private int renderChunksDeep;
	private int glRenderListBase;
	private Minecraft mc;
	private RenderBlocks globalRenderBlocks;
	private IntBuffer glOcclusionQueryBase;
	private boolean occlusionEnabled;
	private int cloudOffsetX;
	/** The star GL Call list */
	private int starGLCallList;
	/**OpenGL sky list*/
	private int glSkyList;
	private int glSkyList2;
	private int minBlockX;
	private int minBlockY;
	private int minBlockZ;
	private int maxBlockX;
	private int maxBlockY;
	private int maxBlockZ;
	private int renderDistance;
	private int renderEntitiesStartupCounter;
	private int countEntitiesTotal;
	private int countEntitiesRendered;
	private int countEntitiesHidden;
	/** Dummy buffer (50k) not used */
	int dummyBuf50k[];
	/** Occlusion query result */
	IntBuffer occlusionResult;
	/** How many renderers are loaded this frame that try to be rendered */
	private int renderersLoaded;
	/** How many renderers are being clipped by the frustrum this frame */
	private int renderersBeingClipped;
	/** How many renderers are being occluded this frame */
	private int renderersBeingOccluded;
	/** How many renderers are actually being rendered this frame */
	private int renderersBeingRendered;
	/** How many renderers are skipping rendering due to not having a render pass this frame */
	private int renderersSkippingRenderPass;
	/**List of OpenGL lists for the current render pass */
	private List<WorldRenderer> glRenderLists;
	/** All render lists (fixed length 4) */
	private RenderList allRenderLists[] = {
			new RenderList(), new RenderList(), new RenderList(), new RenderList()
	};

	double prevSortX;
	double prevSortY;
	double prevSortZ;
	public float damagePartialTime;
	int frustrumCheckOffset;
}
