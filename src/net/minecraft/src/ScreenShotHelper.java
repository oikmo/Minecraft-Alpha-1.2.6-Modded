package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenShotHelper
{

    public ScreenShotHelper()
    {
    }

    public static String func_4148_a(File file, int i, int j)
    {
        try
        {
            File file1 = new File(file, "screenshots");
            file1.mkdir();
            if(field_4286_b == null || field_4286_b.capacity() < i * j * 3)
            {
                field_4286_b = BufferUtils.createByteBuffer(i * j * 3);
                field_4289_c = new byte[i * j * 3];
                field_4288_d = new int[i * j];
            }
            GL11.glPixelStorei(3333 /*GL_PACK_ALIGNMENT*/, 1);
            GL11.glPixelStorei(3317 /*GL_UNPACK_ALIGNMENT*/, 1);
            field_4286_b.clear();
            GL11.glReadPixels(0, 0, i, j, 6407 /*GL_RGB*/, 5121 /*GL_UNSIGNED_BYTE*/, field_4286_b);
            field_4286_b.clear();
            String s = (new StringBuilder()).append("").append(field_4287_a.format(new Date())).toString();
            File file2;
            for(int k = 1; (file2 = new File(file1, (new StringBuilder()).append(s).append(k != 1 ? (new StringBuilder()).append("_").append(k).toString() : "").append(".png").toString())).exists(); k++) { }
            field_4286_b.get(field_4289_c);
            for(int l = 0; l < i; l++)
            {
                for(int i1 = 0; i1 < j; i1++)
                {
                    int j1 = l + (j - i1 - 1) * i;
                    int k1 = field_4289_c[j1 * 3 + 0] & 0xff;
                    int l1 = field_4289_c[j1 * 3 + 1] & 0xff;
                    int i2 = field_4289_c[j1 * 3 + 2] & 0xff;
                    int j2 = 0xff000000 | k1 << 16 | l1 << 8 | i2;
                    field_4288_d[l + i1 * i] = j2;
                }

            }

            BufferedImage bufferedimage = new BufferedImage(i, j, 1);
            bufferedimage.setRGB(0, 0, i, j, field_4288_d, 0, i);
            ImageIO.write(bufferedimage, "png", file2);
            return (new StringBuilder()).append("Saved screenshot as ").append(file2.getName()).toString();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return (new StringBuilder()).append("Failed to save: ").append(exception).toString();
        }
    }

    private static DateFormat field_4287_a = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static ByteBuffer field_4286_b;
    private static byte field_4289_c[];
    private static int field_4288_d[];

}
