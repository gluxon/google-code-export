/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates; 

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.Timer;

import com.sun.squawk.util.MathUtils;
import edu.fhs.input.UltrasonicFHS;


public class RobotTemplate extends IterativeRobot
{
    private static final double PI = Math.PI;

    private static final int SLOT_1 = 1;
    
    //slot on the digital sidecar that is for digital IO
    private static final int SIDECAR_IO_SLOT = 4;

    //initial robot speed to be used during autonomous
    private static final double defaultRobotSpeed = 0.3;

    //circumference of the drive shaft (mm)
    private static final double DRIVE_SHAFT_CIRC = 10.053;

    //leeway for the difference between  the two getDistance() values of the encoders
    private static final double ENCODER_DIST_ERROR = 0;

    //distance the robot should go before putting up game piece in autonomous
    private static final double DISTANCE_TRAVELED = 20;

    //minimum xbox input value needed to respond - this corrects for xbox oversensitivity
    private static final double XBOX_SENSITIVITY = 0.25;

    //height of arm - ground to pivot point
    private static final double ARM_HEIGHT = 54.5;

    //length of arm - pivot point to mid-way of gripper
    private static final double ARM_LENGTH = 83;

    //distance from middle of base of arm to the front
    private static final double FRONT_LENGTH = 16;

    //distance from wall to stop(inches)
    private static double WALL_DISTANCE;

    //three predetermined heights (inches) for arm during auto
    //PULSES is number of arm encoder pulses for above height
    private static final double TOP_HEIGHT_HIGH = 112;
    private static final double H_T_PULSES = 1;

    private static final double MID_HEIGHT_HIGH = 76;
    private static final double H_M_PULSES = 2;

    private static final double BOTTOM_HEIGHT_HIGH = 39;
    private static final double H_B_PULSES = 3;

    //heights of spokes on outer lane (low spokes)inches
    //PULSES is number of arm encoder pulses needed for heigh (line above)
    private static final double TOP_HEIGHT_LOW = 104.5;
    private static final double L_T_PULSES = 1;

    private static final double MID_HEIGHT_LOW = 68;
    private static final double L_M_PULSES = 2;

    private static final double BOTTOM_HEIGHT_LOW = 30.5;
    private static final double L_B_PULSES = 3;
    
    //Lengths of the spokes(inches)
    private static final double SPOKE_LENGTH = 17.5;

    //xboxController is for arm, xboxDriveController is for driving main bot
    private Joystick xboxAuxController, xboxDriveController;

    //drive, arm and claw speed controllers
    private Jaguar jaguarLeft, jaguarRight, jaguarArm, clawJaguar;

    //claw limit switches
    private DigitalInput outerClawLimit, innerClawLimit, armLimit;

    private DriverStationLCD driverStationLCD;

    private Watchdog watchDog;

    //line sensors
    private DigitalInput leftLineSensor, centerLineSensor, rightLineSensor;

    private UltrasonicFHS rangeSensor;

    //two slots for arm height switch
    private DigitalInput heightSwitch1, heightSwitch2;

    //two slots for chosing lanes
    private DigitalInput laneSwitch1, laneSwitch2;

    //Pressure cuttoff
    private DigitalInput pressureCuttoff;

    //solenoid
    private Solenoid miniBotSolenoid;
    private Solenoid miniBotSolenoidRelease;

    //Compressor Spike
    private Relay compressorSpike;

    private DigitalInput lowerArmLimit, upperArmLimit;
    
    private Timer timer;

    private boolean armTimerStarted = false;
    private boolean split = false;
    private boolean endYTurn = false;
    private boolean miniIsDeployed = false;
    private double throttle;
    private double y;
    private int lastStatus;
    private Timer t;
    private int statusValue;

    private String defaultDirection = "LEFT";

    private boolean firstTimeAutonomous = false;
    private boolean armIsLowered;

    public void robotInit()
    {
        try
        {
            xboxAuxController = new Joystick(1);
            xboxDriveController = new Joystick(2);
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Joystick(s) not initialized.");
        }

        try
        {
            //drive jaguars
            jaguarLeft = new Jaguar(9);
            jaguarRight = new Jaguar(8);
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Drive Jaguar(s) not initialized.");
        }

        try
        {
            //arm jags
            jaguarArm = new Jaguar(7);
            clawJaguar = new Jaguar(6);
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Aux Jaguar(s) not initialized.");
        }
        
        try
        {
            //claw limit switches
            outerClawLimit = new DigitalInput(4);
            innerClawLimit = new DigitalInput(5);
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Claw Limit Switch(es) not initialized.");
        }

        try
        {
            //line sensors
            leftLineSensor = new DigitalInput(1);
            centerLineSensor = new DigitalInput(2);
            rightLineSensor = new DigitalInput(3);
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Line Sensor(s) not initialized.");
        }

        try
        {
            //two arm height and lane switch inputs
            //heightSwitch1 = new DigitalInput(6);
            //heightSwitch2 = new DigitalInput(7);
            //laneSwitch1 = new DigitalInput(8);
            //laneSwitch2 = new DigitalInput(9);
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Height / Lane switch(es) not initialized.");
        }

        try
        {
            //declare encoders
            //encoderArm = new EncoderFHS(1,2,3);
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2,"Encoder(s) not initialized." );
        }
        
        try
        {
            miniBotSolenoid = new Solenoid(8, 2);
            miniBotSolenoidRelease = new Solenoid(8, 1);
        }
        catch(Exception e)
        {
            System.out.println("Solenoid not initialized.");
        }

        try
        {
            lowerArmLimit = new DigitalInput(4, 12);
            upperArmLimit = new DigitalInput(4, 13);
        }
        catch(NullPointerException e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2,"Arm limit(s) not initialized." );
        }

        try
        {
            rangeSensor = new UltrasonicFHS (1, 1);
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Ultrasonic not initialized.");
        }

        try
        {
            pressureCuttoff = new DigitalInput(11);
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Pressure cuttoff not initialized.");
        }

        try
        {
            compressorSpike = new Relay(4,1);
        }
        catch(NullPointerException e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Compressor Spike not initialized.");
        }
        catch(Relay.InvalidValueException e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Compressor Spike Invalid Value Exception.");
        }

        driverStationLCD = DriverStationLCD.getInstance();

        t = new Timer();
        timer = new Timer();
        
        watchDog = Watchdog.getInstance();

        watchDog.feed();
        driverStationLCD.updateLCD();

        firstTimeAutonomous = true;
    }


    public void disabledInit()
    {
        firstTimeAutonomous = true;
    }

    public void autonomousPeriodic()
    {
        /***********************LINE FOLLOWING******************/
        
        followLine();
   
        if(firstTimeAutonomous)
        {
            jaguarLeft.set(-defaultRobotSpeed);
            jaguarRight.set(defaultRobotSpeed);
            firstTimeAutonomous = false;
            timer.start();
        }

        if(!this.armTimerStarted)
        {
            Timer.delay(1.0);
            t.start();
            armTimerStarted = true;
        }
        if(!upperArmLimit.get())
        {
            this.moveArm(-0.6);
        }
        else
        {
            this.moveArm(0.0);
        }
        if(rangeSensor.getRangeInches() < 20 && !armIsLowered)
        {
            moveArm(0.6);
            moveGripper(0.2);
            Timer.delay(2);
            armIsLowered = true;
        }

        watchDog.feed();
        driverStationLCD.updateLCD();
        
    }

    public void teleopPeriodic()
    {
        driverStationLCD.println(DriverStationLCD.Line.kUser3, 2, "Line status: " + robotLineSensorDisplay());

        //Main drive code
        throttle = xboxDriveController.getThrottle();
        y = xboxDriveController.getY();

        if (Math.abs(throttle) > XBOX_SENSITIVITY || Math.abs(y) > XBOX_SENSITIVITY)
        {
            jaguarLeft.set(-(throttle*0.65) + y);
            jaguarRight.set(-(throttle*0.65) - y);
        }
        else if (Math.abs(throttle) < XBOX_SENSITIVITY || Math.abs(y) < XBOX_SENSITIVITY)
        {
            jaguarLeft.set(0);
            jaguarRight.set(0);
        }

        //Aux Driver Code
        moveGripper(-xboxAuxController.getRawAxis(5));
        moveArm(xboxAuxController.getY());
        driverStationLCD.println(DriverStationLCD.Line.kUser6,1,upperArmLimit.get()+" | ");

        deployMinibot(3,2);
        driverStationLCD.println(DriverStationLCD.Line.kUser2,1,""+this.rangeSensor.getRangeInches());

        //Compressor
        if(!pressureCuttoff.get() && xboxAuxController.getRawButton(1))
        {
            compressorSpike.set(Relay.Value.kForward);
        }
        else
        {
            compressorSpike.set(Relay.Value.kOff);
        }

        //Watchdog and driverStationLCD updaters
        watchDog.feed();
        driverStationLCD.updateLCD();
    }

 
    public int robotLineSensorDisplay()
    {
        try
        {
            int leftLineValue = (leftLineSensor.get() ? 1 : 0);
            int centerLineValue = (centerLineSensor.get() ? 1 : 0);
            int rightLineValue = (rightLineSensor.get() ? 1 : 0);
            statusValue = leftLineValue * 100 + centerLineValue * 10 + rightLineValue;
        }
        catch(NullPointerException e){}
        
        return statusValue;
    }
    
    public void moveGripper(double speed)
    {         
            if(outerClawLimit.get() && (speed > XBOX_SENSITIVITY))
            {
                clawJaguar.set(speed*0.2);
            }
            else if(innerClawLimit.get() && (speed < -XBOX_SENSITIVITY))
            {
                clawJaguar.set(speed*0.2);
            }
            else
            {
                clawJaguar.set(0.0);
            }
    }

    public void moveArm(double speed)
    {
        if(lowerArmLimit.get() && speed > 0)
        {
            jaguarArm.set(speed);
        }
        else if(upperArmLimit.get() && speed < 0)
        {
            jaguarArm.set(speed);
        }
        else
        {
            jaguarArm.set(0.0);
        }

    }
    
    public void deployMinibot(int input_activate, int input_release)
    {
        try
        {
            if (!miniIsDeployed && xboxAuxController.getRawButton(input_activate))
            {
                miniBotSolenoid.set(true);
                miniBotSolenoidRelease.set(false);
                miniIsDeployed = true;
            }
            if (miniIsDeployed && xboxAuxController.getRawButton(input_release))
            {
                miniBotSolenoid.set(false);
                miniBotSolenoidRelease.set(true);
                miniIsDeployed = false;
            }
        }
        catch(NullPointerException e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Yo dawg, dat minibot's wacked!  " + xboxAuxController.getRawButton(3));
        }
    }
    
    //returns the number of pulses that the arm encoder must see before stopping
    //NEEDS ACTUAL PULSE VALUES - EXPERIMENT!
    public double calculatePulses(int pegHeight)
    {
        switch ((int)(pegHeight))
        {
            case (int)BOTTOM_HEIGHT_LOW:
                return L_B_PULSES;
            case (int)MID_HEIGHT_LOW:
                return L_M_PULSES;
            case (int)TOP_HEIGHT_LOW:
                return L_T_PULSES;
            case (int)BOTTOM_HEIGHT_HIGH:
                return H_B_PULSES;
            case (int)MID_HEIGHT_HIGH:
                return H_M_PULSES;
            case (int)TOP_HEIGHT_HIGH:
                return H_T_PULSES;
            default:
                return L_B_PULSES;
        }
    }

    //line following code
    private void followLine()
    {
        //0 is on the line, 1 is off (note: sensor returns true when off the line)
        int leftLineValue = (leftLineSensor.get() ? 1 : 0);
        int centerLineValue = (centerLineSensor.get() ? 1 : 0);
        int rightLineValue = (rightLineSensor.get() ? 1 : 0);

        double jaguarLeftSpeed = jaguarLeft.get();
        double jaguarRightSpeed = jaguarRight.get();

        statusValue = leftLineValue * 100 + centerLineValue * 10 + rightLineValue;

        driverStationLCD.println(DriverStationLCD.Line.kUser2, 2, "Status: " + statusValue);

        if(rangeSensor.getRangeInches() > 20)
        {
            if(!split)
            {
            //0 is on the line, 1 is off
                switch(statusValue)
                {
                    case 11:
                        //fall through
                    case 1:
                        //robot turns left
                        jaguarLeft.set(jaguarLeftSpeed += 0.015);
                        jaguarRight.set(jaguarRightSpeed += 0.015);
                        lastStatus = 1;
                    break;

                    case 101:
                        //robot goes straight
                        jaguarLeft.set(-defaultRobotSpeed);
                        jaguarRight.set(defaultRobotSpeed);
                    break;

                    case 110:
                        //fall through
                    case 100:
                        //robot turns right
                        jaguarLeft.set(jaguarLeftSpeed -= 0.015);
                        jaguarRight.set(jaguarRightSpeed -= 0.015);
                        lastStatus = 100;
                    break;

                    case 10:
                        //Y split, turns left by default
                        if(defaultDirection.equals("LEFT"))
                        {
                            jaguarLeft.set(0.6);
                            jaguarRight.set(0.6);
                            timer.reset();
                            timer.start();
                        }
                        else
                        {
                            jaguarLeft.set(-0.6);
                            jaguarRight.set(-0.6);
                            timer.reset();
                            timer.start();
                        }
                        split = true;
                    break;

                    default:
                        jaguarLeft.set(0.0);
                        jaguarRight.set(0.0);
                        if (lastStatus == 100)
                        {
                            jaguarLeft.set(jaguarLeftSpeed = (defaultRobotSpeed - .05));
                            jaguarRight.set(jaguarRightSpeed = (defaultRobotSpeed - .05));
                        }
                        if (lastStatus == 1)
                        {
                            jaguarLeft.set(jaguarLeftSpeed = (defaultRobotSpeed + .05));
                            jaguarRight.set(jaguarRightSpeed = (defaultRobotSpeed + .05));
                        }
                    break;

                }
            }
            else
            {
                centerLineValue = (centerLineSensor.get() ? 1 : 0);
                if(centerLineValue == 0)
                {
                    split = false;
                }
            }
        }

    }

    //find what spoke to use through the switches' input
    private double whichPeg()
    {
        if (!laneSwitch1.get() && !laneSwitch2.get())
        {
            if (!heightSwitch1.get() && !heightSwitch2.get())
            {
                return BOTTOM_HEIGHT_LOW;
            }
            if (!heightSwitch1.get() && heightSwitch2.get())
            {
                return MID_HEIGHT_LOW;
            }
            if (heightSwitch1.get() && !heightSwitch2.get())
            {
                return TOP_HEIGHT_LOW;
            }
        }
        if (laneSwitch1.get() || laneSwitch2.get())
        {
            if (!heightSwitch1.get() && !heightSwitch2.get())
            {
                return BOTTOM_HEIGHT_HIGH;
            }
            if (!heightSwitch1.get() && heightSwitch2.get())
            {
                return MID_HEIGHT_HIGH;
            }
            if (heightSwitch1.get() && !heightSwitch2.get())
            {
                return TOP_HEIGHT_HIGH;
            }
            //third option, makes bot go right (well, not left)
            if (!laneSwitch1.get())
            {
                defaultDirection.equals("RIGHT");
            }
        }
        //if none of that works...
        return BOTTOM_HEIGHT_HIGH;
    }

    //sets the distance the robot has to be away from the wall
    private double distanceFromWall(double height)
    {
       double x = Math.sqrt(MathUtils.pow(ARM_LENGTH, 2)-MathUtils.pow(ARM_HEIGHT - height, 2)) + SPOKE_LENGTH;
       return x - FRONT_LENGTH;
    }
}
