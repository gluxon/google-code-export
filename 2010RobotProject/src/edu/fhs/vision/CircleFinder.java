/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fhs.vision;

import edu.fhs.util.StringUtil;
import edu.wpi.first.wpilibj.DriverStationLCD;
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
public class CircleFinder {

	private Gyro gyro;
	private Joystick js;
	private PIDController turnController;
	private RobotDrive drive;
	private AxisCamera cam;
	private boolean lastEnable;
	private final double kScoreThreshold = .01;
	private boolean targetFound;

	public CircleFinder(Gyro Gyro, Joystick joystick, RobotDrive robotDrive) {
		lastEnable = false;
		gyro = Gyro;
		js = joystick;
		drive = robotDrive;
		cam = AxisCamera.getInstance();
		cam.writeResolution(AxisCamera.ResolutionT.k320x240);
		cam.writeBrightness(0);
		gyro.setSensitivity(.007);

		turnController = new PIDController(.08, 0.0, 0.5, gyro, new RotationContoller(), .005);
		turnController.setInputRange(-360.0, 360.0);
		turnController.setTolerance(1 / 90. * 100);
		turnController.disable();
		targetFound = false;
	}

	/**
	 * Manages turn based on output from the pid controller.
	 * The joystick inputs will generally be 0 and 0 so for the most
	 * part just rotate around the center axis.
	 * @param output
	 */
	private class RotationContoller implements PIDOutput {

		/**
		 * Manages turn based on output from the pid controller.
		 * The joystick inputs will generally be 0 and 0 so for the most
		 * part just rotate around the center axis at a speed of "output".
		 * @param output
		 */
		public void pidWrite(double output) {
			drive.mecanumDrive_Polar(js.getMagnitude(), js.getDirectionDegrees(), output);
		}
	}



	public void intialize() { //call during robotInit
		cam.writeResolution(AxisCamera.ResolutionT.k320x240);
		cam.writeBrightness(0);
		gyro.setSensitivity(.007);
		turnController.setInputRange(-360.0, 360.0);
		turnController.setTolerance(1 / 90. * 100);
		turnController.disable();
	}

	public void centerOnCircle(boolean enable) { //enables PID controller.  Will overide user input to robot.
		if (enable == false) {
			DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "TARGET DISABLED");
			turnController.disable();
			lastEnable = false;
		} else {
			DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "TARGET ENABLED   ");
			if (!lastEnable) {
				lastEnable = true;
				turnController.enable();
				turnController.setSetpoint(gyro.pidGet());
			}
			lastEnable = true;
			ColorImage image = null;
			try {
				if (cam.freshImage()) {// && turnController.onTarget()) {
					double gyroAngle = gyro.pidGet();
					image = cam.getImage();
					Thread.yield();
					Target[] targets = Target.findCircularTargets(image);
					Thread.yield();
					if (targets.length == 0 || targets[0].m_score < kScoreThreshold) {
						//If there are no targets or no targets that meet the minimum
						//  threshold create at least one...
						System.out.println("No target found");
						Target[] newTargets = new Target[targets.length + 1];
						newTargets[0] = new Target();
						newTargets[0].m_majorRadius = 0;
						newTargets[0].m_minorRadius = 0;
						newTargets[0].m_score = 0;
						//...and load up all the low scoring targets...
						for (int i = 0; i < targets.length; i++) {
							newTargets[i + 1] = targets[i];
						}
						//...just so we can send them to the dashboard :-/
						//trackerDashboard.updateVisionDashboard(0.0, gyro.getAngle(), 0.0, 0.0, newTargets);
					} else {
						System.out.println(targets[0]);
						System.out.println("Target Angle: " + targets[0].getHorizontalAngle());
						turnController.setSetpoint(gyroAngle + targets[0].getHorizontalAngle());
						//trackerDashboard.updateVisionDashboard(0.0, gyro.getAngle(), 0.0, targets[0].m_xPos / targets[0].m_xMax, targets);
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

	public boolean isTargetInFrame() {
		ColorImage image = null;
		boolean isit = false;
		try {
			if (cam.freshImage()) {// && turnController.onTarget())
				double gyroAngle = gyro.pidGet();
				image = cam.getImage();
				Thread.yield();
				Target[] targets = Target.findCircularTargets(image);
				Thread.yield();
				if (targets.length == 0 || targets[0].m_score < kScoreThreshold) {
				} else {
					isit = true;
				}
			}
		} catch (NIVisionException ex) {
			ex.printStackTrace();
			return false;
		} catch (AxisCameraException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				if (image != null) {
					image.free();
				}
			} catch (NIVisionException ex) {
			}
		}
		return isit;
	}

	public boolean isTargetFound() {
		return targetFound;
	}

	public boolean isOnTarget() {
		if (targetFound == true) {
			return turnController.onTarget();
		}
		return false;
	}

	public void setLastEnable(boolean lastEnable) {
		this.lastEnable = lastEnable;
	}

	public AxisCamera getCam() {
		return cam;
	}

	public RobotDrive getDrive() {
		return drive;
	}

	public Gyro getGyro() {
		return gyro;
	}

	public Joystick getJs() {
		return js;
	}

	public double getkScoreThreshold() {
		return kScoreThreshold;
	}

	public boolean isLastEnable() {
		return lastEnable;
	}

	public PIDController getTurnController() {
		return turnController;
	}
}
