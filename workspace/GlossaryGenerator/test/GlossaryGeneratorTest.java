import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Test fixture for testing GlossaryGenerator implemented static and instance
 * methods.
 */

public class GlossaryGeneratorTest {
    /**
     * Generates a glossary generator with preset paths for testing
     * concision/ease-of-use.
     *
     * @param inContents
     *            input file contents
     * @return [glossary generator with preset paths]
     */
    public GlossaryGenerator createDefaultGlossaryGen(String inContents) {
        final String inPath = "data/data.txt";
        final String outPath = "data";

        SimpleWriter inWriter = new SimpleWriter1L(inPath);
        inWriter.println(inContents);
        inWriter.close();

        return new GlossaryGenerator(inPath, outPath);
    }

    /**
     * Tests extractEntries with entries that are unlinked and have no line
     * breaks in definitions.
     */
    @Test
    public void extractEntriesTestNoLinkNoLineBreaks() {
        GlossaryGenerator gen = this
                .createDefaultGlossaryGen("junit\njava unit testing library\n\n"
                        + "bacon\ndelectable, amazing morning consumable\n\n"
                        + "glossarygenerator\nglossary generator outputting set of "
                        + "index and entry HTML pages given a valid input file\n\n"
                        + "arp\naddress resolution protocol; OSI layer 3 protocol to "
                        + "locate/link MAC to local IP address\n");

        gen.extractEntries();

        Map<String, String> resExpected = new Map1L<>();
        resExpected.add("junit", "java unit testing library");
        resExpected.add("bacon", "delectable, amazing morning consumable");
        resExpected.add("glossarygenerator",
                "glossary generator outputting set of index and entry HTML pages "
                        + "given a valid input file");
        resExpected.add("arp",
                "address resolution protocol; OSI layer 3 protocol to locate/link "
                        + "MAC to local IP address");

        assertEquals(resExpected, gen.getEntries());
    }

    /**
     * Tests extractEntries with entries that are linked but have no line breaks
     * in definitions.
     */
    @Test
    public void extractEntriesTestLinkedNoLineBreaks() {
        GlossaryGenerator gen = this
                .createDefaultGlossaryGen("junit\njava unit testing library\n\n"
                        + "java\nmorning caffeinated beverage; also OOP language\n\n"
                        + "OOP\n[sic] cry of anguish; also 'Object Oriented "
                        + "Programming'\n\n"
                        + "morning\ntime period of day wherein the sun is rising; "
                        + "see also dawn\n");

        gen.extractEntries();

        Map<String, String> resExpected = new Map1L<>();
        resExpected.add("junit", "java unit testing library");
        resExpected.add("java",
                "morning caffeinated beverage; also OOP language");
        resExpected.add("OOP",
                "[sic] cry of anguish; also 'Object Oriented Programming'");
        resExpected.add("morning",
                "time period of day wherein the sun is rising; see also dawn");

        assertEquals(resExpected, gen.getEntries());
    }

    /**
     * Tests extractEntries with entries that are both linked and have line
     * breaks in definition(s) (uses sample terms.txt).
     */
    @Test
    public void extractEntriesTestLinkedAndLineBreaks() {
        GlossaryGenerator gen = new GlossaryGenerator("data/terms.txt", "data");
        gen.extractEntries();

        Map<String, String> resExpected = new Map1L<>();
        resExpected.add("meaning",
                "something that one wishes to convey, especially by language");
        resExpected.add("term", "a word whose definition is in a glossary");
        resExpected.add("word",
                "a string of characters in a language, which has at least one character");
        resExpected.add("definition",
                "a sequence of words that gives meaning to a term");
        resExpected.add("glossary",
                "a list of difficult or specialized terms, with their definitions,"
                        + " usually near the end of a book");
        resExpected.add("language",
                "a set of strings of characters, each of which has meaning");
        resExpected.add("book", "a printed or written literary work");

        assertEquals(resExpected, gen.getEntries());
    }

    /**
     * Tests parseEntry with "entries" that are unlinked (effectively just using
     * one entry).
     */
    @Test
    public void parseEntryTestNoLink() {
        GlossaryGenerator gen = this.createDefaultGlossaryGen("");
        gen.parseEntry("junit", "java unit testing library");

        String resExpected = "<!DOCTYPE html>\n" + "<html lang='en'>\n"
                + "<head>\n" + "<meta charset='UTF-8'>\n"
                + "<title>junit</title>\n" + "<style>\n"
                + GlossaryGenerator.SHARED_DEFAULT_STYLE
                + GlossaryGenerator.TERM_PAGE_DEFAULT_STYLE + "</style>\n"
                + "</head>\n" + "\n" + "<body>\n" + "<h2>junit</h2>\n"
                + "<hr>\n" + "<p>\n" + "java unit testing library </p>\n"
                + "<footer>\n" + "<a href='index.html'>→ Return to index</a>\n"
                + "</footer>\n" + "\n" + "</body>\n" + "</html>\n";

        StringBuilder resActual = new StringBuilder();

        SimpleReader sr = new SimpleReader1L("data/junit.html");
        while (!sr.atEOS()) {
            resActual.append(sr.nextLine()).append("\n");
        }
        sr.close();

        assertEquals(resExpected, resActual.toString());
    }

    /**
     * Tests parseEntry with entries containing "links" (definitions contain a
     * different term verbatim).
     */
    @Test
    public void parseEntryTestLinked() {
        GlossaryGenerator gen = this.createDefaultGlossaryGen("");
        Map<String, String> entries = gen.getEntries();

        entries.add("junit", "java unit testing library");
        entries.add("java", "morning caffeinated beverage; also OOP language");
        entries.add("library", "location where visual media is stored");

        gen.parseAvailableEntries();

        String resExpected = "<!DOCTYPE html>\n" + "<html lang='en'>\n"
                + "<head>\n" + "<meta charset='UTF-8'>\n"
                + "<title>junit</title>\n" + "<style>\n"
                + GlossaryGenerator.SHARED_DEFAULT_STYLE
                + GlossaryGenerator.TERM_PAGE_DEFAULT_STYLE + "</style>\n"
                + "</head>\n" + "\n" + "<body>\n" + "<h2>junit</h2>\n"
                + "<hr>\n" + "<p>\n"
                + "<a href='java.html'>java</a> unit testing "
                + "<a href='library.html'>library</a> </p>\n" + "<footer>\n"
                + "<a href='index.html'>→ Return to index</a>\n" + "</footer>\n"
                + "\n" + "</body>\n" + "</html>\n";

        StringBuilder resActual = new StringBuilder();

        SimpleReader sr = new SimpleReader1L("data/junit.html");
        while (!sr.atEOS()) {
            resActual.append(sr.nextLine()).append("\n");
        }
        sr.close();

        assertEquals(resExpected, resActual.toString());
    }

    /**
     * Tests generateIndexPage with a single entry.
     */
    @Test
    public void generateIndexPageTestSingle() {
        GlossaryGenerator gen = this.createDefaultGlossaryGen("");
        gen.getEntries().add("junit", "java unit testing library");
        gen.parseAvailableEntries();
        gen.generateIndexPage();

        String resExpected = "<!DOCTYPE html>\n" + "<html lang='en'>\n"
                + "<head>\n" + "<meta charset='UTF-8'>\n"
                + "<title>Glossary</title>\n" + "<style>\n"
                + GlossaryGenerator.SHARED_DEFAULT_STYLE
                + GlossaryGenerator.INDEX_PAGE_DEFAULT_STYLE + "</style>\n"
                + "</head>\n" + "\n" + "<body>\n" + "<h2>Glossary</h2>\n"
                + "<hr>\n" + "<ul>\n"
                + "<li><a href='junit.html'>junit</a></li>\n" + "</ul>\n"
                + "</body>\n" + "</html>\n";

        StringBuilder resActual = new StringBuilder();

        SimpleReader sr = new SimpleReader1L("data/index.html");
        while (!sr.atEOS()) {
            resActual.append(sr.nextLine()).append("\n");
        }
        sr.close();

        assertEquals(resExpected, resActual.toString());
    }

    /**
     * Tests generateIndexPage with several entries, in part to validate
     * alphabetical natural ordering.
     */
    @Test
    public void generateIndexPageTestSeveral() {
        GlossaryGenerator gen = this.createDefaultGlossaryGen("");
        Map<String, String> entries = gen.getEntries();

        entries.add("junit", "java unit testing library");
        entries.add("java", "morning caffeinated beverage; also OOP language");
        entries.add("library", "location where visual media is stored");

        gen.parseAvailableEntries();
        gen.generateIndexPage();

        String resExpected = "<!DOCTYPE html>\n" + "<html lang='en'>\n"
                + "<head>\n" + "<meta charset='UTF-8'>\n"
                + "<title>Glossary</title>\n" + "<style>\n"
                + ":root { --palette-def: #6633F59F; --palette-active: #6633F5; }\n"
                + "body { font-family: monospace; }\n"
                + "a { color: var(--palette-def); transition: all 0.15s; }\n"
                + "a:hover { color: var(--palette-active); }\n"
                + "li { transition: all 0.15s; padding: 3px; list-style-type: none; }\n"
                + "li:hover { font-size: 18px }\n" + "</style>\n" + "</head>\n"
                + "\n" + "<body>\n" + "<h2>Glossary</h2>\n" + "<hr>\n"
                + "<ul>\n" + "<li><a href='java.html'>java</a></li>\n"
                + "<li><a href='junit.html'>junit</a></li>\n"
                + "<li><a href='library.html'>library</a></li>\n" + "</ul>\n"
                + "</body>\n" + "</html>\n";

        StringBuilder resActual = new StringBuilder();

        SimpleReader sr = new SimpleReader1L("data/index.html");
        while (!sr.atEOS()) {
            resActual.append(sr.nextLine()).append("\n");
        }
        sr.close();

        assertEquals(resExpected, resActual.toString());
    }

    /**
     * Tests writeHTMLFile with standard styling and body content (non-empty,
     * valid).
     */
    @Test
    public void writeHTMLFileTestStd() {
        GlossaryGenerator.writeHTMLFile("data/WriteTest.html", "writetest",
                "body { background-color: #DDDDDD }", "<h2>testing!</h2>");
        String resExpected = "<!DOCTYPE html>\n" + "<html lang='en'>\n"
                + "<head>\n" + "<meta charset='UTF-8'>\n"
                + "<title>writetest</title>\n" + "<style>\n"
                + "body { background-color: #DDDDDD }</style>\n" + "</head>\n"
                + "\n" + "<body>\n" + "<h2>testing!</h2>\n" + "</body>\n"
                + "</html>\n";

        StringBuilder resActual = new StringBuilder();

        SimpleReader sr = new SimpleReader1L("data/WriteTest.html");
        while (!sr.atEOS()) {
            resActual.append(sr.nextLine()).append("\n");
        }
        sr.close();

        assertEquals(resExpected, resActual.toString());
    }

    /**
     * Tests writeHTMLFile with empty styling and body content.
     */
    @Test
    public void writeHTMLFileTestEmpty() {
        GlossaryGenerator.writeHTMLFile("data/WriteTest.html", "writetest", "",
                "");
        String resExpected = "<!DOCTYPE html>\n" + "<html lang='en'>\n"
                + "<head>\n" + "<meta charset='UTF-8'>\n"
                + "<title>writetest</title>\n" + "<style>\n" + "</style>\n"
                + "</head>\n" + "\n" + "<body>\n" + "\n" + "</body>\n"
                + "</html>\n";

        StringBuilder resActual = new StringBuilder();

        SimpleReader sr = new SimpleReader1L("data/WriteTest.html");
        while (!sr.atEOS()) {
            resActual.append(sr.nextLine()).append("\n");
        }
        sr.close();

        assertEquals(resExpected, resActual.toString());
    }

    /**
     * Tests getSortedStrSet with a single element.
     */
    @Test
    public void getSortedStrSetTestSingle() {
        Set<String> set = new Set1L<>();
        Set<String> setCpy = set.newInstance();
        set.add("junit");
        setCpy.add("junit");

        Sequence<String> resExpected = new Sequence1L<>();
        resExpected.add(0, "junit");

        assertEquals(resExpected, GlossaryGenerator.getSortedStrSet(set));
        assertEquals(set, setCpy);
    }

    /**
     * Tests getSortedStrSet with several elements, in part to validate
     * alphabetical natural ordering.
     */
    @Test
    public void getSortedStrSetTestSeveral() {
        Set<String> set = new Set1L<>();
        Set<String> setCpy = set.newInstance();
        set.add("junit");
        set.add("java");
        set.add("rocketjump");
        set.add("65535");

        setCpy.add("junit");
        setCpy.add("java");
        setCpy.add("rocketjump");
        setCpy.add("65535");

        Sequence<String> resExpected = new Sequence1L<>();
        resExpected.add(0, "rocketjump");
        resExpected.add(0, "junit");
        resExpected.add(0, "java");
        resExpected.add(0, "65535");

        assertEquals(resExpected, GlossaryGenerator.getSortedStrSet(set));
        assertEquals(set, setCpy);
    }

    /**
     * Tests getKeySet with an empty string-string map.
     */
    @Test
    public void getKeySetTestStringStringEmpty() {
        Map<String, String> map = new Map1L<>();
        Map<String, String> mapCpy = map.newInstance();

        Set<String> resExpected = GlossaryGenerator.getKeySet(map);

        assertEquals(resExpected, GlossaryGenerator.getKeySet(map));
        assertEquals(map, mapCpy);
    }

    /**
     * Tests getKeySet with a filled string-string map.
     */
    @Test
    public void getKeySetTestStringStringFilled() {
        Map<String, String> map = new Map1L<>();
        Map<String, String> mapCpy = map.newInstance();
        map.add("int", "65535");
        map.add("byte", "8");

        mapCpy.add("int", "65535");
        mapCpy.add("byte", "8");

        Set<String> resExpected = new Set1L<>();
        resExpected.add("int");
        resExpected.add("byte");

        assertEquals(resExpected, GlossaryGenerator.getKeySet(map));
        assertEquals(map, mapCpy);
    }

    /**
     * Tests getKeySet with a filled NaturalNumber-Queue map.
     */
    @Test
    public void getKeySetTestNNQueueFilled() {
        Map<NaturalNumber, Queue<Double>> map = new Map1L<>();
        Map<NaturalNumber, Queue<Double>> mapCpy = map.newInstance();

        NaturalNumber n1 = new NaturalNumber1L(1);
        NaturalNumber n2 = new NaturalNumber1L(2);
        Queue<Double> q1 = new Queue1L<>();
        Queue<Double> q2 = new Queue1L<>();
        q2.enqueue(2.0);

        map.add(n1, q1);
        map.add(n2, q2);

        mapCpy.add(n1, q1);
        mapCpy.add(n2, q2);

        Set<NaturalNumber> resExpected = new Set1L<>();
        resExpected.add(n1);
        resExpected.add(n2);

        assertEquals(resExpected, GlossaryGenerator.getKeySet(map));
        assertEquals(map, mapCpy);
    }
}
