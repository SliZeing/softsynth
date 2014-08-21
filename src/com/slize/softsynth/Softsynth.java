package com.slize.softsynth;

import com.slize.softsynth.Engien.Output;
import com.slize.softsynth.Modules.Attenuator;
import com.slize.softsynth.Modules.Mixer;
import com.slize.softsynth.Modules.Oscillator;
import com.slize.softsynth.utlis.Waveform;

public class Softsynth {

    public static void main(String[] args) throws Exception {
        Thread output = new Thread(new Output(), "output");

        Oscillator osc1 = new Oscillator();
        Oscillator osc2 = new Oscillator();
        Attenuator attenuator1 = new Attenuator();
        Attenuator attenuator2 = new Attenuator();
        Mixer mixer = new Mixer();

        output.start();

        // Set what module has the output.
        Output.setSampleProvider(mixer);

        osc1.setWaveform(Waveform.SAW);
        osc1.setFrequency(69.0);
        attenuator1.setSampleProvider(osc1);
        attenuator1.setAttenuationRatio(0.05);
        mixer.addSampleProvider(attenuator1);

        osc2.setWaveform(Waveform.SAW);
        osc2.setFrequency(70.0);
        attenuator2.setSampleProvider(osc2);
        attenuator2.setAttenuationRatio(0.05);
        mixer.addSampleProvider(attenuator2);
    }
}
