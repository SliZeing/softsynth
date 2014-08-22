package com.slize.softsynth.Engien;

public class SampleConverter {

    public static short toSample(byte[] buffer, int index) {
        // Get a sample to process
        byte b2 = buffer[index];
        byte b1 = buffer[index + 1];

        // Convert bytes into short sample
        return (short)((b2 << 8) + (b1 & 0xFF));
    }

    public static double getSampleValue(short sample) {
        return (double) sample / (double) Short.MAX_VALUE;
    }

}
