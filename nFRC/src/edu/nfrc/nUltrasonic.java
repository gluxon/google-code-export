package edu.nfrc;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDSource;

public class nUltrasonic extends AnalogChannel implements PIDSource
{
    private final double SCALE_FACTOR = (1/.00952); //to be changed based on results from testing the UltraSonic
    private final double OFFSET = 0.0; //to be changed based on results from testing


    public nUltrasonic(int slot, int channel)
    {
        super(slot, channel);
    }

    public nUltrasonic(int channel)
    {
        super(channel);
    }


    public double getRangeInches()
    {
        return getAverageVoltage()*SCALE_FACTOR-OFFSET;
    }

    public double getRangeMM()
    {
        return getRangeInches() * 25.4;
    }

    public double pidGet()
    {
        return getRangeInches();
    }
}
