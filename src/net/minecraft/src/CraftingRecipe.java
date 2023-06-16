package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class CraftingRecipe
{

    public CraftingRecipe(int i, int j, int ai[], ItemStack itemstack)
    {
        resultId = itemstack.itemID;
        width = i;
        height = j;
        ingredientMap = ai;
        resultStack = itemstack;
    }

    public boolean matchRecipe(int ai[])
    {
        for(int i = 0; i <= 3 - width; i++)
        {
            for(int j = 0; j <= 3 - height; j++)
            {
                if(matches(ai, i, j, true))
                {
                    return true;
                }
                if(matches(ai, i, j, false))
                {
                    return true;
                }
            }

        }

        return false;
    }

    private boolean matches(int ai[], int i, int j, boolean flag)
    {
        for(int k = 0; k < 3; k++)
        {
            for(int l = 0; l < 3; l++)
            {
                int i1 = k - i;
                int j1 = l - j;
                int k1 = -1;
                if(i1 >= 0 && j1 >= 0 && i1 < width && j1 < height)
                {
                    if(flag)
                    {
                        k1 = ingredientMap[(width - i1 - 1) + j1 * width];
                    } else
                    {
                        k1 = ingredientMap[i1 + j1 * width];
                    }
                }
                if(ai[k + l * 3] != k1)
                {
                    return false;
                }
            }

        }

        return true;
    }

    public ItemStack createResult(int ai[])
    {
        return new ItemStack(resultStack.itemID, resultStack.stackSize);
    }

    public int getRecipeSize()
    {
        return width * height;
    }

    private int width;
    private int height;
    private int ingredientMap[];
    private ItemStack resultStack;
    public final int resultId;
}
