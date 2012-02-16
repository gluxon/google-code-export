package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class RobotTemplate extends IterativeRobot
{
    private Joystick joystick;
    private KinectFHS kinect;
    private DriverStationEnhancedIO enhancedIO;
    private DriverStation driverStation;
    
    private Drivetrain drivetrain;
    private Tower tower;
    
    private Sensors sensors;
    private CameraFHS camera;

    private Watchdog watchdog;

    private ImageAnalysis imageAnalysis;

    double gyroLast;
    double gyroOffset;

    public void robotInit()
    {
	joystick = new Joystick(1);
	kinect = new KinectFHS(drivetrain);
        driverStation = DriverStation.getInstance();
        enhancedIO = driverStation.getEnhancedIO();
        
        drivetrain = new Drivetrain(1,2,3,4,joystick,1.0);
        tower = new Tower(5,6,7,8);
	sensors = new Sensors();
	camera = new CameraFHS(drivetrain);

        imageAnalysis = new ImageAnalysis(AxisCamera.getInstance());
	
        watchdog = Watchdog.getInstance();

	gyroLast = 0.0;
	gyroOffset = 0.0;
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
		//if(joystick.getX() > 0.15 || joystick.getY() > 0.15 || joystick.getZ() > 0.15)
			//robotDrive.mecanumDrive_Polar(-joystick.getMagnitude(), -joystick.getDirectionDegrees(), -joystick.getZ());
			//robotDrive.mecanumDrive_Cartesian(-joystick.getX(), -joystick.getZ(), -joystick.getY(), 0.0);
		//else
			//robotDrive.mecanumDrive_Cartesian(0.0, 0.0, 0.0, 0.0);

		//shooterMotorBottom.set(joystick.getThrottle());
		//shooterMotorTop.set(joystick.getThrottle());

		// Enable Autobalancing
        if (joystick.getRawButton(7)) 
        {
            double gyroAngle = sensors.getGyro().getAngle();
            // Change speed based on Angle
            double speed = Math.abs(gyroAngle / 7);
            System.out.println("Motor Speed: " + speed);

            double deadZone = 1;

            if (gyroAngle < deadZone && gyroAngle > -deadZone) 
            {
                System.out.println("Straight: " + gyroAngle);
            }
            else if (gyroAngle > deadZone) 
            {
                System.out.println("DOWN: " + gyroAngle);
		drivetrain.frontLeftSet(speed);
		drivetrain.rearLeftSet(speed);
		drivetrain.frontRightSet(-speed);
		drivetrain.rearRightSet(-speed);
            }
            else if (gyroAngle < -deadZone) 
            {
                System.out.println("UP: " + gyroAngle);
		drivetrain.frontLeftSet(-speed);
		drivetrain.rearLeftSet(-speed);
		drivetrain.frontRightSet(speed);
                drivetrain.rearRightSet(speed);
            }
        }
        
        if (joystick.getRawButton(8)) 
        {
            sensors.getGyro().reset();
        }

	if(joystick.getRawButton(11))
	{
            try 
            {
                camera.centerOnTarget(0);
            }
            catch (AxisCameraException ex) 
            {
                ex.printStackTrace();
            } 
            catch (NIVisionException ex) 
            {
                ex.printStackTrace();
            }
	}
        
        if(joystick.getRawButton(12))
        {
            try 
            {
                imageAnalysis.updateImageTeleop(drivetrain);
                if(imageAnalysis.getRectangles().length > 1)
                {
                    System.out.println("Height:" + imageAnalysis.getHeight(0) + " Distance:" + imageAnalysis.getDistance(0));
                }
            } 
            catch (AxisCameraException ex) 
            {
                ex.printStackTrace();
            } 
            catch (NIVisionException ex) 
            {
                ex.printStackTrace();
            }
        }

	if (joystick.getRawButton(10)) 
        {
            System.out.println(sensors.getUltrasonic().getRangeInches());
        }

	watchdog.feed();
    }
}