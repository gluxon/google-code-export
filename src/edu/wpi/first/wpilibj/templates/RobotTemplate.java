/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.fhs.actuators.KickerControl;
import edu.fhs.actuators.PitchSmoothing;
import edu.fhs.actuators.Pneumatics;
import edu.fhs.input.UltrasonicFHS;
import edu.fhs.input.IRRangeFinderFHS;
import edu.fhs.input.AuxDriver;
import edu.wpi.first.wpilibj.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot
{
private AuxDriver auxDrive;
private Joystick joy1;
private Joystick joy2;
private Joystick joy3;
private Jaguar leftFrontJag;
private Jaguar rightFrontJag;
private Jaguar leftRearJag;
private Jaguar rightRearJag;
private RobotDrive drive;
private Relay re1;
private Relay re2;
private Relay compressorRelay;
private DigitalInput transducer;
private Solenoid solenoid1;
private Solenoid solenoid2;
private Solenoid solenoid3;  //rename relays later according to usasge (elevator, base roller, etc.)
private Solenoid solenoid4;
private DriverStationLCD dsout;
private KickerControl kickerControl = new KickerControl();
private Pneumatics pneumatics = new Pneumatics();
private AnalogChannel pressure;
private Encoder encoder;
private UltrasonicFHS ultrasonicLF;
private UltrasonicFHS ultrasonicRF;
private UltrasonicFHS ultrasonicLB;
private UltrasonicFHS ultrasonicRB;
private IRRangeFinderFHS ir;
private Gyro gyro;
private PitchSmoothing pitchAdj = new PitchSmoothing(2);
private int delay = 20;
private double ultraV;
private double psi;
private int joy1Angle = 0;
double throttleDynamic = 0.0;
    /**    *
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        joy1 = new Joystick(1);
        joy2 = new Joystick(2);
        joy3 = new Joystick(3);
        leftFrontJag = new Jaguar(1);
        leftRearJag = new Jaguar(3);
        rightFrontJag = new Jaguar(2)
                {
                  public void set(double d)
                    {
                        super.set(d * -1);
                    }
                };
         rightRearJag = new Jaguar(4)
                {
                  public void set(double d)
                    {
                        super.set(d * -1);
                    }
                };
        drive = new RobotDrive(leftFrontJag, leftRearJag, rightFrontJag, rightRearJag, 1);

        try
        {
        compressorRelay = new Relay(1, Relay.Direction.kForward);
        transducer = new DigitalInput(10);
        }
        catch(NullPointerException n)
        {
            System.out.println("ERROR: compressor/regulator not connected");
        }
        compressorRelay.set(Relay.Value.kOff);
        if(compressorRelay != null && transducer != null)
        {
            if(!transducer.get()){compressorRelay.set(Relay.Value.kOn);}
            else {compressorRelay.set(Relay.Value.kOff);}
        }
        
       
        try {
            re1 = new Relay(3);
            re2 = new Relay(4);
            auxDrive = new AuxDriver(joy3);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Unable to init devices correctly!" );
            e.printStackTrace();
        }
         
      
       try
       {
            solenoid1 = new Solenoid(8,1);
            solenoid2 = new Solenoid(8,2);
            solenoid3 = new Solenoid(8,3);
            solenoid4 = new Solenoid(8,4);
            kickerControl.setSolenoid1(solenoid1);
            kickerControl.setSolenoid2(solenoid2);
            kickerControl.setSolenoid3(solenoid3);
            kickerControl.setSolenoid4(solenoid4);
       }
        catch(NullPointerException n){
            System.out.println("ERROR: solenoids not connected");
        }

        try
        {
            pressure = new AnalogChannel(1,1);
            ir = new IRRangeFinderFHS(1,2);
            ultrasonicLF = new UltrasonicFHS(1,3);//left front
            ultrasonicRF = new UltrasonicFHS(1,4);//right front
            ultrasonicLB = new UltrasonicFHS(1,5);//left back
            ultrasonicRB = new UltrasonicFHS(1,6);//right back
            gyro = new Gyro(1,7);
        }
         catch(NullPointerException n)
         {
             System.out.println("ERROR: sensors not connected");
         }
            
        if(kickerControl.getSolenoid1() == null || kickerControl.getSolenoid2() == null || kickerControl.getSolenoid3() == null || kickerControl.getSolenoid4() == null)
        {
            dsout.println(DriverStationLCD.Line.kUser6, 1, "solenoids set to null");
        }
        dsout.updateLCD();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
       
        if(compressorRelay != null && transducer != null)
        {
            if(!transducer.get()){compressorRelay.set(Relay.Value.kOn);}
            else {compressorRelay.set(Relay.Value.kOff);}
        }
        
        leftFrontJag.set(0);
        rightFrontJag.set(0);
        leftRearJag.set(0);
        rightRearJag.set(0);
        
        double inputSpeed = 0.5;
        updateDashboard();
        leftFrontJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(inputSpeed, isAutonomous(), gyro.getAngle()));
        rightFrontJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(inputSpeed, isAutonomous(), gyro.getAngle()));
        leftRearJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(inputSpeed, isAutonomous(), gyro.getAngle()));
        rightRearJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(inputSpeed, isAutonomous(), gyro.getAngle()));
        
         }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        updateDashboard();
        if(compressorRelay != null && transducer != null)
        {
            if(!transducer.get()){compressorRelay.set(Relay.Value.kOn);}
            else {compressorRelay.set(Relay.Value.kOff);}
        }

        if(joy1.getRawButton(6))
        {
            compressorRelay.set(Relay.Value.kOn);
        }
        if(joy1.getRawButton(5))
        {
            compressorRelay.set(Relay.Value.kOff);
        }



            if(joy1.getX() < -0.1)
            {
                throttleDynamic = -joy1.getX();
                joy1Angle = 270;
            }
            else if(joy1.getX() > 0.1)
            {
                throttleDynamic = joy1.getX();
                joy1Angle = 90;
            }
            else
            {
                throttleDynamic = joy1.getY();
                joy1Angle = 0;
            }
            drive.holonomicDrive(throttleDynamic, joy1Angle,joy1.getThrottle());//Omni Drive

        //drive.tankDrive(joy1.getY(),joy2.getY());//TankDrive
//        auxDrive.operate();//Auxillary Driver
        
        
         
        dsout.println(DriverStationLCD.Line.kMain6, 1,"Voltage" + truncate(ultrasonicLF.getAverageVoltage()) );
        dsout.println(DriverStationLCD.Line.kUser2, 1, "RF " + truncate(rightFrontJag.get()) + " RR " + truncate(rightRearJag.get()));
        psi = pressure.getAverageVoltage()*37.76-32.89;
        dsout.println(DriverStationLCD.Line.kUser3, 1, "pressure: " + truncate(psi));   
       
    

       
        
        if(joy1.getRawButton(4))
        {
             dsout.println(DriverStationLCD.Line.kUser5, 1, "Kicking...");
//             pneumatics.kick(kickerControl);
            kickerControl.getSolenoid1().set(true);
            kickerControl.getSolenoid2().set(true);
            kickerControl.getSolenoid3().set(true);
            kickerControl.getSolenoid4().set(true);
        }
        else
        {
             dsout.println(DriverStationLCD.Line.kUser5, 1, "");
             kickerControl.getSolenoid1().set(false);
             kickerControl.getSolenoid2().set(false);
             kickerControl.getSolenoid3().set(false);
             kickerControl.getSolenoid4().set(false);
        }
        dsout.updateLCD();
    }

    private double truncate(double rawDouble)
    {
        return ((double) ((int) (100 * rawDouble))) / 100;
    }

    void updateDashboard() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        lowDashData.addCluster();
        {
            lowDashData.addCluster();
            {     //analog modules
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

            lowDashData.addCluster();
            { //digital modules
                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 4;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 6;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

            }
            lowDashData.finalizeCluster();

            lowDashData.addByte(Solenoid.getAll());

            lowDashData.addDouble(999.123d);
        }
        lowDashData.finalizeCluster();
        lowDashData.commit();

    }
}
