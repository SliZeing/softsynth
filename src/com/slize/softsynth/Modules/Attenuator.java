package com.slize.softsynth.Modules;

import com.slize.softsynth.Engien.SampleProvider;

public class Attenuator implements SampleProvider {
    private SampleProvider provider;

    private double attenuationRatio;

    public Attenuator() {
        attenuationRatio = 1.0;
    }

    @Override
    public int getSamples(byte[] buffer, int bufferSize) {
        provider.getSamples(buffer, bufferSize);

        int index = 0;

        for(int i = 0; i < bufferSize / 2; i++) {
            // Get a sample to process
            byte b2 = buffer[index];
            byte b1 = buffer[index+1];

            // Convert bytes into short sample
            short s = (short)((b2 << 8) + b1);

            // Apply envelope value to sample
            s *= attenuationRatio;

            // Store the processed sample
            buffer[index++] = (byte)(s >> 8);
            buffer[index++] = (byte)(s & 0xFF);
        }

        return bufferSize;
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
}
