
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Sensors
{
    private final double DISTANCE_PER_PULSE = .196*Math.PI/1440; //1440 according to guy on Delphi stie in meters

    private UltrasonicFHS ultrasonic;
    private Gyro gyro;

    private Encoder encoderTop;
    private Encoder encoderBottom;

    public Sensors()
    {
	gyro = new Gyro(1, 2);
	ultrasonic = new UltrasonicFHS(1,3);

	encoderBottom = new Encoder(5, 6);
        encoderBottom.start();
        encoderBottom.setDistancePerPulse(DISTANCE_PER_PULSE);
    }

    public Gyro getGyro()
    {
	return gyro;
    }

    public Encoder getEncoder(int x)
    {
        if (x == 1) 
        {
            return encoderTop;
	}
        else 
        {
            return encoderBottom;
	}
    }

    public UltrasonicFHS getUltrasonic()
    {
	return ultrasonic;
    }
}
