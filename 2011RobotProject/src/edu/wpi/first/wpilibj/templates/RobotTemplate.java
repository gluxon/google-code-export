/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Watchdog;

public class RobotTemplate extends IterativeRobot
{
    private static final int SLOT_1 = 1;
    private static final int SLOT_8 = 8;
    private Joystick joystickLeft;
    private Joystick joystickRight;

    private Jaguar jaguarLeft;
    private Jaguar jaguarRight;

    private DriverStationLCD driverStationLCD;

    private Watchdog watchDog;

    public void robotInit()
    {
        joystickLeft = new Joystick(1);
        joystickRight = new Joystick(2);
        
        jaguarLeft = new Jaguar(1);
        jaguarRight = new Jaguar(2);

        driverStationLCD = DriverStationLCD.getInstance();

        watchDog = Watchdog.getInstance();

        watchDog.feed();
        driverStationLCD.updateLCD();
    }

    public void autonomousPeriodic()
    {
        watchDog.feed();
        driverStationLCD.updateLCD();
    }

    public void teleopPeriodic()
    {
        arcadeDrive();

        driverStationLCD.println(DriverStationLCD.Line.kUser2, 0, "Joystick1(X): " + joystickLeft.getX() + " (Y): " + joystickLeft.getY());
        driverStationLCD.println(DriverStationLCD.Line.kUser3, 0, "Joystick2(X): " + joystickRight.getX() + " (Y): " + joystickRight.getY());
        
        watchDog.feed();
        driverStationLCD.updateLCD();
    }

    public void arcadeDrive()
    {
        double leftMotorSpeed;
        double rightMotorSpeed;

        double leftInputY = joystickLeft.getY();
        double leftInputX = joystickLeft.getX();

        if (leftInputY > 0.0)
        {
            if (leftInputX > 0.0)
            {
                leftMotorSpeed = leftInputY - leftInputX;
                rightMotorSpeed = Math.max(leftInputY, leftInputX);
            }
            else
            {
                leftMotorSpeed = Math.max(leftInputY, -leftInputX);
                rightMotorSpeed = leftInputY + leftInputX;
            }
        }
        else
        {
            if (leftInputX > 0.0)
            {
                leftMotorSpeed = -Math.max(-leftInputY, leftInputX);
                rightMotorSpeed = leftInputY + leftInputX;
            }
            else
            {
                leftMotorSpeed = leftInputY - leftInputX;
                rightMotorSpeed = -Math.max(-leftInputY, -leftInputX);
            }
        }

        jaguarLeft.set(leftMotorSpeed);
        jaguarRight.set(rightMotorSpeed);
    }
    
}
