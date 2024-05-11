import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * This program inputs an XML RSS (version 2.0) feed from a given URL and
 * outputs various elements of the feed to the console.
 *
 * @author Lucas Xie
 *
 */
public final class RSSProcessing {
    /**
     * Number of dashes to print for a horizontal ruling.
     */
    static final int HR_LENGTH = 30;

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSProcessing() {
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of the {@code XMLTree} matching the
     *         given tag or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of the {@code XMLTree} matching the
     *   given tag or -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        int childIndex = -1;
        for (int i = 0; childIndex == -1 && i < xml.numberOfChildren(); i++) {
            if (xml.child(i).label().equals(tag)) {
                childIndex = i;
            }
        }

        return childIndex;

    }

    /**
     * Processes one news item and outputs the title, or the description if the
     * title is not present and the link (if available) with appropriate labels.
     * Outputs publication date and source with labels if present, else notifies
     * that it is missing.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures out.content = #out.content * [the title (or description) and
     *          link]
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        int titleIndex = getChildElement(item, "title");
        if (titleIndex != -1) {
            out.println("Title: " + item.child(titleIndex).child(0));
        } else {
            out.println("Description: " + item
                    .child(getChildElement(item, "description")).child(0));
        }

        out.println(
                "Link : " + item.child(getChildElement(item, "link")).child(0));

        int pubDateIndex = getChildElement(item, "pubDate");
        int sourceIndex = getChildElement(item, "source");

        if (pubDateIndex != -1) {
            out.println(
                    "Publication date: " + item.child(pubDateIndex).child(0));
        } else {
            out.println("<pubDate> not present.");
        }

        if (sourceIndex != -1) {
            out.println("Source: " + item.child(sourceIndex).child(0));
        } else {
            out.println("<source> not present.");
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        /*
         * Open I/O streams.
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Input the source URL.
         */
        out.print("Enter the URL of an RSS 2.0 news feed: ");
        String url = in.nextLine();

        // Some suggestions:
        // * https://www.teamfortress.com/rss.xml
        // * https://xkcd.com/rss.xml
        // * https://hnrss.org/frontpage
        // * https://dolphin-emu.org/blog/feeds/

        /*
         * Read XML input and initialize XMLTree. If input is not legal XML,
         * this statement will fail.
         */
        XMLTree xml = new XMLTree1(url);
        /*
         * Extract <channel> element.
         */
        XMLTree channel = xml.child(0);
        String[] tags = { "title", "description", "link" };

        for (String tag : tags) {
            out.print(tag.substring(0, 1).toUpperCase() + tag.substring(1)
                    + ": ");
            out.println(channel.child(getChildElement(channel, tag)).child(0));
        }

        out.println("\n" + "-".repeat(HR_LENGTH) + "\n");

        for (int i = 0; i < channel.numberOfChildren(); i++) {
            XMLTree candidate = channel.child(i);
            if (candidate.label().equals("item")) {
                processItem(candidate, out);
                out.println();
            }
        }

        /*
         * Close I/O streams.
         */
        in.close();
        out.close();
    }

}
