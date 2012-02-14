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

	private Victor ShooterMotorTop;
	private Victor ShooterMotorBottom;

	private Timer timer;
    public void robotInit()
    {
		timer = new Timer();

		joystick = new Joystick(1);
		auxJoystick = new Joystick(2);
		kinect = new KinectFHS(drivetrain);

		drivetrain = new Drivetrain(1,2,3,4,joystick,1.0);
		sensors = new Sensors();
		camera = new CameraFHS(drivetrain);

        imageAnalysis = new ImageAnalysis(AxisCamera.getInstance());

		//Shooter
		ShooterMotorBottom = new Victor(5);

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
		/*
		// Enable Autobalancing
        if (joystick.getRawButton(7)) {

			// Balanced/Straight is from -0.015 to 0.015
			//sensors.getGyro().reset();
            if (sensors.getGyro().getAngle() < 2 && sensors.getGyro().getAngle() > 2) {
				System.out.println("Straight: " + sensors.getGyro().getAngle());
			}
			else if (sensors.getGyro().getAngle() > 2) {
				System.out.println("UP: " + sensors.getGyro().getAngle());
			}
			else if (sensors.getGyro().getAngle() < -2) {
				System.out.println("DOWN: " + sensors.getGyro().getAngle());
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
			}

        }

		// Enable Image Analysis and Processing
        if(joystick.getRawButton(12))
        {
			timer.start();
            try {
                imageAnalysis.updateImage();
                if(imageAnalysis.getRectangles().length > 0)
                {
                    System.out.println("Height:" + imageAnalysis.getHeight(0) + " Distance:" + imageAnalysis.getDistance(0));
                }

            } catch (AxisCameraException ex) {
                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
			System.out.println(timer.get());
			timer.stop();
			timer.reset();
        }

        //System.out.println((int)sensors.getUltrasonic().getRangeInches()+" Inches");
        //System.out.println(sensors.getEncoder().getDistance());
		//System.out.println(sensors.getEncoder().getRate());
        //System.out.println(sensors.getGyro().getAngle());


*/
	watchdog.feed();
    }
}

