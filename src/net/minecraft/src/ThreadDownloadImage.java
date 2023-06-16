package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

class ThreadDownloadImage extends Thread
{
	public static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
    ThreadDownloadImage(ThreadDownloadImageData threaddownloadimagedata, String s, ImageBuffer imagebuffer)
    {
        imageData = threaddownloadimagedata;
        location = s;
        buffer = imagebuffer;
    }

    public void run()
    {
        HttpURLConnection httpurlconnection = null;
        try
        {
            URL url = new URL(location);
            httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestProperty("User-Agent", USER_AGENT);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();
            if(httpurlconnection.getResponseCode() == 404 || httpurlconnection.getResponseCode() == 403)
            {
                return;
            }
            if(buffer == null)
            {
                imageData.image = ImageIO.read(httpurlconnection.getInputStream());
            } else
            {
                imageData.image = buffer.func_883_a(ImageIO.read(httpurlconnection.getInputStream()));
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            httpurlconnection.disconnect();
        }
    }

    final String location; /* synthetic field */
    final ImageBuffer buffer; /* synthetic field */
    final ThreadDownloadImageData imageData; /* synthetic field */
}
