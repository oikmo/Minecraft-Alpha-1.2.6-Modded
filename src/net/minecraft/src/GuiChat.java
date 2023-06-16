package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.input.Keyboard;

public class GuiChat extends GuiScreen
{

    public GuiChat()
    {
        message = "";
        updateCounter = 0;
    }

    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen()
    {
        updateCounter++;
    }

    protected void keyTyped(char c, int i)
    {
        if(i == 1)
        {
            mc.displayGuiScreen(null);
            return;
        }
        if(i == 28)
        {
            String s = message.trim();
            if(s.length() > 0)
            {
                mc.thePlayer.sendChatMessage(message.trim());
            }
            mc.displayGuiScreen(null);
            return;
        }
        if(i == 14 && message.length() > 0)
        {
            message = message.substring(0, message.length() - 1);
        }
        if(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\307\374\351\342\344\340\345\347\352\353\350\357\356\354\304\305\311\346\306\364\366\362\373\371\377\326\334\370\243\330\327\u0192\341\355\363\372\361\321\252\272\277\256\254\275\274\241\253\273".indexOf(c) >= 0 && message.length() < 100)
        {
        	message += c;
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        drawRect(2, height - 14, width - 2, height - 2, 0x80000000);
        drawString(fontRenderer, (new StringBuilder()).append("> ").append(message).append((updateCounter / 6) % 2 != 0 ? "" : "_").toString(), 4, height - 12, 0xe0e0e0);
    }

    protected void mouseClicked(int i, int j, int k)
    {
        if(k != 0 || mc.ingameGUI.field_933_a == null)
        {
            return;
        }
        if(message.length() > 0 && !message.endsWith(" "))
        {
        	message += " ";
        }
        message += mc.ingameGUI.field_933_a;
        
        byte byte0 = 100;
        if(message.length() > byte0)
        {
            message = message.substring(0, byte0);
        }
    }

    private String message;
    private int updateCounter;
}
