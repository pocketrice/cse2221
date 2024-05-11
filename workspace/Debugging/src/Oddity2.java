import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Lucas Xie
 *
 */
public final class Oddity2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */

    private Oddity2() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        final double EPSILON = 0.0001;
        SimpleWriter out = new SimpleWriter1L();
        double x = 456.0;
        double y = 100.0 * 4.56;
        if (Math.abs(x - y) <= EPSILON) {
            out.println("equal");
        } else {
            out.println("not equal");
        }
        out.close();
    }

}
