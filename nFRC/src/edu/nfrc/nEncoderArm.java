package edu.nfrc;

import edu.wpi.first.wpilibj.SpeedController;

public class nEncoderArm extends nArm
{
    double upperRotationLimit;
    double lowerRotationLimit;
    nEncoder encoder;
    
    public nEncoderArm(SpeedController s, nEncoder enc, double upperRot, double lowerRot)
    {
        super(s);
        upperRotationLimit = upperRot;
        lowerRotationLimit = lowerRot;
        encoder = enc;
    }

    public void move(double speed)
    {
        if(speed > 0 && encoder.getAngle() < upperRotationLimit)
        {
            super.move(speed);
        }
        else if(speed < 0 && encoder.getAngle() > lowerRotationLimit)
        {
            super.move(speed);
        }
        else
        {
            super.move(0.0);
        }
    }

    public double getRotation()
    {
        return encoder.getAngle();
    }
}
