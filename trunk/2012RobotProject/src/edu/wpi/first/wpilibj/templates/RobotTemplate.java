package edu.wpi.first.wpilibj.templates;
//Imports
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class RobotTemplate extends IterativeRobot
{
    //Controls
    private Joystick joystick;
    private EnhancedIOFHS enhancedIO;
    //Driverstation
    private DriverStation driverStation;
    private DashboardHigh dashboardHigh;
    //Movement
    private Drivetrain drivetrain;
    private Tower tower;
    //Sensors
    private Sensors sensors;
    //Image
    private CameraFHS camera;
    private ImageAnalysis imageAnalysis;
    //Watchdog
    private Watchdog watchdog;
    //Solenoid
    private Solenoid intakeSolenoid;
    //Misc. Variables
    Timer timer, timer2;
    double rangeLast = 0;
    boolean first = true;
    boolean stopped = false;
    double rangeBeforeStop = 0;
    int luminosityMin;
    double numParticles;
	DriverStationLCD dsout;
	boolean autoFist = true;

    public void robotInit()
    {
		timer2 = new Timer();
        //DriverStation
        driverStation = DriverStation.getInstance();
        dashboardHigh = new DashboardHigh();
        //Controls
		joystick = new Joystick(1);
        enhancedIO = new EnhancedIOFHS(driverStation);
        //Movement
        drivetrain = new Drivetrain(1,2,3,4,joystick,1.0);
		tower = new Tower(driverStation,8,7,6,5,3,enhancedIO);
        //Sensors
        sensors = new Sensors();
        //Solenoid
		intakeSolenoid = new Solenoid(3);
        //Image
        imageAnalysis = new ImageAnalysis(AxisCamera.getInstance());
		camera = new CameraFHS(drivetrain, imageAnalysis);
        //Watchdog
        watchdog = Watchdog.getInstance();
		dsout = DriverStationLCD.getInstance();
		dsout.updateLCD();
    }

    public void autonomousPeriodic()
    {
        /*
        double range = sensors.getUltrasonicRight().getRangeInches();
		System.out.println(range);
		double robo_speed_far = 0.3;
		double robo_speed_close = 0.10;


	if((first || (Math.abs(range-rangeLast) < 20)) && !stopped)
	{
                rangeBeforeStop = range;
		if(first)
		{
                    first = false;
		}

		if(range < 150)
		{
                    drivetrain.frontLeftSet(-robo_speed_far);
                    drivetrain.frontRightSet(robo_speed_far);
                    drivetrain.rearLeftSet(-robo_speed_far);
                    drivetrain.rearRightSet(robo_speed_far);
		}
		else if(range >= 150 && range < 180)
		{
                    drivetrain.frontLeftSet(-robo_speed_close);
                    drivetrain.frontRightSet(robo_speed_close);
                    drivetrain.rearLeftSet(-robo_speed_close);
                    drivetrain.rearRightSet(robo_speed_close);
		}
		else
		{
                    System.out.println("STOP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    drivetrain.frontLeftSet(0.0);
                    drivetrain.frontRightSet(0.0);
                    drivetrain.rearLeftSet(0.0);
                    drivetrain.rearRightSet(0.0);
		}
		rangeLast = range;
	}
	else if(stopped)
	{
            timer.start();
            System.out.println(timer.get());
            drivetrain.frontLeftSet(0.0);
            drivetrain.frontRightSet(0.0);
            drivetrain.rearLeftSet(0.0);
            drivetrain.rearRightSet(0.0);

            if((Math.abs(range-rangeBeforeStop) < 5) && timer.get() < 5)
            {
                timer.stop();
                timer.reset();
            stopped = false;
            rangeLast = range;
            }
	}
	else
	{
            drivetrain.frontLeftSet(0.0);
            drivetrain.frontRightSet(0.0);
            drivetrain.rearLeftSet(0.0);
            drivetrain.rearRightSet(0.0);
	}
*/
		if(autoFist)
		{
			autoFist = false;
			timer2.start();
		}

		double time = timer2.get();

		// Wait 6 seconds for shooting motors to power up (more dense ball first
		if(time <= 6)
		{
			tower.setShooterMotors(0.43);
		}
		// Start Elevator for 1 second
		else if(time > 6 && time <= 7)
		{
			tower.setBallElevator(1.0);
		}
		// Stop Elevator for 12 seconds
		else if(time > 7 && time <= 12)
		{
			tower.setBallElevator(0.0);
		}
		// Start Elevator for 2 seconds
		else if(time > 12 && time <= 14)
		{
			tower.setBallElevator(1.0);
		}
		// Stop after 14 seconds from start
		else if(time > 14)
		{
			timer2.stop();
			tower.setBallElevator(0.0);
			tower.setShooterMotors(0.0);
		}

		// Update values on Dashboard
		dashboardHigh.updateDashboardHigh(drivetrain, 0, sensors.getUltrasonicLeft().getRangeInches(), sensors.getUltrasonicRight().getRangeInches(), 0, luminosityMin, 0, joystick);
		
		tower.startCompressor();
    }

    public void teleopPeriodic()
    {
        //Movement
        /*if(joystick.getRawButton(11))
		{
            try
            {
				System.out.println("a");
                camera.centerBottomTarget();
            }
            catch (AxisCameraException ex){}
            catch (NIVisionException ex){}
		}
		else
		{*/

		//}

		drivetrain.drive();
        dsout.println(DriverStationLCD.Line.kUser4, 2, "Slider: "+(int)(enhancedIO.getSlider()*100)+"%        ");

		//tower.cameraLightOn();
        tower.run();

		//Dashboard
		dashboardHigh.updateDashboardHigh(drivetrain, 0, sensors.getUltrasonicLeft().getRangeInches(), sensors.getUltrasonicRight().getRangeInches(), 0, luminosityMin, 0, joystick);

		//Watchdog
		watchdog.feed();
		dsout.updateLCD();
    }
}