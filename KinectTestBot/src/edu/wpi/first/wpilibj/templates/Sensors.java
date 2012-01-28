
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
    private Encoder encoder;
    
    public Sensors()
    {
	//Analog Sensors
        gyro = new Gyro(1,1);
	ultrasonic = new UltrasonicFHS(1,2);
	    
	//Digital Sensors
	encoder = new Encoder(6, 7);
        encoder.start();
        encoder.setDistancePerPulse(DISTANCE_PER_PULSE);
    }
    
    public Gyro getGyro()
    {
	return gyro;
    }
    
    public Encoder getEncoder()
    {
	return encoder;
    }
    
    public UltrasonicFHS getUltrasonic()
    {
	return ultrasonic;
    }
}
