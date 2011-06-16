package edu.nfrc;

import edu.wpi.first.wpilibj.SpeedController;

public class nTankDrive
{
    SpeedController speedController1;
    SpeedController speedController2;
    SpeedController speedController3;
    SpeedController speedController4;

    public nTankDrive(SpeedController s1, SpeedController s2)
    {
        speedController1 = s1;
        speedController2 = s2;
        speedController3 = null;
        speedController4 = null;
    }

    public void drive(double left, double right)
    {
        speedController1.set(left);
        speedController2.set(right);

        if(speedController3 != null && speedController4 != null)
        {
            speedController3.set(left);
            speedController4.set(right);
        }
    }
}
