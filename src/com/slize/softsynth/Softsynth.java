package com.slize.softsynth;

import com.slize.softsynth.Engien.Output;
import com.slize.softsynth.Modules.*;
import com.slize.softsynth.utlis.Waveform;

public class Softsynth {

    public static void main(String[] args) throws Exception {
        Thread output = new Thread(new Output(), "output");

        Oscillator osc = new Oscillator();

        output.start();

        // Set what module has the output.
        Output.setSampleProvider(osc);

        osc.setWaveform(Waveform.SQU);
        osc.setFrequency(110.0);
    }
}
