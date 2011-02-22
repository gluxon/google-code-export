package edu.fhs.input;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SensorBase;

public class EncoderFHS extends SensorBase implements PIDSource
{
    private static final double biasVoltage = 2.25;
    private AnalogChannel sinChannel;
    private AnalogChannel cosChannel;

    public EncoderFHS( int slotParam, int sinChannelParam, int cosChannelParam)
    {
        sinChannel = new AnalogChannel( slotParam, sinChannelParam );
        cosChannel = new AnalogChannel( slotParam, cosChannelParam );
    }

    protected double getCos()
    {
        return cosChannel.getVoltage() - biasVoltage;
    }

    protected double getSin()
    {
        return sinChannel.getVoltage() - biasVoltage;
    }

    public double getAngle()
    {
        // calculate the angle
        double theta = MathUtils.atan2( getSin(), getCos() );
        // convert to degrees
        return Math.toDegrees(theta) + 180.0;
    }

    /**
     * Get the angle of the encoder for use with PIDControllers
     * @return the current angle according to the encoder
     */
    public double pidGet() {
        return getAngle();
    }


}


