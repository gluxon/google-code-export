package edu.nfrc;

import edu.wpi.first.wpilibj.SpeedController;

public class nLimitSwitchArm extends nArm
{
    nLimitSwitch upperLimitSwitch;
    nLimitSwitch lowerLimitSwitch;
    
    public nLimitSwitchArm(SpeedController s, nLimitSwitch upper, nLimitSwitch lower)
    {
        super(s);
        upperLimitSwitch = upper;
        lowerLimitSwitch = lower;
    }

    public void move(double speed)
    {
        if(speed > 0 && !upperLimitSwitch.get())
        {
            super.move(speed);
        }
        else if(speed < 0 && !lowerLimitSwitch.get())
        {
            super.move(speed);
        }
        else
        {
            super.move(0.0);
        }
    }

    public boolean getLower()
    {
        return lowerLimitSwitch.get();
    }

    public boolean getUpper()
    {
        return upperLimitSwitch.get();
    }
}
