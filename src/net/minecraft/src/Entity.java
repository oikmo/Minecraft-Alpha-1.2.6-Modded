package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;
import java.util.Random;

public abstract class Entity
{
	private static int field_864_a = 0;
    public int entityID;
    public double field_619_ac;
    public boolean field_618_ad;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public boolean onGround;
    public boolean field_9297_aI;
    public boolean field_9296_aJ;
    public boolean field_9295_aK;
    public boolean field_9294_aL;
    public boolean field_9293_aM;
    public boolean isDead;
    public float yOffset;
    public float width;
    public float height;
    public float prevDistanceWalkedModified;
    public float distanceWalkedModified;
    protected boolean entityWalks;
    protected float fallDistance;
    private int field_863_b;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float ySize;
    public float field_9286_aZ;
    public boolean field_9314_ba;
    public float field_632_aO;
    public boolean field_9313_bc;
    protected Random rand;
    public int ticksExisted;
    public int field_9310_bf;
    public int fire;
    protected int field_9308_bh;
    protected boolean inWater;
    public int heartsLife;
    public int air;
    private boolean firstUpdate;
    public String skinUrl;
    protected boolean isImmuneToFire;
    private double minecartType;
    private double field_667_e;
    public boolean field_621_aZ;
    public int field_657_ba;
    public int field_656_bb;
    public int field_654_bc;
    public int field_9303_br;
    public int field_9302_bs;
    public int field_9301_bt;
    public boolean field_9300_bu;
    public boolean field_9299_bv;
    public boolean field_12240_bw;
    public boolean sprinting;
	
    public Entity(World world)
    {
        entityID = field_864_a++;
        field_619_ac = 1.0D;
        field_618_ad = false;
        onGround = false;
        field_9295_aK = false;
        field_9294_aL = false;
        field_9293_aM = true;
        isDead = false;
        yOffset = 0.0F;
        width = 0.6F;
        height = 1.8F;
        prevDistanceWalkedModified = 0.0F;
        distanceWalkedModified = 0.0F;
        entityWalks = true;
        fallDistance = 0.0F;
        field_863_b = 1;
        ySize = 0.0F;
        field_9286_aZ = 0.0F;
        field_9314_ba = false;
        field_632_aO = 0.0F;
        field_9313_bc = false;
        rand = new Random();
        ticksExisted = 0;
        field_9310_bf = 1;
        fire = 0;
        field_9308_bh = 300;
        inWater = false;
        heartsLife = 0;
        air = 300;
        firstUpdate = true;
        isImmuneToFire = false;
        field_621_aZ = false;
        worldObj = world;
        setPosition(0.0D, 0.0D, 0.0D);
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof Entity)
        {
            return ((Entity)obj).entityID == entityID;
        } else
        {
            return false;
        }
    }
    
    public boolean isSprinting() {
    	return sprinting;
    }
    
    public void setSprinting(boolean var) {
    	sprinting = var;
    }

    public int hashCode()
    {
        return entityID;
    }

    protected void preparePlayerToSpawn()
    {
        if(worldObj == null)
        {
            return;
        }
        do
        {
            if(posY <= 0.0D)
            {
                break;
            }
            setPosition(posX, posY, posZ);
            if(worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0)
            {
                break;
            }
            posY++;
        } while(true);
        motionX = motionY = motionZ = 0.0D;
        rotationPitch = 0.0F;
    }

    public void setEntityDead()
    {
        isDead = true;
    }

    protected void setSize(float f, float f1)
    {
        width = f;
        height = f1;
    }

    protected void setRotation(float f, float f1)
    {
        rotationYaw = f;
        rotationPitch = f1;
    }

    public void setPosition(double d, double d1, double d2)
    {
        posX = d;
        posY = d1;
        posZ = d2;
        float f = width / 2.0F;
        float f1 = height;
        boundingBox.setBounds(d - (double)f, (d1 - (double)yOffset) + (double)ySize, d2 - (double)f, d + (double)f, (d1 - (double)yOffset) + (double)ySize + (double)f1, d2 + (double)f);
    }

    public void func_346_d(float f, float f1)
    {
        float f2 = rotationPitch;
        float f3 = rotationYaw;
        rotationYaw += (double)f * 0.14999999999999999D;
        rotationPitch -= (double)f1 * 0.14999999999999999D;
        if(rotationPitch < -90F)
        {
            rotationPitch = -90F;
        }
        if(rotationPitch > 90F)
        {
            rotationPitch = 90F;
        }
        prevRotationPitch += rotationPitch - f2;
        prevRotationYaw += rotationYaw - f3;
    }

    public void onUpdate()
    {
        onEntityUpdate();
    }

    public void onEntityUpdate()
    {
        if(ridingEntity != null && ridingEntity.isDead)
        {
            ridingEntity = null;
        }
        ticksExisted++;
        prevDistanceWalkedModified = distanceWalkedModified;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        prevRotationPitch = rotationPitch;
        prevRotationYaw = rotationYaw;
        
        if(handleWaterMovement())
        {
            if(!inWater && !firstUpdate)
            {
                float f = MathHelper.sqrt_double(motionX * motionX * 0.20000000298023224D + motionY * motionY + motionZ * motionZ * 0.20000000298023224D) * 0.2F;
                if(f > 1.0F)
                {
                    f = 1.0F;
                }
                worldObj.playSoundAtEntity(this, "random.splash", f, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
                float f1 = MathHelper.floor_double(boundingBox.minY);
                for(int i = 0; (float)i < 1.0F + width * 20F; i++)
                {
                    float f2 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    float f4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("bubble", posX + (double)f2, f1 + 1.0F, posZ + (double)f4, motionX, motionY - (double)(rand.nextFloat() * 0.2F), motionZ);
                }

                for(int j = 0; (float)j < 1.0F + width * 20F; j++)
                {
                    float f3 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    float f5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("splash", posX + (double)f3, f1 + 1.0F, posZ + (double)f5, motionX, motionY, motionZ);
                }

            }
            fallDistance = 0.0F;
            inWater = true;
            fire = 0;
        } else
        {
            inWater = false;
        }
        if(worldObj.multiplayerWorld)
        {
            fire = 0;
        } else
        if(fire > 0)
        {
            if(isImmuneToFire)
            {
                fire -= 4;
                if(fire < 0)
                {
                    fire = 0;
                }
            } else
            {
                if(fire % 20 == 0)
                {
                    canAttackEntity(null, 1);
                }
                fire--;
            }
        }
        if(handleLavaMovement())
        {
            setOnFireFromLava();
        }
        if(posY < -64D)
        {
            kill();
        }
        firstUpdate = false;
    }

    protected void setOnFireFromLava()
    {
        if(!isImmuneToFire)
        {
            canAttackEntity(null, 4);
            fire = 600;
        }
    }

    protected void kill()
    {
        setEntityDead();
    }
    
	public boolean func_403_b(double d, double d1, double d2)
    {
        AxisAlignedBB axisalignedbb = boundingBox.getOffsetBoundingBox(d, d1, d2);
        List<?> list = worldObj.getCollidingBoundingBoxes(this, axisalignedbb);
        if(list.size() > 0)
        {
            return false;
        }
        return !worldObj.getIsAnyLiquid(axisalignedbb);
    }
	
	public void moveEntity(double d, double d1, double d2)
    {
        if(field_9314_ba)
        {
            boundingBox.offset(d, d1, d2);
            posX = (boundingBox.minX + boundingBox.maxX) / 2D;
            posY = (boundingBox.minY + (double)yOffset) - (double)ySize;
            posZ = (boundingBox.minZ + boundingBox.maxZ) / 2D;
            return;
        }
        double d3 = posX;
        double d4 = posZ;
        double d5 = d;
        double d6 = d1;
        double d7 = d2;
        AxisAlignedBB axisalignedbb = boundingBox.copy();
        boolean flag = onGround && isSneaking();
        if(flag)
        {
            double d8 = 0.050000000000000003D;
            for(; d != 0.0D && worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(d, -1D, 0.0D)).size() == 0; d5 = d)
            {
                if(d < d8 && d >= -d8)
                {
                    d = 0.0D;
                    continue;
                }
                if(d > 0.0D)
                {
                    d -= d8;
                } else
                {
                    d += d8;
                }
            }

            for(; d2 != 0.0D && worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(0.0D, -1D, d2)).size() == 0; d7 = d2)
            {
                if(d2 < d8 && d2 >= -d8)
                {
                    d2 = 0.0D;
                    continue;
                }
                if(d2 > 0.0D)
                {
                    d2 -= d8;
                } else
                {
                    d2 += d8;
                }
            }

        }
        List<?> list = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(d, d1, d2));
        for(int i = 0; i < list.size(); i++)
        {
            d1 = ((AxisAlignedBB)list.get(i)).calculateYOffset(boundingBox, d1);
        }

        boundingBox.offset(0.0D, d1, 0.0D);
        if(!field_9293_aM && d6 != d1)
        {
            d = d1 = d2 = 0.0D;
        }
        boolean flag1 = onGround || d6 != d1 && d6 < 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            d = ((AxisAlignedBB)list.get(j)).calculateXOffset(boundingBox, d);
        }

        boundingBox.offset(d, 0.0D, 0.0D);
        if(!field_9293_aM && d5 != d)
        {
            d = d1 = d2 = 0.0D;
        }
        for(int k = 0; k < list.size(); k++)
        {
            d2 = ((AxisAlignedBB)list.get(k)).calculateZOffset(boundingBox, d2);
        }

        boundingBox.offset(0.0D, 0.0D, d2);
        if(!field_9293_aM && d7 != d2)
        {
            d = d1 = d2 = 0.0D;
        }
        if(field_9286_aZ > 0.0F && flag1 && ySize < 0.05F && (d5 != d || d7 != d2))
        {
            double d9 = d;
            double d11 = d1;
            double d13 = d2;
            d = d5;
            d1 = field_9286_aZ;
            d2 = d7;
            AxisAlignedBB axisalignedbb1 = boundingBox.copy();
            boundingBox.setBB(axisalignedbb);
            List<AxisAlignedBB> list1 = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(d, d1, d2));
            for(int j2 = 0; j2 < list1.size(); j2++)
            {
                d1 = ((AxisAlignedBB)list1.get(j2)).calculateYOffset(boundingBox, d1);
            }

            boundingBox.offset(0.0D, d1, 0.0D);
            if(!field_9293_aM && d6 != d1)
            {
                d = d1 = d2 = 0.0D;
            }
            for(int k2 = 0; k2 < list1.size(); k2++)
            {
                d = ((AxisAlignedBB)list1.get(k2)).calculateXOffset(boundingBox, d);
            }

            boundingBox.offset(d, 0.0D, 0.0D);
            if(!field_9293_aM && d5 != d)
            {
                d = d1 = d2 = 0.0D;
            }
            for(int l2 = 0; l2 < list1.size(); l2++)
            {
                d2 = ((AxisAlignedBB)list1.get(l2)).calculateZOffset(boundingBox, d2);
            }

            boundingBox.offset(0.0D, 0.0D, d2);
            if(!field_9293_aM && d7 != d2)
            {
                d = d1 = d2 = 0.0D;
            }
            if(d9 * d9 + d13 * d13 >= d * d + d2 * d2)
            {
                d = d9;
                d1 = d11;
                d2 = d13;
                boundingBox.setBB(axisalignedbb1);
            } else
            {
                ySize += 0.5D;
            }
        }
        posX = (boundingBox.minX + boundingBox.maxX) / 2D;
        posY = (boundingBox.minY + (double)yOffset) - (double)ySize;
        posZ = (boundingBox.minZ + boundingBox.maxZ) / 2D;
        field_9297_aI = d5 != d || d7 != d2;
        field_9296_aJ = d6 != d1;
        onGround = d6 != d1 && d6 < 0.0D;
        field_9295_aK = field_9297_aI || field_9296_aJ;
        updateFallState(d1, onGround);
        if(d5 != d)
        {
            motionX = 0.0D;
        }
        if(d6 != d1)
        {
            motionY = 0.0D;
        }
        if(d7 != d2)
        {
            motionZ = 0.0D;
        }
        double d10 = posX - d3;
        double d12 = posZ - d4;
        if(entityWalks && !flag)
        {
            distanceWalkedModified += (double)MathHelper.sqrt_double(d10 * d10 + d12 * d12) * 0.59999999999999998D;
            int l = MathHelper.floor_double(posX);
            int j1 = MathHelper.floor_double(posY - 0.20000000298023224D - (double)yOffset);
            int l1 = MathHelper.floor_double(posZ);
            int i3 = worldObj.getBlockId(l, j1, l1);
            if(distanceWalkedModified > (float)field_863_b && i3 > 0)
            {
                field_863_b++;
                StepSound stepsound = Block.blocksList[i3].stepSound;
                if(worldObj.getBlockId(l, j1 + 1, l1) == Block.snow.blockID)
                {
                    stepsound = Block.snow.stepSound;
                    worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.15F, stepsound.getPitch());
                } else
                if(!Block.blocksList[i3].blockMaterial.getIsLiquid())
                {
                    worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.15F, stepsound.getPitch());
                }
                Block.blocksList[i3].onEntityWalking(worldObj, l, j1, l1, this);
            }
        }
        int i1 = MathHelper.floor_double(boundingBox.minX);
        int k1 = MathHelper.floor_double(boundingBox.minY);
        int i2 = MathHelper.floor_double(boundingBox.minZ);
        int j3 = MathHelper.floor_double(boundingBox.maxX);
        int k3 = MathHelper.floor_double(boundingBox.maxY);
        int l3 = MathHelper.floor_double(boundingBox.maxZ);
        for(int i4 = i1; i4 <= j3; i4++)
        {
            for(int j4 = k1; j4 <= k3; j4++)
            {
                for(int k4 = i2; k4 <= l3; k4++)
                {
                    int l4 = worldObj.getBlockId(i4, j4, k4);
                    if(l4 > 0)
                    {
                        Block.blocksList[l4].onEntityCollidedWithBlock(worldObj, i4, j4, k4, this);
                    }
                }

            }

        }

        ySize *= 0.4F;
        boolean flag2 = handleWaterMovement();
        if(worldObj.isBoundingBoxBurning(boundingBox))
        {
            func_355_a(1);
            if(!flag2)
            {
                fire++;
                if(fire == 0)
                {
                    fire = 300;
                }
            }
        } else
        if(fire <= 0)
        {
            fire = -field_9310_bf;
        }
        if(flag2 && fire > 0)
        {
            worldObj.playSoundAtEntity(this, "random.fizz", 0.7F, 1.6F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
            fire = -field_9310_bf;
        }
    }

    protected void updateFallState(double d, boolean flag)
    {
        if(flag)
        {
            if(fallDistance > 0.0F)
            {
                fall(fallDistance);
                fallDistance = 0.0F;
            }
        } else
        if(d < 0.0D)
        {
            fallDistance -= d;
        }
    }

    public boolean isSneaking()
    {
        return false;
    }

    public AxisAlignedBB func_372_f_()
    {
        return null;
    }

    protected void func_355_a(int i)
    {
        if(!isImmuneToFire)
        {
            canAttackEntity(null, i);
        }
    }

    protected void fall(float f)
    {
    }

    public boolean handleWaterMovement()
    {
        return worldObj.func_682_a(boundingBox.expands(0.0D, -0.40000000596046448D, 0.0D), Material.water, this);
    }

    public boolean isInsideOfMaterial(Material material)
    {
        double d = posY + (double)func_373_s();
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_float(MathHelper.floor_double(d));
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockId(i, j, k);
        if(l != 0 && Block.blocksList[l].blockMaterial == material)
        {
            float f = BlockFluids.func_288_b(worldObj.getBlockMetadata(i, j, k)) - 0.1111111F;
            float f1 = (float)(j + 1) - f;
            return d < (double)f1;
        } else
        {
            return false;
        }
    }

    public float func_373_s()
    {
        return 0.0F;
    }

    public boolean handleLavaMovement()
    {
        return worldObj.func_689_a(boundingBox.expands(0.0D, -0.40000000596046448D, 0.0D), Material.lava);
    }

    public void moveFlying(float f, float f1, float f2)
    {
        float f3 = MathHelper.sqrt_float(f * f + f1 * f1);
        if(f3 < 0.01F)
        {
            return;
        }
        if(f3 < 1.0F)
        {
            f3 = 1.0F;
        }
        f3 = f2 / f3;
        f *= f3;
        f1 *= f3;
        float f4 = MathHelper.sin((rotationYaw * 3.141593F) / 180F);
        float f5 = MathHelper.cos((rotationYaw * 3.141593F) / 180F);
        motionX += f * f5 - f1 * f4;
        motionZ += f1 * f5 + f * f4;
    }

    public float getEntityBrightness(float f)
    {
        int i = MathHelper.floor_double(posX);
        double d = (boundingBox.maxY - boundingBox.minY) * 0.66000000000000003D;
        int j = MathHelper.floor_double((posY - (double)yOffset) + d);
        int k = MathHelper.floor_double(posZ);
        return worldObj.getLightBrightness(i, j, k);
    }

    public void setWorld(World world)
    {
        worldObj = world;
    }

    public void setPositionAndRotation(double d, double d1, double d2, float f, 
            float f1)
    {
        prevPosX = posX = d;
        prevPosY = posY = d1;
        prevPosZ = posZ = d2;
        prevRotationYaw = rotationYaw = f;
        prevRotationPitch = rotationPitch = f1;
        ySize = 0.0F;
        double d3 = prevRotationYaw - f;
        if(d3 < -180D)
        {
            prevRotationYaw += 360F;
        }
        if(d3 >= 180D)
        {
            prevRotationYaw -= 360F;
        }
        setPosition(posX, posY, posZ);
        setRotation(f, f1);
    }

    public void setLocationAndAngles(double d, double d1, double d2, float f, 
            float f1)
    {
        prevPosX = posX = d;
        prevPosY = posY = d1 + (double)yOffset;
        prevPosZ = posZ = d2;
        rotationYaw = f;
        rotationPitch = f1;
        setPosition(posX, posY, posZ);
    }

    public float getDistanceToEntity(Entity entity)
    {
        float f = (float)(posX - entity.posX);
        float f1 = (float)(posY - entity.posY);
        float f2 = (float)(posZ - entity.posZ);
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }

    public double getDistanceSq(double d, double d1, double d2)
    {
        double d3 = posX - d;
        double d4 = posY - d1;
        double d5 = posZ - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double getDistance(double d, double d1, double d2)
    {
        double d3 = posX - d;
        double d4 = posY - d1;
        double d5 = posZ - d2;
        return (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public double getDistanceSqToEntity(Entity entity)
    {
        double d = posX - entity.posX;
        double d1 = posY - entity.posY;
        double d2 = posZ - entity.posZ;
        return d * d + d1 * d1 + d2 * d2;
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
    }

    public void applyEntityCollision(Entity entity)
    {
        if(entity.riddenByEntity == this || entity.ridingEntity == this)
        {
            return;
        }
        double d = entity.posX - posX;
        double d1 = entity.posZ - posZ;
        double d2 = MathHelper.abs_max(d, d1);
        if(d2 >= 0.0099999997764825821D)
        {
            d2 = MathHelper.sqrt_double(d2);
            d /= d2;
            d1 /= d2;
            double d3 = 1.0D / d2;
            if(d3 > 1.0D)
            {
                d3 = 1.0D;
            }
            d *= d3;
            d1 *= d3;
            d *= 0.05000000074505806D;
            d1 *= 0.05000000074505806D;
            d *= 1.0F - field_632_aO;
            d1 *= 1.0F - field_632_aO;
            addVelocity(-d, 0.0D, -d1);
            entity.addVelocity(d, 0.0D, d1);
        }
    }

    public void addVelocity(double d, double d1, double d2)
    {
        motionX += d;
        motionY += d1;
        motionZ += d2;
    }

    protected void func_9281_M()
    {
        field_9294_aL = true;
    }

    public boolean canAttackEntity(Entity entity, int i)
    {
        func_9281_M();
        return false;
    }

    public boolean canBeCollidedWith()
    {
        return false;
    }

    public boolean canBePushed()
    {
        return false;
    }

    public void addToPlayerScore(Entity entity, int i)
    {
    }

    public boolean func_390_a(Vec3D vec3d)
    {
        double d = posX - vec3d.xCoord;
        double d1 = posY - vec3d.yCoord;
        double d2 = posZ - vec3d.zCoord;
        double d3 = d * d + d1 * d1 + d2 * d2;
        return func_384_a(d3);
    }

    public boolean func_384_a(double d)
    {
        double d1 = boundingBox.getAverageEdgeLength();
        d1 *= 64D * field_619_ac;
        return d < d1 * d1;
    }

    public String getEntityTexture()
    {
        return null;
    }

    public boolean func_358_c(NBTTagCompound nbttagcompound)
    {
        String s = getEntityString();
        if(isDead || s == null)
        {
            return false;
        } else
        {
            nbttagcompound.setString("id", s);
            writeToNBT(nbttagcompound);
            return true;
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setTag("Pos", newDoubleNBTList(new double[] {
            posX, posY, posZ
        }));
        nbttagcompound.setTag("Motion", newDoubleNBTList(new double[] {
            motionX, motionY, motionZ
        }));
        nbttagcompound.setTag("Rotation", newFloatNBTList(new float[] {
            rotationYaw, rotationPitch
        }));
        nbttagcompound.setFloat("FallDistance", fallDistance);
        nbttagcompound.setShort("Fire", (short)fire);
        nbttagcompound.setShort("Air", (short)air);
        nbttagcompound.setBoolean("OnGround", onGround);
        writeEntityToNBT(nbttagcompound);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        NBTTagList nbttaglist = nbttagcompound.getTagList("Pos");
        NBTTagList nbttaglist1 = nbttagcompound.getTagList("Motion");
        NBTTagList nbttaglist2 = nbttagcompound.getTagList("Rotation");
        setPosition(0.0D, 0.0D, 0.0D);
        motionX = ((NBTTagDouble)nbttaglist1.tagAt(0)).doubleValue;
        motionY = ((NBTTagDouble)nbttaglist1.tagAt(1)).doubleValue;
        motionZ = ((NBTTagDouble)nbttaglist1.tagAt(2)).doubleValue;
        prevPosX = lastTickPosX = posX = ((NBTTagDouble)nbttaglist.tagAt(0)).doubleValue;
        prevPosY = lastTickPosY = posY = ((NBTTagDouble)nbttaglist.tagAt(1)).doubleValue;
        prevPosZ = lastTickPosZ = posZ = ((NBTTagDouble)nbttaglist.tagAt(2)).doubleValue;
        prevRotationYaw = rotationYaw = ((NBTTagFloat)nbttaglist2.tagAt(0)).floatValue;
        prevRotationPitch = rotationPitch = ((NBTTagFloat)nbttaglist2.tagAt(1)).floatValue;
        fallDistance = nbttagcompound.getFloat("FallDistance");
        fire = nbttagcompound.getShort("Fire");
        air = nbttagcompound.getShort("Air");
        onGround = nbttagcompound.getBoolean("OnGround");
        setPosition(posX, posY, posZ);
        readEntityFromNBT(nbttagcompound);
    }

    protected final String getEntityString()
    {
        return EntityList.getEntityString(this);
    }

    protected abstract void readEntityFromNBT(NBTTagCompound nbttagcompound);

    protected abstract void writeEntityToNBT(NBTTagCompound nbttagcompound);

    protected NBTTagList newDoubleNBTList(double ad[])
    {
        NBTTagList nbttaglist = new NBTTagList();
        double ad1[] = ad;
        int i = ad1.length;
        for(int j = 0; j < i; j++)
        {
            double d = ad1[j];
            nbttaglist.setTag(new NBTTagDouble(d));
        }

        return nbttaglist;
    }

    protected NBTTagList newFloatNBTList(float af[])
    {
        NBTTagList nbttaglist = new NBTTagList();
        float af1[] = af;
        int i = af1.length;
        for(int j = 0; j < i; j++)
        {
            float f = af1[j];
            nbttaglist.setTag(new NBTTagFloat(f));
        }

        return nbttaglist;
    }

    public float func_392_h_()
    {
        return height / 2.0F;
    }

    public EntityItem dropItem(int i, int j)
    {
        return dropItemWithOffset(i, j, 0.0F);
    }

    public EntityItem dropItemWithOffset(int i, int j, float f)
    {
        EntityItem entityitem = new EntityItem(worldObj, posX, posY + (double)f, posZ, new ItemStack(i, j));
        entityitem.field_805_c = 10;
        worldObj.entityJoinedWorld(entityitem);
        return entityitem;
    }

    public boolean isEntityAlive()
    {
        return !isDead;
    }

    public boolean func_345_I()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY + (double)func_373_s());
        int k = MathHelper.floor_double(posZ);
        return worldObj.isBlockOpaqueCube(i, j, k);
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        return false;
    }

    public AxisAlignedBB func_383_b_(Entity entity)
    {
        return null;
    }

    public void func_350_p()
    {
        if(ridingEntity.isDead)
        {
            ridingEntity = null;
            return;
        }
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        onUpdate();
        ridingEntity.func_366_i_();
        field_667_e += ridingEntity.rotationYaw - ridingEntity.prevRotationYaw;
        minecartType += ridingEntity.rotationPitch - ridingEntity.prevRotationPitch;
        for(; field_667_e >= 180D; field_667_e -= 360D) { }
        for(; field_667_e < -180D; field_667_e += 360D) { }
        for(; minecartType >= 180D; minecartType -= 360D) { }
        for(; minecartType < -180D; minecartType += 360D) { }
        double d = field_667_e * 0.5D;
        double d1 = minecartType * 0.5D;
        float f = 10F;
        if(d > (double)f)
        {
            d = f;
        }
        if(d < (double)(-f))
        {
            d = -f;
        }
        if(d1 > (double)f)
        {
            d1 = f;
        }
        if(d1 < (double)(-f))
        {
            d1 = -f;
        }
        field_667_e -= d;
        minecartType -= d1;
        rotationYaw += d;
        rotationPitch += d1;
    }

    public void func_366_i_()
    {
        riddenByEntity.setPosition(posX, posY + func_402_h() + riddenByEntity.func_388_v(), posZ);
    }

    public double func_388_v()
    {
        return (double)yOffset;
    }

    public double func_402_h()
    {
        return (double)height * 0.75D;
    }

    public void mountEntity(Entity entity)
    {
        minecartType = 0.0D;
        field_667_e = 0.0D;
        if(entity == null)
        {
            if(ridingEntity != null)
            {
                setLocationAndAngles(ridingEntity.posX, ridingEntity.boundingBox.minY + (double)ridingEntity.height, ridingEntity.posZ, rotationYaw, rotationPitch);
                ridingEntity.riddenByEntity = null;
            }
            ridingEntity = null;
            return;
        }
        if(ridingEntity == entity)
        {
            ridingEntity.riddenByEntity = null;
            ridingEntity = null;
            setLocationAndAngles(entity.posX, entity.boundingBox.minY + (double)entity.height, entity.posZ, rotationYaw, rotationPitch);
            return;
        }
        if(ridingEntity != null)
        {
            ridingEntity.riddenByEntity = null;
        }
        if(entity.riddenByEntity != null)
        {
            entity.riddenByEntity.ridingEntity = null;
        }
        ridingEntity = entity;
        entity.riddenByEntity = this;
    }

    public void setPositionAndRotation2(double d, double d1, double d2, float f, 
            float f1, int i)
    {
        setPosition(d, d1, d2);
        setRotation(f, f1);
    }

    public float func_4035_j_()
    {
        return 0.1F;
    }

    public Vec3D func_4037_H()
    {
        return null;
    }

    public void func_4039_q()
    {
    }

    public void setVelocity(double d, double d1, double d2)
    {
        motionX = d;
        motionY = d1;
        motionZ = d2;
    }

    public void func_9282_a(byte byte0)
    {
    }

    public void func_9280_g()
    {
    }
}
