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
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.samples.Target;

/**
 *
 * @author programming
 */
public class VisionDirectedDrive {

    private Gyro gyro;
    private Joystick js;
    private PIDController turnController;
    private RobotDrive drive;
    private AxisCamera cam;
    private boolean lastEnable;
    private final double kScoreThreshold = .01;
    private boolean targetFound;



    public VisionDirectedDrive(Gyro g , Joystick j, RobotDrive d){
        lastEnable = false;
        gyro = g;
        js = j;
        drive = d;
        cam = AxisCamera.getInstance();
        turnController = new PIDController(.08 , 0.0 , 0.5 , gyro , new PIDOutput(){
            public void pidWrite(double output) {
                drive.holonomicDrive(js.getMagnitude(), js.getDirectionDegrees(), output);
            }
        }, .005);
        targetFound = false;
    }

    public void intialize(){ //call during robotInit
        cam.writeResolution(AxisCamera.ResolutionT.k320x240);
        cam.writeBrightness(0);
        gyro.setSensitivity(.007);
        turnController.setInputRange(-360.0, 360.0);
        turnController.setTolerance(1 / 90. * 100);
        turnController.disable();
    }

    public void centerOnCircle(boolean enable){ //enables PID controller.  Will overide user input to robot.
        if(enable == false){
            turnController.disable();
            lastEnable = false;
        }else{
            if(!lastEnable){
                lastEnable = true;
                turnController.enable();
                turnController.setSetpoint(gyro.pidGet());
            }
            ColorImage image = null;
            try {
                if (cam.freshImage()) {// && turnController.onTarget()) {
                    targetFound = false;
                    double gyroAngle = gyro.pidGet();
                    image = cam.getImage();
                    Thread.yield();
                    Target[] targets = Target.findCircularTargets(image);
                    Thread.yield();
                    if (targets.length == 0 || targets[0].m_score < kScoreThreshold) {
                        System.out.println("No target found");
                        Target[] newTargets = new Target[targets.length + 1];
                        newTargets[0] = new Target();
                        newTargets[0].m_majorRadius = 0;
                        newTargets[0].m_minorRadius = 0;
                        newTargets[0].m_score = 0;
                        for (int i = 0; i < targets.length; i++) {
                            newTargets[i + 1] = targets[i];
                        }
                        //noCircleFound();
                    } else {
                        targetFound = true;
                        System.out.println(targets[0]);
                        System.out.println("Target Angle: " + targets[0].getHorizontalAngle());
                        turnController.setSetpoint(gyroAngle + targets[0].getHorizontalAngle());
                    }
                }
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            } catch (AxisCameraException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (image != null) {
                        image.free();
                    }
                } catch (NIVisionException ex) {
                }
            }
        }
    }


    private void noCircleFound(){
        turnController.disable();
        drive.holonomicDrive(0, 0, -.2);
        turnController.enable();
    }

    public boolean isTargetFound(){
        return targetFound;
    }

    public boolean isOnTarget(){
        if(targetFound == true)
            return turnController.onTarget();
        return false;
    }

    public void driveToRamp(){
        if (isOnTarget() == false){
            centerOnCircle(true);
        }else{
            centerOnCircle(false);
            drive.holonomicDrive(.5, 0, 0);
        }
    }

}