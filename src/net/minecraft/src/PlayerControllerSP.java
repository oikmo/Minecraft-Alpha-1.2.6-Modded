package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerSP extends PlayerController {
	
	private int curBlockX;
	private int curBlockY;
	private int curBlockZ;
	private float curBlockDamage;
	private float prevBlockDamage;
	private float blockDestroySoundCounter;
	private int blockHitWait;
	
	public PlayerControllerSP(Minecraft minecraft) {
		super(minecraft);
		curBlockX = -1;
		curBlockY = -1;
		curBlockZ = -1;
		curBlockDamage = 0.0F;
		prevBlockDamage = 0.0F;
		blockDestroySoundCounter = 0.0F;
		blockHitWait = 0;
	}

	public void flipPlayer(EntityPlayer entityplayer) {
		entityplayer.rotationYaw = -180F;
	}

	public boolean sendBlockRemoved(int i, int j, int k, int l) {
		int i1 = mc.theWorld.getBlockId(i, j, k);
		int j1 = mc.theWorld.getBlockMetadata(i, j, k);
		boolean flag = super.sendBlockRemoved(i, j, k, l);
		ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();
		boolean flag1 = mc.thePlayer.canHarvestBlock(Block.blocksList[i1]);
		if(itemstack != null) {
			itemstack.hitBlock(i1, i, j, k);
			if(itemstack.stackSize == 0) {
				itemstack.onItemDestroyedByUse(mc.thePlayer);
				mc.thePlayer.destroyCurrentEquippedItem();
			}
		}
		if(flag && flag1) {
			Block.blocksList[i1].harvestBlock(mc.theWorld, i, j, k, j1);
		}
		return flag;
	}

	public void clickBlock(int i, int j, int k, int l) {
		int i1 = mc.theWorld.getBlockId(i, j, k);
		if(i1 > 0 && curBlockDamage == 0.0F) {
			Block.blocksList[i1].onBlockClicked(mc.theWorld, i, j, k, mc.thePlayer);
		}
		if(i1 > 0 && Block.blocksList[i1].getBlockStrength(mc.thePlayer) >= 1.0F) {
			sendBlockRemoved(i, j, k, l);
		}
	}

	public void resetBlockRemoving() {
		curBlockDamage = 0.0F;
		blockHitWait = 0;
	}

	public void sendBlockRemoving(int i, int j, int k, int l) {
		if(blockHitWait > 0) {
			blockHitWait--;
			return;
		}
		if(i == curBlockX && j == curBlockY && k == curBlockZ) {
			int i1 = mc.theWorld.getBlockId(i, j, k);
			if(i1 == 0) {
				return;
			}
			Block block = Block.blocksList[i1];
			curBlockDamage += block.getBlockStrength(mc.thePlayer);
			if(blockDestroySoundCounter % 4F == 0.0F && block != null) {
				mc.sndManager.playSound(block.stepSound.getStepSound(), (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, (block.stepSound.getVolume() + 1.0F) / 8F, block.stepSound.getPitch() * 0.5F);
			}
			blockDestroySoundCounter++;
			if(curBlockDamage >= 1.0F) {
				sendBlockRemoved(i, j, k, l);
				curBlockDamage = 0.0F;
				prevBlockDamage = 0.0F;
				blockDestroySoundCounter = 0.0F;
				blockHitWait = 5;
			}
		} else {
			curBlockDamage = 0.0F;
			prevBlockDamage = 0.0F;
			blockDestroySoundCounter = 0.0F;
			curBlockX = i;
			curBlockY = j;
			curBlockZ = k;
		}
	}

	public void setPartialTime(float f) {
		if(curBlockDamage <= 0.0F) {
			mc.ingameGUI.damageGuiPartialTime = 0.0F;
			mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float f1 = prevBlockDamage + (curBlockDamage - prevBlockDamage) * f;
			mc.ingameGUI.damageGuiPartialTime = f1;
			mc.renderGlobal.damagePartialTime = f1;
		}
	}

	public float getBlockReachDistance() {
		return 4F;
	}

	public void onWorldChange(World world) {
		super.onWorldChange(world);
	}

	public void updateController() {
		prevBlockDamage = curBlockDamage;
		mc.sndManager.playRandomMusicIfReady();
	}
}