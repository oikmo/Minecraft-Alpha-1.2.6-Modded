package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class RecipesCrafting
{

    public RecipesCrafting()
    {
    }

    public void func_1051_a(CraftingManager craftingmanager)
    {
        craftingmanager.addRecipe(new ItemStack(Block.crate), new Object[] {
            "###", "# #", "###", Character.valueOf('#'), Block.planks
        });
        craftingmanager.addRecipe(new ItemStack(Block.stoneOvenIdle), new Object[] {
            "###", "# #", "###", Character.valueOf('#'), Block.cobblestone
        });
        craftingmanager.addRecipe(new ItemStack(Block.workbench), new Object[] {
            "##", "##", Character.valueOf('#'), Block.planks
        });
    }
}
