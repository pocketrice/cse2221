import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
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
public final class Hailstone4 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Hailstone4() {
    }

    private static void outputSeries(NaturalNumber n, SimpleWriter out) {
        NaturalNumber[] series = generateSeries(n);
        NaturalNumber seriesMax = findSeriesMax(series);
        String seriesStr = Arrays.toString(series);

        out.println(seriesStr.substring(1, seriesStr.length() - 1) + " (len = "
                + series.length + ", max = " + seriesMax + ")");
    }

    private static NaturalNumber[] generateSeries(NaturalNumber n) {
        NaturalNumber curr = new NaturalNumber1L(n);
        List<NaturalNumber> seriesList = new ArrayList<>();

        while (!curr.equals(new NaturalNumber1L(1))) {
            seriesList.add(new NaturalNumber1L(curr));
            NaturalNumber currDivTwoRemainder = new NaturalNumber1L(curr);
            if (currDivTwoRemainder.divide(new NaturalNumber1L(2))
                    .equals(new NaturalNumber1L(0))) {
                curr.divide(new NaturalNumber1L(2));
            } else {
                curr.multiply(new NaturalNumber1L(3));
                curr.add(new NaturalNumber1L(1));
            }
        }
        seriesList.add(curr);

        return seriesList.toArray(new NaturalNumber[0]);
    }

    private static NaturalNumber findSeriesMax(NaturalNumber[] series) {
        NaturalNumber max = series[0];

        for (NaturalNumber item : series) {
            if (max.compareTo(item) < 0) {
                max = item;
            }
        }

        return max;
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

        boolean isValidPosInt = false, isRunning = true;
        NaturalNumber input = new NaturalNumber1L();
        NaturalNumber[] largestValPair = { input.newInstance(),
                input.newInstance() };
        NaturalNumber[] longestSeriesPair = { input.newInstance(),
                input.newInstance() };

        while (isRunning) {
            isValidPosInt = false;

            while (!isValidPosInt) {
                out.print("Input a positive nonzero int...  ");
                int candidate = in.nextInteger();

                if (candidate <= 0) {
                    out.println("Input not valid. Violation of: i > 0");
                } else {
                    isValidPosInt = true;
                    input = new NaturalNumber1L(candidate);
                }
            }

            largestValPair[0].clear();
            largestValPair[1].clear();
            longestSeriesPair[0].clear();
            longestSeriesPair[1].clear();

            for (NaturalNumber n = new NaturalNumber1L(0); n
                    .compareTo(input) < 0; n.add(new NaturalNumber1L(1))) {

                NaturalNumber[] series = generateSeries(input);
                NaturalNumber seriesMax = findSeriesMax(series);
                NaturalNumber seriesLen = new NaturalNumber1L(series.length);

                if (largestValPair[0].compareTo(seriesMax) == 0) {
                    largestValPair[1] = (largestValPair[1].compareTo(input) > 0)
                            ? input
                            : largestValPair[1];
                } else if (largestValPair[0].compareTo(seriesMax) < 0) {
                    largestValPair[0] = seriesMax;
                    largestValPair[1] = input;
                }

                if (longestSeriesPair[0].compareTo(seriesLen) == 0) {
                    longestSeriesPair[1] = (longestSeriesPair[1]
                            .compareTo(input) > 0) ? input
                                    : longestSeriesPair[1];
                } else if (longestSeriesPair[0].compareTo(seriesLen) < 0) {
                    longestSeriesPair[0] = seriesLen;
                    ;
                    longestSeriesPair[1] = input;
                }
            }

            out.println("Current largest val: " + largestValPair[0]
                    + " (start num = " + largestValPair[1] + ")");
            out.println("Current longest length: " + longestSeriesPair[0]
                    + " (start num = " + longestSeriesPair[1] + ")");
            out.println("\n\n");
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
