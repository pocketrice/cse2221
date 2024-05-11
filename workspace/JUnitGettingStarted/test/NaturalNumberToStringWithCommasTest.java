import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.naturalnumber.NaturalNumber2;

/**
 * JUnit test fixture for static method toStringWithCommas.
 *
 * @author Lucas Xie
 */

public final class NaturalNumberToStringWithCommasTest {
    /**
     * Test toStringWithCommas w/ input 0, representing a boundary case
     * (NaturalNumber minimum value).
     */
    @Test
    public void toStringWithCommasTest1() {
        NaturalNumber n = new NaturalNumber2(0);
        String expected = "0";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input 999, representing a challenging case
     * (borders the constant used for determining comma placement).
     */
    @Test
    public void toStringWithCommasTest2() {
        NaturalNumber n = new NaturalNumber2(999);
        String expected = "999";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input 1000, representing a boundary case
     * (smallest value such that a comma should be inserted).
     */
    @Test
    public void toStringWithCommasTest3() {
        NaturalNumber n = new NaturalNumber2(1000);
        String expected = "1,000";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input 101, representing a routine case without
     * commas.
     */
    @Test
    public void toStringWithCommasTest4() {
        NaturalNumber n = new NaturalNumber2(101);
        String expected = "101";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input 540302, representing a routine case with
     * one comma and max "between-comma" digits.
     */
    @Test
    public void toStringWithCommasTest5() {
        NaturalNumber n = new NaturalNumber2(540302);
        String expected = "540,302";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input 1000000, representing a routine case
     * with several commas.
     */
    @Test
    public void toStringWithCommasTest6() {
        NaturalNumber n = new NaturalNumber2(1000000);
        String expected = "1,000,000";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input 23456, representing a routine case with
     * a comma but with a digit deficit.
     */
    @Test
    public void toStringWithCommasTest7() {
        NaturalNumber n = new NaturalNumber2(23456);
        String expected = "23,456";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input 75, representing a routine case with no
     * commas.
     */
    @Test
    public void toStringWithCommasTest8() {
        NaturalNumber n = new NaturalNumber2(75);
        String expected = "75";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input null, representing a challenging case
     * with no clear correct output (@requires does not prevent this).
     */
    @Test
    public void toStringWithCommasTest9() {
        NaturalNumber n = null;
        String expected = "null";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input 9999999999, representing a challenging
     * case with an abnormally large number.
     */
    @Test
    public void toStringWithCommasTest10() {
        NaturalNumber n = new NaturalNumber2(String.valueOf(9999999999L));
        String expected = "9,999,999,999";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }

    /**
     * Test toStringWithCommas w/ input 1530 of type NaturalNumber1L,
     * representing a routine case with a different implementation of
     * NaturalNumber.
     */
    @Test
    public void toStringWithCommasTest11() {
        NaturalNumber n = new NaturalNumber1L(1530);
        String expected = "1,530";
        String output = toStringWithCommas(n);

        assertTrue(expected.equals(output));
    }
}
