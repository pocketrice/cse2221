import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Prompts and calculates info about hailstone series for any positive integer.
 *
 * @author Lucas Xie
 *
 */
public final class Hailstone1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Hailstone1() {
    }

    /**
     * Generates and outputs the Hailstone series for the given integer,
     * terminating if the series reaches 1.
     *
     * @param n
     *            the starting integer
     * @param out
     *            the output stream
     */
    private static void generateSeries(int n, SimpleWriter out) {
        int curr = n;
        while (curr != 1) {
            out.print(curr + ", ");

            if (curr % 2 == 0) {
                curr /= 2;
            } else {
                curr = 3 * curr + 1;
            }
        }
        out.println(curr);
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
         * Prompt user for input of positive integer (validity not enforced)
         */
        out.print("Input a positive integer...  ");
        int input = in.nextInteger();

        /*
         * Call the generateSeries method
         */
        generateSeries(input, out);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
