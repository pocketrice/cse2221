import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Lucas Xie
 *
 */
public final class Oddity4 {

    /**
     * No argument constructor--private to prevent instantiation.
     */

    private Oddity4() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        out.println(12345 + 54321);
        out.close();
    }

}
