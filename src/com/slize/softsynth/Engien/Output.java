package com.slize.softsynth.Engien;

import javax.sound.sampled.*;

public class Output implements Runnable, SampleProvider {
    private static final int SAMPLE_RATE = 44100;
    private static final int BIT_RATE = 16;
    private static final int CHANNELS = 1;
    private static final int BUFFER_SIZE = 4096;

    private AudioFormat audioFormat;
    private DataLine.Info info;
    private SourceDataLine sourceLine;
    private static SampleProvider sampleProvider;

    public Output() throws Exception {
        audioFormat = new AudioFormat(SAMPLE_RATE, BIT_RATE, CHANNELS, true, true);
        info = new DataLine.Info(SourceDataLine.class, audioFormat, BUFFER_SIZE);

        // Temporarily set the Sample Provider to this class until another class is specified.
        setSampleProvider(this);
    }

    public void run() {
        byte[] buffer = new byte[BUFFER_SIZE];
        int nBytesRead = 0;

        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat, BUFFER_SIZE);
            sourceLine.start();

            while(nBytesRead != -1) {
                nBytesRead = sampleProvider.getSamples(buffer);

                if(nBytesRead >= 0) {
                    sourceLine.write(buffer, 0, nBytesRead);
                }
            }
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
        finally {
            sourceLine.drain();
            sourceLine.close();
        }
    }

    @Override
    public int getSamples(byte[] buffer) {
        return 0;
    }

    public synchronized static void setSampleProvider(SampleProvider newSampleProvider) {
        sampleProvider = newSampleProvider;
    }

    public static int getSampleRate() {
        return SAMPLE_RATE;
    }

    public static int getBufferSize() {
        return BUFFER_SIZE;
    }
}
