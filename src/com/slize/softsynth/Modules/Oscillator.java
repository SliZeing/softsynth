package com.slize.softsynth.Modules;

import com.slize.softsynth.Engien.Output;
import com.slize.softsynth.Engien.SampleProvider;
import com.slize.softsynth.utlis.Waveform;

public class Oscillator implements SampleProvider {
    private Waveform waveform;
    private long samplesPerPeriod;
    private long sampleNumber;
    private double pulseWidth;


    public Oscillator() {
        waveform = Waveform.SIN;
        setFrequency(440.0);
        setPulseWidth(0.5);
    }

    public double getSample() {
        double value;
        double phase = sampleNumber / (double) samplesPerPeriod;

        switch (waveform) {
            default:
            case SIN:
                value = Math.sin(2.0 * Math.PI * phase);
                break;
            case TRI:
                value = (2.0 * Math.sin(Math.sin(2.0 * Math.PI * phase))) / Math.PI;
                break;
            case SQU:
                if(phase > pulseWidth) {
                    value = 0.0;
                }
                else {
                    value = 1.0;
                }
                break;
            case SAW:
                value = phase - Math.floor(phase);
                break;
        }

        sampleNumber = (sampleNumber + 1) % samplesPerPeriod;

        return value;
    }

    @Override
    public int getSamples(byte[] buffer, int bufferSize) {
        int index = 0;
        for (int i = 0; i < (bufferSize / 2); i++) {
            double ds = getSample() * Short.MAX_VALUE;
            short ss = (short) Math.round(ds);
            buffer[index++] = (byte)(ss >> 8);
            buffer[index++] = (byte)(ss & 0xFF);
        }

        return bufferSize;
    }

    public void setFrequency(double frequency) {
        this.samplesPerPeriod = (long)(Output.getSampleRate() / frequency);
    }

    public void setPulseWidth(double pulseWidth) {
        if(!(pulseWidth > 1.0 || pulseWidth < 0.0)) {
            this.pulseWidth = pulseWidth;
        }
        else {
            throw new IllegalArgumentException("The pulse width has to be between 0.0 and 1.0");
        }
    }

    public void setWaveform(Waveform waveform) {
        this.waveform = waveform;
    }
}
