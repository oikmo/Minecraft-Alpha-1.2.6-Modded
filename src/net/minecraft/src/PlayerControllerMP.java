package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerMP extends PlayerController {

	private int currentBlockX;
	private int currentBlockY;
	private int currentblockZ;
	private float curBlockDamageMP;
	private float prevBlockDamageMP;
	private float stepSoundTickCounter;
	private int blockHitDelay;
	private boolean isHittingBlock;
	private NetClientHandler netClientHandler;
	private int currentPlayerItem;
	
	public PlayerControllerMP(Minecraft minecraft, NetClientHandler netClientHandler) {
		super(minecraft);
		currentBlockX = -1;
		currentBlockY = -1;
		currentblockZ = -1;
		curBlockDamageMP = 0.0F;
		prevBlockDamageMP = 0.0F;
		stepSoundTickCounter = 0.0F;
		blockHitDelay = 0;
		isHittingBlock = false;
		currentPlayerItem = 0;
		this.netClientHandler = netClientHandler;
	}

	public void flipPlayer(EntityPlayer entityplayer) {
		entityplayer.rotationYaw = -180F;
	}

	public boolean sendBlockRemoved(int i, int j, int k, int l) {
		netClientHandler.addToSendQueue(new Packet14BlockDig(3, i, j, k, l));
		int i1 = mc.theWorld.getBlockId(i, j, k);
		boolean flag = super.sendBlockRemoved(i, j, k, l);
		ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();
		if(itemstack != null) {
			itemstack.hitBlock(i1, i, j, k);
			if(itemstack.stackSize == 0) {
				itemstack.onItemDestroyedByUse(mc.thePlayer);
				mc.thePlayer.destroyCurrentEquippedItem();
			}
		}
		return flag;
	}

	public void clickBlock(int i, int j, int k, int l) {
		isHittingBlock = true;
		netClientHandler.addToSendQueue(new Packet14BlockDig(0, i, j, k, l));
		int i1 = mc.theWorld.getBlockId(i, j, k);
		if(i1 > 0 && curBlockDamageMP == 0.0F) {
			Block.blocksList[i1].onBlockClicked(mc.theWorld, i, j, k, mc.thePlayer);
		}
		if(i1 > 0 && Block.blocksList[i1].getBlockStrength(mc.thePlayer) >= 1.0F) {
			sendBlockRemoved(i, j, k, l);
		}
	}

	public void resetBlockRemoving() {
		if(!isHittingBlock) {
			return;
		} else {
			isHittingBlock = false;
			netClientHandler.addToSendQueue(new Packet14BlockDig(2, 0, 0, 0, 0));
			curBlockDamageMP = 0.0F;
			blockHitDelay = 0;
			return;
		}
	}

	public void sendBlockRemoving(int i, int j, int k, int l) {
		isHittingBlock = true;
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet14BlockDig(1, i, j, k, l));
		if(blockHitDelay > 0) {
			blockHitDelay--;
			return;
		}
		if(i == currentBlockX && j == currentBlockY && k == currentblockZ) {
			int i1 = mc.theWorld.getBlockId(i, j, k);
			if(i1 == 0) {
				return;
			}
			Block block = Block.blocksList[i1];
			curBlockDamageMP += block.getBlockStrength(mc.thePlayer);
			if(stepSoundTickCounter % 4F == 0.0F && block != null) {
				mc.sndManager.playSound(block.stepSound.getStepSound(), (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, (block.stepSound.getVolume() + 1.0F) / 8F, block.stepSound.getPitch() * 0.5F);
			}
			stepSoundTickCounter++;
			if(curBlockDamageMP >= 1.0F) {
				sendBlockRemoved(i, j, k, l);
				curBlockDamageMP = 0.0F;
				prevBlockDamageMP = 0.0F;
				stepSoundTickCounter = 0.0F;
				blockHitDelay = 5;
			}
		} else {
			curBlockDamageMP = 0.0F;
			prevBlockDamageMP = 0.0F;
			stepSoundTickCounter = 0.0F;
			currentBlockX = i;
			currentBlockY = j;
			currentblockZ = k;
		}
	}

	public void setPartialTime(float f) {
		if(curBlockDamageMP <= 0.0F) {
			mc.ingameGUI.damageGuiPartialTime = 0.0F;
			mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float f1 = prevBlockDamageMP + (curBlockDamageMP - prevBlockDamageMP) * f;
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
		syncCurrentPlayItem();
		prevBlockDamageMP = curBlockDamageMP;
		mc.sndManager.playRandomMusicIfReady();
	}

	private void syncCurrentPlayItem() {
		ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();
		int i = 0;
		if(itemstack != null) {
			i = itemstack.itemID;
		}
		if(i != currentPlayerItem) {
			currentPlayerItem = i;
			netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(0, currentPlayerItem));
		}
	}

	public boolean sendPlaceBlock(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l) {
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet15Place(itemstack == null ? -1 : itemstack.itemID, i, j, k, l));
		return super.sendPlaceBlock(entityplayer, world, itemstack, i, j, k, l);
	}

	public boolean sendUseItem(EntityPlayer entityplayer, World world, ItemStack itemstack) {
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet15Place(itemstack == null ? -1 : itemstack.itemID, -1, -1, -1, 255));
		return super.sendUseItem(entityplayer, world, itemstack);
	}

	public EntityPlayer createPlayer(World world) {
		return new EntityClientPlayerMP(mc, world, mc.session, netClientHandler);
	}

	public void attackEntity(EntityPlayer entityplayer, Entity entity) {
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet7(entityplayer.entityID, entity.entityID, 1));
		entityplayer.attackTargetEntityWithCurrentItem(entity);
	}

	public void interactWithEntity(EntityPlayer entityplayer, Entity entity) {
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet7(entityplayer.entityID, entity.entityID, 0));
		entityplayer.useCurrentItemOnEntity(entity);
	}
}
