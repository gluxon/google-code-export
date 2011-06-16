package edu.nfrc;

import edu.wpi.first.wpilibj.Jaguar;

public class nJaguar extends Jaguar
{
    private double maxSpeed;

    public nJaguar(int channel)
    {
        super(channel);
    }

    public nJaguar(int slot, int channel)
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

    public void sync(Jaguar v)
    {
        set(v.get());
    }
}
