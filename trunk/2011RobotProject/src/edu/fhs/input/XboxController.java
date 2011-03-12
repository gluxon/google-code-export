/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.input;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author programming
 */
public class XboxController extends Joystick{

    private double sensitivity = 0;

    /*
     * constructor
     * @param port the port number
     * @param sens the minimum value (sensitivity) to return, corrects for oversensitivity
     */
    public XboxController(int port, double sens)
    {
        super(port);
        sensitivity = sens;
    }
    public XboxController(int port)
    {
        super(port);
    }
    /*
     * @return true if A is pressed, false if not
     */
    public boolean AIsPressed()
    {
        return this.getRawButton(1);
    }
    /*
     * @return true if button B is pressed, false if not
     */
    public boolean BIsPressed()
    {
        return this.getRawButton(2);
    }
    /*
     * @return true of button X is pressed, false if not
     */
    public boolean XIsPressed()
    {
        return this.getRawButton(3);
    }
    /*
     * @return true if button Y is pressed, false if not
     */
    public boolean YIsPressed()
    {
        return this.getRawButton(4);
    }

    /*
     * @return the Y value of the left toggle, 1 is top, -1 is bottom
     */
    public double getLeftToggleY()
    {
        return -1 * getAxis(1, sensitivity);
    }

    /*
     * @return the X value of the left toggle, 1 is right, -1 is left, including sensitivity
     */
    public double getLeftToggleX()
    {
        return getAxis(2, sensitivity);
    }

    /*
     * @return the Y value of the right toggle, 1 is high, -1 is bottom
     */
    public double getRightToggleY()
    {
        return -1 * getAxis(5, sensitivity);
    }

    /* 
     * @return the X value of the right toggle, -1 is left, 1 is right
     */
    public double getRightToggleX()
    {
        return getAxis(4, sensitivity);
    }

    /*
     * @return Value of front buttons, where right is 1 and left is -1
     */
    public double getFrontButtons()
    {
        return -1 *getZ();
    }

    /*
     * @return true if start is pressed, false if not
     */
    public boolean StartIsPressed()
    {
        return getRawButton(8);
    }

    /*
     * @return true if back button is pressed, false if not
     */
    public boolean BackIsPressed()
    {
        return getRawButton(7);
    }

    public double getSensitivity()
    {
        return sensitivity;
    }

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
