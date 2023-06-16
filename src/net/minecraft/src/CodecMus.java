package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import paulscode.sound.codecs.CodecJOrbis;

public class CodecMus extends CodecJOrbis
{

    public CodecMus()
    {
    }

    protected InputStream openInputStream()
    {
        try {
			return new MusInputStream(this, url, urlConnection.getInputStream());
		} catch (IOException e) {
			return null;
		}
    }
}
