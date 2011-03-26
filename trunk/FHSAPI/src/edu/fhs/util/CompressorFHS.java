package edu.fhs.util;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SensorBase;

public class CompressorFHS extends SensorBase
{
    private int relayChannel;
    private int relaySlot;
    private int pressureSwitchChannel;
    private int pressureSwitchSlot;

    private Relay relay;
    private DigitalInput compressorSwitch;

    private CompressorFHSThread compressorThread;

    private boolean isEnabled = false;
    private boolean isWorking = false;

    /**
     * Creates a CompressorFHS Object for operating the compressor
     * @param relayChannel The channel input for the relay spike.
     * @param pressureSwitchChannel The channel input for the pressure switch.
     */
    public CompressorFHS(int relayChannel, int pressureSwitchChannel)
    {
        this.relayChannel = relayChannel;
        this.pressureSwitchChannel = pressureSwitchChannel;

        this.pressureSwitchSlot = getDefaultDigitalModule();
        this.relaySlot = getDefaultDigitalModule();

        try
        {
            relay = new Relay(this.relaySlot,this.relayChannel,Relay.Direction.kForward);
            compressorSwitch = new DigitalInput(this.pressureSwitchSlot,this.pressureSwitchChannel);
            compressorThread = new CompressorFHSThread();
            compressorThread.start();
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Creates a CompressorFHS Object for operating the compressor
     * @param relaySlot The slot input for the relay spike.
     * @param relayChannel The channel input for the relay spike.
     * @param pressureSwitchSlot The slot input for the pressure switch.
     * @param pressureSwitchChannel The channel input for the pressure switch.
     */
    public CompressorFHS(int relaySlot, int relayChannel, int pressureSwitchSlot, int pressureSwitchChannel)
    {
        this.relayChannel = relayChannel;
        this.pressureSwitchChannel = pressureSwitchChannel;

        this.pressureSwitchSlot = pressureSwitchSlot;
        this.relaySlot = relaySlot;

        try
        {
            relay = new Relay(this.relaySlot,this.relayChannel,Relay.Direction.kForward);
            compressorSwitch = new DigitalInput(this.pressureSwitchSlot,this.pressureSwitchChannel);
            compressorThread = new CompressorFHSThread();
            compressorThread.start();
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Starts the compressor
     */
    public void start()
    {
        isEnabled = true;
    }

    /**
     * Stops the compressor
     */
    public void stop()
    {
        isEnabled = false;
    }

    /**
     * Gets the current state of the compressor
     * @return The current state of the compressor.
     */
    public boolean getState()
    {
        return isWorking;
    }

    /**
     * Checks the pressure switch and the isEnabled input to turn the compressor on or off.
     */
    private class CompressorFHSThread extends Thread
    {
        public void run()
        {
            if(relay != null && compressorSwitch != null)
            {
                if(isEnabled && !(compressorSwitch.get()))
                {
                    relay.set(Relay.Value.kOn);
                    isWorking = true;
                }
                else
                {
                    relay.set(Relay.Value.kOff);
                    isWorking = false;
                }
            }
        }
    }
}
