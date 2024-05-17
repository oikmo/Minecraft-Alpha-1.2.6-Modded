package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

public class EntityRenderer
{
	private Minecraft mc;
    private float farPlaneDistance;
    public ItemRenderer itemRenderer;
    private Entity pointedEntity;
    private long field_1384_l;
    private Random random;
    volatile int field_1394_b;
    volatile int field_1393_c;
    FloatBuffer fogColorBuffer;
    float fogColorRed;
    float fogColorGreen;
    float fogColorBlue;
    private float fogColor1;
    private float fogColor2;
    public int debugViewDirection;
    private float fovModifierHand;
	private float fovModifierHandPrev;
	private float debugCamFOV = 0.0F;
	private float prevDebugCamFOV = 0.0F;
	private float fovMultiplierTemp;
	private double cameraZoom = .5D;
	private double cameraYaw = 0.0D;
	private double cameraPitch = 0.0D;
	
    public EntityRenderer(Minecraft minecraft)
    {
        farPlaneDistance = 0.0F;
        pointedEntity = null;
        field_1384_l = System.currentTimeMillis();
        random = new Random();
        field_1394_b = 0;
        field_1393_c = 0;
        fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
        mc = minecraft;
        itemRenderer = new ItemRenderer(minecraft);
    }

    public void func_911_a()
    {
        fogColor2 = fogColor1;
        float f = mc.theWorld.getLightBrightness(MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.posY), MathHelper.floor_double(mc.thePlayer.posZ));
        float f1 = (float)(3 - mc.gameSettings.renderDistance) / 3F;
        float f2 = f * (1.0F - f1) + f1;
        fogColor1 += (f2 - fogColor1) * 0.1F;
        itemRenderer.func_895_a();
        if(mc.field_6288_M)
        {
            func_916_c();
        }
    }
    
    private float getFOVModifier(float var1, boolean var2) {
    	//var2 = false;
    	cameraZoom = 1.5f - mc.gameSettings.fovSetting;
    	cameraZoom *= mc.thePlayer.getFOVMultiplier();
    	System.out.println(mc.thePlayer.getFOVMultiplier());
		if(this.debugViewDirection > 0) {
			return 90.0F;
		} else {
			EntityPlayer var3 = (EntityPlayer)this.mc.renderViewEntity;
			float var4 = 70.0F;
			if(var2) {
				var4 += this.mc.gameSettings.fovSetting * 40.0F;
				var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * var1;
			}

			if(var3.getEntityHealth() <= 0) {
				float var5 = (float)var3.deathTime + var1;
				var4 /= (1.0F - 500.0F / (var5 + 500.0F)) * 2.0F + 1.0F;
			}

			return var4 + this.prevDebugCamFOV + (this.debugCamFOV - this.prevDebugCamFOV) * var1;
		}
	}
    
    private void updateFovModifierHand() {
		EntityPlayerSP var1 = (EntityPlayerSP)this.mc.renderViewEntity;
		this.fovMultiplierTemp = var1.getFOVMultiplier();
		this.fovModifierHandPrev = this.fovModifierHand;
		this.fovModifierHand += (this.fovMultiplierTemp - this.fovModifierHand) * 0.5F;
	}
    
    public void func_910_a(float f)
    {
    	//updateFovModifierHand();
        if(mc.thePlayer == null)
        {
            return;
        }
        double d = mc.playerController.getBlockReachDistance();
        mc.objectMouseOver = mc.thePlayer.rayTrace(d, f);
        double d1 = d;
        Vec3D vec3d = mc.thePlayer.getPosition(f);
        if(mc.objectMouseOver != null)
        {
            d1 = mc.objectMouseOver.hitVec.distanceTo(vec3d);
        }
        if(mc.playerController instanceof PlayerControllerTest)
        {
            d1 = d = 32D;
        } else
        {
            if(d1 > 3D)
            {
                d1 = 3D;
            }
            d = d1;
        }
        Vec3D vec3d1 = mc.thePlayer.getLook(f);
        Vec3D vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
        pointedEntity = null;
        float f1 = 1.0F;
        List<?> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.thePlayer, mc.thePlayer.boundingBox.addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d).expands(f1, f1, f1));
        double d2 = 0.0D;
        for(int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity)list.get(i);
            if(!entity.canBeCollidedWith())
            {
                continue;
            }
            float f2 = entity.func_4035_j_();
            AxisAlignedBB axisalignedbb = entity.boundingBox.expands(f2, f2, f2);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
            if(axisalignedbb.isVectorInside(vec3d))
            {
                if(0.0D < d2 || d2 == 0.0D)
                {
                    pointedEntity = entity;
                    d2 = 0.0D;
                }
                continue;
            }
            if(movingobjectposition == null)
            {
                continue;
            }
            double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
            if(d3 < d2 || d2 == 0.0D)
            {
                pointedEntity = entity;
                d2 = d3;
            }
        }

        if(pointedEntity != null && !(mc.playerController instanceof PlayerControllerTest))
        {
            mc.objectMouseOver = new MovingObjectPosition(pointedEntity);
        }
    }

    private float func_914_d(float f)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        float f1 = 70F;
        if(entityplayersp.isInsideOfMaterial(Material.water))
        {
            f1 = 60F;
        }
        if(((EntityPlayer) (entityplayersp)).health <= 0)
        {
            float f2 = (float)((EntityPlayer) (entityplayersp)).deathTime + f;
            f1 /= (1.0F - 500F / (f2 + 500F)) * 2.0F + 1.0F;
        }
        return f1;
    }

    private void hurtCameraEffect(float f)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        float f1 = (float)((EntityPlayer) (entityplayersp)).hurtTime - f;
        if(((EntityPlayer) (entityplayersp)).health <= 0)
        {
            float f2 = (float)((EntityPlayer) (entityplayersp)).deathTime + f;
            GL11.glRotatef(40F - 8000F / (f2 + 200F), 0.0F, 0.0F, 1.0F);
        }
        if(f1 < 0.0F)
        {
            return;
        } else
        {
            f1 /= ((EntityPlayer) (entityplayersp)).maxHurtTime;
            f1 = MathHelper.sin(f1 * f1 * f1 * f1 * 3.141593F);
            float f3 = ((EntityPlayer) (entityplayersp)).field_9331_N;
            GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f1 * 14F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
            return;
        }
    }

    private void setupViewBobbing(float f)
    {
        if(mc.gameSettings.thirdPersonView)
        {
            return;
        } else
        {
            EntityPlayerSP entityplayersp = mc.thePlayer;
            float f1 = ((EntityPlayer) (entityplayersp)).distanceWalkedModified - ((EntityPlayer) (entityplayersp)).prevDistanceWalkedModified;
            float f2 = ((EntityPlayer) (entityplayersp)).distanceWalkedModified + f1 * f;
            float f3 = ((EntityPlayer) (entityplayersp)).prevCameraYaw + (((EntityPlayer) (entityplayersp)).cameraYaw - ((EntityPlayer) (entityplayersp)).prevCameraYaw) * f;
            float f4 = ((EntityPlayer) (entityplayersp)).field_9329_Q + (((EntityPlayer) (entityplayersp)).field_9328_R - ((EntityPlayer) (entityplayersp)).field_9329_Q) * f;
            GL11.glTranslatef(MathHelper.sin(f2 * 3.141593F) * f3 * 0.5F, -Math.abs(MathHelper.cos(f2 * 3.141593F) * f3), 0.0F);
            GL11.glRotatef(MathHelper.sin(f2 * 3.141593F) * f3 * 3F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(Math.abs(MathHelper.cos(f2 * 3.141593F + 0.2F) * f3) * 5F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
            return;
        }
    }

    private void orientCamera(float f)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        double d = ((EntityPlayer) (entityplayersp)).prevPosX + (((EntityPlayer) (entityplayersp)).posX - ((EntityPlayer) (entityplayersp)).prevPosX) * (double)f;
        double d1 = ((EntityPlayer) (entityplayersp)).prevPosY + (((EntityPlayer) (entityplayersp)).posY - ((EntityPlayer) (entityplayersp)).prevPosY) * (double)f;
        double d2 = ((EntityPlayer) (entityplayersp)).prevPosZ + (((EntityPlayer) (entityplayersp)).posZ - ((EntityPlayer) (entityplayersp)).prevPosZ) * (double)f;
        if(mc.gameSettings.thirdPersonView)
        {
            double d3 = 4D;
            float f1 = ((EntityPlayer) (entityplayersp)).rotationYaw;
            float f2 = ((EntityPlayer) (entityplayersp)).rotationPitch;
            if(Keyboard.isKeyDown(59))
            {
                f2 += 180F;
                d3 += 2D;
            }
            double d4 = (double)(-MathHelper.sin((f1 / 180F) * 3.141593F) * MathHelper.cos((f2 / 180F) * 3.141593F)) * d3;
            double d5 = (double)(MathHelper.cos((f1 / 180F) * 3.141593F) * MathHelper.cos((f2 / 180F) * 3.141593F)) * d3;
            double d6 = (double)(-MathHelper.sin((f2 / 180F) * 3.141593F)) * d3;
            for(int i = 0; i < 8; i++)
            {
                float f3 = (i & 1) * 2 - 1;
                float f4 = (i >> 1 & 1) * 2 - 1;
                float f5 = (i >> 2 & 1) * 2 - 1;
                f3 *= 0.1F;
                f4 *= 0.1F;
                f5 *= 0.1F;
                MovingObjectPosition movingobjectposition = mc.theWorld.rayTraceBlocks(Vec3D.createVector(d + (double)f3, d1 + (double)f4, d2 + (double)f5), Vec3D.createVector((d - d4) + (double)f3 + (double)f5, (d1 - d6) + (double)f4, (d2 - d5) + (double)f5));
                if(movingobjectposition == null)
                {
                    continue;
                }
                double d7 = movingobjectposition.hitVec.distanceTo(Vec3D.createVector(d, d1, d2));
                if(d7 < d3)
                {
                    d3 = d7;
                }
            }

            if(Keyboard.isKeyDown(59))
            {
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            }
            GL11.glRotatef(((EntityPlayer) (entityplayersp)).rotationPitch - f2, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(((EntityPlayer) (entityplayersp)).rotationYaw - f1, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, 0.0F, (float)(-d3));
            GL11.glRotatef(f1 - ((EntityPlayer) (entityplayersp)).rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(f2 - ((EntityPlayer) (entityplayersp)).rotationPitch, 1.0F, 0.0F, 0.0F);
        } else
        {
            GL11.glTranslatef(0.0F, 0.0F, -0.1F);
        }
        GL11.glRotatef(((EntityPlayer) (entityplayersp)).prevRotationPitch + (((EntityPlayer) (entityplayersp)).rotationPitch - ((EntityPlayer) (entityplayersp)).prevRotationPitch) * f, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(((EntityPlayer) (entityplayersp)).prevRotationYaw + (((EntityPlayer) (entityplayersp)).rotationYaw - ((EntityPlayer) (entityplayersp)).prevRotationYaw) * f + 180F, 0.0F, 1.0F, 0.0F);
    }
    
    private void renderHand(float var1, int var2) {
		if(this.debugViewDirection <= 0) {
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			float var3 = 0.07F;
			if(this.mc.gameSettings.anaglyph) {
				GL11.glTranslatef((float)(-(var2 * 2 - 1)) * var3, 0.0F, 0.0F);
			}

			if(this.cameraZoom != 1.0D) {
				GL11.glTranslatef((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
				GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0D);
				GLU.gluPerspective(this.getFOVModifier(var1, false), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
			} else {
				GLU.gluPerspective(this.getFOVModifier(var1, false), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
			}

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			if(this.mc.gameSettings.anaglyph) {
				GL11.glTranslatef((float)(var2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
			}

			GL11.glPushMatrix();
			this.hurtCameraEffect(var1);
			if(this.mc.gameSettings.viewBobbing) {
				this.setupViewBobbing(var1);
			}

			GL11.glPopMatrix();
			if(this.mc.gameSettings.thirdPersonView) {
				this.itemRenderer.renderOverlays(var1);
				this.hurtCameraEffect(var1);
			}

			if(this.mc.gameSettings.viewBobbing) {
				this.setupViewBobbing(var1);
			}

		}
	}

    private void setupCameraTransform(float f, int i)
    {
    	
    	
        farPlaneDistance = 256 >> mc.gameSettings.renderDistance;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        float f1 = 0.07F;
        if(mc.gameSettings.anaglyph)
        {
            GL11.glTranslatef((float)(-(i * 2 - 1)) * f1, 0.0F, 0.0F);
        }
        for(int var18 = 0; var18 < 2; ++var18) {
        	if(this.cameraZoom == 1.0D) {
    			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
    			this.renderHand(1, var18);
    		}
        }
        

        GLU.gluPerspective(func_914_d(f), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        if(mc.gameSettings.anaglyph)
        {
            GL11.glTranslatef((float)(i * 2 - 1) * 0.1F, 0.0F, 0.0F);
        }
        //cameraZoom = 1 - mc.gameSettings.fovSetting;
        //System.out.println(cameraZoom);
        if(this.cameraZoom != 1.0D) {
			GL11.glTranslatef((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
			GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0D);
			GLU.gluPerspective(this.getFOVModifier(f, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
		} else {
			GLU.gluPerspective(this.getFOVModifier(f, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
		}
        hurtCameraEffect(f);
        if(mc.gameSettings.viewBobbing)
        {
            setupViewBobbing(f);
        }
        float f2 = mc.thePlayer.prevTimeInPortal + (mc.thePlayer.timeInPortal - mc.thePlayer.prevTimeInPortal) * f;
        if(f2 > 0.0F)
        {
            float f3 = 5F / (f2 * f2 + 5F) - f2 * 0.04F;
            f3 *= f3;
            GL11.glRotatef(f2 * f2 * 1500F, 0.0F, 1.0F, 1.0F);
            GL11.glScalef(1.0F / f3, 1.0F, 1.0F);
            GL11.glRotatef(-f2 * f2 * 1500F, 0.0F, 1.0F, 1.0F);
        }
        orientCamera(f);
        if(this.debugViewDirection > 0) {
			int var7 = this.debugViewDirection - 1;
			if(var7 == 1) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			}

			if(var7 == 2) {
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			}

			if(var7 == 3) {
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			}

			if(var7 == 4) {
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			}

			if(var7 == 5) {
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			}
		}
    }

    private void func_4135_b(float f, int i)
    {
        GL11.glLoadIdentity();
        if(mc.gameSettings.anaglyph)
        {
            GL11.glTranslatef((float)(i * 2 - 1) * 0.1F, 0.0F, 0.0F);
        }
        GL11.glPushMatrix();
        hurtCameraEffect(f);
        if(mc.gameSettings.viewBobbing)
        {
            setupViewBobbing(f);
        }
        if(!mc.gameSettings.thirdPersonView && !Keyboard.isKeyDown(59))
        {
            itemRenderer.renderItemInFirstPerson(f);
        }
        GL11.glPopMatrix();
        if(!mc.gameSettings.thirdPersonView)
        {
            itemRenderer.renderOverlays(f);
            hurtCameraEffect(f);
        }
        if(mc.gameSettings.viewBobbing)
        {
            setupViewBobbing(f);
        }
    }

    public void func_4136_b(float f)
    {
        if(!Display.isActive())
        {
            if(System.currentTimeMillis() - field_1384_l > 500L)
            {
                mc.func_6252_g();
            }
        } else
        {
            field_1384_l = System.currentTimeMillis();
        }
        if(mc.field_6289_L)
        {
            mc.mouseHelper.func_772_c();
            float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8F;
            float f3 = (float)mc.mouseHelper.field_1114_a * f2;
            float f4 = (float)mc.mouseHelper.field_1113_b * f2;
            int l = 1;
            if(mc.gameSettings.invertMouse)
            {
                l = -1;
            }
            mc.thePlayer.func_346_d(f3, f4 * (float)l);
        }
        if(mc.skipRenderWorld)
        {
            return;
        }
        ScaledResolution scaledresolution = new ScaledResolution(mc.displayWidth, mc.displayHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        int k = (Mouse.getX() * i) / mc.displayWidth;
        int i1 = j - (Mouse.getY() * j) / mc.displayHeight - 1;
        if(mc.theWorld != null)
        {
            func_4134_c(f);
            if(!Keyboard.isKeyDown(59))
            {
                mc.ingameGUI.renderGameOverlay(f, mc.currentScreen != null, k, i1);
            }
        } else
        {
            GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16640);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            func_905_b();
        }
        if(mc.currentScreen != null)
        {
            GL11.glClear(256);
            mc.currentScreen.drawScreen(k, i1, f);
        }
    }

    public void func_4134_c(float f)
    {
        func_910_a(f);
        EntityPlayerSP entityplayersp = mc.thePlayer;
        RenderGlobal renderglobal = mc.renderGlobal;
        EffectRenderer effectrenderer = mc.effectRenderer;
        double d = ((EntityPlayer) (entityplayersp)).lastTickPosX + (((EntityPlayer) (entityplayersp)).posX - ((EntityPlayer) (entityplayersp)).lastTickPosX) * (double)f;
        double d1 = ((EntityPlayer) (entityplayersp)).lastTickPosY + (((EntityPlayer) (entityplayersp)).posY - ((EntityPlayer) (entityplayersp)).lastTickPosY) * (double)f;
        double d2 = ((EntityPlayer) (entityplayersp)).lastTickPosZ + (((EntityPlayer) (entityplayersp)).posZ - ((EntityPlayer) (entityplayersp)).lastTickPosZ) * (double)f;
        for(int i = 0; i < 2; i++)
        {
            if(mc.gameSettings.anaglyph)
            {
                if(i == 0)
                {
                    GL11.glColorMask(false, true, true, false);
                } else
                {
                    GL11.glColorMask(true, false, false, false);
                }
            }
            GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
            updateFogColor(f);
            GL11.glClear(16640);
            GL11.glEnable(2884);
            setupCameraTransform(f, i);
            ClippingHelperImplementation.func_1155_a();
            if(mc.gameSettings.renderDistance < 2)
            {
                func_4140_a(-1);
                renderglobal.func_4142_a(f);
            }
            GL11.glEnable(2912);
            func_4140_a(1);
            Frustrum frustrum = new Frustrum();
            frustrum.func_343_a(d, d1, d2);
            mc.renderGlobal.clipRenderersByFrustum(frustrum, f);
            mc.renderGlobal.func_948_a(entityplayersp, false);
            func_4140_a(0);
            GL11.glEnable(2912);
            GL11.glBindTexture(3553, mc.renderEngine.getTexture("/terrain.png"));
            RenderHelper.disableStandardItemLighting();
            renderglobal.func_943_a(entityplayersp, 0, f);
            RenderHelper.enableStandardItemLighting();
            renderglobal.renderEntities(entityplayersp.getPosition(f), frustrum, f);
            effectrenderer.func_1187_b(entityplayersp, f);
            RenderHelper.disableStandardItemLighting();
            func_4140_a(0);
            effectrenderer.func_1189_a(entityplayersp, f);
            if(mc.objectMouseOver != null && entityplayersp.isInsideOfMaterial(Material.water))
            {
                GL11.glDisable(3008);
                renderglobal.func_959_a(entityplayersp, mc.objectMouseOver, 0, ((EntityPlayer) (entityplayersp)).inventory.getCurrentItem(), f);
                renderglobal.drawSelectionBox(entityplayersp, mc.objectMouseOver, 0, ((EntityPlayer) (entityplayersp)).inventory.getCurrentItem(), f);
                GL11.glEnable(3008);
            }
            GL11.glBlendFunc(770, 771);
            func_4140_a(0);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            GL11.glBindTexture(3553, mc.renderEngine.getTexture("/terrain.png"));
            if(mc.gameSettings.fancyGraphics)
            {
                GL11.glColorMask(false, false, false, false);
                int j = renderglobal.func_943_a(entityplayersp, 1, f);
                GL11.glColorMask(true, true, true, true);
                if(mc.gameSettings.anaglyph)
                {
                    if(i == 0)
                    {
                        GL11.glColorMask(false, true, true, false);
                    } else
                    {
                        GL11.glColorMask(true, false, false, false);
                    }
                }
                if(j > 0)
                {
                    renderglobal.func_944_a(1, f);
                }
            } else
            {
                renderglobal.func_943_a(entityplayersp, 1, f);
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            if(mc.objectMouseOver != null && !entityplayersp.isInsideOfMaterial(Material.water))
            {
                GL11.glDisable(3008);
                renderglobal.func_959_a(entityplayersp, mc.objectMouseOver, 0, ((EntityPlayer) (entityplayersp)).inventory.getCurrentItem(), f);
                renderglobal.drawSelectionBox(entityplayersp, mc.objectMouseOver, 0, ((EntityPlayer) (entityplayersp)).inventory.getCurrentItem(), f);
                GL11.glEnable(3008);
            }
            GL11.glDisable(2912);
            if(pointedEntity == null);
            func_4140_a(0);
            GL11.glEnable(2912);
            renderglobal.func_4141_b(f);
            GL11.glDisable(2912);
            func_4140_a(1);
            GL11.glClear(256);
            func_4135_b(f, i);
            if(!mc.gameSettings.anaglyph)
            {
                return;
            }
        }

        GL11.glColorMask(true, true, true, false);
    }

    private void func_916_c()
    {
        if(!mc.gameSettings.fancyGraphics)
        {
            return;
        }
        EntityPlayerSP entityplayersp = mc.thePlayer;
        World world = mc.theWorld;
        int i = MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posX);
        int j = MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posY);
        int k = MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posZ);
        byte byte0 = 16;
        for(int l = 0; l < 150; l++)
        {
            int i1 = (i + random.nextInt(byte0)) - random.nextInt(byte0);
            int j1 = (k + random.nextInt(byte0)) - random.nextInt(byte0);
            int k1 = world.func_696_e(i1, j1);
            int l1 = world.getBlockId(i1, k1 - 1, j1);
            if(k1 > j + byte0 || k1 < j - byte0)
            {
                continue;
            }
            float f = random.nextFloat();
            float f1 = random.nextFloat();
            if(l1 > 0)
            {
                mc.effectRenderer.addEffect(new EntityRainFX(world, (float)i1 + f, (double)((float)k1 + 0.1F) - Block.blocksList[l1].minY, (float)j1 + f1));
            }
        }

    }

    public void func_905_b()
    {
        ScaledResolution scaledresolution = new ScaledResolution(mc.displayWidth, mc.displayHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, i, j, 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
    }

    private void updateFogColor(float f)
    {
        World world = mc.theWorld;
        EntityPlayerSP entityplayersp = mc.thePlayer;
        float f1 = 1.0F / (float)(4 - mc.gameSettings.renderDistance);
        f1 = 1.0F - (float)Math.pow(f1, 0.25D);
        Vec3D vec3d = world.func_4079_a(mc.thePlayer, f);
        float f2 = (float)vec3d.xCoord;
        float f3 = (float)vec3d.yCoord;
        float f4 = (float)vec3d.zCoord;
        Vec3D vec3d1 = world.func_4082_d(f);
        fogColorRed = (float)vec3d1.xCoord;
        fogColorGreen = (float)vec3d1.yCoord;
        fogColorBlue = (float)vec3d1.zCoord;
        fogColorRed += (f2 - fogColorRed) * f1;
        fogColorGreen += (f3 - fogColorGreen) * f1;
        fogColorBlue += (f4 - fogColorBlue) * f1;
        if(entityplayersp.isInsideOfMaterial(Material.water))
        {
            fogColorRed = 0.02F;
            fogColorGreen = 0.02F;
            fogColorBlue = 0.2F;
        } else
        if(entityplayersp.isInsideOfMaterial(Material.lava))
        {
            fogColorRed = 0.6F;
            fogColorGreen = 0.1F;
            fogColorBlue = 0.0F;
        }
        float f5 = fogColor2 + (fogColor1 - fogColor2) * f;
        fogColorRed *= f5;
        fogColorGreen *= f5;
        fogColorBlue *= f5;
        if(mc.gameSettings.anaglyph)
        {
            float f6 = (fogColorRed * 30F + fogColorGreen * 59F + fogColorBlue * 11F) / 100F;
            float f7 = (fogColorRed * 30F + fogColorGreen * 70F) / 100F;
            float f8 = (fogColorRed * 30F + fogColorBlue * 70F) / 100F;
            fogColorRed = f6;
            fogColorGreen = f7;
            fogColorBlue = f8;
        }
        GL11.glClearColor(fogColorRed, fogColorGreen, fogColorBlue, 0.0F);
    }

    private void func_4140_a(int i)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        GL11.glFog(2918, func_908_a(fogColorRed, fogColorGreen, fogColorBlue, 1.0F));
        GL11.glNormal3f(0.0F, -1F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(entityplayersp.isInsideOfMaterial(Material.water))
        {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1F);
            float f = 0.4F;
            float f2 = 0.4F;
            float f4 = 0.9F;
            if(mc.gameSettings.anaglyph)
            {
                float f6 = (f * 30F + f2 * 59F + f4 * 11F) / 100F;
                float f8 = (f * 30F + f2 * 70F) / 100F;
                float f10 = (f * 30F + f4 * 70F) / 100F;
                f = f6;
                f2 = f8;
                f4 = f10;
            }
        } else
        if(entityplayersp.isInsideOfMaterial(Material.lava))
        {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 2.0F);
            float f1 = 0.4F;
            float f3 = 0.3F;
            float f5 = 0.3F;
            if(mc.gameSettings.anaglyph)
            {
                float f7 = (f1 * 30F + f3 * 59F + f5 * 11F) / 100F;
                float f9 = (f1 * 30F + f3 * 70F) / 100F;
                float f11 = (f1 * 30F + f5 * 70F) / 100F;
                f1 = f7;
                f3 = f9;
                f5 = f11;
            }
        } else
        {
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, farPlaneDistance * 0.25F);
            GL11.glFogf(2916, farPlaneDistance);
            if(i < 0)
            {
                GL11.glFogf(2915, 0.0F);
                GL11.glFogf(2916, farPlaneDistance * 0.8F);
            }
            if(GLContext.getCapabilities().GL_NV_fog_distance)
            {
                GL11.glFogi(34138, 34139);
            }
            if(mc.theWorld.worldProvider.field_4220_c)
            {
                GL11.glFogf(2915, 0.0F);
            }
        }
        GL11.glEnable(2903);
        GL11.glColorMaterial(1028, 4608);
    }

    private FloatBuffer func_908_a(float f, float f1, float f2, float f3)
    {
        fogColorBuffer.clear();
        fogColorBuffer.put(f).put(f1).put(f2).put(f3);
        fogColorBuffer.flip();
        return fogColorBuffer;
    }
}
