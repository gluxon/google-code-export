package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class RobotTemplate extends IterativeRobot
{
    private Joystick joystick, joystickAux;
    //private KinectFHS kinect;
    //private EnhancedIOFHS enhancedIO;
    //private DriverStation driverStation;

    //private Drivetrain drivetrain;
    private Tower tower;
	double ShooterSpeed;
	boolean isPressedShooterSpeed;
	boolean bridgeDown;

    private Compressor compressor;
	private Solenoid bridgeSolenoid;
	private Solenoid intakeSolenoid;

		//private RobotDrive robotDrive;
	private Victor frontLeft, rearLeft, frontRight, rearRight;

	//private RobotDrive robotDrive;

    //private Sensors sensors;
    //private CameraFHS camera;

    private Watchdog watchdog;

    //private ImageAnalysis imageAnalysis;
	//private DashboardHigh dashboardHigh;

    //double gyroLast;
    //double gyroOffset;

	//boolean hasAnalyzed;
	//boolean isPressedLast;

	//int luminosityMin;
	//boolean isPressedLastLuminosityMin;

	//double numParticles;

    public void robotInit()
    {
		System.out.println("In robotInit");
	//luminosityMin = 130;
	//isPressedLast = false;
	//isPressedLastLuminosityMin = false;
	//dashboardHigh = new DashboardHigh();
    //gyroLast = 0.0;
	//gyroOffset = 0.0;

	bridgeSolenoid = new Solenoid(2); //slot 3, channel 2
	intakeSolenoid = new Solenoid(3);
	ShooterSpeed = 1.0;
	isPressedShooterSpeed = false;
	bridgeDown = false;

	joystick = new Joystick(1);
    joystickAux  = new Joystick(2);

	frontLeft = new Victor(1);
	rearLeft = new Victor(2);
	frontRight = new Victor(3);
	rearRight = new Victor(4);

	//kinect = new KinectFHS(drivetrain);
        //driverStation = DriverStation.getInstance();
        //enhancedIO = new EnhancedIOFHS(driverStation);

		//robotDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	//1324 <- Motor config for normal robot
    //drivetrain = new Drivetrain(1,2,3,4,joystick,1.0);
    tower = new Tower(8,7,6,5);
	//intakeSolenoid = new Solenoid(3,3);
    //compressor = new Compressor(1,1);

	//sensors = new Sensors();

     //   imageAnalysis = new ImageAnalysis(AxisCamera.getInstance());
		//	camera = new CameraFHS(drivetrain, imageAnalysis);

        watchdog = Watchdog.getInstance();
		System.out.println("Through robotinit");
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



	/*try
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
    }

    public void teleopPeriodic()
    {
		//robotDrive.mecanumDrive_Polar(joystick.getMagnitude(), joystick.getDirectionDegrees(), joystick.getTwist());
        //
		//drivetrain.drive();
		double robotX = -joystick.getX();
        double robotY = -joystick.getY();
		double robotZ = -joystick.getTwist();

        double speed = 1.0;

		//Dunno what the raw buttons do. backwards maybe???
		if(joystick.getRawButton(2))
        {
            speed = 0.2;
        }

        if(joystick.getRawButton(4))
        {
            frontLeft.set(-speed);
            rearLeft.set(speed);
            frontRight.set(-speed);
            rearRight.set(speed);
        }
        else if(joystick.getRawButton(3))
        {
            frontLeft.set(speed);
            rearLeft.set(-speed);
            frontRight.set(speed);
            rearRight.set(-speed);
        }
		else //mecanum drive WORKS
        {
			frontLeft.set(-(robotZ * speed) + (robotY * speed) + (robotX * speed));
			rearLeft.set(-(robotZ * speed) + (robotY * speed) - (robotX * speed));
			frontRight.set(-(robotZ * speed) - (robotY * speed) + (robotX * speed));
			rearRight.set(-(robotZ * speed) - (robotY * speed) - (robotX * speed));
        }

		/*double compState;
		if(compressor.enabled())
			compState = 1;
		else
			compState = 0;*/
/*
		double numRec;
if(imageAnalysis.getRectangles() == null)
	numRec = -1;
			else
	numRec = imageAnalysis.getRectangles().length;
		dashboardHigh.updateDashboardHigh(drivetrain,sensors.getGyro().getAngle(),sensors.getUltrasonicLeft().getRangeInches(),sensors.getUltrasonicRight().getRangeInches(),numRec, luminosityMin, compState,joystick);

		*/

		//To change shooter speed
		if (joystickAux.getRawButton(11) && ShooterSpeed > 0 && isPressedShooterSpeed == false) {
			ShooterSpeed -= 0.05;
		}
		if (joystickAux.getRawButton(12) && ShooterSpeed < 1 && isPressedShooterSpeed == false) {
			ShooterSpeed += 0.05;
		}

		if(joystick.getRawButton(11) || joystick.getRawButton(12))
			isPressedShooterSpeed = true;
		else
			isPressedShooterSpeed = false;

		System.out.println(ShooterSpeed);

		boolean fire = joystickAux.getRawButton(7);
        boolean elevatorUp = joystickAux.getRawButton(1);
        boolean elevatorDown = joystickAux.getRawButton(2);
        boolean ballIntakeIn = joystickAux.getRawButton(3);
        boolean ballIntakeOut = joystickAux.getRawButton(4);
        boolean compressorToggle = joystickAux.getRawButton(5);

        if(fire)
            tower.setShooterMotors(ShooterSpeed);
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
/*
        if(compressorToggle)
            compressor.start();
        else
            compressor.stop();
*/
		bridgeSolenoid.set(joystickAux.getRawButton(10));
		intakeSolenoid.set(joystickAux.getRawButton(9));

       /* if(enhancedIO.getFireButton())
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
/*
		if (joystick.getRawButton(9) && isPressedLastLuminosityMin == false) {
			if (luminosityMin > 0)
				luminosityMin -= 5;
		}

		if (joystick.getRawButton(10) && isPressedLastLuminosityMin == false) {
			if (luminosityMin < 255)
				luminosityMin += 5;
		}

		if(joystick.getRawButton(9) || joystick.getRawButton(10))
			isPressedLastLuminosityMin = true;
		else
			isPressedLastLuminosityMin = false;


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

	if(joystick.getRawButton(11) && isPressedLast == false)
	{
            try
            {
                camera.centerOnTarget(0,luminosityMin);
            }
            catch (AxisCameraException ex)
            {
            }
            catch (NIVisionException ex)
            {
            }
	}

	if(joystick.getRawButton(11))
		isPressedLast = true;
	else
		isPressedLast = false;

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

	if (joystick.getRawButton(12))
        {
            System.out.println(sensors.getUltrasonicLeft().getRangeInches() + " : " + sensors.getUltrasonicRight().getRangeInches());
        }

	*/

	watchdog.feed();
    }
}