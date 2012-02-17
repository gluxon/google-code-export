package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class RobotTemplate extends IterativeRobot
{
    private Joystick joystick, joystickAux;
    private KinectFHS kinect;
    private EnhancedIOFHS enhancedIO;
    private DriverStation driverStation;

    private Drivetrain drivetrain;
    private Tower tower;
    private Compressor compressor;

    private Sensors sensors;
    private CameraFHS camera;

    private Watchdog watchdog;

    private ImageAnalysis imageAnalysis;
	private DashboardHigh dashboardHigh;

    double gyroLast;
    double gyroOffset;

    public void robotInit()
    {
	dashboardHigh = new DashboardHigh();
        gyroLast = 0.0;
	gyroOffset = 0.0;

	joystick = new Joystick(1);
        joystickAux  = new Joystick(2);
	kinect = new KinectFHS(drivetrain);
        driverStation = DriverStation.getInstance();
        enhancedIO = new EnhancedIOFHS(driverStation);
	//1324 <- Motor config for normal robot
        drivetrain = new Drivetrain(1,2,3,4,joystick,1.0);
        tower = new Tower(8,7,6,5);
        compressor = new Compressor(1,1);

	sensors = new Sensors();
	camera = new CameraFHS(drivetrain);
        imageAnalysis = new ImageAnalysis(AxisCamera.getInstance());

        watchdog = Watchdog.getInstance();
    }

    public void autonomousPeriodic()
    {
	//kinect.autonomousKinect();

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

	dashboardHigh.updateDashboardHigh(drivetrain,sensors.getGyro().getAngle(),sensors.getUltrasonicLeft().getRangeInches(),sensors.getUltrasonicRight().getRangeInches(),0.0,compressor.enabled(),joystick);
	
        boolean fire = joystickAux.getRawButton(0);
        boolean elevatorUp = joystickAux.getRawButton(1);
        boolean elevatorDown = joystickAux.getRawButton(2);
        boolean ballIntakeIn = joystickAux.getRawButton(3);
        boolean ballIntakeOut = joystickAux.getRawButton(4);
        boolean compressorToggle = joystickAux.getRawButton(5);
        
        if(fire)
            tower.setShooterMotors(1.0);
        else
            tower.setShooterMotors(0.0);
        
        if(elevatorUp)
            tower.setBallElevator(1.0);
        else if(elevatorDown)
            tower.setBallElevator(-1.0);
        else
            tower.setBallElevator(0.0);
        
        if(ballIntakeIn)
            tower.setBallIntakeMotor(1.0);
        else if(ballIntakeOut)
            tower.setBallIntakeMotor(-1.0);
        else
            tower.setBallIntakeMotor(0.0);
        
        if(compressorToggle)
            compressor.start();
        else
            compressor.stop();
        
        /*
        if(enhancedIO.getFireButton())
        {
            tower.setShooterMotors(enhancedIO.getSlider());
        }
        else
        {
            tower.setShooterMotors(0.0);
        }

        if(enhancedIO.getBallElevatorSwitch()[0])
            tower.setBallElevator(1.0);
        else if(enhancedIO.getBallElevatorSwitch()[1])
            tower.setBallElevator(-1.0);
        else
            tower.setBallElevator(0.0);

        if(enhancedIO.getBallIntakeSwitch()[0])
            tower.setBallIntakeMotor(1.0);
        else if(enhancedIO.getBallIntakeSwitch()[1])
            tower.setBallIntakeMotor(-1.0);
        else
            tower.setBallIntakeMotor(0.0);

        if(enhancedIO.getCompressorSwitch())
            compressor.start();
        else
            compressor.stop();

        /*****Debug*****/

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
            }
            catch (NIVisionException ex)
            {
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
            }
            catch (NIVisionException ex)
            {
            }
        }

	if (joystick.getRawButton(10))
        {
            System.out.println(sensors.getUltrasonicLeft().getRangeInches() + " : " + sensors.getUltrasonicRight().getRangeInches());
        }

	watchdog.feed();
    }
}