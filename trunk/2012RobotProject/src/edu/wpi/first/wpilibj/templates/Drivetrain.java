package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Drivetrain
{
    private Victor frontLeft, frontRight, rearLeft, rearRight;

    private Joystick joystick;

	double robotX, robotY, robotZ, speed;

    public Drivetrain(int frontLeftN, int frontRightN, int rearLeftN, int rearRightN, Joystick joystick, final double speed) {

		frontLeft = new Victor(frontLeftN) {
			public void set(double d) {
				super.set(d * speed);
            }
        };
		frontRight = new Victor(frontRightN) {
			public void set(double d) {
				super.set(d * speed);
            }
        };
		rearLeft = new Victor(rearLeftN) {
			public void set(double d) {
				super.set(d * speed);
            }
        };
		rearRight = new Victor(rearRightN) {
			public void set(double d) {
				super.set(d * speed);
            }
        };

		this.joystick = joystick;

    }

    public double getFrontLeft()
    {
        return this.frontLeft.get();
    }

    public double getFrontRight()
    {
        return this.frontRight.get();
    }

    public double getRearLeft()
    {
        return this.rearLeft.get();
    }

    public double getRearRight()
    {
        return this.rearRight.get();
    }

    public void drive()
    {
		robotX = -joystick.getX();
        robotY = -joystick.getY();
		robotZ = -joystick.getTwist();

		System.out.println("Button 7:" + joystick.getRawButton(7));
        speed = 1.0;

		//Go slower
		if(joystick.getRawButton(2))
        {
            speed = 0.3;
        }
		else {
			speed = 1.0;
		}

		if(joystick.getRawButton(7)==true) { //forward at 0.2 speed, does not work
			frontRight.set(-1);
			frontLeft.set(1);
			rearRight.set(-1);
			rearLeft.set(1);
		}

		if(joystick.getRawButton(8)==true) { //forward at 0.4 speed
			frontRight.set(-0.4);
			frontLeft.set(0.4);
			rearRight.set(-0.4);
			rearLeft.set(0.4);
		}

		if(joystick.getRawButton(9)==true) { //back at 0.4 speed
			frontRight.set(0.4);
			frontLeft.set(-0.4);
			rearRight.set(0.4);
			rearLeft.set(-0.4);
		}

		if(joystick.getRawButton(11)==true) {//strafe left at 0.2, might be wrong
			frontRight.set(0.2);
			frontLeft.set(-0.2);
			rearRight.set(-0.2);
			rearLeft.set(0.2);
		}

		if(joystick.getRawButton(12)==true) {//strafe right at 0.2 speed, might be wrong
			frontRight.set(-0.2);
			frontLeft.set(0.2);
			rearRight.set(0.2);
			rearLeft.set(-0.2);
		}
		else {
			frontLeft.set(-(robotZ * speed) + (robotY * speed) - (robotX * speed));
			rearLeft.set(-(robotZ * speed) + (robotY * speed) + (robotX * speed));
			frontRight.set(-(robotZ * speed) - (robotY * speed) - (robotX * speed));
			rearRight.set(-(robotZ * speed) - (robotY * speed) + (robotX * speed));
        }
		/*if(joystick.getRawButton(3))
			rearLeft.set(.2);
		else
			rearLeft.set(0);
		if(joystick.getRawButton(5))
			frontLeft.set(.2);
		else
			frontLeft.set(0);
		if(joystick.getRawButton(4))
			rearRight.set(.2);
		else
			rearRight.set(0);
		if(joystick.getRawButton(6))
			frontRight.set(.2);
		else
			frontRight.set(0);

        if(joystick.getRawButton(4))
        {
            frontLeft.set(-speed);
            rearLeft.set(speed);
            frontRight.set(-speed);
            rearRight.set(speed);
        }
        else if(joystick.getRawButton(3))
        {
            frontLeft.set(speed);
            rearLeft.set(-speed);
            frontRight.set(speed);
            rearRight.set(-speed);
        }*/

	}
    public void frontLeftSet(double value) {
		frontLeft.set(value);
    }

    public void rearLeftSet(double value) {
		rearLeft.set(value);
    }

    public void frontRightSet(double value) {
		frontRight.set(value);
    }

    public void rearRightSet(double value) {
		rearRight.set(value);
    }

	public void setDiminishedSpeed(double fraction)
	{
		frontLeft.set(getFrontLeft() * fraction);
        rearLeft.set(getRearLeft() * fraction);
        frontRight.set(getFrontRight() * fraction);
        rearRight.set(getRearRight() * fraction);
	}

}
