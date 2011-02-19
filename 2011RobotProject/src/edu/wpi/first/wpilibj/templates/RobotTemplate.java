                 /*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Watchdog;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;


public class RobotTemplate extends IterativeRobot
{
    private static final double PI = Math.PI;

    private static final int SLOT_1 = 1;

    //slot on the digital sidecar that is for digital IO
    private static final int SIDECAR_IO_SLOT = 4;

    //initial robot speed to be used during autonomous
    private static final double defaultRobotSpeed = 0.25;

    //circumference of the drive shaft (mm)
    private static final double DRIVE_SHAFT_CIRC = 10.053;

    //leeway for the difference between  the two getDistance() values of the encoders
    private static final double ENCODER_DIST_ERROR = 0;

    //distance the robot should go before putting up game piece in autonomous
    private static final double DISTANCE_TRAVELED = 20;

    //minimum xbox input value needed to respond - this corrects for xbox oversensitivity
    private static final double XBOX_SENSITIVITY = 0.1;

    //height of arm - ground to pivot point
    private static final double ARM_HEIGHT = 54.5;

    //length of arm - pivot point to mid-way of gripper
    private static final double ARM_LENGTH = 83;

    //distance from middle of base of arm to the front
    private static final double FRONT_LENGTH = 16;

    //distance from wall to stop(inches)
    private static double WALL_DISTANCE;

    //three predetermined heights (inches) for arm during auto
    private static final double TOP_HEIGHT_HIGH = 112;
    private static final double MID_HEIGHT_HIGH = 76;
    private static final double BOTTOM_HEIGHT_HIGH = 39;

    //heights of spokes on outer lane (low spokes)inches
    private static final double TOP_HEIGHT_LOW = 104.5;
    private static final double MID_HEIGHT_LOW = 68;
    private static final double BOTTOM_HEIGHT_LOW = 30.5;
    
    //Lengths of the spokes(inches)
    private static final double SPOKE_LENGTH = 17.5;

    //Pulses of encoderArm per revolution of arm
    private static final double PULSES_PER_REV = 1440;

    //xbox is for arm, joystick is for driving main bot
    private Joystick xboxController, xboxDriveController;

    //drive, arm and claw speed controllers
    private Jaguar jaguarLeft, jaguarRight, jaguarArm, clawJaguar;

    //claw limit switches
    private DigitalInput outerClawLimit, innerClawLimit;

    private DriverStationLCD driverStationLCD;

    private Watchdog watchDog;

    //line sensors
    private DigitalInput leftLineSensor, centerLineSensor, rightLineSensor;

    //two slots for arm height switch
    private DigitalInput heightSwitch1, heightSwitch2;

    //two slots for chosing lanes
    private DigitalInput laneSwitch1, laneSwitch2;

    private Ultrasonic rangeSensor;

    //encoders
    private Encoder encoderLeft, encoderRight, encoderArm;

    private double distanceRobotLeft, distanceRobotRight;

    //solenoid
    private Solenoid miniBotSolenoid;

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

    private String defaultDirection = "LEFT";

    private boolean firstTimeAutonomous;

    public void robotInit()
    {
        try //everything
        {
        xboxController = new Joystick(2);
        xboxDriveController = new Joystick(1);

        //drive jaguars
        jaguarLeft = new Jaguar(9);
        jaguarRight = new Jaguar(8);

        //arm jags
        jaguarArm = new Jaguar(7);
        clawJaguar = new Jaguar(6);
      
        //claw limit switches
        outerClawLimit = new DigitalInput(SIDECAR_IO_SLOT, 4);
        innerClawLimit = new DigitalInput(SIDECAR_IO_SLOT, 5);

        //line sensors
        leftLineSensor = new DigitalInput(1);
        centerLineSensor = new DigitalInput(2);
        rightLineSensor = new DigitalInput(3);

        //two arm height and lane switch inputs
        //heightSwitch1 = new DigitalInput(6);
        //heightSwitch2 = new DigitalInput(7);
        //laneSwitch1 = new DigitalInput(8);
        //laneSwitch2 = new DigitalInput(9);

        //declare encoders
        //encoderLeft = new Encoder(SIDECAR_IO_SLOT, 12);
        //encoderRight = new Encoder(SIDECAR_IO_SLOT, 13);
        //encoderArm = new Encoder(SIDECAR_IO_SLOT, 6);
        
        //Apparently this year's encoders gives 1440 pulses per revolution, or .25 degrees per pulse
        //encoderRight.setDistancePerPulse(DRIVE_SHAFT_CIRC);
        //encoderLeft.setDistancePerPulse(DRIVE_SHAFT_CIRC);
        encoderArm.setDistancePerPulse(DRIVE_SHAFT_CIRC);

        encoderArm.start();

        //ultrasonic sensor
        //rangeSensor = new Ultrasonic (SLOT_1, 4);
        timer = new Timer();

        //camera stuff
        robotCamera = AxisCamera.getInstance();
        robotCamera.writeBrightness(5);
        robotCamera.writeColorLevel(10);
        robotCamera.writeResolution(AxisCamera.ResolutionT.k160x120);

        //minibot deployment solenoid
        miniBotSolenoid = new Solenoid(8, 1);
        }
        catch(NullPointerException ex)
        {
            //no clue what to do; just DON'T PANIC!!!
        }
        

        driverStationLCD = DriverStationLCD.getInstance();

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
       
        encoderLeft.start();
        encoderRight.start();

        
        distanceRobotRight = encoderRight.getDistance();
        distanceRobotLeft = encoderLeft.getDistance();

        robotDirectionLeft = encoderLeft.getDirection();
        robotDirectionRight = encoderRight.getDirection();
        
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
        
        if(firstTimeAutonomous)
        {
            encoderArm.start();
            jaguarLeft.set(-defaultRobotSpeed);
            jaguarRight.set(defaultRobotSpeed);
            firstTimeAutonomous = false;
            pulses = calculatePulses(heightRaised(), ARM_HEIGHT, ARM_LENGTH);
            distanceFromWall(heightRaised());
            timer.start();
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
            followLine();
            /*To use camera: use this method instead of followLine and delete
             * the next else block
             */
            //followCamera();

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

    }

    public void teleopPeriodic()
    {
        /************************sensor data********************/
        int leftLineValue = (leftLineSensor.get() ? 1 : 0);
        int centerLineValue = (centerLineSensor.get() ? 1 : 0);
        int rightLineValue = (rightLineSensor.get() ? 1 : 0);

        //double jaguarLeftSpeed = jaguarLeft.get();
        //double jaguarRightSpeed = jaguarRight.get();

        int statusValue = leftLineValue * 100 + centerLineValue * 10 + rightLineValue;

        //driverStationLCD.println(DriverStationLCD.Line.kUser3, 2, "Line status: " + statusValue);
        //driverStationLCD.println(DriverStationLCD.Line.kUser4, 2, "Inches from wall: " + rangeSensor.getRangeInches());
        //driverStationLCD.println(DriverStationLCD.Line.kUser2, 2, "Arm Encoder Pulses: " + encoderArm.get());
        driverStationLCD.println(DriverStationLCD.Line.kUser3, 2, "Outer: " + outerClawLimit.get());
        driverStationLCD.println(DriverStationLCD.Line.kUser4, 2, "Inner: " + innerClawLimit.get());
        //driverStationLCD.println(DriverStationLCD.Line.kUser5, 2, "right line sensor: " + rightLineSensor.get());

        //camera video output
        /*try
        {
            if (robotCamera.freshImage())
                {
                //image.free();
                 image = robotCamera.getImage();
                }
        }
        catch(edu.wpi.first.wpilibj.camera.AxisCameraException e){
            e.printStackTrace();
        }
        catch(edu.wpi.first.wpilibj.image.NIVisionException e){
            e.printStackTrace();
        }

        /*******************************Main Driver Code**************************************/

            jaguarLeft.set(-(xboxDriveController.getThrottle()*0.65) + xboxDriveController.getY());
            jaguarRight.set(-(xboxDriveController.getThrottle()*0.65) - xboxDriveController.getY());

            //deploys minibot
            //if(xboxDriveController.getTrigger())
                //miniBotSolenoid.set(true);
            //else
                //miniBotSolenoid.set(false);

        /*******************************Aux Driver Code****************************************/

        //if(xboxController.getY() > XBOX_SENSITIVITY || xboxController.getY() < -XBOX_SENSITIVITY)
        //{
            jaguarArm.set(xboxController.getThrottle());
            
            if(outerClawLimit.get() && xboxController.getY() < 0)
            {
                clawJaguar.set(xboxController.getY());
            }
            else if(innerClawLimit.get() && xboxController.getY() > 0)
            {
                clawJaguar.set(xboxController.getY());
            }
        //}

        watchDog.feed();
        driverStationLCD.updateLCD();
    }
    
    //if arm encoder pulses is less than what is needed, raise arm
    public void raiseArm(double pulses)
    {
        while(encoderArm.get()< pulses)
        {
            jaguarArm.set(0.8);
        }
        if (encoderArm.get() > pulses)
        {
            jaguarArm.set(0);
        }
    }

    //moves the claw during teleop
    public void moveClaw(double speed)
    {
        if (speed < XBOX_SENSITIVITY)
        {
            if (!outerClawLimit.get())
            {
                clawJaguar.set(speed);
            }
            else
            {
              driverStationLCD.println(DriverStationLCD.Line.kUser6, 2, "Outer limit is pressed, can't move claw outwards!");
              clawJaguar.set(0.0);
            }


        }
        if (speed > (-1*XBOX_SENSITIVITY))
        {
            if (!innerClawLimit.get())
            {
                clawJaguar.set(speed);
            }
            else
            {
              //overwrites outer limit pressed output
              driverStationLCD.println(DriverStationLCD.Line.kUser6, 2, "Inner limit is pressed, can't move claw inwards! ");
              clawJaguar.set(0.0);
            }
        }
        driverStationLCD.updateLCD();
    }

    //returns the number of pulses that the arm encoder must see before stopping
    public int calculatePulses(double spokeHeight, double ARM_HEIGHT, double ARM_LENGTH)
    {
        //starting angle(Radians), assuming pivot point of arm is 21in from front
        //and arm is 54.5in off of ground
        double startingAngle = 0.368877462;

        //calculates the angle
        double endingAngle;
        if ((ARM_HEIGHT - spokeHeight) != 0)
        {
            endingAngle = MathUtils.acos((ARM_HEIGHT - spokeHeight)/ARM_LENGTH);
        }
        else
            endingAngle = PI / 2.0;
        double deltaAngle = endingAngle - startingAngle;
        return (int)((deltaAngle / (2*PI)) * PULSES_PER_REV);


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

        int statusValue = leftLineValue * 100 + centerLineValue * 10 + rightLineValue;

        driverStationLCD.println(DriverStationLCD.Line.kUser2, 2, "Status: " + statusValue);
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
            case 0:
                jaguarLeft.set(0.0);
                jaguarRight.set(0.0);
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

    //find what spoke to use through the switches' input
    private double heightRaised()
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
    private void distanceFromWall(double height)
    {
       WALL_DISTANCE = Math.sqrt(MathUtils.pow(ARM_LENGTH, 2)-MathUtils.pow(height-ARM_HEIGHT, 2)) + .5 * SPOKE_LENGTH - FRONT_LENGTH;
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

}
