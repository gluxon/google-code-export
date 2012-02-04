package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class RobotTemplate extends IterativeRobot 
{
    private Joystick joystick, auxJoystick;
    private KinectFHS kinect;
    
    private Drivetrain drivetrain;
    private Sensors sensors;
    //private CameraFHS camera;
    
    private Dashboard dashboard;
    private Watchdog watchdog;
    
    //private ImageAnalysis imageAnalysis;

    public void robotInit() 
    {
	joystick = new Joystick(1);
	auxJoystick = new Joystick(2);
	kinect = new KinectFHS(drivetrain);
	  
	drivetrain = new Drivetrain(1,2,3,4,joystick,1.0);   
	sensors = new Sensors();
	//camera = new CameraFHS(drivetrain);
        
        //imageAnalysis = new ImageAnalysis(AxisCamera.getInstance());
	    
        watchdog = Watchdog.getInstance();
    }
    
    public void autonomousPeriodic()
    {
	//kinect.autonomousKinect();
        
        double ultrasonicInches = sensors.getUltrasonic().getRangeInches();
        
        if(ultrasonicInches > 36)
        {
            drivetrain.frontLeftSet(0.5);
            drivetrain.frontRightSet(0.5);
            drivetrain.rearLeftSet(0.5);
            drivetrain.rearRightSet(0.5);
        }
        else
        {
            drivetrain.frontLeftSet(0.0);
            drivetrain.frontRightSet(0.0);
            drivetrain.rearLeftSet(0.0);
            drivetrain.rearRightSet(0.0);
        }
        
        /*
        double gyroAngle = sensors.getGyro().getAngle()/180;
        drivetrain.frontLeftSet(gyroAngle);
        drivetrain.frontRightSet(gyroAngle);
        drivetrain.rearLeftSet(gyroAngle);
        drivetrain.rearRightSet(gyroAngle);
        */
        
        
        /*
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
*/
        watchdog.feed();
    }

    public void teleopPeriodic() 
    {
        drivetrain.drive();
        /*try {
            imageAnalysis.updateImage();
            if(imageAnalysis.getRectangleLength() > -1)
                System.out.println(imageAnalysis.getRectangleLength());
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }*/
            
        //System.out.println((int)sensors.getUltrasonic().getRangeInches()+" Inches");
        //System.out.println(sensors.getEncoder().getDistance());
	//System.out.println(sensors.getEncoder().getRate());
        //System.out.println(sensors.getGyro().getAngle());

        
        
	watchdog.feed();
    }
}
    
