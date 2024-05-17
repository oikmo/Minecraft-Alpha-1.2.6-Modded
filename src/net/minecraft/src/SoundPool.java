package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.*;

public class SoundPool
{

    public SoundPool()
    {
        rand = new Random();
        nameToSoundPoolEntriesMapping = new HashMap<String, ArrayList<?>>();
        allSoundPoolEntries = new ArrayList<SoundPoolEntry>();
        numberOfSoundPoolEntries = 0;
        isGetRandomSound = true;
    }

    public SoundPoolEntry addSound(String s, File file)
    {
        try
        {
            String s1 = s;
            s = s.substring(0, s.indexOf("."));
            if(isGetRandomSound)
            {
                for(; Character.isDigit(s.charAt(s.length() - 1)); s = s.substring(0, s.length() - 1)) { }
            }
            s = s.replaceAll("/", ".");
            if(!nameToSoundPoolEntriesMapping.containsKey(s))
            {
                nameToSoundPoolEntriesMapping.put(s, new ArrayList<Object>());
            }
            SoundPoolEntry soundpoolentry = new SoundPoolEntry(s1, file.toURI().toURL());
            ((List<SoundPoolEntry>)nameToSoundPoolEntriesMapping.get(s)).add(soundpoolentry);
            allSoundPoolEntries.add(soundpoolentry);
            numberOfSoundPoolEntries++;
            return soundpoolentry;
        }
        catch(MalformedURLException malformedurlexception)
        {
            malformedurlexception.printStackTrace();
            throw new RuntimeException(malformedurlexception);
        }
    }

    public SoundPoolEntry getRandomSoundFromSoundPool(String s)
    {
        List<?> list = (List<?>)nameToSoundPoolEntriesMapping.get(s);
        if(list == null)
        {
            return null;
        } else
        {
            return (SoundPoolEntry)list.get(rand.nextInt(list.size()));
        }
    }

    public SoundPoolEntry getRandomSound()
    {
        if(allSoundPoolEntries.size() == 0)
        {
            return null;
        } else
        {
            return (SoundPoolEntry)allSoundPoolEntries.get(rand.nextInt(allSoundPoolEntries.size()));
        }
    }

    private Random rand;
    private Map<String, ArrayList<?>> nameToSoundPoolEntriesMapping;
    private List<SoundPoolEntry> allSoundPoolEntries;
    public int numberOfSoundPoolEntries;
    public boolean isGetRandomSound;
}
