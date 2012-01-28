package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class RobotTemplate extends IterativeRobot 
{
    //Variables
    private final double DISTANCE_PER_PULSE = .196*Math.PI/1440; //1440 according to guy on Delphi stie in meters
    private float rightHandZ, leftHandZ;
    
    //Controls
    private Kinect kinect;
    private Joystick joystick, auxJoystick;
    
    //Movement
    private Drivetrain drivetrain;
    
    //Dashboard
    private Dashboard dashboard;
    
    //Misc
    private Watchdog watchdog;
    
    //Analog Sensors
    private UltrasonicFHS ultrasonic;
    private Gyro gyro;
    private AnalogChannel m_analogChannel; 
    
    //Digital Sensors
    private Encoder encoder;

    public void robotInit() 
    {
	//Controls
	joystick = new Joystick(1);
	auxJoystick = new Joystick(2);
	    
	//Movement
	drivetrain = new Drivetrain(1,2,3,4,joystick,1.0);
            
	//Analog Sensors
        gyro = new Gyro(1,1);
	ultrasonic = new UltrasonicFHS(1,2);
	m_analogChannel = new AnalogChannel(1,3);
	    
	//Digital Sensors
	encoder = new Encoder(6, 7);
        encoder.start();
        encoder.setDistancePerPulse(DISTANCE_PER_PULSE);
	    
	//Misc.
        watchdog = Watchdog.getInstance();
        kinect = Kinect.getInstance();
    }

    public void autonomousPeriodic()
    {
        if (kinect.getNumberOfPlayers() == 1) 
	{
            rightHandZ = kinect.getSkeleton().GetHandRight().getZ();
            leftHandZ = kinect.getSkeleton().GetHandLeft().getZ();

            drivetrain.frontLeftSet(leftHandZ);
            drivetrain.rearLeftSet(leftHandZ);
            drivetrain.frontRightSet(-rightHandZ);
            drivetrain.rearRightSet(-rightHandZ);
        }
        else 
	{
            drivetrain.frontLeftSet(0.0);
            drivetrain.rearLeftSet(0.0);
            drivetrain.frontRightSet(0.0);
            drivetrain.rearRightSet(0.0);
        }

        watchdog.feed();
    }

    public void teleopPeriodic() 
    {
        drivetrain.drive();
        
        System.out.println((int)ultrasonic.getRangeInches()+" Inches");
        System.out.println(m_analogChannel.getAverageVoltage());
        System.out.println(encoder.getDistance());
        System.out.println(gyro.getAngle());
        
	watchdog.feed();
    }
}
  