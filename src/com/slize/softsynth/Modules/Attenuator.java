package com.slize.softsynth.Modules;

import com.slize.softsynth.Engien.SampleProvider;
import com.slize.softsynth.Engien.SampleConverter;

public class Attenuator implements SampleProvider {
    private SampleProvider provider;

    private double attenuationRatio;
    private double attenuationBaseRatio;
    private SampleProvider attenuationCv;

    public Attenuator() {
        setAttenuationBaseRatio(1.0);
    }

    private short attenuate(short sample) {
        return (short) (sample * attenuationRatio);
    }

    @Override
    public int getSamples(byte[] buffer) {
        byte[] cvBuffer = null;

        provider.getSamples(buffer);

        if(attenuationCv != null) {
            cvBuffer = new byte[buffer.length];
            attenuationCv.getSamples(cvBuffer);
        }

        int index = 0;

        for(int i = 0; i < buffer.length / 2; i++) {
            // If there is cv input then apply that to the attenuationRatio
            if(cvBuffer != null) {
                short cvSample = SampleConverter.toSample(cvBuffer, index);
                double cvValue = SampleConverter.getSampleValue(cvSample);


                if(cvValue < 0.0) {
                    cvValue *= -1;
                }

                // Apply modulation value to sample
                if(attenuationRatio * cvValue <= 1.0) {
                    attenuationRatio = attenuationBaseRatio * cvValue;
                }
                else {
                    attenuationRatio = 1.0;
                }
            }

            short sample = SampleConverter.toSample(buffer, index);

            sample = attenuate(sample);

            // Store the processed sample back to the buffer.
            buffer[index++] = (byte)(sample >> 8);
            buffer[index++] = (byte)(sample & 0xFF);
        }

        return buffer.length;
    }

    public void setSampleProvider(SampleProvider provider) {
        this.provider = provider;
    }

    public void setAttenuationBaseRatio(double attenuationRatio) {
        if(attenuationRatio >= 0.0 && attenuationRatio <= 1.0) {
            this.attenuationBaseRatio = attenuationRatio;
        }
        else {
            throw new IllegalArgumentException("attenuationRatio has to be between 0.0 and 1.0");
        }
    }

    public void setAttenuationCv(SampleProvider provider) {
        this.attenuationCv = provider;
    }
}
