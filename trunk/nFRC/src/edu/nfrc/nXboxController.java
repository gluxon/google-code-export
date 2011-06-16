package edu.nfrc;

import edu.wpi.first.wpilibj.Joystick;

public class nXboxController extends Joystick
{
    public nXboxController(int channel)
    {
        super(channel);
    }

    public nXboxController(int channel, int sens)
    {
        super(channel);
    }
}
