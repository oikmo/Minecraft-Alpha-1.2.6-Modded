package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityLiving extends Entity
{

    public EntityLiving(World world)
    {
        super(world);
        heartsHalvesLife = 20;
        renderYawOffset = 0.0F;
        prevRenderYawOffset = 0.0F;
        field_9358_y = true;
        texture = "/mob/char.png";
        field_9355_A = true;
        field_9353_B = 0.0F;
        entityType = null;
        field_9349_D = 1.0F;
        field_9347_E = 0;
        field_9345_F = 0.0F;
        field_9343_G = false;
        field_9331_N = 0.0F;
        deathTime = 0;
        attackTime = 0;
        field_9327_S = false;
        field_9326_T = -1;
        field_9325_U = (float)(Math.random() * 0.89999997615814209D + 0.10000000149011612D);
        field_9348_ae = 0.0F;
        naturalArmorRating = 0;
        field_9344_ag = 0;
        isJumping = false;
        field_9334_al = 0.0F;
        field_9333_am = 0.7F;
        field_4127_c = 0;
        health = 10;
        field_618_ad = true;
        field_9363_r = (float)(Math.random() + 1.0D) * 0.01F;
        setPosition(posX, posY, posZ);
        field_9365_p = (float)Math.random() * 12398F;
        rotationYaw = (float)(Math.random() * 3.1415927410125732D * 2D);
        field_9364_q = 1.0F;
        field_9286_aZ = 0.5F;
    }

    public boolean canEntityBeSeen(Entity entity)
    {
        return worldObj.rayTraceBlocks(Vec3D.createVector(posX, posY + (double)func_373_s(), posZ), Vec3D.createVector(entity.posX, entity.posY + (double)entity.func_373_s(), entity.posZ)) == null;
    }

    public String getEntityTexture()
    {
        return texture;
    }

    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    public boolean canBePushed()
    {
        return !isDead;
    }

    public float func_373_s()
    {
        return height * 0.85F;
    }

    public int func_421_b()
    {
        return 80;
    }

    public void onEntityUpdate()
    {
        prevSwingProgress = swingProgress;
        super.onEntityUpdate();
        if(rand.nextInt(1000) < field_4121_a++)
        {
            field_4121_a = -func_421_b();
            String s = getLivingSound();
            if(s != null)
            {
                worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }
        }
        if(isEntityAlive() && func_345_I())
        {
            canAttackEntity(null, 1);
        }
        if(isImmuneToFire || worldObj.multiplayerWorld)
        {
            fire = 0;
        }
        if(isEntityAlive() && isInsideOfMaterial(Material.water))
        {
            air--;
            if(air == -20)
            {
                air = 0;
                for(int i = 0; i < 8; i++)
                {
                    float f = rand.nextFloat() - rand.nextFloat();
                    float f1 = rand.nextFloat() - rand.nextFloat();
                    float f2 = rand.nextFloat() - rand.nextFloat();
                    worldObj.spawnParticle("bubble", posX + (double)f, posY + (double)f1, posZ + (double)f2, motionX, motionY, motionZ);
                }

                canAttackEntity(null, 2);
            }
            fire = 0;
        } else
        {
            air = field_9308_bh;
        }
        field_9329_Q = field_9328_R;
        if(attackTime > 0)
        {
            attackTime--;
        }
        if(hurtTime > 0)
        {
            hurtTime--;
        }
        if(heartsLife > 0)
        {
            heartsLife--;
        }
        if(health <= 0)
        {
            deathTime++;
            if(deathTime > 20)
            {
                func_6392_F();
                setEntityDead();
                for(int j = 0; j < 20; j++)
                {
                    double d = rand.nextGaussian() * 0.02D;
                    double d1 = rand.nextGaussian() * 0.02D;
                    double d2 = rand.nextGaussian() * 0.02D;
                    worldObj.spawnParticle("explode", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
                }

            }
        }
        field_9359_x = field_9360_w;
        prevRenderYawOffset = renderYawOffset;
        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
    }

    public void spawnExplosionParticle()
    {
        for(int i = 0; i < 20; i++)
        {
            double d = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            double d3 = 10D;
            worldObj.spawnParticle("explode", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - d * d3, (posY + (double)(rand.nextFloat() * height)) - d1 * d3, (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - d2 * d3, d, d1, d2);
        }

    }

    public void func_350_p()
    {
        super.func_350_p();
        field_9362_u = field_9361_v;
        field_9361_v = 0.0F;
    }

    public void setPositionAndRotation2(double d, double d1, double d2, float f, 
            float f1, int i)
    {
        yOffset = 0.0F;
        field_9323_Z = d;
        field_9356_aa = d1;
        field_9354_ab = d2;
        field_9352_ac = f;
        field_9350_ad = f1;
        field_9324_Y = i;
    }

    public void onUpdate()
    {
        super.onUpdate();
        onLivingUpdate();
        double d = posX - prevPosX;
        double d1 = posZ - prevPosZ;
        float f = MathHelper.sqrt_double(d * d + d1 * d1);
        float f1 = renderYawOffset;
        float f2 = 0.0F;
        field_9362_u = field_9361_v;
        float f3 = 0.0F;
        if(f > 0.05F)
        {
            f3 = 1.0F;
            f2 = f * 3F;
            f1 = ((float)Math.atan2(d1, d) * 180F) / 3.141593F - 90F;
        }
        if(swingProgress > 0.0F)
        {
            f1 = rotationYaw;
        }
        if(!onGround)
        {
            f3 = 0.0F;
        }
        field_9361_v = field_9361_v + (f3 - field_9361_v) * 0.3F;
        float f4;
        for(f4 = f1 - renderYawOffset; f4 < -180F; f4 += 360F) { }
        for(; f4 >= 180F; f4 -= 360F) { }
        renderYawOffset += f4 * 0.3F;
        float f5;
        for(f5 = rotationYaw - renderYawOffset; f5 < -180F; f5 += 360F) { }
        for(; f5 >= 180F; f5 -= 360F) { }
        boolean flag = f5 < -90F || f5 >= 90F;
        if(f5 < -75F)
        {
            f5 = -75F;
        }
        if(f5 >= 75F)
        {
            f5 = 75F;
        }
        renderYawOffset = rotationYaw - f5;
        if(f5 * f5 > 2500F)
        {
            renderYawOffset += f5 * 0.2F;
        }
        if(flag)
        {
            f2 *= -1F;
        }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        for(; renderYawOffset - prevRenderYawOffset < -180F; prevRenderYawOffset -= 360F) { }
        for(; renderYawOffset - prevRenderYawOffset >= 180F; prevRenderYawOffset += 360F) { }
        for(; rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        field_9360_w += f2;
    }

    protected void setSize(float f, float f1)
    {
        super.setSize(f, f1);
    }

    public void heal(int i)
    {
        if(health <= 0)
        {
            return;
        }
        health += i;
        if(health > 20)
        {
            health = 20;
        }
        heartsLife = heartsHalvesLife / 2;
    }
    
    public int getEntityHealth() {
		return this.health;
	}
    
    public boolean canAttackEntity(Entity entity, int i)
    {
        if(worldObj.multiplayerWorld)
        {
            return false;
        }
        field_9344_ag = 0;
        if(health <= 0)
        {
            return false;
        }
        field_704_R = 1.5F;
        boolean flag = true;
        if((float)heartsLife > (float)heartsHalvesLife / 2.0F)
        {
            if(i <= naturalArmorRating)
            {
                return false;
            }
            damageEntity(i - naturalArmorRating);
            naturalArmorRating = i;
            flag = false;
        } else
        {
            naturalArmorRating = i;
            prevHealth = health;
            heartsLife = heartsHalvesLife;
            damageEntity(i);
            hurtTime = maxHurtTime = 10;
        }
        field_9331_N = 0.0F;
        if(flag)
        {
            worldObj.func_9425_a(this, (byte)2);
            func_9281_M();
            if(entity != null)
            {
                double d = entity.posX - posX;
                double d1;
                for(d1 = entity.posZ - posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
                {
                    d = (Math.random() - Math.random()) * 0.01D;
                }

                field_9331_N = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - rotationYaw;
                func_434_a(entity, i, d, d1);
            } else
            {
                field_9331_N = (int)(Math.random() * 2D) * 180;
            }
        }
        if(health <= 0)
        {
            if(flag)
            {
                worldObj.playSoundAtEntity(this, getDeathSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }
            onDeath(entity);
        } else
        if(flag)
        {
            worldObj.playSoundAtEntity(this, getHurtSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
        }
        return true;
    }

    public void func_9280_g()
    {
        hurtTime = maxHurtTime = 10;
        field_9331_N = 0.0F;
    }

    protected void damageEntity(int i)
    {
        health -= i;
    }

    protected float getSoundVolume()
    {
        return 1.0F;
    }

    protected String getLivingSound()
    {
        return null;
    }

    protected String getHurtSound()
    {
        return "random.hurt";
    }

    protected String getDeathSound()
    {
        return "random.hurt";
    }

    public void func_434_a(Entity entity, int i, double d, double d1)
    {
        float f = MathHelper.sqrt_double(d * d + d1 * d1);
        float f1 = 0.4F;
        motionX /= 2D;
        motionY /= 2D;
        motionZ /= 2D;
        motionX -= (d / (double)f) * (double)f1;
        motionY += 0.40000000596046448D;
        motionZ -= (d1 / (double)f) * (double)f1;
        if(motionY > 0.40000000596046448D)
        {
            motionY = 0.40000000596046448D;
        }
    }

    public void onDeath(Entity entity)
    {
        if(field_9347_E > 0 && entity != null)
        {
            entity.addToPlayerScore(this, field_9347_E);
        }
        field_9327_S = true;
        if(!worldObj.multiplayerWorld)
        {
            int i = getDropItemId();
            if(i > 0)
            {
                int j = rand.nextInt(3);
                for(int k = 0; k < j; k++)
                {
                    dropItem(i, 1);
                }

            }
        }
        worldObj.func_9425_a(this, (byte)3);
    }

    protected int getDropItemId()
    {
        return 0;
    }

    protected void fall(float f)
    {
        int i = (int)Math.ceil(f - 3F);
        if(i > 0)
        {
            canAttackEntity(null, i);
            int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.20000000298023224D - (double)yOffset), MathHelper.floor_double(posZ));
            if(j > 0)
            {
                StepSound stepsound = Block.blocksList[j].stepSound;
                worldObj.playSoundAtEntity(this, stepsound.func_1145_d(), stepsound.func_1147_b() * 0.5F, stepsound.func_1144_c() * 0.75F);
            }
        }
    }

    public void moveEntityWithHeading(float f, float f1)
    {
        if(handleWaterMovement())
        {
            double d = posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.80000001192092896D;
            motionY *= 0.80000001192092896D;
            motionZ *= 0.80000001192092896D;
            motionY -= 0.02D;
            if(field_9297_aI && func_403_b(motionX, ((motionY + 0.60000002384185791D) - posY) + d, motionZ))
            {
                motionY = 0.30000001192092896D;
            }
        } else
        if(handleLavaMovement())
        {
            double d1 = posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
            motionY -= 0.02D;
            if(field_9297_aI && func_403_b(motionX, ((motionY + 0.60000002384185791D) - posY) + d1, motionZ))
            {
                motionY = 0.30000001192092896D;
            }
        } else
        {
            float f2 = 0.91F;
            if(onGround)
            {
                f2 = 0.5460001F;
                int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if(i > 0)
                {
                    f2 = Block.blocksList[i].slipperiness * 0.91F;
                }
            }
            float f3 = 0.1627714F / (f2 * f2 * f2);
            float var5 = this.onGround ? this.landMovementFactor * f3 : this.jumpMovementFactor;
            moveFlying(f, f1, var5);
            f2 = 0.91F;
            
            if(onGround)
            {
                f2 = 0.5460001F;
                int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if(j > 0)
                {
                    f2 = Block.blocksList[j].slipperiness * 0.91F;
                }
            }
            if(isOnLadder())
            {
                fallDistance = 0.0F;
                if(motionY < -0.14999999999999999D)
                {
                    motionY = -0.14999999999999999D;
                }
            }
            moveEntity(motionX, motionY, motionZ);
            if(field_9297_aI && isOnLadder())
            {
                motionY = 0.20000000000000001D;
            }
            motionY -= 0.080000000000000002D;
            motionY *= 0.98000001907348633D;
            motionX *= f2;
            motionZ *= f2;
        }
        field_705_Q = field_704_R;
        double d2 = posX - prevPosX;
        double d3 = posZ - prevPosZ;
        float f4 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4F;
        if(f4 > 1.0F)
        {
            f4 = 1.0F;
        }
        field_704_R += (f4 - field_704_R) * 0.4F;
        field_703_S += field_704_R;
    }

    public boolean isOnLadder()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        return worldObj.getBlockId(i, j, k) == Block.ladder.blockID || worldObj.getBlockId(i, j + 1, k) == Block.ladder.blockID;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("Health", (short)health);
        nbttagcompound.setShort("HurtTime", (short)hurtTime);
        nbttagcompound.setShort("DeathTime", (short)deathTime);
        nbttagcompound.setShort("AttackTime", (short)attackTime);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        health = nbttagcompound.getShort("Health");
        if(!nbttagcompound.hasKey("Health"))
        {
            health = 10;
        }
        hurtTime = nbttagcompound.getShort("HurtTime");
        deathTime = nbttagcompound.getShort("DeathTime");
        attackTime = nbttagcompound.getShort("AttackTime");
    }

    public boolean isEntityAlive()
    {
        return !isDead && health > 0;
    }

    public void onLivingUpdate()
    {
        if(field_9324_Y > 0)
        {
            double d = posX + (field_9323_Z - posX) / (double)field_9324_Y;
            double d1 = posY + (field_9356_aa - posY) / (double)field_9324_Y;
            double d2 = posZ + (field_9354_ab - posZ) / (double)field_9324_Y;
            double d3;
            for(d3 = field_9352_ac - (double)rotationYaw; d3 < -180D; d3 += 360D) { }
            for(; d3 >= 180D; d3 -= 360D) { }
            rotationYaw += d3 / (double)field_9324_Y;
            rotationPitch += (field_9350_ad - (double)rotationPitch) / (double)field_9324_Y;
            field_9324_Y--;
            setPosition(d, d1, d2);
            setRotation(rotationYaw, rotationPitch);
        }
        if(health <= 0)
        {
            isJumping = false;
            moveStrafing = 0.0F;
            moveForward = 0.0F;
            randomYawVelocity = 0.0F;
        } else
        if(!field_9343_G)
        {
            updateEntityActionState();
        }
        boolean flag = handleWaterMovement();
        boolean flag1 = handleLavaMovement();
        if(isJumping)
        {
            if(flag)
            {
                motionY += 0.039999999105930328D;
            } else
            if(flag1)
            {
                motionY += 0.039999999105930328D;
            } else
            if(onGround)
            {
                func_424_C();
            }
        }
        moveStrafing *= 0.98F;
        moveForward *= 0.98F;
        randomYawVelocity *= 0.9F;
        float var15 = this.landMovementFactor;
        moveEntityWithHeading(moveStrafing, moveForward);
		this.landMovementFactor = var15;
        List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expands(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        if(list != null && list.size() > 0)
        {
            for(int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);
                if(entity.canBePushed())
                {
                    entity.applyEntityCollision(this);
                }
            }

        }
    }

    protected void func_424_C()
    {
        motionY = 0.41999998688697815D;
    }

    protected void updateEntityActionState()
    {
        field_9344_ag++;
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, -1D);
        if(entityplayer != null)
        {
            double d = ((Entity) (entityplayer)).posX - posX;
            double d1 = ((Entity) (entityplayer)).posY - posY;
            double d2 = ((Entity) (entityplayer)).posZ - posZ;
            double d3 = d * d + d1 * d1 + d2 * d2;
            if(d3 > 16384D)
            {
                setEntityDead();
            }
            if(field_9344_ag > 600 && rand.nextInt(800) == 0)
            {
                if(d3 < 1024D)
                {
                    field_9344_ag = 0;
                } else
                {
                    setEntityDead();
                }
            }
        }
        moveStrafing = 0.0F;
        moveForward = 0.0F;
        float f = 8F;
        if(rand.nextFloat() < 0.02F)
        {
            EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, f);
            if(entityplayer1 != null)
            {
                field_4120_b = entityplayer1;
                field_4127_c = 10 + rand.nextInt(20);
            } else
            {
                randomYawVelocity = (rand.nextFloat() - 0.5F) * 20F;
            }
        }
        if(field_4120_b != null)
        {
            faceEntity(field_4120_b, 10F);
            if(field_4127_c-- <= 0 || field_4120_b.isDead || field_4120_b.getDistanceSqToEntity(this) > (double)(f * f))
            {
                field_4120_b = null;
            }
        } else
        {
            if(rand.nextFloat() < 0.05F)
            {
                randomYawVelocity = (rand.nextFloat() - 0.5F) * 20F;
            }
            rotationYaw += randomYawVelocity;
            rotationPitch = field_9334_al;
        }
        boolean flag = handleWaterMovement();
        boolean flag1 = handleLavaMovement();
        if(flag || flag1)
        {
            isJumping = rand.nextFloat() < 0.8F;
        }
    }

    public void faceEntity(Entity entity, float f)
    {
        double d = entity.posX - posX;
        double d2 = entity.posZ - posZ;
        double d1;
        if(entity instanceof EntityLiving)
        {
            EntityLiving entityliving = (EntityLiving)entity;
            d1 = (entityliving.posY + (double)entityliving.func_373_s()) - (posY + (double)func_373_s());
        } else
        {
            d1 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2D - (posY + (double)func_373_s());
        }
        double d3 = MathHelper.sqrt_double(d * d + d2 * d2);
        float f1 = (float)((Math.atan2(d2, d) * 180D) / 3.1415927410125732D) - 90F;
        float f2 = (float)((Math.atan2(d1, d3) * 180D) / 3.1415927410125732D);
        rotationPitch = -updateRotation(rotationPitch, f2, f);
        rotationYaw = updateRotation(rotationYaw, f1, f);
    }

    private float updateRotation(float f, float f1, float f2)
    {
        float f3;
        for(f3 = f1 - f; f3 < -180F; f3 += 360F) { }
        for(; f3 >= 180F; f3 -= 360F) { }
        if(f3 > f2)
        {
            f3 = f2;
        }
        if(f3 < -f2)
        {
            f3 = -f2;
        }
        return f + f3;
    }

    public void func_6392_F()
    {
    }

    public boolean getCanSpawnHere()
    {
        return worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.getIsAnyLiquid(boundingBox);
    }

    protected void kill()
    {
        canAttackEntity(null, 4);
    }

    public float getSwingProgress(float f)
    {
        float f1 = swingProgress - prevSwingProgress;
        if(f1 < 0.0F)
        {
            f1++;
        }
        return prevSwingProgress + f1 * f;
    }

    public Vec3D getPosition(float f)
    {
        if(f == 1.0F)
        {
            return Vec3D.createVector(posX, posY, posZ);
        } else
        {
            double d = prevPosX + (posX - prevPosX) * (double)f;
            double d1 = prevPosY + (posY - prevPosY) * (double)f;
            double d2 = prevPosZ + (posZ - prevPosZ) * (double)f;
            return Vec3D.createVector(d, d1, d2);
        }
    }

    public Vec3D func_4037_H()
    {
        return getLook(1.0F);
    }

    public Vec3D getLook(float f)
    {
        if(f == 1.0F)
        {
            float f1 = MathHelper.cos(-rotationYaw * 0.01745329F - 3.141593F);
            float f3 = MathHelper.sin(-rotationYaw * 0.01745329F - 3.141593F);
            float f5 = -MathHelper.cos(-rotationPitch * 0.01745329F);
            float f7 = MathHelper.sin(-rotationPitch * 0.01745329F);
            return Vec3D.createVector(f3 * f5, f7, f1 * f5);
        } else
        {
            float f2 = prevRotationPitch + (rotationPitch - prevRotationPitch) * f;
            float f4 = prevRotationYaw + (rotationYaw - prevRotationYaw) * f;
            float f6 = MathHelper.cos(-f4 * 0.01745329F - 3.141593F);
            float f8 = MathHelper.sin(-f4 * 0.01745329F - 3.141593F);
            float f9 = -MathHelper.cos(-f2 * 0.01745329F);
            float f10 = MathHelper.sin(-f2 * 0.01745329F);
            return Vec3D.createVector(f8 * f9, f10, f6 * f9);
        }
    }

    public MovingObjectPosition rayTrace(double d, float f)
    {
        Vec3D vec3d = getPosition(f);
        Vec3D vec3d1 = getLook(f);
        Vec3D vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
        return worldObj.rayTraceBlocks(vec3d, vec3d2);
    }

    public int func_6391_i()
    {
        return 4;
    }

    public ItemStack getHeldItem()
    {
        return null;
    }

    public void func_9282_a(byte byte0)
    {
        if(byte0 == 2)
        {
            field_704_R = 1.5F;
            heartsLife = heartsHalvesLife;
            hurtTime = maxHurtTime = 10;
            field_9331_N = 0.0F;
            worldObj.playSoundAtEntity(this, getHurtSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            canAttackEntity(null, 0);
        } else
        if(byte0 == 3)
        {
            worldObj.playSoundAtEntity(this, getDeathSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            health = 0;
            onDeath(null);
        } else
        {
            super.func_9282_a(byte0);
        }
    }

    public int heartsHalvesLife = 20;
    public float field_9365_p;
    public float field_9364_q;
    public float field_9363_r;
    public float renderYawOffset;
    public float prevRenderYawOffset;
    protected float field_9362_u;
    protected float field_9361_v;
    protected float field_9360_w;
    protected float field_9359_x;
    protected boolean field_9358_y;
    protected String texture;
    protected boolean field_9355_A;
    protected float field_9353_B;
    protected String entityType;
    protected float field_9349_D;
    protected int field_9347_E;
    protected float field_9345_F;
    public boolean field_9343_G;
    public float prevSwingProgress;
    public float swingProgress;
    public int health;
    public int prevHealth;
    private int field_4121_a;
    public int hurtTime;
    public int maxHurtTime;
    public float field_9331_N;
    public int deathTime;
    public int attackTime;
    public float field_9329_Q;
    public float field_9328_R;
    protected boolean field_9327_S;
    public int field_9326_T;
    public float field_9325_U;
    public float field_705_Q;
    public float field_704_R;
    public float field_703_S;
    protected int field_9324_Y;
    protected double field_9323_Z;
    protected double field_9356_aa;
    protected double field_9354_ab;
    protected double field_9352_ac;
    protected double field_9350_ad;
    float field_9348_ae;
    protected int naturalArmorRating;
    protected int field_9344_ag;
    protected float moveStrafing;
    protected float moveForward;
    protected float randomYawVelocity;
    protected boolean isJumping;
    protected float field_9334_al;
    protected float field_9333_am;
    private Entity field_4120_b;
    private int field_4127_c;
    public float landMovementFactor = 0.1F;
	public float jumpMovementFactor = 0.02F;
}
