package io;

import stdlib.*;

public class ShannonEntropy {
    public static void main(String[] args) {
        int x = 0;
        int m = Integer.parseInt(args[0]);
        double[] numberOf = new double[m + 1];
        int n = 0;
        do {
            x = StdIn.readInt();
            if (x >= 1 && x <= m) {
                numberOf[x]++;
                n++;
            }
        } while (!StdIn.isEmpty());
        for (int i = 1; i <= m; i++) {
            numberOf[i] = numberOf[i]/(double)n;
        }
        for (int i = 1; i <= m; i++) {
            if (numberOf[i] != 0) {
                numberOf[i] = (-1 * numberOf[i]) * (Math.log(numberOf[i]) / Math.log(2));
            }
        }
        double sum = 0;
        for (int i = 0; i < numberOf.length; i++) {
            sum += numberOf[i];
        }
        System.out.println(sum);
    }
}
