package com.gmail.petrikov05.util;

import java.util.Random;

public class RandomUtil {

    private static final Random RANDOM = new Random();

    public static int getRandomIntRange(int minValue, int maxValue) {
        return RANDOM.nextInt(maxValue + 1 - minValue) + minValue;
    }

}
