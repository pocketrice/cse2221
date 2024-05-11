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
public final class Hailstone2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Hailstone2() {
    }

    /**
     * Generates and outputs the Hailstone series starting with the given
     * integer as well as its length.
     *
     * @param n
     *            the starting integer
     * @param out
     *            the output stream
     */
    private static void generateSeries(int n, SimpleWriter out) {
        int curr = n, length = 1;
        while (curr != 1) {
            out.print(curr + ", ");
            length++;

            curr = (curr % 2 == 0) ? curr /= 2 : 3 * curr + 1;
        }

        out.println(curr + " (length = " + length + ")");
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
