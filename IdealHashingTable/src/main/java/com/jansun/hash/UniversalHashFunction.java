package com.jansun.hash;

import java.math.BigInteger;
import java.util.Random;

public class UniversalHashFunction {
    public UniversalHashFunction(int m) {
        this.m = m;
        Random rand = new Random();
        a = rand.nextInt(p-1) + 1;
        b = rand.nextInt(p);
    }

    public int computeHash(int k) {
        // (a * k + b) % p
        BigInteger bigInteger = BigInteger.valueOf(k);
        bigInteger = bigInteger.multiply(BigInteger.valueOf(a));
        bigInteger = bigInteger.add(BigInteger.valueOf(b));
        bigInteger = bigInteger.mod(BigInteger.valueOf(p));
        int intValue = bigInteger.intValue();
        return intValue % m;
    }

    private int a;
    private int b;
    private final int p = Integer.MAX_VALUE;
    private int m;

}
