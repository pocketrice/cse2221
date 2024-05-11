import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML aggregate of RSS (version 2.0) feeds from a given
 * URL into the corresponding HTML index and feed pages.
 *
 * @author Lucas Xie
 *
 */
public final class RSSAggregator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSAggregator() {
    }

    /**
     * Processes one XML RSS (version 2.0) feed from a given URL converting it
     * into the corresponding HTML output file.
     *
     * @param url
     *            the URL of the RSS feed
     * @param file
     *            the name of the HTML output file
     * @param out
     *            the output stream to report progress or errors
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * [reads RSS feed from url, saves HTML document with table of news items
     *   to file, appends to out.content any needed messages]
     * </pre>
     */
    private static void processFeed(String url, String file, SimpleWriter out) {
        XMLTree channel = new XMLTree1(url).child(0);
        SimpleWriter writerOut = new SimpleWriter1L("src/" + file);

        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        String titleItem, descItem;

        int xmlTitleIndex = getChildElement(channel, "title");
        if (channel.child(xmlTitleIndex).numberOfChildren() == 1) {
            titleItem = channel.child(xmlTitleIndex).child(0).label();
        } else {
            titleItem = "Empty Title";
        }

        int descIndex = getChildElement(channel, "description");
        if (channel.child(descIndex).numberOfChildren() == 1) {
            descItem = channel.child(descIndex).child(0).label();
        } else {
            descItem = "No description.";
        }

        writerOut.println("<html>");
        writerOut.println("<head>");
        writerOut.println("<title>" + titleItem + "</title>");
        writerOut.println("<style>");
        writerOut.println("body {");
        writerOut.println("font-family: 'Verdana', sans-serif;");
        writerOut.println("padding: 20px;");
        writerOut.println("}\n");

        writerOut.println("h1 {");
        writerOut.println("text-align: center;");
        writerOut.println("}\n");

        writerOut.println("p {");
        writerOut.println("font-style: italic;");
        writerOut.println("}\n");

        writerOut.println("td {");
        writerOut.println("padding: 3px 5px;");
        writerOut.println("}");
        writerOut.println("</style>");
        writerOut.println("</head>");
        writerOut.println("<body>");
        writerOut
                .println("<h1><a href='"
                        + channel.child(getChildElement(channel, "link"))
                                .child(0).label()
                        + "'>" + titleItem + "</a></h1>");
        writerOut.println("<p>" + descItem + "</p>");
        writerOut.println("<table border='1'>");
        writerOut.println("<tr>");
        writerOut.println("<th>Date</th>");
        writerOut.println("<th>Source</th>");
        writerOut.println("<th>News</th>");
        writerOut.println("</tr>");

        for (int i = 0; i < channel.numberOfChildren(); i++) {
            XMLTree item = channel.child(i);

            if (item.label().equals("item")) {
                String pubDateItem, sourceItem, descriptorItem;

                int pubDateIndex = getChildElement(item, "pubDate");
                int sourceIndex = getChildElement(item, "source");

                if (pubDateIndex != -1) {
                    pubDateItem = item.child(pubDateIndex).child(0).label();
                } else {
                    pubDateItem = "No date available.";
                }

                if (sourceIndex != -1
                        && item.child(sourceIndex).numberOfChildren() == 1) {
                    sourceItem = "<a href='"
                            + item.child(sourceIndex).attributeValue("url")
                            + "'>" + item.child(sourceIndex).child(0).toString()
                            + "</a>";
                } else {
                    sourceItem = "No source available.";
                }

                int titleIndex = getChildElement(item, "title");
                int descriptionIndex = getChildElement(item, "description");

                if (item.child(titleIndex).numberOfChildren() == 1) {
                    descriptorItem = item.child(titleIndex).child(0).label();
                } else if (item.child(descriptionIndex)
                        .numberOfChildren() == 1) {
                    descriptorItem = item.child(descriptionIndex).child(0)
                            .label();
                } else {
                    descriptorItem = "No title available.";
                }

                int linkIndex = getChildElement(item, "link");
                if (linkIndex != -1) {
                    descriptorItem = "<a href='"
                            + item.child(linkIndex).child(0).label() + "'>"
                            + descriptorItem + "</a>";
                }

                writerOut.println("<tr>");
                writerOut.println("<td>" + pubDateItem + "</td>");
                writerOut.println("<td>" + sourceItem + "</td>");
                writerOut.println("<td>" + descriptorItem + "</td>");
                writerOut.println("</tr>");
            }
        }

        writerOut.println("</table>");
        writerOut.println("</body>");
        writerOut.println("</html>");
        out.println("Finished writing to " + file + ".");

        writerOut.close();
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
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
     * Validates and reports whether the valid XML document's root tag has label
     * "feeds" and has an attribute of "title".
     *
     * @param item
     *            the RSS feeds aggregate
     * @requires [the label of the root of xml is a tag]
     * @return whether or not xml has label "feeds" and has attribute "title"
     * @ensures <pre>
     * validateRssAggregate = [whether or not xml has label
     * "feeds" and has attribute "title"]
     * </pre>
     */
    private static boolean validateRssAggregate(XMLTree item) {
        return (item.isTag() && item.label().equals("feeds")
                && item.hasAttribute("title"));
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
        SimpleWriter consoleOut = new SimpleWriter1L();

        /*
         * Prompt for source URL until valid RSS aggregate is extracted.
         */
        boolean isValidRSSAggregate = false;
        XMLTree feeds;

        do {
            /*
             * Read XML input and initialize XMLTree. If input is not legal XML,
             * this statement will fail.
             */
            consoleOut.println("Enter URL to a RSS 2.0 feed aggregate: ");
            feeds = new XMLTree1(in.nextLine());

            /*
             * Validate extracted valid XML document if it is a valid RSS
             * aggregate.
             */
            if (validateRssAggregate(feeds)) {
                isValidRSSAggregate = true;
            } else {
                consoleOut.println(
                        "Violation of: XML document is not a valid RSS aggregate.\n\n");
            }
        } while (!isValidRSSAggregate);

        /*
         * Prompt for name of RSS aggregate index page and initialise file
         * writer.
         */
        consoleOut.print(
                "\nEnter the plain filename (no file extension, e.g. 'index')"
                        + " for RSS aggregate index page: ");
        String indexPageFilename = in.nextLine();
        SimpleWriter indexWriter = new SimpleWriter1L(
                "src/" + indexPageFilename + ".html");

        /*
         * Process RSS XML document and output to HTML file named as specified
         * title.
         */
        String indexTitle = feeds.attributeValue("title");
        indexWriter.println("<html>");
        indexWriter.println("<head>");
        indexWriter.println("<title>" + indexTitle + "</title>");
        indexWriter.println("</head>");
        indexWriter.println("<body>");
        indexWriter.println("<h1>" + indexTitle + "</h1>");
        indexWriter.println("<ul>");

        for (int i = 0; i < feeds.numberOfChildren(); i++) {
            XMLTree feed = feeds.child(i);
            processFeed(feed.attributeValue("url"), feed.attributeValue("file"),
                    consoleOut);
            indexWriter.println("<li><a href='" + feed.attributeValue("file")
                    + "'>" + feed.attributeValue("name") + "</a>");
        }

        indexWriter.println("</ul>");
        indexWriter.println("</body>");
        indexWriter.println("</html>");

        consoleOut.println("\nFinished parsing feeds and writing to "
                + indexPageFilename + ".html!");

        /*
         * Close I/O streams.
         */
        in.close();
        indexWriter.close();
        consoleOut.close();
    }
}
