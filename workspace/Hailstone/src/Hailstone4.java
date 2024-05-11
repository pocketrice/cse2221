import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Prompts and calculates info about hailstone series for any positive integer
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

    /**
     * Generates and outputs the Hailstone series starting with the given
     * integer as well as its length and max value.
     *
     * @param n
     *            the starting integer
     * @param out
     *            the output stream
     */
    private static void generateSeries(int n, SimpleWriter out) {
        int curr = n, length = 1, max = Integer.MIN_VALUE;
        while (curr != 1) {
            out.print(curr + ", ");
            length++;

            max = Math.max(max, curr);

            curr = (curr % 2 == 0) ? curr /= 2 : 3 * curr + 1;
        }
        out.println(curr + " (length = " + length + ", max = " + max + ")");
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

        while (isRunning) {
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
             * Ask user for if they wish to calculate another series and resume
             * iteration if true
             */
            out.print(
                    "Calculate another hailstone series? Input y if desired...  ");
            String choice = in.nextLine();
            isRunning = choice.equals("y");

            out.print("\n\n");
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
