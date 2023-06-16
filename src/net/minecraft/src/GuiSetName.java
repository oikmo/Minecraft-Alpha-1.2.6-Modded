package net.minecraft.src;

public class GuiSetName extends GuiScreen
{
	private GuiScreen updateCounter;
    private int parentScreen;
    private String name;
	
    public GuiSetName(GuiScreen guiscreen)
    {
        parentScreen = 0;
        name = "";
        updateCounter = guiscreen;
    }

    public void updateScreen()
    {
        parentScreen++;
    }

    public void initGui()
    {
    	System.out.println("ran!");
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Confirm"));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, "Cancel"));
        name = mc.gameSettings.name.replaceAll("_", ":");
        ((GuiButton)controlList.get(0)).enabled = name.length() > 0;
    }

    @SuppressWarnings("static-access")
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
        	mc.currentName = name;
            mc.gameSettings.name = name;
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(updateCounter);
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
	        j = 32 - name.length();
	        if(j > s.length())
	        {
	            j = s.length();
	        }
	        if(j > 0)
	        {
	        	name += s.substring(0, j); 
	        }
        }
        if(c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }
        if(i == 14 && name.length() > 0)
        {
        	name = name.substring(0, name.length() - 1);
        }
        if(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\307\374\351\342\344\340\345\347\352\353\350\357\356\354\304\305\311\346\306\364\366\362\373\371\377\326\334\370\243\330\327\u0192\341\355\363\372\361\321\252\272\277\256\254\275\274\241\253\273".indexOf(c) >= 0 && name.length() < 32)
		{
        	name += c;
		}
        ((GuiButton)controlList.get(0)).enabled = name.length() > 0;
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Set Player Name?", width / 2, (height / 4 - 60) + 20, color(EnumColor.WHITE));
        drawString(fontRenderer, "Since this is modded, minecraft launcher will not open it.", width / 2 - 140, (height / 4 - 60) + 60 + 0, color(EnumColor.GREY));
        drawCenteredString(fontRenderer, "However, you can set your player name here.", width / 2, (height / 4 - 60) + 60 + 9, color(EnumColor.GREY));
        drawCenteredString(fontRenderer, "Default is \"player\".", width /2, (height / 4 - 60) + 60 + 18, color(EnumColor.GREY));
        drawCenteredString(fontRenderer, "Enter your player name you want to use:", width / 2, (height / 4 - 60) + 60 + 36, color(EnumColor.GREY));
        int k = width / 2 - 100;
        int l = (height / 4 - 10) + 50 + 18;
        char c = '\310';
        byte byte0 = 20;
        drawRect(k - 1, l - 1, k + c + 1, l + byte0 + 1, color(EnumColor.FFGREY));
        drawRect(k, l, k + c, l + byte0, color(EnumColor.FFBLACK));
        //System.out.println();
        drawString(fontRenderer, (new StringBuilder()).append(name).append((parentScreen / 6) % 2 != 0 ? "" : "_").toString(), k + 4, l + (byte0 - 8) / 2, color(EnumColor.LIGHTGREY));
        super.drawScreen(i, j, f);
    }
}
