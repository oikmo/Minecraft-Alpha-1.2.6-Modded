package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.PrintStream;
import java.util.*;

public class CraftingManager
{

    public static final CraftingManager getInstance()
    {
        return instance;
    }

    private CraftingManager()
    {
        recipes = new ArrayList<CraftingRecipe>();
        (new RecipesTools()).func_1122_a(this);
        (new RecipesWeapons()).func_766_a(this);
        (new RecipesIngots()).func_810_a(this);
        (new RecipesFood()).func_976_a(this);
        (new RecipesCrafting()).func_1051_a(this);
        (new RecipesArmor()).func_1148_a(this);
        addRecipe(new ItemStack(Item.paper, 3), new Object[] {
            "###", Character.valueOf('#'), Item.reed
        });
        addRecipe(new ItemStack(Item.book, 1), new Object[] {
            "#", "#", "#", Character.valueOf('#'), Item.paper
        });
        addRecipe(new ItemStack(Block.fence, 2), new Object[] {
            "###", "###", Character.valueOf('#'), Item.stick
        });
        addRecipe(new ItemStack(Block.jukebox, 1), new Object[] {
            "###", "#X#", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.diamond
        });
        addRecipe(new ItemStack(Block.bookShelf, 1), new Object[] {
            "###", "XXX", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.book
        });
        addRecipe(new ItemStack(Block.blockSnow, 1), new Object[] {
            "##", "##", Character.valueOf('#'), Item.snowball
        });
        addRecipe(new ItemStack(Block.blockClay, 1), new Object[] {
            "##", "##", Character.valueOf('#'), Item.clay
        });
        addRecipe(new ItemStack(Block.brick, 1), new Object[] {
            "##", "##", Character.valueOf('#'), Item.brick
        });
        addRecipe(new ItemStack(Block.lightStone, 1), new Object[] {
            "###", "###", "###", Character.valueOf('#'), Item.lightStoneDust
        });
        addRecipe(new ItemStack(Block.cloth, 1), new Object[] {
            "###", "###", "###", Character.valueOf('#'), Item.silk
        });
        addRecipe(new ItemStack(Block.tnt, 1), new Object[] {
            "X#X", "#X#", "X#X", Character.valueOf('X'), Item.gunpowder, Character.valueOf('#'), Block.sand
        });
        addRecipe(new ItemStack(Block.stairSingle, 3), new Object[] {
            "###", Character.valueOf('#'), Block.cobblestone
        });
        addRecipe(new ItemStack(Block.ladder, 1), new Object[] {
            "# #", "###", "# #", Character.valueOf('#'), Item.stick
        });
        addRecipe(new ItemStack(Item.doorWood, 1), new Object[] {
            "##", "##", "##", Character.valueOf('#'), Block.planks
        });
        addRecipe(new ItemStack(Item.doorSteel, 1), new Object[] {
            "##", "##", "##", Character.valueOf('#'), Item.ingotIron
        });
        addRecipe(new ItemStack(Item.sign, 1), new Object[] {
            "###", "###", " X ", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.stick
        });
        addRecipe(new ItemStack(Block.planks, 4), new Object[] {
            "#", Character.valueOf('#'), Block.wood
        });
        addRecipe(new ItemStack(Item.stick, 4), new Object[] {
            "#", "#", Character.valueOf('#'), Block.planks
        });
        addRecipe(new ItemStack(Block.torchWood, 4), new Object[] {
            "X", "#", Character.valueOf('X'), Item.coal, Character.valueOf('#'), Item.stick
        });
        addRecipe(new ItemStack(Item.bowlEmpty, 4), new Object[] {
            "# #", " # ", Character.valueOf('#'), Block.planks
        });
        addRecipe(new ItemStack(Block.minecartTrack, 16), new Object[] {
            "X X", "X#X", "X X", Character.valueOf('X'), Item.ingotIron, Character.valueOf('#'), Item.stick
        });
        addRecipe(new ItemStack(Item.minecartEmpty, 1), new Object[] {
            "# #", "###", Character.valueOf('#'), Item.ingotIron
        });
        addRecipe(new ItemStack(Block.pumpkinLantern, 1), new Object[] {
            "A", "B", Character.valueOf('A'), Block.pumpkin, Character.valueOf('B'), Block.torchWood
        });
        addRecipe(new ItemStack(Item.minecartCrate, 1), new Object[] {
            "A", "B", Character.valueOf('A'), Block.crate, Character.valueOf('B'), Item.minecartEmpty
        });
        addRecipe(new ItemStack(Item.minecartPowered, 1), new Object[] {
            "A", "B", Character.valueOf('A'), Block.stoneOvenIdle, Character.valueOf('B'), Item.minecartEmpty
        });
        addRecipe(new ItemStack(Item.boat, 1), new Object[] {
            "# #", "###", Character.valueOf('#'), Block.planks
        });
        addRecipe(new ItemStack(Item.bucketEmpty, 1), new Object[] {
            "# #", " # ", Character.valueOf('#'), Item.ingotIron
        });
        addRecipe(new ItemStack(Item.flintAndSteel, 1), new Object[] {
            "A ", " B", Character.valueOf('A'), Item.ingotIron, Character.valueOf('B'), Item.flint
        });
        addRecipe(new ItemStack(Item.bread, 1), new Object[] {
            "###", Character.valueOf('#'), Item.wheat
        });
        addRecipe(new ItemStack(Block.stairCompactPlanks, 4), new Object[] {
            "#  ", "## ", "###", Character.valueOf('#'), Block.planks
        });
        addRecipe(new ItemStack(Item.fishingRod, 1), new Object[] {
            "  #", " #X", "# X", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Item.silk
        });
        addRecipe(new ItemStack(Block.stairCompactCobblestone, 4), new Object[] {
            "#  ", "## ", "###", Character.valueOf('#'), Block.cobblestone
        });
        addRecipe(new ItemStack(Item.painting, 1), new Object[] {
            "###", "#X#", "###", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Block.cloth
        });
        addRecipe(new ItemStack(Item.appleGold, 1), new Object[] {
            "###", "#X#", "###", Character.valueOf('#'), Block.blockGold, Character.valueOf('X'), Item.appleRed
        });
        addRecipe(new ItemStack(Block.lever, 1), new Object[] {
            "X", "#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.stick
        });
        addRecipe(new ItemStack(Block.torchRedstoneActive, 1), new Object[] {
            "X", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Item.redstone
        });
        addRecipe(new ItemStack(Item.pocketSundial, 1), new Object[] {
            " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotGold, Character.valueOf('X'), Item.redstone
        });
        addRecipe(new ItemStack(Item.compass, 1), new Object[] {
            " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), Item.redstone
        });
        addRecipe(new ItemStack(Block.button, 1), new Object[] {
            "#", "#", Character.valueOf('#'), Block.stone
        });
        addRecipe(new ItemStack(Block.pressurePlateStone, 1), new Object[] {
            "###", Character.valueOf('#'), Block.stone
        });
        addRecipe(new ItemStack(Block.pressurePlatePlanks, 1), new Object[] {
            "###", Character.valueOf('#'), Block.planks
        });
        Collections.sort(recipes, new RecipeSorter(this));
        System.out.println((new StringBuilder()).append(recipes.size()).append(" recipes").toString());
    }

    void addRecipe(ItemStack itemstack, Object aobj[])
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        if(aobj[i] instanceof String[])
        {
            String as[] = (String[])aobj[i++];
            for(int l = 0; l < as.length; l++)
            {
                String s2 = as[l];
                k++;
                j = s2.length();
                s = (new StringBuilder()).append(s).append(s2).toString();
            }

        } else
        {
            while(aobj[i] instanceof String) 
            {
                String s1 = (String)aobj[i++];
                k++;
                j = s1.length();
                s = (new StringBuilder()).append(s).append(s1).toString();
            }
        }
        HashMap<Character, Integer> hashmap = new HashMap<Character, Integer>();
        for(; i < aobj.length; i += 2)
        {
            Character character = (Character)aobj[i];
            int i1 = 0;
            if(aobj[i + 1] instanceof Item)
            {
                i1 = ((Item)aobj[i + 1]).shiftedIndex;
            } else
            if(aobj[i + 1] instanceof Block)
            {
                i1 = ((Block)aobj[i + 1]).blockID;
            }
            hashmap.put(character, Integer.valueOf(i1));
        }

        int ai[] = new int[j * k];
        for(int j1 = 0; j1 < j * k; j1++)
        {
            char c = s.charAt(j1);
            if(hashmap.containsKey(Character.valueOf(c)))
            {
                ai[j1] = ((Integer)hashmap.get(Character.valueOf(c))).intValue();
            } else
            {
                ai[j1] = -1;
            }
        }

        recipes.add(new CraftingRecipe(j, k, ai, itemstack));
    }

    public ItemStack craft(int ai[])
    {
        for(int i = 0; i < recipes.size(); i++)
        {
            CraftingRecipe craftingrecipe = (CraftingRecipe)recipes.get(i);
            if(craftingrecipe.matchRecipe(ai))
            {
                return craftingrecipe.createResult(ai);
            }
        }

        return null;
    }

    private static final CraftingManager instance = new CraftingManager();
    private List<CraftingRecipe> recipes;

}
