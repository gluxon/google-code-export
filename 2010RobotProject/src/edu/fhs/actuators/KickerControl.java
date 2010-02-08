/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.actuators;

import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author programming
 */
public class KickerControl {
    private Relay relay1;
    private Relay relay2;
    private Relay relay3;
    private Relay relay4;

    public void setRelay4(Relay relay4) {
        this.relay4 = relay4;
    }

    public void setRelay1(Relay relay1) {
        this.relay1 = relay1;
    }

    public void setRelay2(Relay relay2) {
        this.relay2 = relay2;
    }

    public void setRelay3(Relay relay3) {
        this.relay3 = relay3;
    }

    public Relay getRelay1() {
        return relay1;
    }

    public Relay getRelay2() {
        return relay2;
    }

    public Relay getRelay3() {
        return relay3;
    }

    public Relay getRelay4() {
        return relay4;
    }
}
