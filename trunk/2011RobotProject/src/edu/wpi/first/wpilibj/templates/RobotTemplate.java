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
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

import com.sun.squawk.util.MathUtils;
import edu.fhs.input.UltrasonicFHS;


public class RobotTemplate extends IterativeRobot
{
    private static final double PI = Math.PI;

    private static final int SLOT_1 = 1;
    
    //slot on the digital sidecar that is for digital IO
    private static final int SIDECAR_IO_SLOT = 4;

    //initial robot speed to be used during autonomous
    private static final double defaultRobotSpeed = 0.35;

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
    
    //camera
    private AxisCamera robotCamera;
    private Timer timer;
    
    ColorImage image;
    BinaryImage firstImage;
    ParticleAnalysisReport[] analysis;

    private boolean robotDirectionLeft;
    private boolean robotDirectionRight;

    private boolean split = false;
    private boolean endYTurn = false;
    private int pulses = 0;
    private boolean miniIsDeployed = false;
    private Relay relay;
    private double throttle;
    private double y;
    private int lastStatus;
    private int statusValue;

    private String defaultDirection = "LEFT";

    private boolean firstTimeAutonomous = false;

    public void robotInit()
    {
        driverStationLCD = DriverStationLCD.getInstance();

        try //everything
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
            pressureCuttoff = new DigitalInput(12);
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

        timer = new Timer();
        
        watchDog = Watchdog.getInstance();
        
        firstTimeAutonomous = true;
        
        watchDog.feed();
        
        driverStationLCD.updateLCD();
    }


    public void disabledInit()
    {
        firstTimeAutonomous = true;
    }

    public void autonomousPeriodic()
    {
      /*******************************Encoder**********************************/
       
        /*encoderLeft.start();
        encoderRight.start();

        
        distanceRobotRight = encoderRight.getDistance();
        distanceRobotLeft = encoderLeft.getDistance();

        robotDirectionLeft = encoderLeft.getDirection();
        robotDirectionRight = encoderRight.getDirection();
        */
        
        //starts robot
        /*

        //if left encoder goes further than right encoder, make left go slower and
        //right go faster
        if(distanceRobotLeft > (distanceRobotRight + ENCODER_DIST_ERROR))
        {
            jaguarLeft.set(jaguarLeftSpeed -= 0.05);
            jaguarRight.set(jaguarLeftSpeed += 0.05);
        }
        //if right encoder goes further than left encoder, make right go slower and
        //left go faster
        if(distanceRobotRight > (distanceRobotLeft + ENCODER_DIST_ERROR))
        {
            jaguarLeft.set(jaguarLeftSpeed += 0.05);
            jaguarRight.set(jaguarLeftSpeed -= 0.05);
        }

        //if both encoders go same distance, set jaguars to the same speed
        if(Math.abs(distanceRobotRight - distanceRobotLeft) <= ENCODER_DIST_ERROR)
        {
            jaguarLeft.set(defaultRobotSpeed);
            jaguarRight.set(defaultRobotSpeed);
        }

        //if either of the encoders go the right distance, the game piece will be
        //put up
        if(distanceRobotLeft == DISTANCE_TRAVELED || distanceRobotRight == DISTANCE_TRAVELED)
        {
            jaguarLeft.set(0.0);
            jaguarRight.set(0.0);
        }
*/
        /***********************LINE FOLLOWING******************/

        followLine();
        
        driverStationLCD.println(DriverStationLCD.Line.kUser2, 2, "Robot Speed Left: " + jaguarLeft.get());
        driverStationLCD.println(DriverStationLCD.Line.kUser3, 2, "Robot Speed Right: " + jaguarRight.get());
        
        if(firstTimeAutonomous)
        {
            //encoderArm.start();
            jaguarLeft.set(-defaultRobotSpeed);
            jaguarRight.set(defaultRobotSpeed);
            firstTimeAutonomous = false;
            //pulses = (int) calculatePulses((int)whichPeg());
            //WALL_DISTANCE = distanceFromWall(whichPeg());
            //timer.start();
        }
        if (!upperArmLimit.get())
        {
            moveArm(-0.8);
        }
        /*
        if (rangeSensor.getRangeInches() < 20) //to be changed
        {
            timer.delay(.5);
            //moveArm(.8);
            moveGripper(.8);
        }

        //raise arm 1/4 of the way that it needs to go, then stop for 5 seconds
        //so extension can drop out and lock into place
        raiseArm(pulses/4.0);
        if(timer.get()>5000000)
        {
        raiseArm(pulses);
        timer.stop();
        }
        if(rangeSensor.getRangeInches() > WALL_DISTANCE)
        {
            
            /*To use camera: use this method instead of followLine and delete
             * the next else block
             */
            //followCamera();
/*
        }
    
        else
        {
            if (!laneSwitch1.get() && !laneSwitch2.get())
            {
            jaguarLeft.set(0);
            jaguarRight.set(0);
            moveClaw(-.2);
            }
            //turn right and move back after left turn at y split, then move forward and put piece
            if (laneSwitch1.get()&& !endYTurn)
            {
                endYTurn = true;
                jaguarLeft.set(.10);
                jaguarRight.set(-.20);
                Timer.delay(.5);
                jaguarLeft.set(-.1);
                jaguarRight.set(.1);
                Timer.delay(.2);
                jaguarLeft.set(0);
                jaguarRight.set(0);
                moveClaw(-.2);
            } 
            //turn left and move back after right turn at y split, then move forward and put piece
            if (laneSwitch2.get()&& !endYTurn)
            {
                endYTurn = true;
                jaguarLeft.set(.20);
                jaguarRight.set(-.10);
                Timer.delay(.5);
                jaguarLeft.set(-.1);
                jaguarRight.set(.1);
                Timer.delay(.2);
                jaguarLeft.set(0);
                jaguarRight.set(0);
                moveClaw(-.2);
            }
        }

        watchDog.feed();
        driverStationLCD.updateLCD();

  */
    }

    public void teleopPeriodic()
    {
        if(robotCamera == null)
        {
            try
            {
                robotCamera = AxisCamera.getInstance();
            }
            catch(NullPointerException e)
            {
                
            }

        }
        
        //SensorData
        driverStationLCD.println(DriverStationLCD.Line.kUser3, 2, "Line status: " + robotLineSensorDisplay());

        //Camera Video Output
        //robotCamera();

        //Main Driver Code
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
        moveGripper(-xboxAuxController.getY());
        moveArm(xboxAuxController.getMagnitude());
        
        //moveArm(xboxController.getThrottle());
        deployMinibot(3,2);
        //Compressor
        //compressor();

        //Display Output
        //driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Arm Encoder Pulses: " + encoderArm.getAngle());
        driverStationLCD.println(DriverStationLCD.Line.kUser3, 2, "Ultrasonic(Inches): " + rangeSensor.getRangeInches());
        //driverStationLCD.println(DriverStationLCD.Line.kUser3, 2, "Arm Upper:  " + lowerArmLimit.get());
        //driverStationLCD.println(DriverStationLCD.Line.kUser4, 2, "Arm Bottom:  " + upperArmLimit.get());
        //Watchdog and driverStationLCD updaters
        watchDog.feed();
        driverStationLCD.updateLCD();
    }

    public void compressor()
    {
        if(!pressureCuttoff.get() && xboxAuxController.getRawButton(1))
        {
            compressorSpike.set(Relay.Value.kOn);
        }
        else if(pressureCuttoff.get() || !xboxAuxController.getRawButton(1))
        {
            compressorSpike.set(Relay.Value.kOff);
        }
        else
        {
            compressorSpike.set(Relay.Value.kOff);
        }
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

    public void robotCamera()
    {
        try
        {
            if (robotCamera.freshImage())
            {
                image = robotCamera.getImage();
            }
        }
        catch(edu.wpi.first.wpilibj.camera.AxisCameraException e)
        {
            e.printStackTrace();
        }
        catch(edu.wpi.first.wpilibj.image.NIVisionException e)
        {
            e.printStackTrace();
        }
    }
    
    public void moveGripper(double speed)
    {         
            if(outerClawLimit.get() && (speed > XBOX_SENSITIVITY))
            {
                clawJaguar.set(speed);
            }
            else if(innerClawLimit.get() && (speed < -XBOX_SENSITIVITY))
            {
                clawJaguar.set(speed);
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
    
    //if arm encoder pulses is less than what is needed, raise arm
    /*public void raiseArm(double pulses)
    {
        if (encoderArm.getAngle()< pulses)
        {
            jaguarArm.set(0.8);
        }
        if (encoderArm.getAngle() > pulses)
        {
            jaguarArm.set(0);
        }
    }*/

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

        if(rangeSensor.getRangeInches() > 18)
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
                        }
                        else
                        {
                            jaguarLeft.set(-0.6);
                            jaguarRight.set(-0.6);
                        }
                        split = true;
                    break;

                    default:
                        jaguarLeft.set(0.0);
                        jaguarRight.set(0.0);
                        if (lastStatus == 100)
                        {
                            jaguarLeft.set(jaguarLeftSpeed -= 0.015);
                            jaguarRight.set(jaguarRightSpeed -= 0.015);
                        }
                        if (lastStatus == 1)
                        {
                            jaguarLeft.set(jaguarLeftSpeed += 0.015);
                            jaguarRight.set(jaguarRightSpeed += 0.015);
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

    //code for following vision targets in x plane only (drive motors, not arm)
    private void followCamera()
    {
        try
        {
            image = robotCamera.getImage();
            firstImage = image.thresholdHSL(140, 155, 100, 255, 40, 255);
            analysis = firstImage.getOrderedParticleAnalysisReports(3);
        }
        catch(edu.wpi.first.wpilibj.camera.AxisCameraException e){}
        catch(edu.wpi.first.wpilibj.image.NIVisionException e){}

        if (image != null)
        {
            for (int i = 0; i < analysis.length; i++)
            {
                ParticleAnalysisReport imageAnalysis = analysis[i];
                if (imageAnalysis.particleToImagePercent > .001)
                {
                    jaguarLeft.set(defaultRobotSpeed + imageAnalysis.center_mass_x_normalized);
                    jaguarRight.set(defaultRobotSpeed + imageAnalysis.center_mass_x_normalized);
                }
            }
        }
    }
    //raises the arm for a set amount of time
}
