import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Lucas Xie
 *
 */
public final class Oddity {

    /**
     * No argument constructor--private to prevent instantiation.
     */

    private Oddity() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        final int[] values = { 8, -4, 3, 0, -5 };
        int i = 0;
        while (i < values.length) {
            int remainder = Math.abs(values[i] % 2);
            if (remainder == 1) {
                out.println("odd");
            } else {
                out.println("even");
            }
            i = i + 1;
        }
        out.close();
    }

}
