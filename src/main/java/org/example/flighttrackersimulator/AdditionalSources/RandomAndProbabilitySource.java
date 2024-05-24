package org.example.flighttrackersimulator.AdditionalSources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomAndProbabilitySource {

    public static int randInt(int min, int max)
    {
        Random random = new Random();
        return ( random.nextInt( max - min + 1 ) + min );
    }

    public static double randDouble(double min, double max)
    {
        Random random = new Random();
        return ( min + (max - min) * random.nextDouble() );
    }

    public static boolean randProbability(double probability)
    {
        return probability > randDouble(0, 100);
    }

    public static Object randSelectArray(Object[] ar)
    {
        return ar[randInt(0, ar.length)];
    }
    
    public static Object randSelectList(List<Object> list)
    {
        return list.get(randInt(0, list.size()));
    }
}
