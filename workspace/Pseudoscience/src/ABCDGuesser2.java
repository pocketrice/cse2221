import java.util.Arrays;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Tool for analysing results of De Jager's formula with any given values.
 *
 * @author Lucas Xie
 *
 */
public final class ABCDGuesser2 {
    /**
     * Set of possible constants for use for exponents in De Jager's formula.
     */
    static final double[] DE_JAGER_CONSTS = { -5, -4, -3, -2, -1, -1.0 / 2,
            -1.0 / 3, -1.0 / 4, 0, 1.0 / 4, 1.0 / 3, 1.0 / 2, 1, 2, 3, 4, 5 };

    /**
     * Max allowed relative percent error for De Jager's formula result.
     */
    static final double MAX_EPSILON = 0.01;

    /**
     * Number of constants required for De Jager's formula.
     */
    static final int DE_JAGER_COUNT = 4;

    /**
     * Constant to multiply by to convert standard num to percent.
     */
    static final int PERCENT_CONVERTER = 100;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        boolean isValid = false;
        String input = null;

        while (!isValid) {
            out.print("Input a positive double...  ");
            input = in.nextLine();
            isValid = FormatChecker.canParseDouble(input)
                    && Double.parseDouble(input) > 0;

            if (!isValid) {
                out.println("Error: please input a positive double.\n");
            }
        }

        return Double.parseDouble(input);
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double input = -1.0;

        while (input < 0.0 || input == 1.0) {
            out.print("Inputs of 1 are prohibited. ");
            input = getPositiveDouble(in, out);

            if (input == 1.0) {
                out.println("Error: please input a positive non-one double.\n");
            }
        }

        return input;
    }

    /**
     * Format and print results of De Jager's formula with preset values.
     *
     * @param currentEpsilon
     *            final percent error from combination of De Jager constants
     * @param wxyzVals
     *            positive non-one doubles from user used in De Jager's formula
     * @param deJagerNums
     *            optimal set of constants used in De Jager's formula
     * @param out
     *            the output stream
     */
    private static void printDeJagerResults(double currentEpsilon,
            double[] wxyzVals, double[] deJagerNums, SimpleWriter out) {
        if (currentEpsilon < MAX_EPSILON) {
            out.println("Formula valid: epsilon below max value.");
        } else {
            out.println("Formula failed: epsilon above max value.");
        }

        out.println("{a,b,c,d} = " + Arrays.toString(deJagerNums));
        out.println("w^a * x^b * y^c * z^d = "
                + Math.pow(wxyzVals[0], deJagerNums[0])
                        * Math.pow(wxyzVals[1], deJagerNums[1])
                        * Math.pow(wxyzVals[2], deJagerNums[2])
                        * Math.pow(wxyzVals[3], deJagerNums[3]));
        out.print("Epsilon = ");
        out.print(currentEpsilon * PERCENT_CONVERTER, 2, false);
        out.println("%");
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
        double mu, w, x, y, z;
        double currentEpsilon = Double.MAX_VALUE;
        double[] deJagerNums = new double[DE_JAGER_COUNT];

        /*
         * Prompt for De Jager formula inputs and determine constants that
         * provide lowest error and print results.
         */
        out.println("Set a mu value.");
        mu = getPositiveDouble(in, out);

        out.println("Set a w value.");
        w = getPositiveDoubleNotOne(in, out);

        out.println("Set an x value.");
        x = getPositiveDoubleNotOne(in, out);

        out.println("Set a y value.");
        y = getPositiveDoubleNotOne(in, out);

        out.println("Set a z value.");
        z = getPositiveDoubleNotOne(in, out);

        for (int i = 0; i < DE_JAGER_CONSTS.length
                && currentEpsilon != 0; i++) {
            for (int j = 0; j < DE_JAGER_CONSTS.length
                    && currentEpsilon != 0; j++) {
                for (int k = 0; k < DE_JAGER_CONSTS.length
                        && currentEpsilon != 0; k++) {
                    for (int l = 0; l < DE_JAGER_CONSTS.length
                            && currentEpsilon != 0; l++) {
                        double deJagerResult = Math.pow(w, DE_JAGER_CONSTS[i])
                                * Math.pow(x, DE_JAGER_CONSTS[j])
                                * Math.pow(y, DE_JAGER_CONSTS[k])
                                * Math.pow(z, DE_JAGER_CONSTS[l]);
                        double deJagerEpsilon = Math.abs(deJagerResult - mu)
                                / mu;
                        if (deJagerEpsilon < currentEpsilon) {
                            currentEpsilon = deJagerEpsilon;
                            deJagerNums[0] = DE_JAGER_CONSTS[i];
                            deJagerNums[1] = DE_JAGER_CONSTS[j];
                            deJagerNums[2] = DE_JAGER_CONSTS[k];
                            deJagerNums[3] = DE_JAGER_CONSTS[l];

                        }
                    }
                }
            }
        }

        printDeJagerResults(currentEpsilon, new double[] { w, x, y, z },
                deJagerNums, out);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
