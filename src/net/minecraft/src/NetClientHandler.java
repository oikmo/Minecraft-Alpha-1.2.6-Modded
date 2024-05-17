package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.net.*;
import java.util.Random;

import net.minecraft.client.Minecraft;

public class NetClientHandler extends NetHandler
{

    public NetClientHandler(Minecraft minecraft, String s, int i) throws UnknownHostException, IOException
    {
        disconnected = false;
        field_1210_g = false;
        rand = new Random();
        mc = minecraft;
        Socket socket = new Socket(InetAddress.getByName(s), i);
        netManager = new NetworkManager(socket, "Client", this);
    }

    public void processReadPackets()
    {
        if(disconnected)
        {
            return;
        } else
        {
            netManager.processReadPackets();
            return;
        }
    }

    public void handleLogin(Packet1Login packet1login)
    {
        mc.playerController = new PlayerControllerMP(mc, this);
        worldClient = new WorldClient(this, packet1login.field_4074_d, packet1login.field_4073_e);
        worldClient.multiplayerWorld = true;
        mc.changeWorld1(worldClient);
        mc.displayGuiScreen(new GuiDownloadTerrain(this));
        mc.thePlayer.entityID = packet1login.protocolVersion;
        System.out.println((new StringBuilder()).append("clientEntityId: ").append(packet1login.protocolVersion).toString());
    }

    public void handlePickupSpawn(Packet21PickupSpawn packet21pickupspawn)
    {
        double d = (double)packet21pickupspawn.xPosition / 32D;
        double d1 = (double)packet21pickupspawn.yPosition / 32D;
        double d2 = (double)packet21pickupspawn.zPosition / 32D;
        EntityItem entityitem = new EntityItem(worldClient, d, d1, d2, new ItemStack(packet21pickupspawn.itemId, packet21pickupspawn.count));
        entityitem.motionX = (double)packet21pickupspawn.rotation / 128D;
        entityitem.motionY = (double)packet21pickupspawn.pitch / 128D;
        entityitem.motionZ = (double)packet21pickupspawn.roll / 128D;
        entityitem.field_9303_br = packet21pickupspawn.xPosition;
        entityitem.field_9302_bs = packet21pickupspawn.yPosition;
        entityitem.field_9301_bt = packet21pickupspawn.zPosition;
        worldClient.func_712_a(packet21pickupspawn.entityId, entityitem);
    }

    public void handleVehicleSpawn(Packet23VehicleSpawn packet23vehiclespawn)
    {
        double d = (double)packet23vehiclespawn.xPosition / 32D;
        double d1 = (double)packet23vehiclespawn.yPosition / 32D;
        double d2 = (double)packet23vehiclespawn.zPosition / 32D;
        Entity obj = null;
        if(packet23vehiclespawn.type == 10)
        {
            obj = new EntityMinecart(worldClient, d, d1, d2, 0);
        }
        if(packet23vehiclespawn.type == 11)
        {
            obj = new EntityMinecart(worldClient, d, d1, d2, 1);
        }
        if(packet23vehiclespawn.type == 12)
        {
            obj = new EntityMinecart(worldClient, d, d1, d2, 2);
        }
        if(packet23vehiclespawn.type == 90)
        {
            obj = new EntityFish(worldClient, d, d1, d2);
        }
        if(packet23vehiclespawn.type == 60)
        {
            obj = new EntityArrow(worldClient, d, d1, d2);
        }
        if(packet23vehiclespawn.type == 61)
        {
            obj = new EntitySnowball(worldClient, d, d1, d2);
        }
        if(packet23vehiclespawn.type == 1)
        {
            obj = new EntityBoat(worldClient, d, d1, d2);
        }
        if(packet23vehiclespawn.type == 50)
        {
            obj = new EntityTNTPrimed(worldClient, d, d1, d2);
        }
        if(obj != null)
        {
            obj.field_9303_br = packet23vehiclespawn.xPosition;
            obj.field_9302_bs = packet23vehiclespawn.yPosition;
            obj.field_9301_bt = packet23vehiclespawn.zPosition;
            obj.rotationYaw = 0.0F;
            obj.rotationPitch = 0.0F;
            obj.entityID = packet23vehiclespawn.entityId;
            worldClient.func_712_a(packet23vehiclespawn.entityId, ((Entity) (obj)));
        }
    }

    public void func_6498_a(Packet28 packet28)
    {
        Entity entity = func_12246_a(packet28.field_6367_a);
        if(entity == null)
        {
            return;
        } else
        {
            entity.setVelocity((double)packet28.field_6366_b / 8000D, (double)packet28.field_6369_c / 8000D, (double)packet28.field_6368_d / 8000D);
            return;
        }
    }

    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet20namedentityspawn)
    {
        double d = (double)packet20namedentityspawn.xPosition / 32D;
        double d1 = (double)packet20namedentityspawn.yPosition / 32D;
        double d2 = (double)packet20namedentityspawn.zPosition / 32D;
        float f = (float)(packet20namedentityspawn.rotation * 360) / 256F;
        float f1 = (float)(packet20namedentityspawn.pitch * 360) / 256F;
        EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(mc.theWorld, packet20namedentityspawn.name);
        entityotherplayermp.field_9303_br = packet20namedentityspawn.xPosition;
        entityotherplayermp.field_9302_bs = packet20namedentityspawn.yPosition;
        entityotherplayermp.field_9301_bt = packet20namedentityspawn.zPosition;
        int i = packet20namedentityspawn.currentItem;
        if(i == 0)
        {
            entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = null;
        } else
        {
            entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = new ItemStack(i);
        }
        entityotherplayermp.setPositionAndRotation(d, d1, d2, f, f1);
        worldClient.func_712_a(packet20namedentityspawn.entityId, entityotherplayermp);
    }

    public void handleEntityTeleport(Packet34EntityTeleport packet34entityteleport)
    {
        Entity entity = func_12246_a(packet34entityteleport.entityId);
        if(entity == null)
        {
            return;
        } else
        {
            entity.field_9303_br = packet34entityteleport.xPosition;
            entity.field_9302_bs = packet34entityteleport.yPosition;
            entity.field_9301_bt = packet34entityteleport.zPosition;
            double d = (double)entity.field_9303_br / 32D;
            double d1 = (double)entity.field_9302_bs / 32D + 0.015625D;
            double d2 = (double)entity.field_9301_bt / 32D;
            float f = (float)(packet34entityteleport.yaw * 360) / 256F;
            float f1 = (float)(packet34entityteleport.pitch * 360) / 256F;
            entity.setPositionAndRotation2(d, d1, d2, f, f1, 3);
            return;
        }
    }

    public void handleEntity(Packet30Entity packet30entity)
    {
        Entity entity = func_12246_a(packet30entity.entityId);
        if(entity == null)
        {
            return;
        } else
        {
            entity.field_9303_br += packet30entity.xPosition;
            entity.field_9302_bs += packet30entity.yPosition;
            entity.field_9301_bt += packet30entity.zPosition;
            double d = (double)entity.field_9303_br / 32D;
            double d1 = (double)entity.field_9302_bs / 32D + 0.015625D;
            double d2 = (double)entity.field_9301_bt / 32D;
            float f = packet30entity.rotating ? (float)(packet30entity.yaw * 360) / 256F : entity.rotationYaw;
            float f1 = packet30entity.rotating ? (float)(packet30entity.pitch * 360) / 256F : entity.rotationPitch;
            entity.setPositionAndRotation2(d, d1, d2, f, f1, 3);
            return;
        }
    }

    public void handleDestroyEntity(Packet29DestroyEntity packet29destroyentity)
    {
        worldClient.func_710_c(packet29destroyentity.entityId);
    }

    public void handleFlying(Packet10Flying packet10flying)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        double d = ((EntityPlayer) (entityplayersp)).posX;
        double d1 = ((EntityPlayer) (entityplayersp)).posY;
        double d2 = ((EntityPlayer) (entityplayersp)).posZ;
        float f = ((EntityPlayer) (entityplayersp)).rotationYaw;
        float f1 = ((EntityPlayer) (entityplayersp)).rotationPitch;
        if(packet10flying.moving)
        {
            d = packet10flying.xPosition;
            d1 = packet10flying.yPosition;
            d2 = packet10flying.zPosition;
        }
        if(packet10flying.rotating)
        {
            f = packet10flying.yaw;
            f1 = packet10flying.pitch;
        }
        entityplayersp.ySize = 0.0F;
        entityplayersp.motionX = entityplayersp.motionY = entityplayersp.motionZ = 0.0D;
        entityplayersp.setPositionAndRotation(d, d1, d2, f, f1);
        packet10flying.xPosition = ((EntityPlayer) (entityplayersp)).posX;
        packet10flying.yPosition = ((EntityPlayer) (entityplayersp)).boundingBox.minY;
        packet10flying.zPosition = ((EntityPlayer) (entityplayersp)).posZ;
        packet10flying.stance = ((EntityPlayer) (entityplayersp)).posY;
        netManager.addToSendQueue(packet10flying);
        if(!field_1210_g)
        {
            mc.thePlayer.prevPosX = mc.thePlayer.posX;
            mc.thePlayer.prevPosY = mc.thePlayer.posY;
            mc.thePlayer.prevPosZ = mc.thePlayer.posZ;
            field_1210_g = true;
            mc.displayGuiScreen(null);
        }
    }

    public void handlePreChunk(Packet50PreChunk packet50prechunk)
    {
        worldClient.func_713_a(packet50prechunk.xPosition, packet50prechunk.yPosition, packet50prechunk.mode);
    }

    public void handleMultiBlockChange(Packet52MultiBlockChange packet52multiblockchange)
    {
        Chunk chunk = worldClient.getChunkFromChunkCoords(packet52multiblockchange.xPosition, packet52multiblockchange.zPosition);
        int i = packet52multiblockchange.xPosition * 16;
        int j = packet52multiblockchange.zPosition * 16;
        for(int k = 0; k < packet52multiblockchange.size; k++)
        {
            short word0 = packet52multiblockchange.coordinateArray[k];
            int l = packet52multiblockchange.typeArray[k] & 0xff;
            byte byte0 = packet52multiblockchange.metadataArray[k];
            int i1 = word0 >> 12 & 0xf;
            int j1 = word0 >> 8 & 0xf;
            int k1 = word0 & 0xff;
            chunk.setBlockIDWithMetadata(i1, k1, j1, l, byte0);
            worldClient.func_711_c(i1 + i, k1, j1 + j, i1 + i, k1, j1 + j);
            worldClient.func_701_b(i1 + i, k1, j1 + j, i1 + i, k1, j1 + j);
        }

    }

    public void handleMapChunk(Packet51MapChunk packet51mapchunk)
    {
        worldClient.func_711_c(packet51mapchunk.xPosition, packet51mapchunk.yPosition, packet51mapchunk.zPosition, (packet51mapchunk.xPosition + packet51mapchunk.xSize) - 1, (packet51mapchunk.yPosition + packet51mapchunk.ySize) - 1, (packet51mapchunk.zPosition + packet51mapchunk.zSize) - 1);
        worldClient.func_693_a(packet51mapchunk.xPosition, packet51mapchunk.yPosition, packet51mapchunk.zPosition, packet51mapchunk.xSize, packet51mapchunk.ySize, packet51mapchunk.zSize, packet51mapchunk.chunk);
    }

    public void handleBlockChange(Packet53BlockChange packet53blockchange)
    {
        worldClient.func_714_c(packet53blockchange.xPosition, packet53blockchange.yPosition, packet53blockchange.zPosition, packet53blockchange.type, packet53blockchange.metadata);
    }

    public void handleKickDisconnect(Packet255KickDisconnect packet255kickdisconnect)
    {
        netManager.networkShutdown("Got kicked");
        disconnected = true;
        mc.changeWorld1(null);
        mc.displayGuiScreen(new GuiConnectFailed("Disconnected by server", packet255kickdisconnect.reason));
    }

    public void handleErrorMessage(String s)
    {
        if(disconnected)
        {
            return;
        } else
        {
            disconnected = true;
            mc.changeWorld1(null);
            mc.displayGuiScreen(new GuiConnectFailed("Connection lost", s));
            return;
        }
    }

    public void addToSendQueue(Packet packet)
    {
        if(disconnected)
        {
            return;
        } else
        {
            netManager.addToSendQueue(packet);
            return;
        }
    }

    public void handleCollect(Packet22Collect packet22collect)
    {
        Entity entity = func_12246_a(packet22collect.collectedEntityId);
        Object obj = (EntityLiving)func_12246_a(packet22collect.collectorEntityId);
        if(obj == null)
        {
            obj = mc.thePlayer;
        }
        if(entity != null)
        {
            worldClient.playSoundAtEntity(entity, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            mc.effectRenderer.addEffect(new EntityPickupFX(mc.theWorld, entity, ((Entity) (obj)), -0.5F));
            worldClient.func_710_c(packet22collect.collectedEntityId);
        }
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch packet16blockitemswitch)
    {
        Entity entity = func_12246_a(packet16blockitemswitch.unused);
        if(entity == null)
        {
            return;
        }
        EntityPlayer entityplayer = (EntityPlayer)entity;
        int i = packet16blockitemswitch.id;
        if(i == 0)
        {
            entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
        } else
        {
            entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = new ItemStack(i);
        }
    }

    public void handleChat(Packet3Chat packet3chat)
    {
        mc.ingameGUI.addChatMessage(packet3chat.message);
    }

    public void handleArmAnimation(Packet18ArmAnimation packet18armanimation)
    {
        Entity entity = func_12246_a(packet18armanimation.entityId);
        if(entity == null)
        {
            return;
        }
        if(packet18armanimation.animate == 1)
        {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            entityplayer.func_457_w();
        } else
        if(packet18armanimation.animate == 100)
        {
            entity.field_9300_bu = true;
        } else
        if(packet18armanimation.animate == 101)
        {
            entity.field_9300_bu = false;
        } else
        if(packet18armanimation.animate == 102)
        {
            entity.field_9299_bv = true;
        } else
        if(packet18armanimation.animate == 103)
        {
            entity.field_9299_bv = false;
        } else
        if(packet18armanimation.animate == 104)
        {
            entity.field_12240_bw = true;
        } else
        if(packet18armanimation.animate == 105)
        {
            entity.field_12240_bw = false;
        } else
        if(packet18armanimation.animate == 2)
        {
            entity.func_9280_g();
        }
    }

    public void handleAddToInventory(Packet17AddToInventory packet17addtoinventory)
    {
        mc.thePlayer.inventory.addItemStackToInventory(new ItemStack(packet17addtoinventory.id, packet17addtoinventory.count, packet17addtoinventory.durability));
    }

    public void handleHandshake(Packet2Handshake packet2handshake)
    {
        if(packet2handshake.username.equals("-"))
        {
            addToSendQueue(new Packet1Login(mc.session.inventory, "Password", 6));
        } else
        {
            try
            {
                URL url = new URL((new StringBuilder()).append("http://www.minecraft.net/game/joinserver.jsp?user=").append(mc.session.inventory).append("&sessionId=").append(mc.session.field_6543_c).append("&serverId=").append(packet2handshake.username).toString());
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
                String s = bufferedreader.readLine();
                bufferedreader.close();
                if(s.equalsIgnoreCase("ok"))
                {
                    addToSendQueue(new Packet1Login(mc.session.inventory, "Password", 6));
                } else
                {
                    netManager.networkShutdown((new StringBuilder()).append("Failed to login: ").append(s).toString());
                }
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
                netManager.networkShutdown((new StringBuilder()).append("Internal client error: ").append(exception.toString()).toString());
            }
        }
    }

    public void disconnect()
    {
        disconnected = true;
        netManager.networkShutdown("Closed");
    }

    public void handleMobSpawn(Packet24MobSpawn packet24mobspawn)
    {
        double d = (double)packet24mobspawn.xPosition / 32D;
        double d1 = (double)packet24mobspawn.yPosition / 32D;
        double d2 = (double)packet24mobspawn.zPosition / 32D;
        float f = (float)(packet24mobspawn.yaw * 360) / 256F;
        float f1 = (float)(packet24mobspawn.pitch * 360) / 256F;
        EntityLiving entityliving = (EntityLiving)EntityList.createEntity(packet24mobspawn.type, mc.theWorld);
        entityliving.field_9303_br = packet24mobspawn.xPosition;
        entityliving.field_9302_bs = packet24mobspawn.yPosition;
        entityliving.field_9301_bt = packet24mobspawn.zPosition;
        entityliving.entityID = packet24mobspawn.entityId;
        entityliving.setPositionAndRotation(d, d1, d2, f, f1);
        entityliving.field_9343_G = true;
        worldClient.func_712_a(packet24mobspawn.entityId, entityliving);
    }

    public void handleUpdateTime(Packet4UpdateTime packet4updatetime)
    {
        mc.theWorld.setWorldTime(packet4updatetime.time);
    }

    public void handlePlayerInventory(Packet5PlayerInventory packet5playerinventory)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        if(packet5playerinventory.type == -1)
        {
            ((EntityPlayer) (entityplayersp)).inventory.mainInventory = packet5playerinventory.stacks;
        }
        if(packet5playerinventory.type == -2)
        {
            ((EntityPlayer) (entityplayersp)).inventory.craftingInventory = packet5playerinventory.stacks;
        }
        if(packet5playerinventory.type == -3)
        {
            ((EntityPlayer) (entityplayersp)).inventory.armorInventory = packet5playerinventory.stacks;
        }
    }

    public void handleComplexEntity(Packet59ComplexEntity packet59complexentity)
    {
        if(packet59complexentity.entityNBT.getInteger("x") != packet59complexentity.xPosition)
        {
            return;
        }
        if(packet59complexentity.entityNBT.getInteger("y") != packet59complexentity.yPosition)
        {
            return;
        }
        if(packet59complexentity.entityNBT.getInteger("z") != packet59complexentity.zPosition)
        {
            return;
        }
        TileEntity tileentity = worldClient.getBlockTileEntity(packet59complexentity.xPosition, packet59complexentity.yPosition, packet59complexentity.zPosition);
        if(tileentity != null)
        {
            try
            {
                tileentity.readFromNBT(packet59complexentity.entityNBT);
            }
            catch(Exception exception) { }
            worldClient.func_701_b(packet59complexentity.xPosition, packet59complexentity.yPosition, packet59complexentity.zPosition, packet59complexentity.xPosition, packet59complexentity.yPosition, packet59complexentity.zPosition);
        }
    }

    public void handleSpawnPosition(Packet6SpawnPosition packet6spawnposition)
    {
        worldClient.spawnX = packet6spawnposition.xPosition;
        worldClient.spawnY = packet6spawnposition.yPosition;
        worldClient.spawnZ = packet6spawnposition.zPosition;
    }

    public void func_6497_a(Packet39 packet39)
    {
        Object obj = func_12246_a(packet39.field_6365_a);
        Entity entity = func_12246_a(packet39.field_6364_b);
        if(packet39.field_6365_a == mc.thePlayer.entityID)
        {
            obj = mc.thePlayer;
        }
        if(obj == null)
        {
            return;
        } else
        {
            ((Entity) (obj)).mountEntity(entity);
            return;
        }
    }

    public void func_9447_a(Packet38 packet38)
    {
        Entity entity = func_12246_a(packet38.field_9274_a);
        if(entity != null)
        {
            entity.func_9282_a(packet38.field_9273_b);
        }
    }

    private Entity func_12246_a(int i)
    {
        if(i == mc.thePlayer.entityID)
        {
            return mc.thePlayer;
        } else
        {
            return worldClient.func_709_b(i);
        }
    }

    public void handleHealth(Packet8 packet8)
    {
        mc.thePlayer.setHealth(packet8.healthMP);
    }

    public void func_9448_a(Packet9 packet9)
    {
        mc.respawn();
    }

    public void func_12245_a(Packet60 packet60)
    {
        Explosion explosion = new Explosion(mc.theWorld, null, packet60.field_12236_a, packet60.field_12235_b, packet60.field_12239_c, packet60.field_12238_d);
        explosion.field_12251_g = packet60.field_12237_e;
        explosion.func_12247_b();
    }

    private boolean disconnected;
    private NetworkManager netManager;
    public String field_1209_a;
    private Minecraft mc;
    private WorldClient worldClient;
    private boolean field_1210_g;
    Random rand;
}
