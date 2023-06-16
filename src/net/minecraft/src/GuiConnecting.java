package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class GuiConnecting extends GuiScreen
{

    public GuiConnecting(Minecraft minecraft, String s, int i)
    {
        cancelled = false;
        minecraft.changeWorld1(null);
        (new ThreadConnectToServer(this, minecraft, s, i)).start();
    }

    public void updateScreen()
    {
        if(clientHandler != null)
        {
            clientHandler.processReadPackets();
        }
    }

    protected void keyTyped(char c, int i)
    {
    }

    public void initGui()
    {
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, "Cancel"));
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == 0)
        {
            cancelled = true;
            if(clientHandler != null)
            {
                clientHandler.disconnect();
            }
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        if(clientHandler == null)
        {
            drawCenteredString(fontRenderer, "Connecting to the server...", width / 2, height / 2 - 50, 0xffffff);
            drawCenteredString(fontRenderer, "", width / 2, height / 2 - 10, 0xffffff);
        } else
        {
            drawCenteredString(fontRenderer, "Logging in...", width / 2, height / 2 - 50, 0xffffff);
            drawCenteredString(fontRenderer, clientHandler.field_1209_a, width / 2, height / 2 - 10, 0xffffff);
        }
        super.drawScreen(i, j, f);
    }

    static NetClientHandler setNetClientHandler(GuiConnecting guiconnecting, NetClientHandler netclienthandler)
    {
        return guiconnecting.clientHandler = netclienthandler;
    }

    static boolean isCancelled(GuiConnecting guiconnecting)
    {
        return guiconnecting.cancelled;
    }

    static NetClientHandler getNetClientHandler(GuiConnecting guiconnecting)
    {
        return guiconnecting.clientHandler;
    }

    private NetClientHandler clientHandler;
    private boolean cancelled;
}
