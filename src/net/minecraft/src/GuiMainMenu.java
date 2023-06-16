package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBVertexBlend;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;

public class GuiMainMenu extends GuiScreen
{

    public GuiMainMenu()
    {
        splashText = "missingno";
        try
        {
            ArrayList<String> arraylist = new ArrayList<String>();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((GuiMainMenu.class).getResourceAsStream("/title/splashes.txt")));
            do
            {
                String s1;
                if((s1 = bufferedreader.readLine()) == null)
                {
                    break;
                }
                s1 = s1.trim();
                if(s1.length() > 0)
                {
                    arraylist.add(s1);
                }
            } while(true);
            splashText = (String)arraylist.get(rand.nextInt(arraylist.size()));
        }
        catch(Exception exception) { }
    }

    public void updateScreen()
    {
        if(logoEffects != null)
        {
            for(int i = 0; i < logoEffects.length; i++)
            {
                for(int j = 0; j < logoEffects[i].length; j++)
                {
                    logoEffects[i][j].func_875_a();
                }

            }

        }
    }

    protected void keyTyped(char c, int i)
    {
    }

    public void initGui()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if(calendar.get(2) + 1 == 11 && calendar.get(5) == 9)
        {
            splashText = "Happy birthday, ez!";
        } else
        if(calendar.get(2) + 1 == 6 && calendar.get(5) == 1)
        {
            splashText = "Happy birthday, Notch!";
        } else
        if(calendar.get(2) + 1 == 12 && calendar.get(5) == 24)
        {
            splashText = "Merry X-mas!";
        } else
        if(calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
        {
            splashText = "Happy new year!";
        }
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 48, "Singleplayer"));
        controlList.add(new GuiButton(2, width / 2 - 100, height / 4 + 72, "Multiplayer"));
        controlList.add(new GuiButton(3, width / 2 - 100, height / 4 + 96, "Options..."));
        controlList.add(new GuiButton(4, 4, height - 24, 55, 20, "Set Name"));
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, "Quit game..."));
        if(mc.field_6320_i == null)
        {
            ((GuiButton)controlList.get(1)).enabled = false;
        }
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == 0)
        {
        	try {
				Mouse.create();
			} catch (LWJGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	Mouse.destroy();
			Keyboard.destroy();
            Display.destroy();
            int i = 0;
            while(i<100) {
            	i++;
            }
            System.exit(0);
            
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if(guibutton.id == 2)
        {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if(guibutton.id == 3)
        {
        	mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }
        if(guibutton.id == 4)
        {
            mc.displayGuiScreen(new GuiSetName(this));
        }
        
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        Tessellator tessellator = Tessellator.instance;
        drawLogo(f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gui/logo.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.setColorOpaque_I(color(EnumColor.WHITE));
        GL11.glPushMatrix();
        GL11.glTranslatef(width / 2 + 90, 70F, 0.0F);
        GL11.glRotatef(-20F, 0.0F, 0.0F, 1.0F);
        float f1 = 1.8F - MathHelper.abs(MathHelper.sin(((float)(System.currentTimeMillis() % 1000L) / 1000F) * 3.141593F * 2.0F) * 0.1F);
        f1 = (f1 * 100F) / (float)(fontRenderer.getStringWidth(splashText) + 32);
        GL11.glScalef(f1, f1, f1);
        drawCenteredString(fontRenderer, splashText, 0, -8, color(EnumColor.YELLOW));
        GL11.glPopMatrix();
        drawString(fontRenderer, Minecraft.version, 2, 2, color(EnumColor.DARKGREY));
        String s = "Copyright Mojang Specifications. Do not distribute.";
        drawString(fontRenderer, s, width - fontRenderer.getStringWidth(s) - 2, height - 10, color(EnumColor.WHITE));
        super.drawScreen(i, j, f);
    }

    private void drawLogo(float f)
    {
        if(logoEffects == null)
        {
            logoEffects = new LogoEffectRandomizer[minecraftLogo[0].length()][minecraftLogo.length];
            for(int i = 0; i < logoEffects.length; i++)
            {
                for(int j = 0; j < logoEffects[i].length; j++)
                {
                    logoEffects[i][j] = new LogoEffectRandomizer(this, i, j);
                }

            }

        }
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ScaledResolution scaledresolution = new ScaledResolution(mc.displayWidth, mc.displayHeight);
        int k = 120 * scaledresolution.scaleFactor;
        GLU.gluPerspective(70F, (float)mc.displayWidth / (float)k, 0.05F, 100F);
        GL11.glViewport(0, mc.displayHeight - k, mc.displayWidth, k);
        GL11.glMatrixMode(ARBVertexBlend.GL_MODELVIEW0_ARB);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glDepthMask(true);
        RenderBlocks renderblocks = new RenderBlocks();
        for(int l = 0; l < 3; l++)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.4F, 0.6F, -13F);
            if(l == 0)
            {
                GL11.glClear(256);
                GL11.glTranslatef(0.0F, -0.4F, 0.0F);
                GL11.glScalef(0.98F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(770, 771);
            }
            if(l == 1)
            {
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glClear(256);
            }
            if(l == 2)
            {
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(768, 1);
            }
            GL11.glScalef(1.0F, -1F, 1.0F);
            GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(0.89F, 1.0F, 0.4F);
            GL11.glTranslatef((float)(-minecraftLogo[0].length()) * 0.5F, (float)(-minecraftLogo.length) * 0.5F, 0.0F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/terrain.png"));
            if(l == 0)
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/title/black.png"));
            }
            for(int i1 = 0; i1 < minecraftLogo.length; i1++)
            {
                for(int j1 = 0; j1 < minecraftLogo[i1].length(); j1++)
                {
                    char c = minecraftLogo[i1].charAt(j1);
                    if(c == ' ')
                    {
                        continue;
                    }
                    GL11.glPushMatrix();
                    LogoEffectRandomizer logoeffectrandomizer = logoEffects[j1][i1];
                    float f1 = (float)(logoeffectrandomizer.field_1311_b + (logoeffectrandomizer.field_1312_a - logoeffectrandomizer.field_1311_b) * (double)f);
                    float f2 = 1.0F;
                    float f3 = 1.0F;
                    float f4 = 0.0F;
                    if(l == 0)
                    {
                        f2 = f1 * 0.04F + 1.0F;
                        f3 = 1.0F / f2;
                        f1 = 0.0F;
                    }
                    GL11.glTranslatef(j1, i1, f1);
                    GL11.glScalef(f2, f2, f2);
                    GL11.glRotatef(f4, 0.0F, 1.0F, 0.0F);
                    renderblocks.func_1238_a(Block.stone, f3);
                    GL11.glPopMatrix();
                }

            }

            GL11.glPopMatrix();
        }

        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glMatrixMode(5889 /*GL_PROJECTION*/);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
        GL11.glPopMatrix();
        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
    }

    static Random getRand()
    {
        return rand;
    }

    private static final Random rand = new Random();
    String minecraftLogo[] = {
        " *   * * *   * *** *** *** *** *** ***", 
        " ** ** * **  * *   *   * * * * *    * ",
        " * * * * * * * **  *   **  *** **   * ", 
        " *   * * *  ** *   *   * * * * *    * ", 
        " *   * * *   * *** *** * * * * *    * "
    };
    private LogoEffectRandomizer logoEffects[][];
    private String splashText;

}
