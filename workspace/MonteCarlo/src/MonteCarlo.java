import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Monte Carlo Estimate: compute percentage of pseudo-random 2D points in
 * [0.0,2.0) x and y intervals that fall within the area of circle of radius 1
 * centered at (1.0, 1.0).
 */
public final class MonteCarlo {
    public static final int CIRCLE_RADIUS = 1; // <-- TA Note: move these into the main as you don't need them global. This is to avoid requiring javadocs.
    public static final double SQUARE_LENGTH = 2.0;
    public static final double[] CIRCLE_CENTER = { 1.0, 1.0 };

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private MonteCarlo() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        /*
         * Open input and output streams
         */
        SimpleReader input = new SimpleReader1L();
        SimpleWriter output = new SimpleWriter1L();
        /*
         * Ask user for number of points to generate
         */
        output.print("Number of points: ");
        int n = input.nextInteger();
        /*
         * Declare counters and initialize them
         */
        int ptsInInterval = 0, ptsInSubinterval = 0;
        /*
         * Create pseudo-random number generator
         */
        Random rnd = new Random1L();
        /*
         * Generate points and count how many fall within circle's area
         */
        while (ptsInInterval < n) {
            /*
             * Generate pseudo-random numbers X and Y in [0.0, SQUARE_LENGTH)
             * interval
             */
            double[] coord = new double[] { rnd.nextDouble() * SQUARE_LENGTH,
                    rnd.nextDouble() * SQUARE_LENGTH };

            /*
             * Increment total number of generated points
             */
            ptsInInterval++;

            /*
             * Calculate difference between coord X and Y and circle center X
             * and Y respectively for use in distance calculations
             */
            double diffX = coord[0] - CIRCLE_CENTER[0];
            double diffY = coord[1] - CIRCLE_CENTER[1];

            /*
             * Calculate distance to point from circle center squared, as the
             * square root operation is expensive
             */
            double dist2 = diffX * diffX + diffY * diffY;

            /*
             * If distance squared isn't greater than distance to circle
             * perimeter squared, increment subinterval counter
             */
            if (dist2 <= CIRCLE_RADIUS * CIRCLE_RADIUS) {
                ptsInSubinterval++;
            }
        }

        /*
         * Estimate and print area of circle by finding percentage of points in
         * subinterval within total interval and multiplying by area of square
         * of possibilities
         */
        double estimate = SQUARE_LENGTH * SQUARE_LENGTH * ptsInSubinterval
                / ptsInInterval; // <-- TA Note: originally had "double estimate = (SQUARE_LENGTH * SQUARE_LENGTH) * ((double) ptsInSubinterval / ptsInInterval). This is not preferable, though more readable; casting hasn't been taught yet.
        output.println("Estimate of circle's area: " + estimate);
        /*
         * Close input and output streams
         */
        input.close();
        output.close();
    }

}
