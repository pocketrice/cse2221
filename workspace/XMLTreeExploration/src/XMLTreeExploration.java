import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to investigate XMLTree's methods on a Columbus weather report.
 *
 * @author Lucas Xie
 *
 */
public final class XMLTreeExploration {
    /**
     * Number of dashes to print for a horizontal ruling.
     */
    static final int HR_LENGTH = 30;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private XMLTreeExploration() {
    }

    /**
     * Output info about the middle child of the given {@code XMLTree}.
     *
     * @param xt
     *            the {@code XMLTree} whose middle child is to be printed
     * @param out
     *            the output stream
     * @updates out.content
     * @requires <pre>
     * [the label of the root of xt is a tag] and
     * [xt has at least one child] and out.is_open
     * </pre>
     * @ensures <pre>
     * out.content = #out.content * [the label of middle child
     *  of xt, whether root of middle child is tag or text,
     *  and if it is a tag, number of children of middle child]
     * </pre>
     */
    private static void printMiddleNode(XMLTree xt, SimpleWriter out) {
        XMLTree midNode = xt.child(xt.numberOfChildren() / 2);

        out.println("Midnode label: " + midNode.label());
        out.println("Label type: " + (midNode.isTag() ? "TAG" : "TEXT"));

        if (midNode.isTag()) {
            out.println("# of children: " + midNode.numberOfChildren());
        }
    }

    /**
     * Output all attributes' names and values for the root of the given
     * {@code XMLTree}.
     *
     * @param xt
     *            the {@code XMLTree} whose root's attributes are to be printed
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [label of the root of xt is a tag] and out.is_open
     * @ensures <pre>
     * out.content =
     *  #out.content * [name and value of each attribute of the root of xt]
     *  </pre>
     */
    private static void printRootAttributes(XMLTree xt, SimpleWriter out) {
        for (String attr : xt.attributeNames()) {
            out.println("#" + attr + " = " + xt.attributeValue(attr));
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        final int astronomyIndex = 10;
        final int itemIndex = 12;
        final int forecastIndex = 9;
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Output responses to XMLTree Methods lab questions.
         */
        XMLTree xml = new XMLTree1(
                "http://web.cse.ohio-state.edu/software/2221/web-sw1/"
                        + "extras/instructions/xmltree-model/columbus-weather.xml");
        // out.println(xml);
        xml.display("XML Tree!");

        out.println("Is xml a tag? " + xml.isTag());
        out.println("Label of xml: " + xml.label());

        out.println("\n" + "-".repeat(HR_LENGTH) + "\n");

        XMLTree results = xml.child(0);
        XMLTree channel = results.child(0);
        out.println(channel.numberOfChildren());

        XMLTree title = xml.child(0).child(0).child(1);
        XMLTree titleText = title.child(0);
        out.println(titleText.label());

        out.println(xml.child(0).child(0).numberOfChildren() + "\n"
                + xml.child(0).child(0).child(1).child(0).label());

        out.println("\n" + "-".repeat(HR_LENGTH) + "\n");

        XMLTree astronomy = channel.child(astronomyIndex);
        out.println("astronomy has attribute 'sunset'? "
                + astronomy.hasAttribute("sunset"));
        out.println("astronomy has attribute 'midday'? "
                + astronomy.hasAttribute("midday"));
        out.println(astronomy.attributeValue("sunrise") + " "
                + astronomy.attributeValue("sunset"));

        out.println("\n" + "-".repeat(HR_LENGTH) + "\n");

        printMiddleNode(channel, out);

        out.println("\n" + "-".repeat(HR_LENGTH) + "\n");

        XMLTree item = channel.child(itemIndex);
        XMLTree forecast = item.child(forecastIndex);
        printRootAttributes(forecast, out);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
