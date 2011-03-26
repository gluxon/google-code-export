package edu.fhs.drive;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;

public class RobotDriveFHS 
{
	SpeedController frontLeftMotor;
	SpeedController frontRightMotor;
	SpeedController rearLeftMotor;
	SpeedController rearRightMotor;
	
	public RobotDriveFHS(int frontLeft, int frontRight, int backLeft, int backRight)
	{
		frontLeftMotor = new Jaguar(frontLeft);
		frontRightMotor = new Jaguar(frontRight);
		rearLeftMotor = new Jaguar(backLeft);
		rearRightMotor = new Jaguar(backRight);
	}
	
	public void tankDrive(double vLeft, double vRight)
	{
		frontLeftMotor.set(vLeft);
		frontRightMotor.set(vRight);
	}
	
	public void arcadeDrive(double vSpeed, double rotation)
	{
		if(rotation > 0)
		{
			frontLeftMotor.set(-vSpeed-rotation);
			frontRightMotor.set(-vSpeed-rotation);
		}
		else
		{
			frontLeftMotor.set(-vSpeed+rotation);
			frontRightMotor.set(-vSpeed+rotation);
		}
	}
	
	public void mecanumDrive(double vSpeed, double direction, double rotation)
	{
		double frontLeftMotorSpeed, frontRightMotorSpeed, rearLeftMotorSpeed,rearRightMotorSpeed;
		
		vSpeed *= Math.sqrt(2.0);
		
		double directionRadians = Math.PI / 180.0 * (direction + 45.0);
		double cosDirection = Math.cos(directionRadians);
		double sinDirection = Math.sin(directionRadians);
		
		frontLeftMotorSpeed = sinDirection * vSpeed + rotation;
		frontRightMotorSpeed = cosDirection * vSpeed - rotation;
		rearLeftMotorSpeed = cosDirection * vSpeed + rotation;
		rearRightMotorSpeed = sinDirection * vSpeed - rotation;
		
		frontLeftMotor.set(frontLeftMotorSpeed * -1.0);
		frontRightMotor.set(frontRightMotorSpeed * 1.0);
		rearLeftMotor.set(rearLeftMotorSpeed * 1.0);
		rearRightMotor.set(rearRightMotorSpeed * -1.0);
	}
}
