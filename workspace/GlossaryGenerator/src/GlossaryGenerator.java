import components.map.Map;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Glossary generator outputting set of index and entry HTML pages given a valid
 * input file.
 *
 * @author Lucas Xie
 */
public class GlossaryGenerator {
    /**
     * Default CSS style to be applied to all term HTML pages.
     */
    public static final String TERM_PAGE_DEFAULT_STYLE = "h2 { color: red; "
            + "font-style: italic; }\n"
            + "footer { position: absolute; bottom: 50px; }\n";

    /**
     * Default CSS style to be applied to the glossary index HTML page.
     */
    public static final String INDEX_PAGE_DEFAULT_STYLE = "li { transition: all 0.15s; "
            + "padding: 3px; list-style-type: none; }\n"
            + "li:hover { font-size: 18px }\n";

    /**
     * Default CSS style to be shared by all HTML pages.
     */
    public static final String SHARED_DEFAULT_STYLE = ":root { --palette-def: #6633F59F; "
            + "--palette-active: #6633F5; }\n"
            + "body { font-family: monospace; }\n"
            + "a { color: var(--palette-def); transition: all 0.15s; }\n"
            + "a:hover { color: var(--palette-active); }\n";

    /**
     * Map representing term and definition pairs extracted from input file.
     */
    private final Map<String, String> entries;

    /**
     * Filepaths for input and output locations.
     */
    private final String outputDirPath, inputFilePath;

    /**
     * Constructor with input and output filepaths.
     *
     * @param inPath
     *            {@code String} representing input file filepath
     * @param outPath
     *            {@code String} representing output folder filepath
     */
    public GlossaryGenerator(String inPath, String outPath) {
        this.entries = new Map1L<>();
        this.inputFilePath = inPath;

        /*
         * Append directory indicator (/) if not present
         */
        if (!(outPath.charAt(outPath.length() - 1) == '/')) {
            this.outputDirPath = outPath + '/';
        } else {
            this.outputDirPath = outPath;
        }
    }

    /**
     * Purely for unit testing purposes; returns entries map.
     *
     * @return map with glossary entries
     */
    public Map<String, String> getEntries() {
        return this.entries;
    }

    /**
     * Extracts terms and definitions from input file and loads them into the
     * entry map.
     *
     * @updates entries
     * @requires inputFilePath is valid filepath to entry file formatted with
     *           term and definitions separated by line breaks
     * @ensures <pre>
     *     for all {k,v}: pair of string of character
     *          where ({k,v} is in [entries present in input file])
     *        (entries = #entries * {k,v})
     * </pre>
     */
    public void extractEntries() {
        SimpleReader in = new SimpleReader1L(this.inputFilePath);

        while (!in.atEOS()) {
            String termName = in.nextLine();

            StringBuilder termDef = new StringBuilder();
            String line;

            do {
                line = in.nextLine();
                termDef.append(line);
                /*
                 * Effectively replace all line breaks with space character (<p>
                 * tag ensures text wrapping)
                 */
                termDef.append(' ');
            } while (!line.isEmpty());

            this.entries.add(termName, termDef.toString().trim());
        }

        in.close();
    }

    /**
     * Parses entry (adding hyperlinks in definition to associated entries if
     * present) and writes entry to new HTML file at specified output directory.
     *
     * @param term
     *            entry term
     * @param definition
     *            entry definition
     * @requires |term| > 0 && |definition| > 0
     * @ensures <pre>
     *     [an HTMl file with default CSS stylings is generated at output directory of
     *     name x.html such that x is the value of {@code term}, with definition parsed
     *     with hyperlinks to other entry HTML files if entry term value is present
     *     verbatim and footer with hyperlink to index page]
     * </pre>
     */
    public void parseEntry(String term, String definition) {
        assert !term.isEmpty() : "Violation of: |term| > 0";
        assert !definition.isEmpty() : "Violation of: |definition| > 0";

        StringBuilder bodyContent = new StringBuilder();

        bodyContent.append("<h2>").append(term).append("</h2>\n");
        bodyContent.append("<hr>\n");
        bodyContent.append("<p>\n");

        int charIndex = 0;
        char c;
        StringBuilder wordStrb = new StringBuilder();

        do {
            /*
             * For the purposes of checking last word in definition string,
             * simply set it to a non-alpha character to guarantee check.
             */
            if (charIndex == definition.length()) {
                c = ' ';
            } else {
                c = definition.charAt(charIndex);
            }

            if (!String.valueOf(c).matches("[a-zA-Z]")) {
                String word = wordStrb.toString();

                if (this.entries.hasKey(word)) {
                    bodyContent.append("<a href='").append(word)
                            .append(".html'>");
                    bodyContent.append(word);
                    bodyContent.append("</a>");
                } else {
                    bodyContent.append(word);
                }

                bodyContent.append(c);
                wordStrb.delete(0, wordStrb.length());
            } else {
                wordStrb.append(c);
            }
            charIndex++;

        } while (charIndex <= definition.length());

        bodyContent.append("</p>\n");
        bodyContent.append("<footer>\n");
        bodyContent.append("<a href='index.html'>").append("→ Return to index")
                .append("</a>\n");
        bodyContent.append("</footer>\n");

        writeHTMLFile(this.outputDirPath + term + ".html", term,
                SHARED_DEFAULT_STYLE + TERM_PAGE_DEFAULT_STYLE,
                bodyContent.toString());
    }

    /**
     * Parses all entries within the entry map.
     *
     * @ensures <pre>
     *     [every entry within the entry map is parsed]
     * </pre>
     */
    public void parseAvailableEntries() {
        for (Map.Pair<String, String> entry : this.entries) {
            this.parseEntry(entry.key(), entry.value());
        }
    }

    /**
     * Generate index HTML page with hyperlinks to all other entry HTML files in
     * alphabetical natural order at specified output directory.
     *
     * @ensures <pre>
     *     [an HTML file with default CSS stylings is generated at output
     *     directory of name index.html with hyperlinks to associated
     *     entry pages sorted in alphabetical natural order]
     * </pre>
     */
    public void generateIndexPage() {
        StringBuilder bodyContent = new StringBuilder();
        /*
         * getSortedStrSet ensures output sequence is naturally ordered, which
         * is alphabetical natural order.
         */
        Sequence<String> sortedTerms = getSortedStrSet(getKeySet(this.entries));

        bodyContent.append("<h2>Glossary</h2>\n");
        bodyContent.append("<hr>\n");
        bodyContent.append("<ul>\n");

        for (int i = 0; i < sortedTerms.length(); i++) {
            String term = sortedTerms.entry(i);
            bodyContent.append("<li><a href='").append(term).append(".html'>")
                    .append(term).append("</a></li>\n");
        }

        bodyContent.append("</ul>");

        writeHTMLFile(this.outputDirPath + "index.html", "Glossary",
                SHARED_DEFAULT_STYLE + INDEX_PAGE_DEFAULT_STYLE,
                bodyContent.toString());
    }

    /**
     * Performs all necessary operations to fully generate a glossary (for the
     * purposes of encapsulation).
     *
     * @requires inputFilePath and outputDirPath are valid filepaths to entry
     *           file and output directory respectively
     * @ensures <pre>
     *     [index and entry HTML pages are generated at specified output directory]
     * </pre>
     */
    public void generateGlossary() {
        this.extractEntries();
        this.parseAvailableEntries();
        this.generateIndexPage();
    }

    /**
     * Writes (or overwrites) valid HTML file at specified filepath.
     *
     * @param filepath
     *            absolute or relative filepath of output file
     * @param title
     *            title of HTML page
     * @param styleContent
     *            internal CSS of HTML page
     * @param bodyContent
     *            body content of HTML page
     * @requires filepath is valid filepath, styleContent and bodyContent
     *           represent valid CSS and HTML snippets respectively
     * @ensures <pre>
     *     [a valid HTML file with specified internal CSS stylings/body
     *     content and title is written to specified filepath]
     * </pre>
     */
    public static void writeHTMLFile(String filepath, String title,
            String styleContent, String bodyContent) {
        SimpleWriter out = new SimpleWriter1L(filepath);
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>" + title + "</title>");
        out.println("<style>");
        out.print(styleContent);
        out.println("</style>");
        out.println("</head>");

        out.println("\n<body>");
        out.println(bodyContent);
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     * Generates a sequence ordered by natural ordering for a set of type
     * {@code String}.
     *
     * @param set
     *            set of type {@code String}
     * @return sequence of type {@code String} sorted by natural ordering
     * @requires set != null
     * @ensures <pre>
     *     [returns a sequence of type {@code String} sorted by natural ordering]
     * </pre>
     */
    public static Sequence<String> getSortedStrSet(Set<String> set) {
        assert set != null : "Violation of: set is not null";

        Set<String> temp = new Set1L<>();
        temp.transferFrom(set);

        Sequence<String> seq = new Sequence1L<>();

        while (temp.size() > 0) {
            int seqIndex = 0;
            String item = temp.removeAny();
            set.add(item);

            while (seqIndex < seq.length()
                    && seq.entry(seqIndex).compareTo(item) < 0) {
                seqIndex++;
            }

            seq.add(seqIndex, item);
        }

        return seq;
    }

    /**
     * Returns key set of given map.
     *
     * @param map
     *            map to extract keyset from
     * @return set of type {@code T} containing {@code map}'s keys
     * @param <T>
     *            type of {@code map}'s keys
     * @param <K>
     *            type of {@code map}'s values
     * @ensures <pre>
     * [returns a set of type {@code T} containing {@code map}'s keys]
     * </pre>
     */
    public static <T, K> Set<T> getKeySet(Map<T, K> map) {
        Map<T, K> temp = map.newInstance();
        temp.transferFrom(map);
        Set<T> set = new Set1L<>();

        while (temp.size() > 0) {
            Map.Pair<T, K> pair = temp.removeAny();
            map.add(pair.key(), pair.value());
            set.add(pair.key());
        }

        return set;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments (ignored)
     */
    public static void main(String[] args) {
        SimpleWriter consoleOut = new SimpleWriter1L();
        SimpleReader consoleIn = new SimpleReader1L();
        SimpleReader pathValidator;

        /*
         * Prompt and validate input filepath
         */
        consoleOut.println("Input filepath of input file.");
        String inputPath = consoleIn.nextLine();
        pathValidator = new SimpleReader1L(inputPath);
        pathValidator.close();

        /*
         * Prompt/validate output filepath and generate glossary (implicitly
         * validated by instantiation of GlossaryGenerator)
         */
        consoleOut.println("Input filepath of output folder.");
        String outputPath = consoleIn.nextLine();

        GlossaryGenerator glossaryGen = new GlossaryGenerator(inputPath,
                outputPath);
        glossaryGen.generateGlossary();

        consoleOut.println("Done. Glossary written to " + outputPath + ".");

        /*
         * Close I/O streams
         */
        consoleOut.close();
        consoleIn.close();
    }
}
