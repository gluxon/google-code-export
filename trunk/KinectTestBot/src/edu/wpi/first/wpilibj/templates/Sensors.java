
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Sensors
{
    //Variables
    private final double DISTANCE_PER_PULSE = .196*Math.PI/1440; //1440 according to guy on Delphi stie in meters

    //Analog Sensors
    private UltrasonicFHS ultrasonic;
    private Gyro gyro;

    //Digital Sensors
    private Encoder encoderTop;
	private Encoder encoderBottom;

    public Sensors()
    {
		//Analog Sensors
		gyro = new Gyro(1,1);
		ultrasonic = new UltrasonicFHS(1,2);

		//Digital Sensors
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
		if (x == 1) {
			return encoderTop;
		}
		else {
			return encoderBottom;
		}
    }

    public UltrasonicFHS getUltrasonic()
    {
	return ultrasonic;
    }
}
