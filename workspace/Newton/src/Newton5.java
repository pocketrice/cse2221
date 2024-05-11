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
public final class Newton5 {
    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton5() {
    }

    /**
     * Computes estimate of kth root of x to within specified relative error.
     *
     * @param x
     *            positive number to compute kth root of
     * @param k
     *            positive number greater than 2 representing number of root
     * @param epsilon
     *            exclusive max of relative error permitted for approximation
     * @return estimate of square root
     */
    private static double sqrt(double x, int k, double epsilon) {
        double r = x;

        /*
         * Recursively set guess to 1/k [(k-1)r + x / r^(n-1)] until less than
         * error bound. x is multiplied on both sides of formula to avoid
         * divide-by-zero. Formula adapted from
         * https://planetmath.org/nthrootbynewtonsmethod.
         */
        while (r != 0
                && Math.abs(Math.pow(r, k) - x) >= epsilon * epsilon * x) {
            r = ((k - 1) * r + x / (Math.pow(r, k - 1))) / k;
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
        boolean isRunning = true;

        /*
         * Prompt user for an error bound and base x and k values, and output
         * results of square root from Newton iteration. Repeats calculation for
         * given x and k values until negative x value is received from user and
         * execution is stopped.
         */
        out.println("Pick epsilon value...  ");
        double epsilon = in.nextDouble();
        out.println("\n\n");

        while (isRunning) {
            out.println(
                    "Pick x value to find kth root via Newtonian iteration...  ");
            double x = in.nextDouble();

            out.println("Pick k value.");
            int k = in.nextInteger();

            if (x >= 0) {
                out.println(sqrt(x, k, epsilon) + "\n\n");
            } else {
                isRunning = false;
            }
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
