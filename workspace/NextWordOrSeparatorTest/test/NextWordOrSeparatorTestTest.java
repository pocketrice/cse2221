import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;

public class NextWordOrSeparatorTestTest {

    @Test
    public void generateElementsTestAlphaNoDupl() {
        String str = "abc";
        Set<Character> csetCand = new Set1L<>();
        Set<Character> csetExp = new Set1L<>();
        csetExp.add('a');
        csetExp.add('b');
        csetExp.add('c');

        NextWordOrSeparatorTest.generateElements(str, csetCand);

        assertTrue(csetCand.equals(csetExp));
    }

    @Test
    public void generateElementsTestAlphaNumNoDupl() {
        String str = "abc1053od";
        Set<Character> csetCand = new Set1L<>();
        Set<Character> csetExp = new Set1L<>();
        csetExp.add('a');
        csetExp.add('b');
        csetExp.add('c');
        csetExp.add('1');
        csetExp.add('0');
        csetExp.add('5');
        csetExp.add('3');
        csetExp.add('o');
        csetExp.add('d');

        NextWordOrSeparatorTest.generateElements(str, csetCand);

        assertTrue(csetCand.equals(csetExp));
    }

    @Test
    public void generateElementsTestAlphaNumAsciiNoDupl() {
        String str = "abc103ƒçß˚øª#[";
        Set<Character> csetCand = new Set1L<>();
        Set<Character> csetExp = new Set1L<>();
        csetExp.add('a');
        csetExp.add('b');
        csetExp.add('c');
        csetExp.add('1');
        csetExp.add('0');
        csetExp.add('3');
        csetExp.add('ƒ');
        csetExp.add('ç');
        csetExp.add('ß');
        csetExp.add('˚');
        csetExp.add('ø');
        csetExp.add('ª');
        csetExp.add('#');
        csetExp.add('[');

        NextWordOrSeparatorTest.generateElements(str, csetCand);

        assertTrue(csetCand.equals(csetExp));
    }

    @Test
    public void generateElementsTestAlphaDupl() {
        String str = "abcabcbbcabc";
        Set<Character> csetCand = new Set1L<>();
        Set<Character> csetExp = new Set1L<>();
        csetExp.add('a');
        csetExp.add('b');
        csetExp.add('c');

        NextWordOrSeparatorTest.generateElements(str, csetCand);

        assertTrue(csetCand.equals(csetExp));
    }

    @Test
    public void generateElementsTestAlphaNumAsciiDupl() {
        String str = "abbcac1003ƒçß˚øßª##[";
        Set<Character> csetCand = new Set1L<>();
        Set<Character> csetExp = new Set1L<>();
        csetExp.add('a');
        csetExp.add('b');
        csetExp.add('c');
        csetExp.add('1');
        csetExp.add('0');
        csetExp.add('3');
        csetExp.add('ƒ');
        csetExp.add('ç');
        csetExp.add('ß');
        csetExp.add('˚');
        csetExp.add('ø');
        csetExp.add('ª');
        csetExp.add('#');
        csetExp.add('[');

        NextWordOrSeparatorTest.generateElements(str, csetCand);

        assertTrue(csetCand.equals(csetExp));
    }

    @Test
    public void nextWordOrSeparatorTestSingleWord() {
        String str = "test130f1";
        String resExp = "test130f1";
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add(',');
        separators.add('\t');

        String resCand = NextWordOrSeparatorTest.nextWordOrSeparator(str, 0,
                separators);

        assertTrue(resExp.equals(resCand));
    }

    @Test
    public void nextWordOrSeparatorTestSingleSep() {
        String str = "   ,  , , ";
        String resExp = "   ,  , , ";

        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add(',');
        separators.add('\t');

        int pos = 0;

        do {
            String resWord = NextWordOrSeparatorTest.nextWordOrSeparator(str,
                    pos, separators);
            pos += resWord.length();
            resCand.addRightFront(resWord);

        } while (pos < str.length());

        assertTrue(resExp.equals(resCand));
    }

    @Test
    public void nextWordOrSeparatorTestSeveralWords() {
        String str = "making bacon";
        String[] resExp = new String[] { "making", " ", "bacon" };
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add(',');
        separators.add('\t');

        String resCand = NextWordOrSeparatorTest.nextWordOrSeparator(str, 0,
                separators);

        assertTrue(resExp.equals(resCand));
    }
}
