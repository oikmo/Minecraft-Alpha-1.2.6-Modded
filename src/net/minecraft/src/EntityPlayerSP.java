package net.minecraft.src;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class EntityPlayerSP extends EntityPlayer
{

    public EntityPlayerSP(Minecraft minecraft, World world, Session session, int i)
    {
        super(world);
        timeUntilPortal = 20;
        inPortal = false;
        mc = minecraft;
        dimension = i;
        name = minecraft.gameSettings.name;
        UUID = UUIDRetriever.GetUUIDFromName(name);
        if(session != null && session.inventory != null && session.inventory.length() > 0)
        {
        	skinUrl = UUIDRetriever.GetSkinLinkFromUUID(UUIDRetriever.GetUUIDFromName(name));
			System.out.println((new StringBuilder()).append("Loading texture ").append(skinUrl).toString());
        }
        
    }

    public void updateEntityActionState()
    {
        super.updateEntityActionState();
        moveStrafing = movementInput.moveStrafe;
        moveForward = movementInput.field_1173_b;
        isJumping = movementInput.jump;
    }
    
    public float getFOVMultiplier() {
		float var1 = 1.0F;

		var1 *= (this.landMovementFactor / this.speedOnGround + 1.0F) / 2.0F;
		if(var1 == 1) {
			return var1;
		} else {
			return (float) (var1 - 0.2f);
		}
		//return var1;
	}
    
    public boolean isWASD() {
    	return Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_A);
    }
    
    public void onLivingUpdate()
    {
    	
    	if(Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.keyCode) && isWASD()) {
    		this.setSprinting(true);
    	} else if(!isWASD() && this.isSprinting()) {
    		this.setSprinting(false);
    	}
    	
        prevTimeInPortal = timeInPortal;
        if(inPortal)
        {
            if(timeInPortal == 0.0F)
            {
                mc.sndManager.playSoundFX("portal.trigger", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            }
            timeInPortal += 0.0125F;
            if(timeInPortal >= 1.0F)
            {
                timeInPortal = 1.0F;
                timeUntilPortal = 10;
                mc.sndManager.playSoundFX("portal.travel", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                mc.usePortal();
            }
            inPortal = false;
        } else
        {
            if(timeInPortal > 0.0F)
            {
                timeInPortal -= 0.05F;
            }
            if(timeInPortal < 0.0F)
            {
                timeInPortal = 0.0F;
            }
        }
        if(timeUntilPortal > 0)
        {
            timeUntilPortal--;
        }
        movementInput.updatePlayerMoveState(this);
        if(movementInput.sneak && ySize < 0.2F)
        {
            ySize = 0.2F;
        }
        super.onLivingUpdate();
    } 

    public void func_458_k()
    {
        movementInput.func_798_a();
    }

    public void func_460_a(int i, boolean flag)
    {
        movementInput.func_796_a(i, flag);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Score", score);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        score = nbttagcompound.getInteger("Score");
    }

    public void displayGUIChest(IInventory iinventory)
    {
        mc.displayGuiScreen(new GuiChest(inventory, iinventory));
    }

    public void displayGUIEditSign(TileEntitySign tileentitysign)
    {
        mc.displayGuiScreen(new GuiEditSign(tileentitysign));
    }

    public void displayWorkbenchGUI()
    {
        mc.displayGuiScreen(new GuiCrafting(inventory));
    }

    public void displayGUIFurnace(TileEntityFurnace tileentityfurnace)
    {
        mc.displayGuiScreen(new GuiFurnace(inventory, tileentityfurnace));
    }

    public void func_443_a_(Entity entity, int i)
    {
        mc.effectRenderer.addEffect(new EntityPickupFX(mc.theWorld, entity, this, -0.5F));
    }

    public int getPlayerArmorValue()
    {
        return inventory.getTotalArmorValue();
    }

    public void useCurrentItemOnEntity(Entity entity)
    {
        if(entity.interact(this))
        {
            return;
        }
        ItemStack itemstack = getCurrentEquippedItem();
        if(itemstack != null && (entity instanceof EntityLiving))
        {
            itemstack.useItemOnEntity((EntityLiving)entity);
            if(itemstack.stackSize <= 0)
            {
                itemstack.onItemDestroyedByUse(this);
                destroyCurrentEquippedItem();
            }
        }
    }

    public void sendChatMessage(String s)
    {
    }

    public void func_6420_o()
    {
    }

    public boolean isSneaking()
    {
        return movementInput.sneak;
    }

    public void func_4039_q()
    {
        if(timeUntilPortal > 0)
        {
            timeUntilPortal = 10;
            return;
        } else
        {
            inPortal = true;
            return;
        }
    }

    public void setHealth(int i)
    {
        int j = health - i;
        if(j <= 0)
        {
            health = i;
        } else
        {
            naturalArmorRating = j;
            prevHealth = health;
            heartsLife = heartsHalvesLife;
            damageEntity(j);
            hurtTime = maxHurtTime = 10;
        }
    }
    /*
     * public void setHealth(int var1) {
		int f = this.getEntityHealth() - var1;
		if(j <= 0) {
			this.setEntityHealth(var1);
			if(j < 0) {
				this.heartsLife = this.heartsHalvesLife / 2;
			}
		} else {
			this.naturalArmorRating = j;
			this.setEntityHealth(this.getEntityHealth());
			this.heartsLife = this.heartsHalvesLife;
			this.damageEntity(DamageSource.generic, j);
			this.hurtTime = this.maxHurtTime = 10;
		}

	}
     */

    public void respawnPlayer()
    {
        mc.respawn();
    }

    public MovementInput movementInput;
    private Minecraft mc;
    public int timeUntilPortal;
    private boolean inPortal;
    public float timeInPortal;
    public float prevTimeInPortal;
}
