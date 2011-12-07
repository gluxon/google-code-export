package edu.fhs.input;

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

    public void start()
    {
        isEnabled = true;
    }

    public void stop()
    {
        isEnabled = false;
    }

    public boolean getState()
    {
        return isWorking;
    }

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
