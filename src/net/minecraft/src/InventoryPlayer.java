package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class InventoryPlayer
    implements IInventory
{

    public InventoryPlayer(EntityPlayer entityplayer)
    {
        mainInventory = new ItemStack[37];
        armorInventory = new ItemStack[4];
        craftingInventory = new ItemStack[4];
        currentItem = 0;
        field_845_f = false;
        player = entityplayer;
    }

    public ItemStack getCurrentItem()
    {
        return mainInventory[currentItem];
    }

    private int getInventorySlotContainItem(int i)
    {
        for(int j = 0; j < mainInventory.length; j++)
        {
            if(mainInventory[j] != null && mainInventory[j].itemID == i)
            {
                return j;
            }
        }

        return -1;
    }

    private int getFirstPartialMatchingStack(int i)
    {
        for(int j = 0; j < mainInventory.length; j++)
        {
            if(mainInventory[j] != null && mainInventory[j].itemID == i && mainInventory[j].stackSize < mainInventory[j].getMaxStackSize() && mainInventory[j].stackSize < getInventoryStackLimit())
            {
                return j;
            }
        }

        return -1;
    }

    private int getFirstEmptyStack()
    {
        for(int i = 0; i < mainInventory.length; i++)
        {
            if(mainInventory[i] == null)
            {
                return i;
            }
        }

        return -1;
    }

    public void setCurrentItem(int i, boolean flag)
    {
        int j = getInventorySlotContainItem(i);
        if(j >= 0 && j < 9)
        {
            currentItem = j;
            return;
        } else
        {
            return;
        }
    }

    public void changeCurrentItem(int i)
    {
        if(i > 0)
        {
            i = 1;
        }
        if(i < 0)
        {
            i = -1;
        }
        for(currentItem -= i; currentItem < 0; currentItem += 9) { }
        for(; currentItem >= 9; currentItem -= 9) { }
    }

    private int addItemsToInventory(int i, int j)
    {
        int k = getFirstPartialMatchingStack(i);
        if(k < 0)
        {
            k = getFirstEmptyStack();
        }
        if(k < 0)
        {
            return j;
        }
        if(mainInventory[k] == null)
        {
            mainInventory[k] = new ItemStack(i, 0);
        }
        int l = j;
        if(l > mainInventory[k].getMaxStackSize() - mainInventory[k].stackSize)
        {
            l = mainInventory[k].getMaxStackSize() - mainInventory[k].stackSize;
        }
        if(l > getInventoryStackLimit() - mainInventory[k].stackSize)
        {
            l = getInventoryStackLimit() - mainInventory[k].stackSize;
        }
        if(l == 0)
        {
            return j;
        } else
        {
            j -= l;
            mainInventory[k].stackSize += l;
            mainInventory[k].animationsToGo = 5;
            return j;
        }
    }

    public void decrementAnimations()
    {
        for(int i = 0; i < mainInventory.length; i++)
        {
            if(mainInventory[i] != null && mainInventory[i].animationsToGo > 0)
            {
                mainInventory[i].animationsToGo--;
            }
        }

    }

    public boolean consumeInventoryItem(int i)
    {
        int j = getInventorySlotContainItem(i);
        if(j < 0)
        {
            return false;
        }
        if(--mainInventory[j].stackSize <= 0)
        {
            mainInventory[j] = null;
        }
        return true;
    }

    public boolean addItemStackToInventory(ItemStack itemstack)
    {
        if(itemstack.itemDamage == 0)
        {
            itemstack.stackSize = addItemsToInventory(itemstack.itemID, itemstack.stackSize);
            if(itemstack.stackSize == 0)
            {
                return true;
            }
        }
        int i = getFirstEmptyStack();
        if(i >= 0)
        {
            mainInventory[i] = itemstack;
            mainInventory[i].animationsToGo = 5;
            return true;
        } else
        {
            return false;
        }
    }

    public ItemStack decrStackSize(int i, int j)
    {
        ItemStack aitemstack[] = mainInventory;
        if(i >= mainInventory.length)
        {
            aitemstack = armorInventory;
            i -= mainInventory.length;
        }
        if(aitemstack[i] != null)
        {
            if(aitemstack[i].stackSize <= j)
            {
                ItemStack itemstack = aitemstack[i];
                aitemstack[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = aitemstack[i].splitStack(j);
            if(aitemstack[i].stackSize == 0)
            {
                aitemstack[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        ItemStack aitemstack[] = mainInventory;
        if(i >= aitemstack.length)
        {
            i -= aitemstack.length;
            aitemstack = armorInventory;
        }
        if(i >= aitemstack.length)
        {
            i -= aitemstack.length;
            aitemstack = craftingInventory;
        }
        aitemstack[i] = itemstack;
    }

    public float getStrVsBlock(Block block)
    {
        float f = 1.0F;
        if(mainInventory[currentItem] != null)
        {
            f *= mainInventory[currentItem].getStrVsBlock(block);
        }
        return f;
    }

    public NBTTagList writeToNBT(NBTTagList nbttaglist)
    {
        for(int i = 0; i < mainInventory.length; i++)
        {
            if(mainInventory[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                mainInventory[i].writeToNBT(nbttagcompound);
                nbttaglist.setTag(nbttagcompound);
            }
        }

        for(int j = 0; j < armorInventory.length; j++)
        {
            if(armorInventory[j] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)(j + 100));
                armorInventory[j].writeToNBT(nbttagcompound1);
                nbttaglist.setTag(nbttagcompound1);
            }
        }

        for(int k = 0; k < craftingInventory.length; k++)
        {
            if(craftingInventory[k] != null)
            {
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Slot", (byte)(k + 80));
                craftingInventory[k].writeToNBT(nbttagcompound2);
                nbttaglist.setTag(nbttagcompound2);
            }
        }

        return nbttaglist;
    }

    public void readFromNBT(NBTTagList nbttaglist)
    {
        mainInventory = new ItemStack[36];
        armorInventory = new ItemStack[4];
        craftingInventory = new ItemStack[4];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound.getByte("Slot") & 0xff;
            if(j >= 0 && j < mainInventory.length)
            {
                mainInventory[j] = new ItemStack(nbttagcompound);
            }
            if(j >= 80 && j < craftingInventory.length + 80)
            {
                craftingInventory[j - 80] = new ItemStack(nbttagcompound);
            }
            if(j >= 100 && j < armorInventory.length + 100)
            {
                armorInventory[j - 100] = new ItemStack(nbttagcompound);
            }
        }

    }

    public int getSizeInventory()
    {
        return mainInventory.length + 4;
    }

    public ItemStack getStackInSlot(int i)
    {
        ItemStack aitemstack[] = mainInventory;
        if(i >= aitemstack.length)
        {
            i -= aitemstack.length;
            aitemstack = armorInventory;
        }
        if(i >= aitemstack.length)
        {
            i -= aitemstack.length;
            aitemstack = craftingInventory;
        }
        return aitemstack[i];
    }

    public String getInvName()
    {
        return "Inventory";
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public int getDamageVsEntity(Entity entity)
    {
        ItemStack itemstack = getStackInSlot(currentItem);
        if(itemstack != null)
        {
            return itemstack.getDamageVsEntity(entity);
        } else
        {
            return 1;
        }
    }

    public boolean canHarvestBlock(Block block)
    {
        if(block.blockMaterial != Material.rock && block.blockMaterial != Material.iron && block.blockMaterial != Material.builtSnow && block.blockMaterial != Material.snow)
        {
            return true;
        }
        ItemStack itemstack = getStackInSlot(currentItem);
        if(itemstack != null)
        {
            return itemstack.canHarvestBlock(block);
        } else
        {
            return false;
        }
    }

    public ItemStack armorItemInSlot(int i)
    {
        return armorInventory[i];
    }

    public int getTotalArmorValue()
    {
        int i = 0;
        int j = 0;
        int k = 0;
        for(int l = 0; l < armorInventory.length; l++)
        {
            if(armorInventory[l] != null && (armorInventory[l].getItem() instanceof ItemArmor))
            {
                int i1 = armorInventory[l].getMaxDamage();
                int j1 = armorInventory[l].itemDamage;
                int k1 = i1 - j1;
                j += k1;
                k += i1;
                int l1 = ((ItemArmor)armorInventory[l].getItem()).damageReduceAmmount;
                i += l1;
            }
        }

        if(k == 0)
        {
            return 0;
        } else
        {
            return ((i - 1) * j) / k + 1;
        }
    }

    public void damageArmor(int i)
    {
        for(int j = 0; j < armorInventory.length; j++)
        {
            if(armorInventory[j] == null || !(armorInventory[j].getItem() instanceof ItemArmor))
            {
                continue;
            }
            armorInventory[j].damageItem(i);
            if(armorInventory[j].stackSize == 0)
            {
                armorInventory[j].func_1097_a(player);
                armorInventory[j] = null;
            }
        }

    }

    public void dropAllItems()
    {
        for(int i = 0; i < mainInventory.length; i++)
        {
            if(mainInventory[i] != null)
            {
                player.dropPlayerItemWithRandomChoice(mainInventory[i], true);
                mainInventory[i] = null;
            }
        }

        for(int j = 0; j < armorInventory.length; j++)
        {
            if(armorInventory[j] != null)
            {
                player.dropPlayerItemWithRandomChoice(armorInventory[j], true);
                armorInventory[j] = null;
            }
        }

    }

    public void onInventoryChanged()
    {
        field_845_f = true;
    }

    public boolean compareInventory(InventoryPlayer inventoryplayer)
    {
        for(int i = 0; i < mainInventory.length; i++)
        {
            if(!compareItemStack(inventoryplayer.mainInventory[i], mainInventory[i]))
            {
                return false;
            }
        }

        for(int j = 0; j < armorInventory.length; j++)
        {
            if(!compareItemStack(inventoryplayer.armorInventory[j], armorInventory[j]))
            {
                return false;
            }
        }

        for(int k = 0; k < craftingInventory.length; k++)
        {
            if(!compareItemStack(inventoryplayer.craftingInventory[k], craftingInventory[k]))
            {
                return false;
            }
        }

        return true;
    }

    private boolean compareItemStack(ItemStack itemstack, ItemStack itemstack1)
    {
        if(itemstack == null && itemstack1 == null)
        {
            return true;
        }
        if(itemstack == null || itemstack1 == null)
        {
            return false;
        } else
        {
            return itemstack.itemID == itemstack1.itemID && itemstack.stackSize == itemstack1.stackSize && itemstack.itemDamage == itemstack1.itemDamage;
        }
    }

    public InventoryPlayer copyInventory()
    {
        InventoryPlayer inventoryplayer = new InventoryPlayer(null);
        for(int i = 0; i < mainInventory.length; i++)
        {
            inventoryplayer.mainInventory[i] = mainInventory[i] == null ? null : mainInventory[i].copy();
        }

        for(int j = 0; j < armorInventory.length; j++)
        {
            inventoryplayer.armorInventory[j] = armorInventory[j] == null ? null : armorInventory[j].copy();
        }

        for(int k = 0; k < craftingInventory.length; k++)
        {
            inventoryplayer.craftingInventory[k] = craftingInventory[k] == null ? null : craftingInventory[k].copy();
        }

        return inventoryplayer;
    }

    public ItemStack mainInventory[];
    public ItemStack armorInventory[];
    public ItemStack craftingInventory[];
    public int currentItem;
    private EntityPlayer player;
    public ItemStack draggingItemStack;
    public boolean field_845_f;
}
