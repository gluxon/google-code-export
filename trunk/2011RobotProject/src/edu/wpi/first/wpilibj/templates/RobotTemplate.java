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
import edu.wpi.first.wpilibj.Watchdog;

public class RobotTemplate extends IterativeRobot
{
    private static final int SLOT_1 = 1;

    private Joystick xboxController;
    private Joystick auxDriverJoystick;

    private Jaguar jaguarLeft;
    private Jaguar jaguarRight;

    private Jaguar jaguarArm;

    private DriverStationLCD driverStationLCD;

    private Watchdog watchDog;

    private DigitalInput leftLineSensor;
    private DigitalInput centerLineSensor;
    private DigitalInput rightLineSensor;

    private Encoder encoderLeft;
    private Encoder encoderRight;

    private double distanceRobotLeft;
    private double distanceRobotRight;

    private boolean robotDirectionLeft;
    private boolean robotDirectionRight;

    private String defaultDirection = "LEFT";

    private boolean firstTimeAutonomous;

    public void robotInit()
    {
        xboxController = new Joystick(1);
        auxDriverJoystick = new Joystick(2);
        
        jaguarLeft = new Jaguar(1);
        jaguarRight = new Jaguar(2);

        jaguarArm = new Jaguar(3);

        leftLineSensor = new DigitalInput(1);
        centerLineSensor = new DigitalInput(2);
        rightLineSensor = new DigitalInput(3);

        encoderLeft = new Encoder(SLOT_1,1,SLOT_1,2);
        encoderRight = new Encoder(SLOT_1,3,SLOT_1,4);

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

        //driverStationLCD.println(DriverStationLCD.Line.kMain6, 0, "Left Distance: " + distanceRobotLeft);
        //driverStationLCD.println(DriverStationLCD.Line.kUser2, 0, "Right Distance: " + distanceRobotRight);
        //driverStationLCD.println(DriverStationLCD.Line.kUser3, 0, "Left Direction (True = Forward: " + robotDirectionLeft);
        //driverStationLCD.println(DriverStationLCD.Line.kUser4, 0, "Right Direction (True = Forward: " + robotDirectionRight);

        /****************************Line Following******************************/
        
        int leftLineValue = leftLineSensor.get()? 1: 0;
        int centerLineValue = centerLineSensor.get()? 1: 0;
        int rightLineValue = rightLineSensor.get()? 1: 0;

        int statusValue = leftLineValue * 100 + centerLineValue * 10 + rightLineValue;

        double defaultRobotSpeed = 0.5;

        if(firstTimeAutonomous)
        {
            jaguarLeft.set(defaultRobotSpeed);
            jaguarRight.set(defaultRobotSpeed);
        }


        double jaguarLeftSpeed = jaguarLeft.get();
        double jaguarRightSpeed = jaguarRight.get();


        switch(statusValue)
        {
            case 0 | 111 | 110 | 11:
                jaguarLeft.set(0.0);
                jaguarRight.set(0.0);
            break;

            case 1:
                jaguarLeft.set(jaguarLeftSpeed -= 0.1);
                jaguarRight.set(jaguarRightSpeed += 0.1);
            break;

            case 10:
                jaguarLeft.set(jaguarLeftSpeed);
                jaguarRight.set(jaguarRightSpeed);
            break;

            case 100:
                jaguarLeft.set(jaguarLeftSpeed += 0.1);
                jaguarRight.set(jaguarRightSpeed -= 0.1);
            break;

            case 101:
                if(defaultDirection.equals("LEFT"))
                {
                    jaguarLeft.set(-0.5);
                    jaguarRight.set(0.5);
                }
                else
                {
                    jaguarLeft.set(0.5);
                    jaguarRight.set(-0.5);
                }
            break;
            
        }

        watchDog.feed();
        driverStationLCD.updateLCD();
    }

    public void teleopPeriodic()
    {
        watchDog.feed();
        driverStationLCD.updateLCD();

        /*******************************Main Driver Code***************************************/

        if(xboxController.getThrottle() > 0.1 || xboxController.getThrottle() < -0.1 || xboxController.getY() > 0.1 || xboxController.getY() < -0.1)
        {
            jaguarLeft.set(-xboxController.getThrottle()+xboxController.getY());
            jaguarRight.set(-xboxController.getThrottle()-xboxController.getY());
        }

        /*******************************Aux Driver Code****************************************/

        if(auxDriverJoystick.getY() > 0.1 || xboxController.getY() < -0.1)
        {
            jaguarArm.set(auxDriverJoystick.getY());
        }
    }

}
