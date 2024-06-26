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
public final class Newton4 {
    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton4() {
    }

    /**
     * Computes estimate of square root of x to within specified relative error.
     *
     * @param x
     *            positive number to compute square root of
     * @param epsilon
     *            exclusive max of relative error permitted for approximation
     * @return estimate of square root
     */
    private static double sqrt(double x, double epsilon) {
        double r = x;

        /*
         * Recursively set guess to average of itself and x divided by itself
         * until less than error bound. x is multiplied on both sides of formula
         * to avoid divide-by-zero.
         */
        while (r != 0 && Math.abs(r * r - x) >= epsilon * epsilon * x) {
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
        boolean isRunning = true;

        /*
         * Prompt user for an error bound and base x value, and output results
         * of square root from Newton iteration. Repeats calculation for given x
         * values until a negative x value is received from user and execution
         * is stopped.
         */
        out.println("Pick epsilon value...  ");
        double epsilon = in.nextDouble();
        out.println("\n\n");

        while (isRunning) {
            out.println(
                    "Pick x value to find square root via Newtonian iteration...  ");
            double x = in.nextDouble();

            if (x >= 0) {
                out.println(sqrt(x, epsilon) + "\n\n");
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
