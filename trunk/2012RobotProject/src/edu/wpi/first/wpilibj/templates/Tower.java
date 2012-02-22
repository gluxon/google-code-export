package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Tower
{
	private boolean control;

    private Victor bottomShooterMotor, topShooterMotor, ballElevatorMotor, ballIntakeMotor;
	private Joystick joystickAux;
    private Compressor compressor;
	private Solenoid bridgeSolenoid;
	private Solenoid intakeSolenoid;
	private Relay cameraLights;
	private EnhancedIOFHS enhancedIO;

	private double shooterSpeed;
	private boolean isPressedShooterSpeed;
	private boolean bridgeDown;
	private boolean isShooting;
	private boolean compressorToggle;
	private boolean isPressedCompressorToggle;
	private boolean cameraLightState;
	DriverStation driverStation;

    public Tower(DriverStation ds, int bottomShooterMotorN, int topShooterMotorN, int ballElevatorMotorN, int ballIntakeMotorN, int cameraLightN, EnhancedIOFHS IO)
    {

		joystickAux = new Joystick(2);
		driverStation = ds;
        bottomShooterMotor = new Victor(bottomShooterMotorN);
        topShooterMotor = new Victor(topShooterMotorN);
        ballElevatorMotor = new Victor(ballElevatorMotorN);
        ballIntakeMotor = new Victor(ballIntakeMotorN);
		compressor = new Compressor(6,2);
		compressor.start();
		enhancedIO = IO;
		cameraLights = new Relay(cameraLightN,Relay.Direction.kForward);

		bridgeSolenoid = new Solenoid(2);
		intakeSolenoid = new Solenoid(3);
		shooterSpeed = 1.0;
		isPressedShooterSpeed = false;
		bridgeDown = false;
		isShooting = false;
		//compressorToggle = false;
		//isPressedCompressorToggle = false;
		cameraLightState = false;

		control = true; // true = driver station, false = aux controller

		compressor.start();
    }

	public boolean toggleCameraLight()
	{
		if(cameraLightState)
		{
			cameraLights.set(Relay.Value.kOff);
			cameraLightState = false;
			return cameraLightState;
		}
		else
		{
			cameraLights.set(Relay.Value.kOn);
			cameraLightState = true;
			return cameraLightState;
		}
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
        setTopShooterMotor(-input*0.95);
    }

	public void setShooterSpeed(double input)
	{
		shooterSpeed = input;
	}

	public void shoot()
	{
		isShooting = true;
		setShooterSpeed(enhancedIO.getSlider());
		setShooterMotors(shooterSpeed);
		for(int i = 0; i < 12; i++)
		{
			setShooterSpeed(enhancedIO.getSlider());
			setShooterMotors(shooterSpeed);
			if(enhancedIO.getFireButton()) {
				//Timer.delay(.25); THIS IS DANGEROUS!
			}
			else
			{
				isShooting = false;
				return;
			}
		}
		setBallElevator(1.0);
		while(enhancedIO.getFireButton())
		{
			Timer.delay(.25);
			setShooterSpeed(enhancedIO.getSlider());
			setShooterMotors(shooterSpeed);
			System.out.println(shooterSpeed);
		}
		isShooting = false;
	}

	public void run()
	{

		System.out.println(shooterSpeed);

			if(isShooting == false)
			{
				if(enhancedIO.getFireButton())
					shoot();
				else
				{
					setShooterMotors(0.0);
					if(enhancedIO.getBallElevatorSwitch()[0])
					setBallElevator(1.0);
					else if(enhancedIO.getBallElevatorSwitch()[1])
					setBallElevator(-1.0);
					else
					setBallElevator(0.0);
				}
			}

			if(enhancedIO.getBallIntakeSwitch()[0]) {
				System.out.println("BallIntakeIn");
				setBallIntakeMotor(.5);
			}
			else if(enhancedIO.getBallIntakeSwitch()[1]) {
				System.out.println("BallIntakeOut");
				setBallIntakeMotor(-.5);
			}
			else
				setBallIntakeMotor(0.0);

			if(!bridgeDown && enhancedIO.getBridgeManipulatorSwitch()[1])
			{
				bridgeSolenoid.set(true);
				bridgeDown = true;
			}

			if(bridgeDown && enhancedIO.getBridgeManipulatorSwitch()[0])
			{
				bridgeSolenoid.set(false);
				bridgeDown = false;
			}

	}

	public void runAux()
	{
		if(joystickAux.getRawButton(11) && shooterSpeed > 0.05 && isPressedShooterSpeed == false) {
			shooterSpeed -= 0.05;
		}
		if (joystickAux.getRawButton(12) && shooterSpeed < 1 && isPressedShooterSpeed == false) {
			shooterSpeed += 0.05;
		}

		if(joystickAux.getRawButton(11) || joystickAux.getRawButton(12))
			isPressedShooterSpeed = true;
		else
			isPressedShooterSpeed = false;

		System.out.println(shooterSpeed);

		boolean fire = joystickAux.getRawButton(1);
		boolean shootingMotor = joystickAux.getRawButton(2);
        boolean ballIntakeIn = joystickAux.getRawButton(3);
        boolean ballIntakeOut = joystickAux.getRawButton(5);
		boolean elevatorUp = joystickAux.getRawButton(6);
        boolean elevatorDown = joystickAux.getRawButton(4);


			if(isShooting == false)
			{
				if(fire)
					shoot();
				else
				{
					setShooterMotors(0.0);
					if(elevatorUp)
					setBallElevator(1.0);
					else if(elevatorDown)
					setBallElevator(-1.0);
					else
					setBallElevator(0.0);
				}
			}

			if(ballIntakeIn)
				setBallIntakeMotor(1.0);
			else if(ballIntakeOut)
				setBallIntakeMotor(-1.0);
			else
				setBallIntakeMotor(0.0);

			if(!bridgeDown && enhancedIO.getBridgeManipulatorSwitch()[1])
			{
				bridgeSolenoid.set(true);
				bridgeDown = true;
			}

			if(bridgeDown && enhancedIO.getBridgeManipulatorSwitch()[0])
			{
				bridgeSolenoid.set(false);
				bridgeDown = false;
			}

		bridgeSolenoid.set(joystickAux.getRawButton(10));
		intakeSolenoid.set(joystickAux.getRawButton(9));
	}
}
