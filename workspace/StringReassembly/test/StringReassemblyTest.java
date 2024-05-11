import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Test fixture for testing StringReassembly implemented methods.
 */
public class StringReassemblyTest {
    /**
     * Tests combination with two alphabetic (a-zA-Z) overlapping single-words.
     */
    @Test
    public void combinationTestAlphaOverlapWord() {
        String resTest = StringReassembly.combination("test", "ester", 3);
        String resExpected = "tester";

        assertEquals(resTest, resExpected);
    }

    /**
     * Tests combination with two alphanumeric (a-zA-Z0-9) overlapping
     * single-words.
     */
    @Test
    public void combinationTestAlphanumericOverlapWord() {
        String resTest = StringReassembly.combination("orange", "rangeler", 5);
        String resExpected = "orangeler";

        assertEquals(resTest, resExpected);
    }

    /**
     * Tests combination with two alphanumeric (a-zA-Z0-9) overlapping
     * multi-words.
     */
    @Test
    public void combinationTestAlphanumericOverlapMultiWords() {
        String resTest = StringReassembly.combination("string the", "theory",
                3);
        String resExpected = "string theory";

        assertEquals(resTest, resExpected);
    }

    /**
     * Tests addToSetAvoidingSubstrings with a string that satisfies
     * precondition and is not a superstring of any in set.
     */
    @Test
    public void addToSetAvoidingSubstringsTestValidNotSubstr() {
        Set<String> resTest = new Set1L<>();
        Set<String> resExpected = resTest.newInstance();
        resTest.add("apple");
        resTest.add("orange");
        StringReassembly.addToSetAvoidingSubstrings(resTest, "banana");

        resExpected.add("apple");
        resExpected.add("orange");
        resExpected.add("banana");

        assertEquals(resTest, resExpected);
    }

    /**
     * Tests addToSetAvoidingSubstrings with a string that doesn't satisfy
     * precondition and is not a superstring of any in set.
     */
    @Test
    public void addToSetAvoidingSubstringsTestInvalidNotSubstr() {
        Set<String> resTest = new Set1L<>();
        Set<String> resExpected = resTest.newInstance();
        resTest.add("apple");
        resTest.add("orange");
        StringReassembly.addToSetAvoidingSubstrings(resTest, "app");

        resExpected.add("apple");
        resExpected.add("orange");

        assertEquals(resTest, resExpected);
    }

    /**
     * Tests addToSetAvoidingSubstrings with a string that satisfies
     * precondition and superstrings one item in set.
     */
    @Test
    public void addToSetAvoidingSubstringsTestValidSingleSubstr() {
        Set<String> resTest = new Set1L<>();
        Set<String> resExpected = resTest.newInstance();
        resTest.add("apple");
        resTest.add("orange");
        StringReassembly.addToSetAvoidingSubstrings(resTest, "oranger");

        resExpected.add("apple");
        resExpected.add("oranger");

        assertEquals(resTest, resExpected);
    }

    /**
     * Tests addToSetAvoidingSubstrings with a string that satisfies
     * precondition and superstrings >1 in set.
     */
    @Test
    public void addToSetAvoidingSubstringsTestValidMultipleSubstr() {
        Set<String> resTest = new Set1L<>();
        Set<String> resExpected = resTest.newInstance();
        resTest.add("apple");
        resTest.add("orange");
        resTest.add("oran");
        StringReassembly.addToSetAvoidingSubstrings(resTest, "appler");
        StringReassembly.addToSetAvoidingSubstrings(resTest, "oranger");

        resExpected.add("appler");
        resExpected.add("oranger");

        assertEquals(resTest, resExpected);
    }

    /**
     * Tests linesFromInput with sample standard fragmented input.
     */
    @Test
    public void linesFromInputTest() {
        SimpleWriter out = new SimpleWriter1L("src/cheer-8-2.txt");
        out.println("Bucks -- Beat");
        out.println("Go Bucks");
        out.println("Beat Mich");
        out.println("Michigan~");
        out.println("o Bucks -- B");

        SimpleReader in = new SimpleReader1L("src/cheer-8-2.txt");
        Set<String> resTest = StringReassembly.linesFromInput(in);
        Set<String> resExpected = resTest.newInstance();

        resExpected.add("Bucks -- Beat");
        resExpected.add("Go Bucks");
        resExpected.add("Beat Mich");
        resExpected.add("Michigan~");
        resExpected.add("o Bucks -- B");

        assertEquals(resTest, resExpected);

        out.close();
    }

    /**
     * Tests printWithLineSeparators with sample standard input.
     */
    @Test
    public void printWithLineSeparatorsTest() {
        SimpleWriter out = new SimpleWriter1L("src/test.txt");
        SimpleReader in = new SimpleReader1L("src/test.txt");
        String resExpected = "test\ntest2";

        StringReassembly.printWithLineSeparators("test~test2", out);

        StringBuilder resTest = new StringBuilder();

        while (!in.atEOS()) {
            resTest.append(in.nextLine());
            resTest.append('\n');
        }

        resTest.deleteCharAt(resTest.length() - 1);

        assertEquals(resExpected, resTest.toString());

        out.close();
        in.close();
    }
}
