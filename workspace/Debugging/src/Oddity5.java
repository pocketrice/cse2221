import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Lucas Xie
 *
 */
public final class Oddity5 {

    /**
     * No argument constructor--private to prevent instantiation.
     */

    private Oddity5() {
    }

    /**
     * Returns a modulo b using "clock arithmetic".
     *
     * @param a
     *            the first operand
     * @param b
     *            the modulus
     * @return a modulo b
     * @requires b > 0
     * @ensures mod = a mod b
     */
    private static int mod(int a, int b) {
        int result;

        if (a > 0) {
            result = a % b;
        } else {
            result = b - Math.abs(a % b);
        }

        return result;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        final int clockModA = 67;
        final int clockModB = 24;
        SimpleWriter out = new SimpleWriter1L();
        out.println(mod(clockModA, clockModB));
        out.println(mod(-clockModA, clockModB));
    }

}
