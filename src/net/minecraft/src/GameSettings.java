package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GameSettings
{
	private static final String RENDER_DISTANCES[] = {
        "FAR", "NORMAL", "SHORT", "TINY"
	};
	private static final String DIFFICULTY_LEVELS[] = {
        "Peaceful", "Easy", "Normal", "Hard"
    };
    public float musicVolume;
    public float soundVolume;
    public float mouseSensitivity;
    public boolean invertMouse;
    public int renderDistance;
    public boolean viewBobbing;
    public boolean anaglyph;
    public boolean limitFramerate;
    public boolean fancyGraphics;
    public String skin;
    public KeyBinding keyBindForward;
    public KeyBinding keyBindLeft;
    public KeyBinding keyBindBack;
    public KeyBinding keyBindRight;
    public KeyBinding keyBindJump;
    public KeyBinding keyBindInventory;
    public KeyBinding keyBindDrop;
    public KeyBinding keyBindChat;
    public KeyBinding keyBindToggleFog;
    public KeyBinding keyBindSneak;
    public KeyBinding keyBindSprint;
    public KeyBinding keyBindings[];
    public float fovSetting;
    protected Minecraft mc;
    private File optionsFile;
    public int numberOfOptions;
    public int difficulty;
    public boolean thirdPersonView;
    public String lastServer;
    public String name;
	
    public GameSettings(Minecraft minecraft, File file)
    {
        musicVolume = 1.0F;
        soundVolume = 1.0F;
        mouseSensitivity = 0.5F;
        fovSetting = 90f;
        invertMouse = false;
        renderDistance = 0;
        viewBobbing = true;
        anaglyph = false;
        limitFramerate = false;
        fancyGraphics = true;
        skin = "Default";
        keyBindForward = new KeyBinding("Forward", Keyboard.KEY_W);
        keyBindLeft = new KeyBinding("Left", Keyboard.KEY_A);
        keyBindBack = new KeyBinding("Back", Keyboard.KEY_S);
        keyBindRight = new KeyBinding("Right", Keyboard.KEY_D);
        keyBindJump = new KeyBinding("Jump", Keyboard.KEY_SPACE);
        keyBindInventory = new KeyBinding("Inventory", Keyboard.KEY_I);
        keyBindDrop = new KeyBinding("Drop", Keyboard.KEY_Q);
        keyBindChat = new KeyBinding("Chat", Keyboard.KEY_T);
        keyBindToggleFog = new KeyBinding("Toggle fog", Keyboard.KEY_F);
        keyBindSneak = new KeyBinding("Sneak", Keyboard.KEY_LSHIFT);
        keyBindSprint = new KeyBinding("Sprint", Keyboard.KEY_LCONTROL);
        keyBindings = (new KeyBinding[] {
            keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat, keyBindToggleFog, keyBindSprint
        });
        numberOfOptions = 11;
        difficulty = 2;
        thirdPersonView = false;
        lastServer = "";
        name = "player";
        
        mc = minecraft;
        optionsFile = new File(file, "options.txt");
        loadOptions();
    }

    public GameSettings()
    {
        musicVolume = 1.0F;
        soundVolume = 1.0F;
        mouseSensitivity = 0.5F;
        fovSetting = 90f;
        invertMouse = false;
        renderDistance = 0;
        viewBobbing = true;
        anaglyph = false;
        limitFramerate = false;
        fancyGraphics = true;
        skin = "Default";
        keyBindForward = new KeyBinding("Forward", Keyboard.KEY_W);
        keyBindLeft = new KeyBinding("Left", Keyboard.KEY_A);
        keyBindBack = new KeyBinding("Back", Keyboard.KEY_S);
        keyBindRight = new KeyBinding("Right", Keyboard.KEY_D);
        keyBindJump = new KeyBinding("Jump", Keyboard.KEY_SPACE);
        keyBindInventory = new KeyBinding("Inventory", Keyboard.KEY_I);
        keyBindDrop = new KeyBinding("Drop", Keyboard.KEY_Q);
        keyBindChat = new KeyBinding("Chat", Keyboard.KEY_T);
        keyBindToggleFog = new KeyBinding("Toggle fog", Keyboard.KEY_F);
        keyBindSneak = new KeyBinding("Sneak", Keyboard.KEY_LSHIFT);
        keyBindSprint = new KeyBinding("Sprint", Keyboard.KEY_LCONTROL);
        keyBindings = (new KeyBinding[] {
            keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat, keyBindToggleFog, keyBindSprint
        });
        numberOfOptions = 11;
        difficulty = 2;
        thirdPersonView = false;
        lastServer = "";
        name = "player";
        
    }

    public String getKeyBinding(int i)
    {
        return (new StringBuilder()).append(keyBindings[i].keyDescription).append(": ").append(Keyboard.getKeyName(keyBindings[i].keyCode)).toString();
    }

    public void setKeyBinding(int i, int j)
    {
        keyBindings[i].keyCode = j;
        saveOptions();
    }

    public void setOptionFloatValue(int i, float f)
    {
        if(i == 0)
        {
            musicVolume = f;
            mc.sndManager.onSoundOptionsChanged();
        }
        if(i == 1)
        {
            soundVolume = f;
            mc.sndManager.onSoundOptionsChanged();
        }
        if(i == 2)
        {
            mouseSensitivity = f;
        }
        if(i == 3) {
        	fovSetting = f;
        }
    }

    

    public int getOptionControlType(int i)
    {
        if(i == 0)
        {
            return 1;
        }
        if(i == 1)
        {
            return 1;
        }
        if(i == 2)
        {
            return 1;
        }
        
        return i != 3 ? 0 : 1;
    }

    public float getOptionFloatValue(int i)
    {
        if(i == 0)
        {
            return musicVolume;
        }
        if(i == 1)
        {
            return soundVolume;
        }
        if(i == 2)
        {
            return mouseSensitivity;
        } 
        if(i == 3) {
        	return fovSetting;
        }
        else
        {
            return 0.0F;
        }
    }
    
    public void setOptionValue(int i, int j)
    {
        if(i == 4)
        {
            invertMouse = !invertMouse;
        }
        if(i == 5)
        {
            renderDistance = renderDistance + j & 3;
        }
        if(i == 6)
        {
            viewBobbing = !viewBobbing;
        }
        if(i == 7)
        {
            anaglyph = !anaglyph;
            mc.renderEngine.refreshTextures();
        }
        if(i == 8)
        {
            limitFramerate = !limitFramerate;
        }
        if(i == 9)
        {
            difficulty = difficulty + j & 3;
        }
        if(i == 10)
        {
            fancyGraphics = !fancyGraphics;
            mc.renderGlobal.loadRenderers();
        }
        saveOptions();
    }

    public String getOptionDisplayString(int i)
    {
        if(i == 0)
        {
            return (new StringBuilder()).append("Music: ").append(musicVolume <= 0.0F ? "OFF" : (new StringBuilder()).append((int)(musicVolume * 100F)).append("%").toString()).toString();
        }
        if(i == 1)
        {
            return (new StringBuilder()).append("Sound: ").append(soundVolume <= 0.0F ? "OFF" : (new StringBuilder()).append((int)(soundVolume * 100F)).append("%").toString()).toString();
        }
        if(i == 2)
        {
            if(mouseSensitivity == 0.0F)
            {
                return "Sensitivity: *yawn*";
            }
            if(mouseSensitivity == 1.0F)
            {
                return "Sensitivity: HYPERSPEED!!!";
            } else
            {
                return (new StringBuilder()).append("Sensitivity: ").append((int)(mouseSensitivity * 200F)).append("%").toString();
            }
        }
        if(i == 3) {
         	 if(fovSetting == 0.0F)
              {
                  return "FOV: psycho.";
              }
              if(fovSetting == 1.0F)
              {
                  return "FOV: QUAKE PRO";
              } else
              {
                  return (new StringBuilder()).append("FOV: ").append((int)((fovSetting * 100F))).toString();
              }
        }
        if(i == 4)
        {
            return (new StringBuilder()).append("Invert mouse: ").append(invertMouse ? "ON" : "OFF").toString();
        }
        if(i == 5)
        {
            return (new StringBuilder()).append("Render distance: ").append(RENDER_DISTANCES[renderDistance]).toString();
        }
        if(i == 6)
        {
            return (new StringBuilder()).append("View bobbing: ").append(viewBobbing ? "ON" : "OFF").toString();
        }
        if(i == 7)
        {
            return (new StringBuilder()).append("3d anaglyph: ").append(anaglyph ? "ON" : "OFF").toString();
        }
        if(i == 8)
        {
            return (new StringBuilder()).append("Limit framerate: ").append(limitFramerate ? "ON" : "OFF").toString();
        }
        if(i == 9)
        {
            return (new StringBuilder()).append("Difficulty: ").append(DIFFICULTY_LEVELS[difficulty]).toString();
        }
        if(i == 10)
        {
            return (new StringBuilder()).append("Graphics: ").append(fancyGraphics ? "FANCY" : "FAST").toString();
        } 
        
        else
        {
            return "";
        }
        
    }

    public void loadOptions()
    {
        try
        {
            if(!optionsFile.exists())
            {
                return;
            }
            BufferedReader bufferedreader = new BufferedReader(new FileReader(optionsFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                String as[] = s.split(":");
                if(as[0].equals("music"))
                {
                    musicVolume = parseFloat(as[1]);
                }
                if(as[0].equals("sound"))
                {
                    soundVolume = parseFloat(as[1]);
                }
                if(as[0].equals("mouseSensitivity"))
                {
                    mouseSensitivity = parseFloat(as[1]);
                }
                if(as[0].equals("fov")) {
                	fovSetting = parseFloat(as[1]);
                }
                if(as[0].equals("invertYMouse"))
                {
                    invertMouse = as[1].equals("true");
                }
                if(as[0].equals("viewDistance"))
                {
                    renderDistance = Integer.parseInt(as[1]);
                }
                if(as[0].equals("bobView"))
                {
                    viewBobbing = as[1].equals("true");
                }
                if(as[0].equals("anaglyph3d"))
                {
                    anaglyph = as[1].equals("true");
                }
                if(as[0].equals("limitFramerate"))
                {
                    limitFramerate = as[1].equals("true");
                }
                if(as[0].equals("difficulty"))
                {
                    difficulty = Integer.parseInt(as[1]);
                }
                if(as[0].equals("fancyGraphics"))
                {
                    fancyGraphics = as[1].equals("true");
                }
                if(as[0].equals("skin"))
                {
                    skin = as[1];
                }
                if(as[0].equals("lastServer"))
                {
                    lastServer = as[1];
                }
                if(as[0].equals("name")) {
                	name = as[1];
                }
                
                int i = 0;
                while(i < keyBindings.length) 
                {
                    if(as[0].equals((new StringBuilder()).append("key_").append(keyBindings[i].keyDescription).toString()))
                    {
                        keyBindings[i].keyCode = Integer.parseInt(as[1]);
                    }
                    i++;
                }
            }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            System.out.println("Failed to load options");
            exception.printStackTrace();
        }
    }

    private float parseFloat(String s)
    {
        if(s.equals("true"))
        {
            return 1.0F;
        }
        if(s.equals("false"))
        {
            return 0.0F;
        } else
        {
            return Float.parseFloat(s);
        }
    }

    public void saveOptions()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(optionsFile));
            printwriter.println((new StringBuilder()).append("music:").append(musicVolume).toString());
            printwriter.println((new StringBuilder()).append("sound:").append(soundVolume).toString());
            printwriter.println((new StringBuilder()).append("invertYMouse:").append(invertMouse).toString());
            printwriter.println((new StringBuilder()).append("mouseSensitivity:").append(mouseSensitivity).toString());
            printwriter.println((new StringBuilder()).append("fov:").append(fovSetting).toString());
            printwriter.println((new StringBuilder()).append("viewDistance:").append(renderDistance).toString());
            printwriter.println((new StringBuilder()).append("bobView:").append(viewBobbing).toString());
            printwriter.println((new StringBuilder()).append("anaglyph3d:").append(anaglyph).toString());
            printwriter.println((new StringBuilder()).append("limitFramerate:").append(limitFramerate).toString());
            printwriter.println((new StringBuilder()).append("difficulty:").append(difficulty).toString());
            printwriter.println((new StringBuilder()).append("fancyGraphics:").append(fancyGraphics).toString());
            printwriter.println((new StringBuilder()).append("skin:").append(skin).toString());
            printwriter.println((new StringBuilder()).append("lastServer:").append(lastServer).toString());
            printwriter.println((new StringBuilder()).append("name:").append(name).toString());
            
            for(int i = 0; i < keyBindings.length; i++)
            {
                printwriter.println((new StringBuilder()).append("key_").append(keyBindings[i].keyDescription).append(":").append(keyBindings[i].keyCode).toString());
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            System.out.println("Failed to save options");
            exception.printStackTrace();
        }
    }

    

}
