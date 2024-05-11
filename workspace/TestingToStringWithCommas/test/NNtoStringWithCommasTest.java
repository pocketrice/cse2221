import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.naturalnumber.NaturalNumber2;

public final class NNtoStringWithCommasTest {

    /**
     * Calls the method under test.
     *
     * @param n
     *            the number to pass to the method under test
     * @return the {@code String} returned by the method under test
     * @ensures <pre>
     * redirectToMethodUnderTest = [String returned by the method under test]
     * </pre>
     */
    private static String redirectToMethodUnderTest(NaturalNumber n) {
        return NNtoStringWithCommas2.toStringWithCommas(n);
    }

    @Test
    public void toStringWithCommasTest1() {
        NaturalNumber n = new NaturalNumber2(0);
        String expected = "0";
        String output = redirectToMethodUnderTest(n);

        assertEquals(expected, output); // TA COMMENT: USE ASSERTEQUALS!!
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
        String output = redirectToMethodUnderTest(n);

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
        String output = redirectToMethodUnderTest(n);

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
        String output = redirectToMethodUnderTest(n);

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
        String output = redirectToMethodUnderTest(n);

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
        String output = redirectToMethodUnderTest(n);

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
        String output = redirectToMethodUnderTest(n);

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
        String output = redirectToMethodUnderTest(n);

        assertTrue(expected.equals(output));
    }

//    /**
//     * Test toStringWithCommas w/ input null, representing a challenging case
//     * with no clear correct output (@requires does not prevent this).
//     */
//    @Test
//    public void toStringWithCommasTest9() {
//        NaturalNumber n = null;
//        String expected = "null";
//        String output = redirectToMethodUnderTest(n);
//
//        assertTrue(expected.equals(output));
//    }

    /**
     * Test toStringWithCommas w/ input 9999999999, representing a challenging
     * case with an abnormally large number.
     */
    @Test
    public void toStringWithCommasTest10() {
        NaturalNumber n = new NaturalNumber2(String.valueOf(9999999999L));
        String expected = "9,999,999,999";
        String output = redirectToMethodUnderTest(n);

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
        String output = redirectToMethodUnderTest(n);

        assertTrue(expected.equals(output));
    }

    @Test
    public void toStringWithCommasTest12() {
        NaturalNumber n1 = new NaturalNumber2(500);
        String exp1 = "500";
        String exp2 = "1,600";
        String op1 = redirectToMethodUnderTest(n1);
        n1.add(new NaturalNumber2(1100));
        String op2 = redirectToMethodUnderTest(n1);

        assertTrue(exp1.equals(op1));
        assertTrue(exp2.equals(op2));
    }

    @Test
    public void toStringWithCommasTest13() {
        NaturalNumber n1 = new NaturalNumber2(1600);
        NaturalNumber n2 = new NaturalNumber2(1600);
        String exp1 = "1,600";

        String op1 = redirectToMethodUnderTest(n1);

        assertTrue(exp1.equals(op1));
        assertTrue(n1.equals(n2));
    }
}
