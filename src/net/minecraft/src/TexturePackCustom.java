package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class TexturePackCustom extends TexturePackBase
{

    public TexturePackCustom(File file) throws IOException
    {
        texturePackName = -1;
        texturePackFileName = file.getName();
        field_6493_h = file;
    }

    private String func_6492_b(String s)
    {
        if(s != null && s.length() > 34)
        {
            s = s.substring(0, 34);
        }
        return s;
    }

    public void func_6485_a(Minecraft minecraft)
    {
        ZipFile zipfile = null;
        InputStream inputstream = null;
        try
        {
            zipfile = new ZipFile(field_6493_h);
            try
            {
                inputstream = zipfile.getInputStream(zipfile.getEntry("pack.txt"));
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
                firstDescriptionLine = func_6492_b(bufferedreader.readLine());
                secondDescriptionLine = func_6492_b(bufferedreader.readLine());
                bufferedreader.close();
                inputstream.close();
            }
            catch(Exception exception) { }
            try
            {
                inputstream = zipfile.getInputStream(zipfile.getEntry("pack.png"));
                field_6494_g = ImageIO.read(inputstream);
                inputstream.close();
            }
            catch(Exception exception1) { }
            zipfile.close();
        }
        catch(Exception exception2)
        {
            exception2.printStackTrace();
        }
        finally
        {
            try
            {
                inputstream.close();
            }
            catch(Exception exception4) { }
            try
            {
                zipfile.close();
            }
            catch(Exception exception5) { }
        }
    }

    public void func_6484_b(Minecraft minecraft)
    {
        if(field_6494_g != null)
        {
            minecraft.renderEngine.deleteTexture(texturePackName);
        }
        closeTexturePackFile();
    }

    public void func_6483_c(Minecraft minecraft)
    {
        if(field_6494_g != null && texturePackName < 0)
        {
            texturePackName = minecraft.renderEngine.allocateAndSetupTexture(field_6494_g);
        }
        if(field_6494_g != null)
        {
            minecraft.renderEngine.bindTexture(texturePackName);
        } else
        {
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, minecraft.renderEngine.getTexture("/gui/unknown_pack.png"));
        }
    }

    public void func_6482_a()
    {
        try
        {
            field_6496_e = new ZipFile(field_6493_h);
        }
        catch(Exception exception) { }
    }

    public void closeTexturePackFile()
    {
        try
        {
            field_6496_e.close();
        }
        catch(Exception exception) { }
        field_6496_e = null;
    }

    public InputStream func_6481_a(String s)
    {
        try
        {
            java.util.zip.ZipEntry zipentry = field_6496_e.getEntry(s.substring(1));
            if(zipentry != null)
            {
                return field_6496_e.getInputStream(zipentry);
            }
        }
        catch(Exception exception) { }
        return (TexturePackBase.class).getResourceAsStream(s);
    }

    private ZipFile field_6496_e;
    private int texturePackName;
    private BufferedImage field_6494_g;
    private File field_6493_h;
}
