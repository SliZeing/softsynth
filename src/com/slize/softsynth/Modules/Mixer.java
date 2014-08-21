package com.slize.softsynth.Modules;

import com.slize.softsynth.Engien.Output;
import com.slize.softsynth.Engien.SampleProvider;
import com.slize.softsynth.utlis.SampleConverter;

import java.util.ArrayList;

public class Mixer implements SampleProvider {
    private ArrayList<SampleProvider> providers = new ArrayList<SampleProvider>();
    private ArrayList<byte[]> tempBuffers = new ArrayList<byte[]>();

    @Override
    public int getSamples(byte[] buffer, int bufferSize) {
        int index = 0;

        // Get all the buffers from the providers
        for(int i = 0; i < tempBuffers.size(); i++) {
            providers.get(i).getSamples(tempBuffers.get(i), bufferSize);
        }

        for(int i = 0; i < bufferSize / 2; i++) {
            short sample = 0;

            // Add all the buffers to the mixer
            for (byte[] tempBuffer : tempBuffers) {
                sample += SampleConverter.toSample(tempBuffer, index);
            }

            buffer[index++] = (byte)(sample >> 8);
            buffer[index++] = (byte)(sample & 0xFF);
        }

        return bufferSize;
    }

    public void addSampleProvider(SampleProvider provider) {
        this.providers.add(provider);
        this.tempBuffers.add(new byte[Output.getBufferSize()]);
    }
}
