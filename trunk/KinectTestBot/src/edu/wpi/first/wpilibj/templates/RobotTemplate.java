package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class RobotTemplate extends IterativeRobot 
{
    private Joystick joystick, auxJoystick;
    private KinectFHS kinect;
    
    private Drivetrain drivetrain;
    private Sensors sensors;
    private CameraFHS camera;
    
    private Dashboard dashboard;
    private Watchdog watchdog;

    public void robotInit() 
    {
	joystick = new Joystick(1);
	auxJoystick = new Joystick(2);
	kinect = new KinectFHS(drivetrain);
	    
	drivetrain = new Drivetrain(1,2,3,4,joystick,1.0);   
	sensors = new Sensors();
	camera = new CameraFHS(drivetrain);
	    
        watchdog = Watchdog.getInstance();
    }
    
    public void autonomousPeriodic()
    {
	//kinect.autonomousKinect();
	
	try 
	{
	    camera.centerOnFirstTarget();
	} 
	catch (AxisCameraException ex) 
	{
	    ex.printStackTrace();
	} 
	catch (NIVisionException ex) 
	{
	    ex.printStackTrace();
	}

        watchdog.feed();
    }

    public void teleopPeriodic() 
    {
        drivetrain.drive();
        
        //System.out.println((int)sensors.getUltrasonic().getRangeInches()+" Inches");
        //System.out.println(sensors.getEncoder().getDistance());
	System.out.println(sensors.getEncoder().getRate());
        //System.out.println(sensors.getGyro().getAngle());
        
	watchdog.feed();
    }
}
  