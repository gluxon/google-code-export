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
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.samples.Target;

import edu.fhs.input.UltrasonicFHS;
import edu.fhs.input.IRRangeFinderFHS;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;

import java.util.Date;

public class RobotTemplate extends IterativeRobot
{
    private static final int MOTOR_LEFT = 1;
    private static final int MOTOR_RIGHT = 2;
    private static final int SLOT_1 = 1;
    private static final int SLOT_8 = 8;
    private static final double ROBOT_SPEED = 1;

    private int AUTONOMOUS_MODE = 0;

    private DriverStationLCD dsout;

    private Date time = new Date();
    private Target[] targets = new Target[1];

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
    private AnalogChannel limitSwitchRight;
    
    public void robotInit()
    {
        joy1 = new Joystick(1);

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

        dsout = DriverStationLCD.getInstance();

        try
        {
            limitSwitchRight = new AnalogChannel(SLOT_1, 4);
        }
        catch(NullPointerException n)
        {
            
        }
/*
        try
        {
            gyro1 = new Gyro(SLOT_1, 1);
            ultrasonic1 = new UltrasonicFHS(SLOT_1, 2);
            irrange1 = new IRRangeFinderFHS(SLOT_1, 3);
            geartooth1 = new GearTooth(SLOT_1, 4);
            accel1 = new Accelerometer(SLOT_1, 5);
        }
        catch(NullPointerException n)
        {
            dsout.println(DriverStationLCD.Line.kUser5, 0,"ERROR: Sensor(s) not connected!");
        }
  */      dsout.updateLCD();
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
        dsout.updateLCD();
    }

    public void teleopPeriodic()
    {
        leftMotor.set(joy1.getX()-joy1.getY());
        rightMotor.set(joy1.getX()+joy1.getY());

        dsout.println(DriverStationLCD.Line.kMain6, limitSwitchRight.getValue(), null);

        dsout.updateLCD();
    }

    void updateDashboardLow() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        lowDashData.addCluster();
        lowDashData.addCluster();
        //analog modules
        lowDashData.addCluster();
        for (int i = 1; i <= 8; i++) {
            lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
        }
        lowDashData.finalizeCluster();
        lowDashData.addCluster();
        for (int i = 1; i <= 8; i++) {
            lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
        }
        lowDashData.finalizeCluster();
        lowDashData.finalizeCluster();
        lowDashData.addCluster();
        //digital modules
        lowDashData.addCluster();
        lowDashData.addCluster();
        int module = 4;
        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
        lowDashData.addCluster();
        for (int i = 1; i <= 10; i++) {
            lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
        }
        lowDashData.finalizeCluster();
        lowDashData.finalizeCluster();
        lowDashData.finalizeCluster();
        lowDashData.addCluster();
        lowDashData.addCluster();
        module = 6;
        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
        lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
        lowDashData.addCluster();
        for (int i = 1; i <= 10; i++) {
            lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
        }
        lowDashData.finalizeCluster();
        lowDashData.finalizeCluster();
        lowDashData.finalizeCluster();
        lowDashData.finalizeCluster();
        lowDashData.addByte(Solenoid.getAll());
        lowDashData.addDouble(0.0);
        lowDashData.finalizeCluster();
        lowDashData.commit();

    }

    public void updateDashboardHigh(double joyStickX, double gyroAngle, double gyroRate,
            double targetX, Target[] targets) {
        Dashboard highDashData = DriverStation.getInstance().getDashboardPackerHigh();
        highDashData.addCluster(); // wire (2 elements)
        highDashData.addCluster(); // tracking data
        highDashData.addDouble(joyStickX); // Joystick X
        highDashData.addDouble(((gyroAngle + 360.0 + 180.0) % 360.0) - 180.0); // angle
        highDashData.addDouble(gyroRate); // angular rate
        highDashData.addDouble(targetX); // other X
        highDashData.finalizeCluster();
        highDashData.addCluster(); // target Info (2 elements)
        highDashData.addArray();
		//TODO Limit what we send back - we don't display targets at dash,
		//  do we really need to send anything at all?
        for (int i = 0; i < targets.length; i++) {
            highDashData.addCluster(); // targets

            double targetScore = 0;
            double m_xPos = 0;
            double m_xMax = 1; //this becomes a divisor
            double m_yPos = 0;
            if (targets != null & targets[i] != null) {
                targetScore = targets[i].m_score;
                m_xPos = targets[i].m_xPos;
                m_xMax = targets[i].m_xMax;
                m_yPos = targets[i].m_yPos;
            }
            highDashData.addDouble(targetScore); // target score
            highDashData.addCluster(); // Circle Description (5 elements)
            highDashData.addCluster(); // Position (2 elements)
            highDashData.addFloat((float) (m_xPos / m_xMax)); // X
            highDashData.addFloat((float) m_yPos); // Y
            highDashData.finalizeCluster();
            if (targets == null || targets[i] == null) {
                highDashData.addDouble(0d); // Angle
                highDashData.addDouble(0d); // Major Radius
                highDashData.addDouble(0d); // Minor Radius
                highDashData.addDouble(0d); // Raw score
            } else {
                highDashData.addDouble(targets[i].m_rotation); // Angle
                highDashData.addDouble(targets[i].m_majorRadius); // Major Radius
                highDashData.addDouble(targets[i].m_minorRadius); // Minor Radius
                highDashData.addDouble(targets[i].m_rawScore); // Raw score
            }
            highDashData.finalizeCluster(); // Position
        }
        highDashData.finalizeCluster(); // targets
        highDashData.finalizeArray();
        highDashData.addInt((int) time.getTime());
        highDashData.finalizeCluster(); // target Info
        highDashData.finalizeCluster(); // wire
        highDashData.commit();
    }
}