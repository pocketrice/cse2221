import java.util.Arrays;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Lucas Xie
 *
 */
public final class CoinChange3 {
    /**
     * Standard US currency coin values.
     */
    static final int[] COIN_DENOMS = { 100, 50, 25, 10, 5, 1 };

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CoinChange3() {
    }

    /**
     * Greedily convert a given amount of cents into given coin denominations.
     *
     * @param cents
     *            Amount of cents to convert
     * @param coins
     *            Coin denominations
     * @return Converted array representing total coins per each denomination
     *
     */
    public static int[] coinCountsToMakeChange(int cents, int[] coins) {
        int val = cents;
        int[] counts = new int[coins.length];

        for (int i = 0; i < coins.length; i++) {
            if (val / coins[i] != 0) {
                counts[i] = val / coins[i];
            }

            val %= coins[i];
        }

        return counts;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Put your main program code here
         */
        out.print("Input # of cents to greedy convert...  ");
        out.print(Arrays.toString(
                coinCountsToMakeChange(in.nextInteger(), COIN_DENOMS)));

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
