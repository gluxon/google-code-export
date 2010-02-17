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

    public void setSolenoid1(Solenoid solenoid) {
        solenoid1 = solenoid;
    }

    public Solenoid getSolenoid2() {
        return solenoid2;
    }

    public void setSolenoid2(Solenoid solenoid) {
        solenoid2 = solenoid;
    }

    public Solenoid getSolenoid3() {
        return solenoid3;
    }

    public void setSolenoid3(Solenoid solenoid) {
        solenoid3 = solenoid;
    }

    public Solenoid getSolenoid4() {
        return solenoid4;
    }

    public void setSolenoid4(Solenoid solenoid) {
        solenoid4 = solenoid;
    }

}
