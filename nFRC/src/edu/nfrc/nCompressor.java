package edu.nfrc;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

public class nCompressor
{
    Relay compressorSpike;
    DigitalInput pressureCuttoff;
    
    public nCompressor(int compressorChannel, int pressureCutoffChannel)
    {
        compressorSpike = new Relay(4,compressorChannel,Relay.Direction.kForward);
        pressureCuttoff = new DigitalInput(pressureCutoffChannel);
    }

    public nCompressor(int compressorSlot, int compressorChannel, int pressureCutoffSlot, int pressureCutoffChannel)
    {
        compressorSpike = new Relay(compressorSlot,compressorChannel,Relay.Direction.kForward);
        pressureCuttoff = new DigitalInput(pressureCutoffSlot,pressureCutoffChannel);
    }

    public void start()
    {
        if(!pressureCuttoff.get())
        {
            compressorSpike.set(Relay.Value.kOn);
        }
        else
        {
            compressorSpike.set(Relay.Value.kOff);
        }
    }

    public void stop()
    {
        compressorSpike.set(Relay.Value.kOff);
    }

    public boolean getPressureCutoffState()
    {
        return pressureCuttoff.get();
    }
}
