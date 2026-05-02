package org.example;
import java.util.Random;

public class RandomNumberGenerator {
    private static Random rand = new Random();

    public static double nextDouble() {
        return rand.nextDouble();
    }
    public static int nextInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}