package com.slize.softsynth;

import com.slize.softsynth.Engien.Output;
import com.slize.softsynth.Modules.Attenuator;
import com.slize.softsynth.Modules.Oscillator;

public class Softsynth {

    public static void main(String[] args) throws Exception {
        Thread output = new Thread(new Output(), "output");

        Oscillator osc1 = new Oscillator();
        Oscillator lfo = new Oscillator();
        Attenuator attenuator = new Attenuator();

        output.start();

        // Set what module has the output.
        Output.setSampleProvider(attenuator);

        attenuator.setSampleProvider(osc1);
        attenuator.setAttenuationRatio(0.5);
        attenuator.setLfo(lfo);
        lfo.setFrequency(1.0);

        while(true) {
            lfo.setFrequency(2.0);
            Thread.sleep(200);
            lfo.setFrequency(5.0);
            Thread.sleep(1000);
        }
    }
}
