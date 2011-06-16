package edu.nfrc;

import edu.wpi.first.wpilibj.DigitalInput;

public class nLimitSwitch extends DigitalInput
{
    public nLimitSwitch(int channel)
    {
        super(channel);
    }

    public nLimitSwitch(int slot, int channel)
    {
        super(slot,channel);
    }

    public boolean get()
    {
        return super.get();
    }

    public boolean compare(nLimitSwitch n)
    {
        if(get() == n.get())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
