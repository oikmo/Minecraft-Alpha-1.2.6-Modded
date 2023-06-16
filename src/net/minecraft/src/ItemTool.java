package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ItemTool extends Item
{

    public ItemTool(int i, int j, int k, Block ablock[])
    {
        super(i);
        efficiencyOnProperMaterial = 4F;
        ingredientQuality = k;
        blocksEffectiveAgainst = ablock;
        maxStackSize = 1;
        maxDamage = 32 << k;
        if(k == 3)
        {
            maxDamage *= 4;
        }
        efficiencyOnProperMaterial = (k + 1) * 2;
        damageVsEntity = j + k;
    }

    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
        {
            if(blocksEffectiveAgainst[i] == block)
            {
                return efficiencyOnProperMaterial;
            }
        }

        return 1.0F;
    }

    public void hitEntity(ItemStack itemstack, EntityLiving entityliving)
    {
        itemstack.damageItem(2);
    }

    public void hitBlock(ItemStack itemstack, int i, int j, int k, int l)
    {
        itemstack.damageItem(1);
    }

    public int getDamageVsEntity(Entity entity)
    {
        return damageVsEntity;
    }

    public boolean isFull3D()
    {
        return true;
    }

    private Block blocksEffectiveAgainst[];
    private float efficiencyOnProperMaterial;
    private int damageVsEntity;
    protected int ingredientQuality;
}
