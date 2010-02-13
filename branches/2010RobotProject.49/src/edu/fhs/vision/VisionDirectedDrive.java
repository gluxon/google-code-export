/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.vision;

import edu.wpi.first.wpilibj.Gyro;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Ultrasonic;

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
    private final double MINUMUM_RANGE = 24.0;
    private PIDController driveTowardsRamp;
    private Ultrasonic ultraFrontRight;

    public VisionDirectedDrive(Gyro g, Joystick j, RobotDrive d,Ultrasonic fr){
        gyro = g;
        js = j;
        drive = d;
        circleFinder = new CircleFinder(g,j,d);
        mode = 0;
        lastMode = 0;
        ultraFrontRight = fr;
        driveTowardsRamp = new PIDController(.5,.2,.8,ultraFrontRight,new PIDOutput(){
            public void pidWrite(double output){
                drive.holonomicDrive(output, 0, 0);
            }
        });
    }
    
    public void initialize(){
        circleFinder.intialize();
        driveTowardsRamp.setTolerance(1.0);
        driveTowardsRamp.setSetpoint(MINUMUM_RANGE);
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
}

   