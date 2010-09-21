/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;


public class RobotTemplate extends IterativeRobot
{

    private static final int MOTOR_LEFT = 1;
    private static final int MOTOR_RIGHT = 2;

    private Joystick joy1;
    private Joystick joy2;
    private Victor leftMotor;
    private Victor rightMotor;

    public void robotInit()
    {
        joy1 = new Joystick(1);
        joy2 = new Joystick(2);
        leftMotor = new Victor(MOTOR_LEFT);
        rightMotor = new Victor(MOTOR_RIGHT);
    }

    public void autonomousPeriodic()
    {

    }

    public void teleopPeriodic()
    {
        joy1.getY();
        joy2.getY();
    }
    
}
