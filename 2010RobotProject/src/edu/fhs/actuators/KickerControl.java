/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.actuators;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author programming
 */
public class KickerControl {
    private Solenoid solenoid1;
    private Solenoid solenoid2;
    private Solenoid solenoid3;
    private Solenoid solenoid4;

    public Solenoid getSolenoid1() {
        return solenoid1;
    }

    public void setSolenoid1(Solenoid solenoid1) {
        this.solenoid1 = solenoid1;
    }

    public Solenoid getSolenoid2() {
        return solenoid2;
    }

    public void setSolenoid2(Solenoid solenoid2) {
        this.solenoid2 = solenoid2;
    }

    public Solenoid getSolenoid3() {
        return solenoid3;
    }

    public void setSolenoid3(Solenoid solenoid3) {
        this.solenoid3 = solenoid3;
    }

    public Solenoid getSolenoid4() {
        return solenoid4;
    }

    public void setSolenoid4(Solenoid solenoid4) {
        this.solenoid4 = solenoid4;
    }

}
