package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class GuiDeleteWorld extends GuiSelectWorld
{

    public GuiDeleteWorld(GuiScreen guiscreen)
    {
        super(guiscreen);
        screenTitle = "Delete world";
    }
    
	public void initGui2()
    {
        controlList.add(new GuiButton(6, width / 2 - 100, height / 6 + 168, "Cancel"));
    }

    public void selectWorld(int i)
    {
        String s = getWorldName(i);
        if(s != null)
        {
            mc.displayGuiScreen(new GuiYesNo(this, "Are you sure you want to delete this world?", (new StringBuilder()).append("'").append(s).append("' will be lost forever!").toString(), i));
        }
    }

    public void deleteWorld(boolean flag, int i)
    {
        if(flag)
        {
            java.io.File file = Minecraft.getMinecraftDir();
            World.deleteWorld(file, getWorldName(i));
        }
        mc.displayGuiScreen(parentScreen);
    }
}
