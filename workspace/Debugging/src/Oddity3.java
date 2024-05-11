import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Lucas Xie
 *
 */
public final class Oddity3 {

    /**
     * No argument constructor--private to prevent instantiation.
     */

    private Oddity3() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        final long microsPerDay = 24l * 60 * 60 * 1000 * 1000;
        final long millisPerDay = 24l * 60 * 60 * 1000;
        out.println(microsPerDay / millisPerDay);
        out.close();
    }

}
