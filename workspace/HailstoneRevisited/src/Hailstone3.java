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
public final class Hailstone3 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Hailstone3() {
    }

    /**
     * Generates and outputs the Hailstone series as well as its length and max
     * value starting with the given {@code NaturalNumber}.
     *
     * @param n
     *            the starting natural number
     * @param out
     *            the output stream
     * @updates out.content
     * @requires n > 0 and out.is_open
     * @ensures out.content = #out.content * [the Hailstone series starting with
     *          n alongside its length and max value]
     */
    private static void generateSeries(NaturalNumber n, SimpleWriter out) {
        NaturalNumber curr = new NaturalNumber1L(n);
        NaturalNumber seriesMax = new NaturalNumber1L(n);

        int seriesLen = 0;

        while (!curr.equals(new NaturalNumber1L(1))) {
            out.print(curr + ", ");
            NaturalNumber currDivTwoRemainder = new NaturalNumber1L(curr);
            if (currDivTwoRemainder.divide(new NaturalNumber1L(2))
                    .equals(new NaturalNumber1L(0))) {
                curr.divide(new NaturalNumber1L(2));
            } else {
                curr.multiply(new NaturalNumber1L(3));
                curr.add(new NaturalNumber1L(1));
            }

            seriesLen++;
            if (seriesMax.compareTo(curr) < 0) {
                seriesMax = new NaturalNumber1L(curr);
            }
        }
        seriesLen++; // Accounting for final num of series (1)
        out.println(
                curr + " (len = " + seriesLen + ", max = " + seriesMax + ")");
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
         * Put your main program code here; it may call myMethod as shown
         */
        out.print("Input a positive integer...  ");
        NaturalNumber input = new NaturalNumber1L(in.nextInteger());
        generateSeries(input, out);
        out.println("Current input value: " + input);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
