package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class RecipesIngots
{

    public RecipesIngots()
    {
        field_1198_a = (new Object[][] {
            new Object[] {
                Block.blockGold, Item.ingotGold
            }, new Object[] {
                Block.blockSteel, Item.ingotIron
            }, new Object[] {
                Block.blockDiamond, Item.diamond
            }
        });
    }

    public void func_810_a(CraftingManager craftingmanager)
    {
        for(int i = 0; i < field_1198_a.length; i++)
        {
            Block block = (Block)field_1198_a[i][0];
            Item item = (Item)field_1198_a[i][1];
            craftingmanager.addRecipe(new ItemStack(block), new Object[] {
                "###", "###", "###", Character.valueOf('#'), item
            });
            craftingmanager.addRecipe(new ItemStack(item, 9), new Object[] {
                "#", Character.valueOf('#'), block
            });
        }

    }

    private Object field_1198_a[][];
}
