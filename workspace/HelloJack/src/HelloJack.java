import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Practice project to learn basics of using Eclipse that prompts the user for a
 * name and outputs "Hello, <name>"
 *
 * @author L. Xie
 *
 */
public final class HelloJack {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private HelloJack() {
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
         * Put your main program code here
         */

        out.println("Please input your name.");
        String str = in.nextLine().trim();
        out.println("Hello, " + str);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
