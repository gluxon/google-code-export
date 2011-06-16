package edu.nfrc;

import edu.wpi.first.wpilibj.SpeedController;

public class nArm
{
    SpeedController speedController1;
   
    public nArm(SpeedController s)
    {
        speedController1 = s;
    }

    public void move(double speed)
    {
        speedController1.set(speed);
    }
}
