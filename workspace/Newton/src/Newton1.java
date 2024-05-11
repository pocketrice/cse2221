import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Calculator tool for finding square root of number using Newton iteration.
 *
 * @author Lucas Xie
 *
 */
public final class Newton1 {
    /**
     * Error term to represent satisfactory amount of error when doing Newton
     * iteration.
     */
    static final double EPSILON = 0.0001;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton1() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double r = x;

        /*
         * Recursively set guess to average of itself and x divided by itself
         * until less than error bound. x is multiplied on both sides of formula
         * to avoid divide-by-zero.
         */
        while (Math.abs(r * r - x) >= EPSILON * EPSILON * x) {
            r = (r + x / r) / 2;
        }

        return r;
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
         * Prompt user for a base x value, and output results of square root
         * from Newton iteration.
         */

        out.print(
                "Pick a number to find square root via Newtonian iteration...  ");
        out.println(sqrt(in.nextDouble()));

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
