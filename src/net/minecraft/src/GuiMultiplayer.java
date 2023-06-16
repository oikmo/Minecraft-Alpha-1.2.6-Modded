package net.minecraft.src;

public class GuiMultiplayer extends GuiScreen
{
	private GuiScreen updateCounter;
    private int parentScreen;
    private String serverAddress;
    
    public GuiMultiplayer(GuiScreen guiscreen)
    {
        parentScreen = 0;
        serverAddress = "";
        updateCounter = guiscreen;
    }

    public void updateScreen()
    {
        parentScreen++;
    }

    public void initGui()
    {
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Connect"));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, "Cancel"));
        serverAddress = mc.gameSettings.lastServer.replaceAll("_", ":");
        ((GuiButton)controlList.get(0)).enabled = serverAddress.length() > 0;
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(updateCounter);
        } else
        if(guibutton.id == 0)
        {
            mc.gameSettings.lastServer = serverAddress.replaceAll(":", "_");
            mc.gameSettings.saveOptions();
            String as[] = serverAddress.split(":");
            mc.displayGuiScreen(new GuiConnecting(mc, as[0], as.length <= 1 ? 25565 : func_4067_a(as[1], 25565)));
        }
    }

    private int func_4067_a(String s, int i)
    {
        try
        {
            return Integer.parseInt(s.trim());
        }
        catch(Exception exception)
        {
            return i;
        }
    }

    protected void keyTyped(char c, int i)
    {
        if(c == '\026')
        {
	        String s;
	        int j;
	        s = GuiScreen.getClipboardString();
	        if(s == null)
	        {
	            s = "";
	        }
	        j = 32 - serverAddress.length();
	        if(j > s.length())
	        {
	            j = s.length();
	        }
	        if(j > 0)
	        {
	        	serverAddress += s.substring(0, j); 
	        }
        }
        if(c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }
        if(i == 14 && serverAddress.length() > 0)
        {
            serverAddress = serverAddress.substring(0, serverAddress.length() - 1);
        }
        if(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\307\374\351\342\344\340\345\347\352\353\350\357\356\354\304\305\311\346\306\364\366\362\373\371\377\326\334\370\243\330\327\u0192\341\355\363\372\361\321\252\272\277\256\254\275\274\241\253\273".indexOf(c) >= 0 && serverAddress.length() < 32)
		{
        	serverAddress += c;
		}
        ((GuiButton)controlList.get(0)).enabled = serverAddress.length() > 0;
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Play Multiplayer", width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, "Minecraft Multiplayer is currently not finished, but there", width / 2 - 140, (height / 4 - 60) + 60 + 0, 0xa0a0a0);
        drawString(fontRenderer, "is some buggy early testing going on.", width / 2 - 140, (height / 4 - 60) + 60 + 9, 0xa0a0a0);
        drawString(fontRenderer, "Enter the IP of a server to connect to it:", width / 2 - 140, (height / 4 - 60) + 60 + 36, 0xa0a0a0);
        int k = width / 2 - 100;
        int l = (height / 4 - 10) + 50 + 18;
        char c = '\310';
        byte byte0 = 20;
        drawRect(k - 1, l - 1, k + c + 1, l + byte0 + 1, 0xffa0a0a0);
        drawRect(k, l, k + c, l + byte0, 0xff000000);
        drawString(fontRenderer, (new StringBuilder()).append(serverAddress).append((parentScreen / 6) % 2 != 0 ? "" : "_").toString(), k + 4, l + (byte0 - 8) / 2, 0xe0e0e0);
        super.drawScreen(i, j, f);
    }
}
