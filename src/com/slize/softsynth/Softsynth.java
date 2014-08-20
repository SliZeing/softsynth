package com.slize.softsynth;

import com.slize.softsynth.Engien.Output;

public class Softsynth {

    public static void main(String[] args) throws Exception {
        Thread output = new Thread(new Output(), "output");

        output.start();
    }
}
