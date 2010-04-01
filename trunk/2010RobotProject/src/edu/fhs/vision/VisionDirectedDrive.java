/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.vision;

import edu.fhs.input.UltrasonicFHS;
import edu.fhs.input.IRRangeFinderFHS;
import edu.fhs.actuators.KickerControl;
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

    private Gyro gyro;
    private Joystick js;
    private RobotDrive drive;
    private CircleFinder circleFinder;
    private int mode;
    private int lastMode;
    private int kickingDelay;
    private PIDController driveTowardsRamp;
    private UltrasonicFHS ultraFrontRight;
    private UltrasonicFHS ir;
    private KickerControl kc;
    private final double DISTANCE_FROM_RAMP = 24.0;
    private final double KICKING_DISTANCE_THRESHHOLD = 7.0;
    

    public VisionDirectedDrive(Gyro g, Joystick j, RobotDrive d,UltrasonicFHS fr,UltrasonicFHS i,KickerControl k){
        gyro = g;
        js = j;
        drive = d;
        circleFinder = new CircleFinder(g,j,d);
        mode = 0;
        lastMode = 0;
        ultraFrontRight = fr;
        ir=i;
        kc = k;
        kickingDelay = 0;
        driveTowardsRamp = new PIDController(.5,.2,.8,ultraFrontRight,new PIDOutput(){
            public void pidWrite(double output){
                drive.holonomicDrive(output, 0, 0);
            }
        });
    }
    
    public void initialize(){
        circleFinder.intialize();
        driveTowardsRamp.setInputRange(0.0, 480.0);
        driveTowardsRamp.setTolerance(1.0);
        driveTowardsRamp.setSetpoint(DISTANCE_FROM_RAMP);
    }
    
    public void autonomousZoneOne(){

        
        switch (mode){
            case 0: circleFinder.centerOnCircle(!circleFinder.isOnTarget());
                    if(circleFinder.isOnTarget()){
                        mode++;
                    }break;
            case 1: if(lastMode ==0){
                        driveTowardsRamp.enable();
                        lastMode++;
                    }else if(driveTowardsRamp.onTarget()){
                        driveTowardsRamp.disable();
                        mode++;
                    }break;
            default: break;
        }

      
        
    }
    
    public void autonomousZoneTwo(){
        
        switch (mode){

            case 0: if(ir.getRangeInches() > KICKING_DISTANCE_THRESHHOLD){
                        drive.holonomicDrive(.3,0,0);
                    }else{
                        drive.holonomicDrive(0,0,0);
                        mode++;
                    }break;

            case 1: if(kickingDelay == 0){
                        kick(true);
                        kickingDelay++;
                    }else if(kickingDelay < 250){
                        kickingDelay++;
                    }else{
                        kick(false);
                        kickingDelay = 0;
                        mode++;
                    }break;

            case 2: if (ir.getRangeInches() > KICKING_DISTANCE_THRESHHOLD){
                        drive.holonomicDrive(.3,90,0);
                    }else{
                        mode++;
                    }break;

            case 3: circleFinder.centerOnCircle(!circleFinder.isOnTarget());
                    if(circleFinder.isOnTarget()){
                        mode++;
                    }break;

            case 4: if(kickingDelay == 0){
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
    
    public void kick(boolean k){
        kc.getSolenoid1().set(k);
        kc.getSolenoid2().set(k);
        kc.getSolenoid3().set(k);
        kc.getSolenoid4().set(k);
    }
}

   