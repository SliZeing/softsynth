package com.slize.softsynth.Modules;

import com.slize.softsynth.Engien.SampleProvider;
import com.slize.softsynth.Engien.SampleConverter;

public class Attenuator implements SampleProvider {
    private SampleProvider provider;

    private double attenuationRatio;
    private SampleProvider cv;

    public Attenuator() {
        attenuationRatio = 1.0;
    }

    private short attenuate(short sample, short cvSample) {
        if(cv != null) {
            double modulator = SampleConverter.getSampleValue(cvSample);

            // The modulator should not be < 0.0, so we convert the value to a positive number if it's < 0.0.
            if(modulator < 0.0) {
                modulator *= -1;
            }

            // Apply modulation value to sample
            if(attenuationRatio * modulator <= 1.0) {
                sample *= attenuationRatio * modulator;
            }
        }
        else {
            sample *= attenuationRatio;
        }

        return sample;
    }

    @Override
    public int getSamples(byte[] buffer) {
        byte[] cvBuffer = new byte[buffer.length];

        provider.getSamples(buffer);

        if(cv != null) {
            cv.getSamples(cvBuffer);
        }

        int index = 0;

        for(int i = 0; i < buffer.length / 2; i++) {
            short sample = SampleConverter.toSample(buffer, index);

            short cvSample = SampleConverter.toSample(cvBuffer, index);

            sample = attenuate(sample, cvSample);

            // Store the processed sample back to the buffer.
            buffer[index++] = (byte)(sample >> 8);
            buffer[index++] = (byte)(sample & 0xFF);
        }

        return buffer.length;
    }

    public void setSampleProvider(SampleProvider provider) {
        this.provider = provider;
    }

    public void setAttenuationRatio(double attenuationRatio) {
        if(attenuationRatio >= 0.0 && attenuationRatio <= 1.0) {
            this.attenuationRatio = attenuationRatio;
        }
        else {
            throw new IllegalArgumentException("attenuationRatio has to be between 0.0 and 1.0");
        }
    }

    public void setCv(SampleProvider provider) {
        this.cv = provider;
    }
}
