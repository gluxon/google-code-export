/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.input;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This is a class for the Xbox controller that has sensitivity adjustment built
 * in.  Specify the sensitivity in the constructor (double between 0 and 1) and
 * the getToggle methods will not return any value below that value.  This is
 * used to correct for the stickiness of the controller so that the bot does not
 * make uncontrollable small movements.
 * @author FRC Team 178 Enforcers
 */
public class XboxController extends Joystick{

    private double sensitivity = 0;

    /**
     * @param port The port number.
     * @param sens The minimum value (sensitivity) to return, corrects for oversensitivity.
     */
    public XboxController(int port, double sens)
    {
        super(port);
        sensitivity = sens;
    }
    /**
     * @param port The port number.
     * @param sens The minimum value (sensitivity) to return, corrects for oversensitivity.
     */
    public XboxController(int port)
    {
        super(port);
    }
    /**
     * @return True if A is pressed, false if not
     */
    public boolean AIsPressed()
    {
        return this.getRawButton(1);
    }
    /**
     * @return True if button B is pressed, false if not
     */
    public boolean BIsPressed()
    {
        return this.getRawButton(2);
    }
    /**
     * @return True of button X is pressed, false if not
     */
    public boolean XIsPressed()
    {
        return this.getRawButton(3);
    }
    /**
     * @return True if button Y is pressed, false if not
     */
    public boolean YIsPressed()
    {
        return this.getRawButton(4);
    }

    /**
     * @return The Y value of the left toggle, 1 is top, -1 is bottom
     */
    public double getLeftToggleY()
    {
        return -1 * getAxis(1, sensitivity);
    }

    /**
     * @return The X value of the left toggle, 1 is right, -1 is left, including sensitivity
     */
    public double getLeftToggleX()
    {
        return getAxis(2, sensitivity);
    }

    /**
     * @return The Y value of the right toggle, 1 is high, -1 is bottom
     */
    public double getRightToggleY()
    {
        return -1 * getAxis(5, sensitivity);
    }

    /**
     * @return The X value of the right toggle, -1 is left, 1 is right
     */
    public double getRightToggleX()
    {
        return getAxis(4, sensitivity);
    }

    /**
     * @return Value of front buttons, where right is 1 and left is -1
     */
    public double getFrontButtons()
    {
        return -1 *getZ();
    }

    /**
     * @return True if start is pressed, false if not
     */
    public boolean StartIsPressed()
    {
        return getRawButton(8);
    }

    /**
     * @return True if back button is pressed, false if not
     */
    public boolean BackIsPressed()
    {
        return getRawButton(7);
    }

    /**
     *
     * @return Minimum value of the controller to return
     */
    public double getSensitivity()
    {
        return sensitivity;
    }

    /**
     *
     * @param newSens Double value of the new sensitivity
     */
    public void setSenstivity(double newSens)
    {
        sensitivity = newSens;
    }

    private double getAxis(int axis, double sensitivity)
    {
        if (getRawAxis(axis) >= sensitivity || getRawAxis(axis) <= (-1 * sensitivity))
            return getRawAxis(axis);
        return 0;
    }
}