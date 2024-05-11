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
public final class CoinChange2 {
    /**
     * Standard US currency coin names.
     */
    static final String[] CHANGE_DENOMS = { "dollars", "half-dollars",
            "quarters", "dimes", "nickels", "pennies" };

    /**
     * Standard US currency coin values.
     */
    static final int[] CHANGE_VALS = { 100, 50, 25, 10, 5, 1 };

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CoinChange2() {
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

        for (int i = 0; i < CHANGE_DENOMS.length; i++) {
            if (val / CHANGE_VALS[i] != 0) {
                out.print(val / CHANGE_VALS[i] + " " + CHANGE_DENOMS[i]);

                if (i < CHANGE_DENOMS.length - 1) {
                    out.print(", ");
                }
            }

            val %= CHANGE_VALS[i];
        }
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
