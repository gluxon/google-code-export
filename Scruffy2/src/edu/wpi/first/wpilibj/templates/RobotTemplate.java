/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Accelerometer;
import edu.wpi.first.wpilibj.GearTooth;
import edu.wpi.first.wpilibj.RobotDrive;

import edu.fhs.input.UltrasonicFHS;
import edu.fhs.input.IRRangeFinderFHS;

public class RobotTemplate extends IterativeRobot
{
    private static final int MOTOR_LEFT = 1;
    private static final int MOTOR_RIGHT = 2;
    private static final int SLOT_1 = 1;
    private static final int SLOT_8 = 8;
    private static final double ROBOT_SPEED = 0.5;

    private int AUTONOMOUS_MODE = 0;

    private Joystick joy1;
    private Joystick joy2;

    private RobotDrive robotDrive1;

    private Victor leftMotor;
    private Victor rightMotor;

    private Gyro gyro1;
    private UltrasonicFHS ultrasonic1;
    private IRRangeFinderFHS irrange1;
    private GearTooth geartooth1;
    private Accelerometer accel1;
    
    public void robotInit()
    {
        joy1 = new Joystick(1);
        joy2 = new Joystick(2);

        leftMotor = new Victor(MOTOR_LEFT)
        {
            public void set(double d)
            {
                super.set(d * ROBOT_SPEED);
            }
        };
        rightMotor = new Victor(MOTOR_RIGHT)
        {
            public void set(double d)
            {
                super.set(d * ROBOT_SPEED);
            }
        };

        robotDrive1 = new RobotDrive(leftMotor, rightMotor);

        gyro1 = new Gyro(SLOT_1, 1);
        ultrasonic1 = new UltrasonicFHS(SLOT_1, 2);
        irrange1 = new IRRangeFinderFHS(SLOT_1, 3);
        geartooth1 = new GearTooth(SLOT_1, 4);
        accel1 = new Accelerometer(SLOT_1, 5);
    }

    public void autonomousPeriodic()
    {
        if(AUTONOMOUS_MODE == 0)
        {
            leftMotor.set(0.0);
            rightMotor.set(0.0);
        }
        else if(AUTONOMOUS_MODE == 1)
        {
            if(ultrasonic1.getRangeInches() > 120.0)
            {
                robotDrive1.tankDrive(1.0, 1.0);
            }
            else
            {
                robotDrive1.tankDrive(0.0, 0.0);
            }
        }
        else if(AUTONOMOUS_MODE == 2)
        {
            if(irrange1.getRangeInches() > 10.0)
            {
                robotDrive1.tankDrive(1.0, 1.0);
            }
            else
            {
                robotDrive1.tankDrive(0.0, 0.0);
            }
        }
    }

    public void teleopPeriodic()
    {
        robotDrive1.tankDrive(joy1, joy2);
    }
}