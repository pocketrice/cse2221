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
public final class CoinChange1 {

    /**
     * Standard US currency coin values.
     */
    static final int DOLLAR_VAL = 100, HALFDOLLAR_VAL = 50, QUARTER_VAL = 25,
            DIME_VAL = 10, NICKEL_VAL = 5;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CoinChange1() {
    }

    /**
     * Greedily convert a given amount of cents into coins.
     *
     * @param cents
     *            Amount of cents to convert
     * @param out
     *            Output stream
     *
     */
    public static void convertGreedyChange(int cents, SimpleWriter out) {
        int val = cents;
        out.print(val / DOLLAR_VAL + " dollars, ");
        val %= DOLLAR_VAL;

        out.print(val / HALFDOLLAR_VAL + " half-dollars, ");
        val %= HALFDOLLAR_VAL;

        out.print(val / QUARTER_VAL + " quarters, ");
        val %= QUARTER_VAL;

        out.print(val / DIME_VAL + " dimes, ");
        val %= DIME_VAL;

        out.print(val / NICKEL_VAL + " nickels, ");
        val %= NICKEL_VAL;

        out.print(val + " cents.");
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
        convertGreedyChange(in.nextInteger(), out);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
