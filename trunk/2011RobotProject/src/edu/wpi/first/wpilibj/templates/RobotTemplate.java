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
    private Joystick xboxController;

    private Jaguar jaguarLeft;
    private Jaguar jaguarRight;

    private DriverStationLCD driverStationLCD;

    private Watchdog watchDog;

    public void robotInit()
    {
        xboxController = new Joystick(1);
        
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
        if(xboxController.getThrottle() > 0.1 || xboxController.getThrottle() < -0.1 || xboxController.getY() > 0.1 || xboxController.getY() < -0.1)
        {
            jaguarLeft.set(-xboxController.getThrottle()+xboxController.getY());
            jaguarRight.set(-xboxController.getThrottle()-xboxController.getY());
        }
        
        watchDog.feed();
        driverStationLCD.updateLCD();
    }

}
