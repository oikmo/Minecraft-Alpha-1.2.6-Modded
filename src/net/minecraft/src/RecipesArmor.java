package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class RecipesArmor
{

    public RecipesArmor()
    {
        field_1680_b = (new Object[][] {
            new Object[] {
                Item.leather, Block.fire, Item.ingotIron, Item.diamond, Item.ingotGold
            }, new Object[] {
                Item.helmetLeather, Item.helmetChain, Item.helmetSteel, Item.helmetDiamond, Item.helmetGold
            }, new Object[] {
                Item.plateLeather, Item.plateChain, Item.plateSteel, Item.plateDiamond, Item.plateGold
            }, new Object[] {
                Item.legsLeather, Item.legsChain, Item.legsSteel, Item.legsDiamond, Item.legsGold
            }, new Object[] {
                Item.bootsLeather, Item.bootsChain, Item.bootsSteel, Item.bootsDiamond, Item.bootsGold
            }
        });
    }

    public void func_1148_a(CraftingManager craftingmanager)
    {
        for(int i = 0; i < field_1680_b[0].length; i++)
        {
            Object obj = field_1680_b[0][i];
            for(int j = 0; j < field_1680_b.length - 1; j++)
            {
                Item item = (Item)field_1680_b[j + 1][i];
                craftingmanager.addRecipe(new ItemStack(item), new Object[] {
                    field_1681_a[j], Character.valueOf('X'), obj
                });
            }

        }

    }

    private String field_1681_a[][] = {
        {
            "XXX", "X X"
        }, {
            "X X", "XXX", "XXX"
        }, {
            "XXX", "X X", "X X"
        }, {
            "X X", "X X"
        }
    };
    private Object field_1680_b[][];
}
