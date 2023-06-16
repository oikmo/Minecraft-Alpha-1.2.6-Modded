package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class GuiControls extends GuiScreen
{

    public GuiControls(GuiScreen guiscreen, GameSettings gamesettings)
    {
        screenTitle = "Controls";
        buttonId = -1;
        parentScreen = guiscreen;
        options = gamesettings;
    }

    public void initGui()
    {
        for(int i = 0; i < options.keyBindings.length; i++)
        {
            controlList.add(new GuiSmallButton(i, (width / 2 - 155) + (i % 2) * 160, height / 6 + 24 * (i >> 1), options.getKeyBinding(i)));
        }

        controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, "Done"));
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        for(int i = 0; i < options.keyBindings.length; i++)
        {
            ((GuiButton)controlList.get(i)).displayString = options.getKeyBinding(i);
        }

        if(guibutton.id == 200)
        {
            mc.displayGuiScreen(parentScreen);
        } else
        {
            buttonId = guibutton.id;
            guibutton.displayString = (new StringBuilder()).append("> ").append(options.getKeyBinding(guibutton.id)).append(" <").toString();
        }
    }

    protected void keyTyped(char c, int i)
    {
        if(buttonId >= 0)
        {
            options.setKeyBinding(buttonId, i);
            ((GuiButton)controlList.get(buttonId)).displayString = options.getKeyBinding(buttonId);
            buttonId = -1;
        } else
        {
            super.keyTyped(c, i);
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
        super.drawScreen(i, j, f);
    }

    private GuiScreen parentScreen;
    protected String screenTitle;
    private GameSettings options;
    private int buttonId;
}
