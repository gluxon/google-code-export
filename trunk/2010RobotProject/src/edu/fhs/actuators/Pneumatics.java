/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.actuators;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author programming
 */
public class Pneumatics {
    private boolean isKicking = false;
    public void decompress(){}

    public void delayedCompress()
    {

    }

    public void kick(KickerControl control)
    {
        if(!isKicking){
            isKicking = true;
            TimedCycle oneKick = new TimedCycle(control);
            Thread kickThread = new Thread(oneKick);
            kickThread.start();
        }
    }

    public class TimedCycle implements Runnable
    {
        private KickerControl control;

        public TimedCycle(KickerControl control)
        {
            this.control = control;
        }
            

        public void run() /*extends gate piston forward to release gate, then
                            retacts gate piston and extends main kicker piston
                            to reset the kicker, then retracts the main kicker
                            piston                                            
                                                                              */
        {
            control.getRelay1Open().set(Relay.Value.kOn);
            Timer.delay(500);
            control.getRelay1Close().set(Relay.Value.kOff);
            control.getRelay2Open().set(Relay.Value.kOn);
            Timer.delay(1000);
            control.getRelay2Close().set(Relay.Value.kOff);
            isKicking = false;
        }



    }
}