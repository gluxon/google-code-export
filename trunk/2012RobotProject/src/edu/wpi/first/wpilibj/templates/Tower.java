package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Tower
{
    private Victor bottomShooterMotor, topShooterMotor, ballElevatorMotor, ballIntakeMotor;
	private Joystick joystickAux;
    private Compressor compressor;
	private Solenoid bridgeSolenoid;
	private Solenoid intakeSolenoid;

	private double ShooterSpeed;
	private boolean isPressedShooterSpeed;
	private boolean bridgeDown;
	private boolean isShooting;
	private boolean compressorToggle;
	private boolean isPressedCompressorToggle;

    public Tower(int bottomShooterMotorN, int topShooterMotorN, int ballElevatorMotorN, int ballIntakeMotorN, Joystick joystickA)
    {
        bottomShooterMotor = new Victor(bottomShooterMotorN);
        topShooterMotor = new Victor(topShooterMotorN);
        ballElevatorMotor = new Victor(ballElevatorMotorN);
        ballIntakeMotor = new Victor(ballIntakeMotorN);
		compressor = new Compressor(6,2);
		compressor.start();
		joystickAux = joystickA;

		bridgeSolenoid = new Solenoid(2);
		intakeSolenoid = new Solenoid(3);
		ShooterSpeed = 1.0;
		isPressedShooterSpeed = false;
		bridgeDown = false;
		isShooting = false;
		compressorToggle = false;
		isPressedCompressorToggle = false;
    }

    public Victor getBottomShooterMotor()
    {
        return bottomShooterMotor;
    }

    public Victor getTopShooterMotor()
    {
        return topShooterMotor;
    }

    public Victor getBallElevatorMotor()
    {
        return ballElevatorMotor;
    }

    public Victor getBallIntakeMotor()
    {
        return ballIntakeMotor;
    }

    public void setBottomShooterMotor(double input)
    {
        bottomShooterMotor.set(input);
    }

    public void setTopShooterMotor(double input)
    {
        topShooterMotor.set(input);
    }

    public void setBallElevator(double input)
    {
        ballElevatorMotor.set(input);
    }

    public void setBallIntakeMotor(double input)
    {
        ballIntakeMotor.set(input);
    }

    public void setShooterMotors(double input)
    {
        setBottomShooterMotor(input);
        setTopShooterMotor(-input);
    }
	public void shoot()
	{
		isShooting = true;
		setShooterMotors(1.0);
		Timer.delay(3);
		setBallElevator(1.0);
		Timer.delay(3);
		isShooting = false;

	}
	public void run()
	{
		if(joystickAux.getRawButton(11) && ShooterSpeed > 0 && isPressedShooterSpeed == false) {
			ShooterSpeed -= 0.05;
		}
		if (joystickAux.getRawButton(12) && ShooterSpeed < 1 && isPressedShooterSpeed == false) {
			ShooterSpeed += 0.05;
		}

		if(joystickAux.getRawButton(11) || joystickAux.getRawButton(12))
			isPressedShooterSpeed = true;
		else
			isPressedShooterSpeed = false;

		//System.out.println(ShooterSpeed);

		boolean fire = joystickAux.getRawButton(1);
		boolean shootingMotor = joystickAux.getRawButton(2);
        boolean ballIntakeIn = joystickAux.getRawButton(3);
        boolean ballIntakeOut = joystickAux.getRawButton(5);
		boolean elevatorUp = joystickAux.getRawButton(6);
        boolean elevatorDown = joystickAux.getRawButton(4);

		if(joystickAux.getRawButton(7) && isPressedCompressorToggle == false)
		  {
			compressorToggle = !compressorToggle;
	        if(compressorToggle)
            compressor.start();
			else
            compressor.stop();
		  }

		if(joystickAux.getRawButton(7))
			isPressedCompressorToggle = true;
		else
			isPressedCompressorToggle = false;


		if(shootingMotor)
			setShooterMotors(1.0);
		else
			setShooterMotors(0.0);

        if(fire && isShooting == false)
            shoot();

        if(elevatorUp)
            setBallElevator(1.0);
        else if(elevatorDown)
            setBallElevator(-1.0);
        else
            setBallElevator(0.0);

        if(ballIntakeIn)
            setBallIntakeMotor(1.0);
        else if(ballIntakeOut)
            setBallIntakeMotor(-1.0);
        else
            setBallIntakeMotor(0.0);

		bridgeSolenoid.set(joystickAux.getRawButton(10));
		intakeSolenoid.set(joystickAux.getRawButton(9));
		System.out.println(compressor.enabled());
	}
}
