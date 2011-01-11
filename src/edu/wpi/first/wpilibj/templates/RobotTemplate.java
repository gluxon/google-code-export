/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Watchdog;

public class RobotTemplate extends IterativeRobot
{
    private static final int SLOT_1 = 1;
    private static final int SLOT_8 = 8;

    private static final int DRIVE_MODE = 0;

    private Joystick joystick1;
    private Joystick joystick2;

    private Jaguar jaguarLeft;
    private Jaguar jaguarRight;

    private DriverStationLCD driverStationLCD;
    private DriverStation driverStation;

    private Watchdog watchdog1;

    public void robotInit()
    {
        joystick1 = new Joystick(1);
        joystick2 = new Joystick(2);
        
        jaguarLeft = new Jaguar(1);
        jaguarRight = new Jaguar(2);

        driverStationLCD = DriverStationLCD.getInstance();
        driverStation = DriverStation.getInstance();

        watchdog1 = Watchdog.getInstance();

        watchdog1.feed();
        driverStationLCD.updateLCD();
    }

    public void autonomousPeriodic()
    {
        watchdog1.feed();
        driverStationLCD.updateLCD();
    }

    public void teleopPeriodic()
    {
        if(DRIVE_MODE == 0)
        {
            jaguarLeft.set(joystick1.getY());
            jaguarRight.set(joystick2.getY());
        }
        else if(DRIVE_MODE == 1)
        {
            jaguarLeft.set(joystick1.getY() - joystick1.getX());
            jaguarRight.set(joystick1.getY() + joystick1.getX());
        }
        
        driverStationLCD.println(DriverStationLCD.Line.kUser2, 0, "Joystick1(X): " + joystick1.getX() + " (Y): " + joystick1.getY());
        driverStationLCD.println(DriverStationLCD.Line.kUser3, 0, "Joystick2(X): " + joystick2.getX() + " (Y): " + joystick2.getY());
        
        watchdog1.feed();
        driverStationLCD.updateLCD();
    }
    
}
