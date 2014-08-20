package com.slize.softsynth;

import com.slize.softsynth.Engien.Output;
import com.slize.softsynth.Modules.Oscillator;

public class Softsynth {

    public static void main(String[] args) throws Exception {
        Thread output = new Thread(new Output(), "output");
        Oscillator osc = new Oscillator();

        output.start();

        Output.setSampleProvider(osc);

        while(true) {
            osc.setFrequency(440.00 / 2);
            Thread.sleep(1000);
            osc.setFrequency(523.25 / 2);
            Thread.sleep(1000);
            osc.setFrequency(659.25 / 2);
            Thread.sleep(1000);
        }
    }
}
