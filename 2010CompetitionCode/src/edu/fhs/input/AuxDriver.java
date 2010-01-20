/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.input;
import edu.wpi.first.wpilibj.*;
/**
 *
 * @author programming
 */
public class AuxDriver
{
    private Joystick auxStick;
    private Relay upTake;
    // Make these the same as in the Labview program
    private static final int UPTAKE_CHANNEL = 5;
    private static final int UPTAKE_SLOT= 1;


    public AuxDriver(Joystick aJoy)
    {
        auxStick = aJoy;
        upTake  = new Relay(UPTAKE_CHANNEL , UPTAKE_SLOT);
    }

    public void operate()
    {
        boolean state = auxStick.getRawButton(1);
        if (state)
        {
            upTake.set(Relay.Value.kForward);
        }
        else
        {
            upTake.set(Relay.Value.kOff);
        }
    }
}
