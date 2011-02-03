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

public class RobotTemplate extends IterativeRobot
{
    private static final int SLOT_1 = 1;

    //slot on the digital sidecar that is for digital IO - confirm slot?
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
    private static final double XBOX_SENSITIVITY = 0.2;

    //distance for arm to travel to put up uber piece during autonomous (mm)
    private static final double VERTICAL_DISTANCE = 5;

    //xbox is for arm, joystick is for driving main bot
    private Joystick xboxController;
    private Joystick driverJoystick;

    //wheel jags
    private Jaguar jaguarLeft;
    private Jaguar jaguarRight;

    //claw jags
    private Jaguar jaguarArm;
    private Victor clawVictor;

    //claw limit switches
    private DigitalInput outerClawLimit;
    private DigitalInput innerClawLimit;

    private DriverStationLCD driverStationLCD;

    private Watchdog watchDog;

    //line sensors
    private DigitalInput leftLineSensor;
    private DigitalInput centerLineSensor;
    private DigitalInput rightLineSensor;

    //encoders
    private Encoder encoderLeft;
    private Encoder encoderRight;
    private Encoder encoderArm;

    private double distanceRobotLeft;
    private double distanceRobotRight;

    //solenoid
    private Solenoid miniBotSolenoid;

    private boolean robotDirectionLeft;
    private boolean robotDirectionRight;

    private boolean split = false;

    private String defaultDirection = "LEFT";

    private boolean firstTimeAutonomous;

    public void robotInit()
    {
        xboxController = new Joystick(2);
        driverJoystick = new Joystick(1);

        //drive jaguars
        jaguarLeft = new Jaguar(1);
        jaguarRight = new Jaguar(2);

        //arm jags
        jaguarArm = new Jaguar(3);
        clawVictor = new Victor(4);
        
        //claw limit switches
        outerClawLimit = new DigitalInput(SIDECAR_IO_SLOT, 4);
        innerClawLimit = new DigitalInput(SIDECAR_IO_SLOT, 5);

        //line sensors
        leftLineSensor = new DigitalInput(1);
        centerLineSensor = new DigitalInput(2);
        rightLineSensor = new DigitalInput(3);

        try
        {
        encoderLeft = new Encoder(SLOT_1,1,SLOT_1,2);
        encoderRight = new Encoder(SLOT_1,3,SLOT_1,4);
        encoderArm = new Encoder(SLOT_1, 5,SLOT_1, 6);
        
        //Apparently this year's encoders gives 1440 pulses per revolution, or .25 degrees per pulse
        encoderRight.setDistancePerPulse(DRIVE_SHAFT_CIRC * (.25/360));
        encoderLeft.setDistancePerPulse(DRIVE_SHAFT_CIRC * (.25/360));
        }
        catch(NullPointerException ex)
        {

        }
        //minibot solenoid
        //miniBotSolenoid = new Solenoid(8, 1);

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
       
      /*  encoderLeft.start();
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
          //  putGamePiece();
        }
*/
        if(firstTimeAutonomous)
        {
            encoderArm.start();
            jaguarLeft.set(-defaultRobotSpeed);
            jaguarRight.set(defaultRobotSpeed);
            //raiseArm();
            firstTimeAutonomous = false;
        }

        /****************************Line Following******************************/
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

            case 0:
                //not sure if robot will be able to get on end of line after Y split
                jaguarLeft.set(0.0);
                jaguarRight.set(0.0);
                putGamePiece();
            break;

            default:
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
     
        
        watchDog.feed();
        driverStationLCD.updateLCD();

    }

    public void teleopPeriodic()
    {
        /************************line sensor data********************/
        int leftLineValue = (leftLineSensor.get() ? 1 : 0);
        int centerLineValue = (centerLineSensor.get() ? 1 : 0);
        int rightLineValue = (rightLineSensor.get() ? 1 : 0);

        //double jaguarLeftSpeed = jaguarLeft.get();
        //double jaguarRightSpeed = jaguarRight.get();

        int statusValue = leftLineValue * 100 + centerLineValue * 10 + rightLineValue;

        driverStationLCD.println(DriverStationLCD.Line.kUser3, 2, "Status: " + statusValue);

        /*******************************Main Driver Code**************************************/

            jaguarLeft.set(-driverJoystick.getX()+ driverJoystick.getY());
            jaguarRight.set(-driverJoystick.getX()- driverJoystick.getY());



        /*******************************Aux Driver Code****************************************/

        if(xboxController.getY() > XBOX_SENSITIVITY || xboxController.getY() < -XBOX_SENSITIVITY)
        {
            jaguarArm.set(xboxController.getY());
            moveClaw(xboxController.getThrottle());
        }

         
        watchDog.feed();
        driverStationLCD.updateLCD();
    }
    public void raiseArm()
    {
       while(encoderArm.getDistance()< VERTICAL_DISTANCE)
        {
            jaguarArm.set(.2);
        }
        jaguarArm.set(0.0);
    }
    public void putGamePiece ()
    {
            //Note: may have to move forward
            encoderArm.stop();
            moveClaw(.2);
    }
    public void moveClaw(double speed)
    {
        if (speed > XBOX_SENSITIVITY)
        {
            if (!outerClawLimit.get())
            {
                clawVictor.set(speed);
            }
            else
              driverStationLCD.println(DriverStationLCD.Line.kUser4, 2, "Outer limit is pressed, can't move claw outwards!");
        }
        if (speed < -XBOX_SENSITIVITY)
        {
            if (!innerClawLimit.get())
            {
                clawVictor.set(speed);
            }
            else
            {
              //overwrites outer limit pressed output
              driverStationLCD.println(DriverStationLCD.Line.kUser4, 2, "Inner limit is pressed, can't move claw inwards! ");
            }
        }
        driverStationLCD.updateLCD();
    }
}