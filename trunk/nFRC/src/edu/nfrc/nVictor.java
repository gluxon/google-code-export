package edu.nfrc;

import edu.wpi.first.wpilibj.Victor;

public class nVictor extends Victor
{
    private double maxSpeed;

    public nVictor(int channel)
    {
        super(channel);
    }

    public nVictor(int slot, int channel)
    {
        super(slot,channel);
    }

    public void set(double speed)
    {
        if(speed <= maxSpeed && speed >= -maxSpeed)
        {
            super.set(speed);
        }
    }

    public void setMaxSpeed(double speed)
    {
        maxSpeed = speed;
    }

    public void sync(Victor v)
    {
        set(v.get());
    }
}
