import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Test fixture for testing CryptoUtilities implemented methods.
 */
public class CryptoUtilitiesTest {

    /**
     * Tests reduceToGCD with large non-coprime values.
     */
    @Test
    public void reduceToGCDTestLargeNonCoprime() {
        NaturalNumber n1 = new NaturalNumber2(60);
        NaturalNumber n2 = new NaturalNumber2(30);

        CryptoUtilities.reduceToGCD(n1, n2);

        assertEquals(n1, new NaturalNumber2(30));
        assertEquals(n2, new NaturalNumber2(0));
    }

    /**
     * Tests reduceToGCD with large non-coprime values.
     */
    @Test
    public void reduceToGCDTestLargeNonCoprime2() {
        NaturalNumber n1 = new NaturalNumber2(70);
        NaturalNumber n2 = new NaturalNumber2(30);

        CryptoUtilities.reduceToGCD(n1, n2);

        assertEquals(n1, new NaturalNumber2(10));
        assertEquals(n2, new NaturalNumber2(0));
    }

    /**
     * Tests reduceToGCD with large non-coprime values.
     */
    @Test
    public void reduceToGCDTestLargeNonCoprime3() {
        NaturalNumber n1 = new NaturalNumber2(75);
        NaturalNumber n2 = new NaturalNumber2(150);

        CryptoUtilities.reduceToGCD(n1, n2);

        assertEquals(n1, new NaturalNumber2(75));
        assertEquals(n2, new NaturalNumber2(0));
    }

    /**
     * Tests reduceToGCD with same large non-coprime pair in reversed order.
     */
    @Test
    public void reduceToGCDTestReversedLargeNonCoprime3() {
        NaturalNumber n1 = new NaturalNumber2(150);
        NaturalNumber n2 = new NaturalNumber2(75);

        CryptoUtilities.reduceToGCD(n1, n2);

        assertEquals(n1, new NaturalNumber2(75));
        assertEquals(n2, new NaturalNumber2(0));
    }

    /**
     * Tests reduceToGCD with coprime values.
     */
    @Test
    public void reduceToGCDTestCoprime() {
        NaturalNumber n1 = new NaturalNumber2(4);
        NaturalNumber n2 = new NaturalNumber2(7);

        CryptoUtilities.reduceToGCD(n1, n2);

        assertEquals(n1, new NaturalNumber2(1));
        assertEquals(n2, new NaturalNumber2(0));
    }

    /**
     * Tests powerMod with routine values.
     */
    @Test
    public void powerModTestStd1() {
        NaturalNumber n1 = new NaturalNumber2(3);
        NaturalNumber n2 = new NaturalNumber2(5);
        NaturalNumber m = new NaturalNumber2(5);

        CryptoUtilities.powerMod(n1, n2, m);

        assertEquals(n1, new NaturalNumber2(3));
    }

    /**
     * Tests powerMod with zero-th power.
     */
    @Test
    public void powerModTestZeroPower() {
        NaturalNumber n1 = new NaturalNumber2(100);
        NaturalNumber n2 = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(5);

        CryptoUtilities.powerMod(n1, n2, m);

        assertEquals(n1, new NaturalNumber2(1));
    }

    /**
     * Tests powerMod with n = 0.
     */
    @Test
    public void powerModTestZeroN() {
        NaturalNumber n1 = new NaturalNumber2(0);
        NaturalNumber n2 = new NaturalNumber2(5);
        NaturalNumber m = new NaturalNumber2(2);

        CryptoUtilities.powerMod(n1, n2, m);

        assertEquals(n1, new NaturalNumber2(0));
    }

    /**
     * Tests powerMod with routine values.
     */
    @Test
    public void powerModTestStd2() {
        NaturalNumber n1 = new NaturalNumber2(6);
        NaturalNumber n2 = new NaturalNumber2(5);
        NaturalNumber m = new NaturalNumber2(11);

        CryptoUtilities.powerMod(n1, n2, m);

        assertEquals(n1, new NaturalNumber2(10));
    }

    /**
     * Tests isEven with routine odd value.
     */
    @Test
    public void isEvenTestStdOdd() {
        NaturalNumber n = new NaturalNumber2(17);

        assertFalse(CryptoUtilities.isEven(n));
    }

    /**
     * Tests isEven with routine even value.
     */
    @Test
    public void isEvenTestStdEven() {
        NaturalNumber n = new NaturalNumber2(4);

        assertTrue(CryptoUtilities.isEven(n));
    }

    /**
     * Tests isEven with zero.
     */
    @Test
    public void isEvenTestZero() {
        NaturalNumber n = new NaturalNumber2(0);

        assertTrue(CryptoUtilities.isEven(n));
    }

    /**
     * Tests isEven with large routine odd value.
     */
    @Test
    public void isEvenTestLargeOdd() {
        NaturalNumber n = new NaturalNumber2(153);

        assertFalse(CryptoUtilities.isEven(n));
    }

    /**
     * Tests isWitnessToComposite with routine values (composite).
     */
    @Test
    public void isWitnessToCompositeTestStdComp1() {
        NaturalNumber w = new NaturalNumber2(5);
        NaturalNumber n = new NaturalNumber2(21);

        assertTrue(CryptoUtilities.isWitnessToCompositeness(w, n));
    }

    /**
     * Tests isWitnessToComposite with routine values (composite).
     */
    @Test
    public void isWitnessToCompositeTestStdComp2() {
        NaturalNumber w = new NaturalNumber2(2);
        NaturalNumber n = new NaturalNumber2(15);

        assertTrue(CryptoUtilities.isWitnessToCompositeness(w, n));
    }

    /**
     * Tests isWitnessToComposite with routine values (prime).
     */
    @Test
    public void isWitnessToCompositeTestStdPrime() {
        NaturalNumber w = new NaturalNumber2(3);
        NaturalNumber n = new NaturalNumber2(7);

        assertFalse(CryptoUtilities.isWitnessToCompositeness(w, n));
    }

    /**
     * Tests isPrime2 with n ≤ 4, which is a manually-set value.
     */
    @Test
    public void isPrime2TestSmallManualPrime() {
        NaturalNumber n = new NaturalNumber2(3);

        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests isPrime2 with n = 4, which is the largest manually-set value.
     */
    @Test
    public void isPrime2TestManualEdge() {
        NaturalNumber n = new NaturalNumber2(4);

        assertFalse(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests isPrime2 with n ≤ 4, which is a manually-set value.
     */
    @Test
    public void isPrime2TestSmallManualPrime2() {
        NaturalNumber n = new NaturalNumber2(2);

        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests isPrime2 with a composite value that satisfies n > 4 (not manual).
     */
    @Test
    public void isPrime2Test4SmallComp() {
        NaturalNumber n = new NaturalNumber2(10);

        assertFalse(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests isPrime2 with a large prime value that satisfies n > 4 (not
     * manual).
     */
    @Test
    public void isPrime2TestLargePrime() {
        NaturalNumber n = new NaturalNumber2(257);

        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests isPrime2 with a large composite value that satisfies n > 4 (not
     * manual).
     */
    @Test
    public void isPrime2TestLargeComp() {
        NaturalNumber n = new NaturalNumber2(200);

        assertFalse(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests isPrime2 with a small prime value that satisfies n > 4 (not
     * manual).
     */
    @Test
    public void isPrime2TestSmallPrime() {
        NaturalNumber n = new NaturalNumber2(5);

        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests generateNextLikelyPrime with n = 4 (largest manually-set value).
     */
    @Test
    public void generateNextLikelyPrimeTestManualEdge() {
        NaturalNumber n = new NaturalNumber2(4);
        CryptoUtilities.generateNextLikelyPrime(n);

        assertEquals(n, new NaturalNumber2(5));
    }

    /**
     * Tests generateNextLikelyPrime with a small prime value satisfying n > 4
     * (not manual).
     */
    @Test
    public void generateNextLikelyPrimeTestSmallPrime() {
        NaturalNumber n = new NaturalNumber2(7);
        CryptoUtilities.generateNextLikelyPrime(n);

        assertEquals(n, new NaturalNumber2(7));
    }

    /**
     * Tests generateNextLikelyPrime with a large composite value satisfying n >
     * 4 (not manual).
     */
    @Test
    public void generateNextLikelyPrimeTestLargeComp() {
        NaturalNumber n = new NaturalNumber2(90);
        CryptoUtilities.generateNextLikelyPrime(n);

        assertEquals(n, new NaturalNumber2(97));
    }

    /**
     * Tests isPrime3 with a small prime value satisfying n ≤ 4 (manual).
     */
    @Test
    public void isPrime3TestSmallManualPrime() {
        NaturalNumber n = new NaturalNumber2(3);
        NaturalNumber k = new NaturalNumber2(200);

        assertTrue(CryptoUtilities.isPrime3(n, k));
    }

    /**
     * Tests isPrime3 with a small prime value satisfying n = 4 (largest
     * manually-set value).
     */
    @Test
    public void isPrime3TestManualEdge() {
        NaturalNumber n = new NaturalNumber2(4);
        NaturalNumber k = new NaturalNumber2(200);

        assertFalse(CryptoUtilities.isPrime3(n, k));
    }

    /**
     * Tests isPrime3 with a small composite value satisfying n > 4 (not
     * manual).
     */
    @Test
    public void isPrime3TestSmallComp() {
        NaturalNumber n = new NaturalNumber2(10);
        NaturalNumber k = new NaturalNumber2(200);

        assertFalse(CryptoUtilities.isPrime3(n, k));
    }
}
