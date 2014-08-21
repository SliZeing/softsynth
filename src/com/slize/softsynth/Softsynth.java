package com.slize.softsynth;

import com.slize.softsynth.Engien.Output;
import com.slize.softsynth.Modules.*;
import com.slize.softsynth.utlis.Waveform;

public class Softsynth {

    public static void main(String[] args) throws Exception {
        Thread output = new Thread(new Output(), "output");

        Oscillator osc1 = new Oscillator();
        Oscillator osc2 = new Oscillator();
        Attenuator attenuator1 = new Attenuator();

        output.start();

        // Set what module has the output.
        Output.setSampleProvider(attenuator1);

        osc1.setWaveform(Waveform.SAW);
        osc1.setFrequency(69.0);
        osc2.setFrequency(1.0);
        attenuator1.setSampleProvider(osc1);
        attenuator1.setAttenuationRatio(0.5);
        attenuator1.setCv(osc2);
    }
}
