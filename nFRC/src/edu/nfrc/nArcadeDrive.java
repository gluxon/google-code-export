package edu.nfrc;

import edu.wpi.first.wpilibj.SpeedController;

public class nArcadeDrive
{
    SpeedController speedController1;
    SpeedController speedController2;
    SpeedController speedController3;
    SpeedController speedController4;

    public nArcadeDrive(SpeedController s1, SpeedController s2)
    {
        speedController1 = s1;
        speedController2 = s2;
        speedController3 = null;
        speedController4 = null;
    }
    public void drive(double x, double y)
    {
        speedController1.set(-(x*1.0) + (y*1.0));
        speedController2.set(-(x*1.0) - (y*1.0));

        if(speedController3 != null && speedController4 != null)
        {
            speedController3.set(-(x*1.0) + (y*1.0));
            speedController4.set(-(x*1.0) - (y*1.0));
        }
    }
}
