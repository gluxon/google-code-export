/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.vision;

import edu.fhs.input.UltrasonicFHS;
import edu.fhs.actuators.KickerControl;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 * @author programming
 */
public class VisionDirectedDrive{

    private RobotDrive drive;
    private CircleFinder circleFinder;
    private int mode;
    private int lastMode;
    private int kickingDelay;
    private PIDController driveTowardsRamp;
    private UltrasonicFHS ultraFrontRight;
    private UltrasonicFHS ir;
    private KickerControl kc;
    private AnalogChannel pressure;
    private final double DISTANCE_FROM_RAMP = 24.0;
    private final double KICKING_DISTANCE_THRESHHOLD = 10.0;
    

    public VisionDirectedDrive(Gyro Gyro, Joystick joystick, RobotDrive drive,UltrasonicFHS fr,UltrasonicFHS ultrasonic,KickerControl kicker, AnalogChannel psi){
        this.drive = drive;
        circleFinder = new CircleFinder(Gyro,joystick,this.drive);
        mode = 0;
        lastMode = 0;
        ultraFrontRight = fr;
        ir=ultrasonic;
        kc = kicker;
        kickingDelay = 0;
        pressure = psi;
        driveTowardsRamp = new PIDController(.5,.2,.8,ultraFrontRight,new PIDOutput(){
            public void pidWrite(double output){
                VisionDirectedDrive.this.drive.holonomicDrive(output, 0, 0);
            }
        });
    }
    
    public void initialize(){
        circleFinder.intialize();
        driveTowardsRamp.setInputRange(0.0, 480.0);
        driveTowardsRamp.setTolerance(1.0);
        driveTowardsRamp.setSetpoint(DISTANCE_FROM_RAMP);
    }
    
    public void autonomousCloseZone(){

        
        switch (mode){
            case 0: circleFinder.centerOnCircle(!circleFinder.isOnTarget());
                    if(circleFinder.isOnTarget()){
                        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "TARGET           ");
                        System.out.println("found");
                        mode++;
                    }break;
            case 1: if(lastMode ==0){
                        driveTowardsRamp.enable();
                        lastMode++;
                    }else if(driveTowardsRamp.onTarget()){
                        driveTowardsRamp.disable();
                        mode++;
                        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "DRIVING TO TARGET");
                        System.out.println("driving");
                    }break;
            default:
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "TARGET NOT FOUND ");
                System.out.println("no target");
                break;
        }

      
        
    }
    
    public void autonomousMiddleZone(){
        
        switch (mode){

            
            case 0: if((pressure.getAverageVoltage()*37.76-32.89) > 40){
                        mode++;
                    }break;
            
            case 1: if(ir.getRangeInches() > KICKING_DISTANCE_THRESHHOLD){
                        drive.holonomicDrive(.5,0,0);
                    }else{
                        drive.holonomicDrive(0,0,0);
                        mode++;
                    }break;

            case 2: if(kickingDelay == 0){
                        kick(true);
                        kickingDelay++;
                    }else if(kickingDelay < 250){
                        kickingDelay++;
                    }else{
                        kick(false);
                        kickingDelay = 0;
                        mode++;
                    }break;

            case 3: if (ir.getRangeInches() > KICKING_DISTANCE_THRESHHOLD){
                        drive.holonomicDrive(.3,90,0);
                    }else{
                        mode++;
                    }break;

            case 4: circleFinder.centerOnCircle(!circleFinder.isOnTarget());
                    if(circleFinder.isOnTarget()){
                        mode++;
                    }break;

            case 5: if(kickingDelay == 0){
                        kick(true);
                        kickingDelay++;
                    }else if(kickingDelay < 250){
                        kickingDelay++;
                    }else{
                        kick(false);
                        kickingDelay = 0;
                        mode++;
                    }break;

            default: break;
        }
    }

        public void autonomousFarZone(){

        }
        
    
    
    public void kick(boolean k){
        
        kc.getSolenoid1().set(k);
        kc.getSolenoid2().set(k);
        kc.getSolenoid3().set(k);
        kc.getSolenoid4().set(k);
        
    }
}

   