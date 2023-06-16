package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class NetHandler
{

    public NetHandler()
    {
    }

    public void handleMapChunk(Packet51MapChunk packet51mapchunk)
    {
    }

    public void func_4114_b(Packet packet)
    {
    }

    public void handleErrorMessage(String s)
    {
    }

    public void handleKickDisconnect(Packet255KickDisconnect packet255kickdisconnect)
    {
        func_4114_b(packet255kickdisconnect);
    }

    public void handleLogin(Packet1Login packet1login)
    {
        func_4114_b(packet1login);
    }

    public void handleFlying(Packet10Flying packet10flying)
    {
        func_4114_b(packet10flying);
    }

    public void handleMultiBlockChange(Packet52MultiBlockChange packet52multiblockchange)
    {
        func_4114_b(packet52multiblockchange);
    }

    public void handleBlockDig(Packet14BlockDig packet14blockdig)
    {
        func_4114_b(packet14blockdig);
    }

    public void handleBlockChange(Packet53BlockChange packet53blockchange)
    {
        func_4114_b(packet53blockchange);
    }

    public void handlePreChunk(Packet50PreChunk packet50prechunk)
    {
        func_4114_b(packet50prechunk);
    }

    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet20namedentityspawn)
    {
        func_4114_b(packet20namedentityspawn);
    }

    public void handleEntity(Packet30Entity packet30entity)
    {
        func_4114_b(packet30entity);
    }

    public void handleEntityTeleport(Packet34EntityTeleport packet34entityteleport)
    {
        func_4114_b(packet34entityteleport);
    }

    public void handlePlace(Packet15Place packet15place)
    {
        func_4114_b(packet15place);
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch packet16blockitemswitch)
    {
        func_4114_b(packet16blockitemswitch);
    }

    public void handleDestroyEntity(Packet29DestroyEntity packet29destroyentity)
    {
        func_4114_b(packet29destroyentity);
    }

    public void handlePickupSpawn(Packet21PickupSpawn packet21pickupspawn)
    {
        func_4114_b(packet21pickupspawn);
    }

    public void handleCollect(Packet22Collect packet22collect)
    {
        func_4114_b(packet22collect);
    }

    public void handleChat(Packet3Chat packet3chat)
    {
        func_4114_b(packet3chat);
    }

    public void handleAddToInventory(Packet17AddToInventory packet17addtoinventory)
    {
        func_4114_b(packet17addtoinventory);
    }

    public void handleVehicleSpawn(Packet23VehicleSpawn packet23vehiclespawn)
    {
        func_4114_b(packet23vehiclespawn);
    }

    public void handleArmAnimation(Packet18ArmAnimation packet18armanimation)
    {
        func_4114_b(packet18armanimation);
    }

    public void handleHandshake(Packet2Handshake packet2handshake)
    {
        func_4114_b(packet2handshake);
    }

    public void handleMobSpawn(Packet24MobSpawn packet24mobspawn)
    {
        func_4114_b(packet24mobspawn);
    }

    public void handleUpdateTime(Packet4UpdateTime packet4updatetime)
    {
        func_4114_b(packet4updatetime);
    }

    public void handlePlayerInventory(Packet5PlayerInventory packet5playerinventory)
    {
        func_4114_b(packet5playerinventory);
    }

    public void handleComplexEntity(Packet59ComplexEntity packet59complexentity)
    {
        func_4114_b(packet59complexentity);
    }

    public void handleSpawnPosition(Packet6SpawnPosition packet6spawnposition)
    {
        func_4114_b(packet6spawnposition);
    }

    public void func_6498_a(Packet28 packet28)
    {
        func_4114_b(packet28);
    }

    public void func_6497_a(Packet39 packet39)
    {
        func_4114_b(packet39);
    }

    public void func_6499_a(Packet7 packet7)
    {
        func_4114_b(packet7);
    }

    public void func_9447_a(Packet38 packet38)
    {
        func_4114_b(packet38);
    }

    public void handleHealth(Packet8 packet8)
    {
        func_4114_b(packet8);
    }

    public void func_9448_a(Packet9 packet9)
    {
        func_4114_b(packet9);
    }

    public void func_12245_a(Packet60 packet60)
    {
        func_4114_b(packet60);
    }
}
