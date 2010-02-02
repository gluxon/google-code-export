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
        private Relay relay1Open;
    private Relay relay1Close;
    private Relay relay2Open;
    private Relay relay2Close;

    /**
     * @return the relay1Open
     */
    public Relay getRelay1Open() {
        return relay1Open;
    }

    /**
     * @param relay1Open the relay1Open to set
     */
    public void setRelay1Open(Relay relay1Open) {
        this.relay1Open = relay1Open;
    }

    /**
     * @return the relay1Close
     */
    public Relay getRelay1Close() {
        return relay1Close;
    }

    /**
     * @param relay1Close the relay1Close to set
     */
    public void setRelay1Close(Relay relay1Close) {
        this.relay1Close = relay1Close;
    }

    /**
     * @return the relay2Open
     */
    public Relay getRelay2Open() {
        return relay2Open;
    }

    /**
     * @param relay2Open the relay2Open to set
     */
    public void setRelay2Open(Relay relay2Open) {
        this.relay2Open = relay2Open;
    }

    /**
     * @return the relay2Close
     */
    public Relay getRelay2Close() {
        return relay2Close;
    }

    /**
     * @param relay2Close the relay2Close to set
     */
    public void setRelay2Close(Relay relay2Close) {
        this.relay2Close = relay2Close;
    }
}
