package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class MovementInputFromOptions extends MovementInput
{

    public MovementInputFromOptions(GameSettings gamesettings)
    {
        field_1179_f = new boolean[10];
        field_1178_g = gamesettings;
    }

    public void func_796_a(int i, boolean flag)
    {
        byte byte0 = -1;
        if(i == field_1178_g.keyBindForward.keyCode)
        {
            byte0 = 0;
        }
        if(i == field_1178_g.keyBindBack.keyCode)
        {
            byte0 = 1;
        }
        if(i == field_1178_g.keyBindLeft.keyCode)
        {
            byte0 = 2;
        }
        if(i == field_1178_g.keyBindRight.keyCode)
        {
            byte0 = 3;
        }
        if(i == field_1178_g.keyBindJump.keyCode)
        {
            byte0 = 4;
        }
        if(i == field_1178_g.keyBindSneak.keyCode)
        {
            byte0 = 5;
        }
        if(byte0 >= 0)
        {
            field_1179_f[byte0] = flag;
        }
    }

    public void func_798_a()
    {
        for(int i = 0; i < 10; i++)
        {
            field_1179_f[i] = false;
        }

    }

    public void updatePlayerMoveState(EntityPlayer entityplayer)
    {
        moveStrafe = 0.0F;
        field_1173_b = 0.0F;
        if(field_1179_f[0])
        {
            field_1173_b++;
        }
        if(field_1179_f[1])
        {
            field_1173_b--;
        }
        if(field_1179_f[2])
        {
            moveStrafe++;
        }
        if(field_1179_f[3])
        {
            moveStrafe--;
        }
        jump = field_1179_f[4];
        sneak = field_1179_f[5];
        if(sneak)
        {
            moveStrafe *= 0.29999999999999999D;
            field_1173_b *= 0.29999999999999999D;
        }
    }

    private boolean field_1179_f[];
    private GameSettings field_1178_g;
}
