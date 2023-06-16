package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Comparator;

class RecipeSorter
    implements Comparator<Object>
{

    RecipeSorter(CraftingManager craftingmanager)
    {
        field_1557_a = craftingmanager;
    }

    public int func_1040_a(CraftingRecipe craftingrecipe, CraftingRecipe craftingrecipe1)
    {
        if(craftingrecipe1.getRecipeSize() < craftingrecipe.getRecipeSize())
        {
            return -1;
        }
        return craftingrecipe1.getRecipeSize() <= craftingrecipe.getRecipeSize() ? 0 : 1;
    }

    public int compare(Object obj, Object obj1)
    {
        return func_1040_a((CraftingRecipe)obj, (CraftingRecipe)obj1);
    }

    final CraftingManager field_1557_a; /* synthetic field */
}
