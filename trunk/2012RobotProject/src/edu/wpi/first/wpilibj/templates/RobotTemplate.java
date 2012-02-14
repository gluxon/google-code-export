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
    private CameraFHS camera;

    private Dashboard dashboard;
    private Watchdog watchdog;

    private ImageAnalysis imageAnalysis;

	//private RobotDrive robotDrive;

	private Victor shooterMotorTop;
	private Victor shooterMotorBottom;

	// Variable for storing last gyroscope angle
	double gyroLast;
	double gyroOffset;

    public void robotInit()
    {
		joystick = new Joystick(1);
		auxJoystick = new Joystick(2);
		kinect = new KinectFHS(drivetrain);

		drivetrain = new Drivetrain(1,2,3,4,joystick,1.0);
		sensors = new Sensors();
		//camera = new CameraFHS(drivetrain);

        //imageAnalysis = new ImageAnalysis(AxisCamera.getInstance());
		//robotDrive = new RobotDrive(1,3,2,4);

		//Shooter
		shooterMotorBottom = new Victor(5);

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
        if (joystick.getRawButton(7)) {

			// Balanced/Straight is from -0.015 to 0.015
			//sensors.getGyro().reset();

			double deadZone = 1;

			double gyroAngle = sensors.getGyro().getAngle() - gyroOffset;
			// if the gyro's Angle changed by less than 0.7, then revert to last angle (solves drifting to some extent)
			double gyroCurrentOffset = gyroAngle - gyroLast;
			if (Math.abs(gyroCurrentOffset) < 0.1) {
				gyroAngle = gyroLast;
				gyroOffset = gyroOffset + gyroCurrentOffset;
			}
			gyroLast = gyroAngle;

			/*double speed = Math.abs(gyroAngle / 3);
			System.out.println(speed);*/

            if (gyroAngle < deadZone && gyroAngle > -deadZone) {
				System.out.println("Straight: " + gyroAngle);
			}
			else if (gyroAngle > deadZone) {
				System.out.println("DOWN: " + gyroAngle);
				drivetrain.frontLeftSet(-speed);
				drivetrain.rearLeftSet(-speed);
				drivetrain.frontRightSet(speed);
				drivetrain.rearRightSet(speed);
			}
			else if (gyroAngle < -deadZone) {
				System.out.println("UP: " + gyroAngle);
				drivetrain.frontLeftSet(speed);
				drivetrain.rearLeftSet(speed);
				drivetrain.frontRightSet(-speed);
				drivetrain.rearRightSet(-speed);
			}

        }

		// Reset Gyroscope
        if (joystick.getRawButton(8)) {
			sensors.getGyro().reset();
        }

		// Optical Encoders
        if (joystick.getRawButton(9)) {

			//ShooterMotorBottom.set(0.5);

			System.out.println(sensors.getEncoder(2).getRaw());

            /*if (sensors.getEncoder(2).getRate() < 0.015 && sensors.getEncoder(2).getRate() > -0.015) {
				System.out.println();
			}
			else if (sensors.getEncoder(2).getRate() > 0.015) {
				System.out.println("UP: " + sensors.getGyro().getAngle());
			}
			else if (sensors.getEncoder(2).getRate() < 0.015) {
				System.out.println("DOWN: " + sensors.getGyro().getAngle());
			}*/

        }

		// Enable Centering
		if(joystick.getRawButton(11))
		{
			try {
				camera.centerOnTarget(0);
			} catch (AxisCameraException ex) {
				ex.printStackTrace();
			} catch (NIVisionException ex) {
				ex.printStackTrace();
			}
		}
		// Enable Image Analysis and Processing
        if(joystick.getRawButton(12))
        {
            try {
                imageAnalysis.updateImageTeleop(drivetrain);
                if(imageAnalysis.getRectangles().length > 1)
                {
                    System.out.println("Height:" + imageAnalysis.getHeight(0) + " Distance:" + imageAnalysis.getDistance(0));
                }

            } catch (AxisCameraException ex) {
                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
        }

        //System.out.println((int)sensors.getUltrasonic().getRangeInches()+" Inches");
        //System.out.println(sensors.getEncoder().getDistance());
		//System.out.println(sensors.getEncoder().getRate());
        //System.out.println(sensors.getGyro().getAngle());

		if (joystick.getRawButton(10)) {
			System.out.println(sensors.getUltrasonic().getRangeInches());
		}

	watchdog.feed();
    }
}