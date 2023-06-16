package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.net.ConnectException;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;

class ThreadConnectToServer extends Thread
{

    ThreadConnectToServer(GuiConnecting guiconnecting, Minecraft minecraft, String s, int i)
    {
        connectingGui = guiconnecting;
        mc = minecraft;
        hostName = s;
        port = i;
    }

    public void run()
    {
        try
        {
            GuiConnecting.setNetClientHandler(connectingGui, new NetClientHandler(mc, hostName, port));
            if(GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }
            GuiConnecting.getNetClientHandler(connectingGui).addToSendQueue(new Packet2Handshake(mc.field_6320_i.inventory));
        }
        catch(UnknownHostException unknownhostexception)
        {
            if(GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }
            mc.displayGuiScreen(new GuiConnectFailed("Failed to connect to the server", (new StringBuilder()).append("Unknown host '").append(hostName).append("'").toString()));
        }
        catch(ConnectException connectexception)
        {
            if(GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }
            mc.displayGuiScreen(new GuiConnectFailed("Failed to connect to the server", connectexception.getMessage()));
        }
        catch(Exception exception)
        {
            if(GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }
            exception.printStackTrace();
            mc.displayGuiScreen(new GuiConnectFailed("Failed to connect to the server", exception.toString()));
        }
    }

    final Minecraft mc; /* synthetic field */
    final String hostName; /* synthetic field */
    final int port; /* synthetic field */
    final GuiConnecting connectingGui; /* synthetic field */
}
