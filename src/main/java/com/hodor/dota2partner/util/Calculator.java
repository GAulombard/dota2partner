package com.hodor.dota2partner.util;

public class Calculator {

    public static float winRateCalculator(int win, int loss) {

        double winRate = ((double) win / (double) (win + loss)) * 100;
        return ((float) Math.round(winRate * 100) / 100);

    }

    public static long steamId64toSteamId32(long steamId64) {

        return steamId64 - 76561197960265728L;
    }
}
