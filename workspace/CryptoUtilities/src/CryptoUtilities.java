import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 *
 * @author Lucas Xie
 *
 */
public final class CryptoUtilities {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities() {
    }

    /**
     * Useful constant, not a magic number: 3.
     */
    private static final int THREE = 3;

    /**
     * Constant representing NaturalNumber with value 4, for use with composite
     * witness RNG.
     */
    private static final NaturalNumber FOUR = new NaturalNumber2(4);

    /**
     * Constant representing NaturalNumber with value 2, for use with testing
     * evenness.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2);

    /**
     * Constant representing NaturalNumber with value 1, for use with p = 0
     * powers.
     */
    private static final NaturalNumber ONE = new NaturalNumber2(1);

    /**
     * Constant representing NaturalNumber with value 0.
     */
    private static final NaturalNumber ZERO = new NaturalNumber2(0);

    /**
     * Pseudo-random number generator.
     */
    private static final Random GENERATOR = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     *
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires n > 0
     * @ensures <pre>
     * randomNumber = [a random number uniformly distributed in [0, n]]
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        final int base = 10;
        NaturalNumber result;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d]
             */
            int x = (int) ((d + 1) * GENERATOR.nextDouble());
            result = new NaturalNumber2(x);
            n.multiplyBy10(d);
        } else {
            /*
             * Incoming n has more than one digit, so generate a random number
             * (NaturalNumber) uniformly distributed in [0, n], and another
             * (int) uniformly distributed in [0, 9] (i.e., a random digit)
             */
            result = randomNumber(n);
            int lastDigit = (int) (base * GENERATOR.nextDouble());
            result.multiplyBy10(lastDigit);
            n.multiplyBy10(d);
            if (result.compareTo(n) > 0) {
                /*
                 * In this case, we need to try again because generated number
                 * is greater than n; the recursive call's argument is not
                 * "smaller" than the incoming value of n, but this recursive
                 * call has no more than a 90% chance of being made (and for
                 * large n, far less than that), so the probability of
                 * termination is 1
                 */
                result = randomNumber(n);
            }
        }
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {

        /*
         * Use Euclid's algorithm; in pseudocode: if m = 0 then GCD(n, m) = n
         * else GCD(n, m) = GCD(m, n mod m)
         */

        if (!m.isZero()) {
            NaturalNumber rem = n.divide(m);
            reduceToGCD(m, rem);
            n.transferFrom(m);
        }
    }

    /**
     * Reports whether n is even.
     *
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures isEven = (n mod 2 = 0)
     */
    public static boolean isEven(NaturalNumber n) {
        return (new NaturalNumber2(n).divide(TWO).isZero());
    }

    /**
     * Updates n to its p-th power modulo m.
     *
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires m > 1
     * @ensures n = #n ^ (p) mod m
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p,
            NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";

        /*
         * Use the fast-powering algorithm as previously discussed in class,
         * with the additional feature that every multiplication is followed
         * immediately by "reducing the result modulo m"
         */

        if (p.equals(ZERO)) {
            n.copyFrom(ONE);
        } else if (!p.equals(ONE)) {
            NaturalNumber pCopy = new NaturalNumber2(p);
            NaturalNumber nCopy = new NaturalNumber2(n);
            NaturalNumber nMod;

            if (isEven(p)) {
                pCopy.divide(TWO);

                n.multiply(new NaturalNumber2(n));
                nMod = new NaturalNumber2(n).divide(m);
                n.transferFrom(nMod);

                powerMod(n, pCopy, m);
            } else {
                pCopy.decrement();

                powerMod(n, pCopy, m);

                n.multiply(nCopy);
                nMod = new NaturalNumber2(n).divide(m);
                n.transferFrom(nMod);
            }
        }
    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     *
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires n > 2 and 1 < w < n - 1
     * @ensures <pre>
     * isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w,
            NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();

        NaturalNumber sqWModN = new NaturalNumber2(w);
        NaturalNumber wPowNDecModN = new NaturalNumber2(w);
        NaturalNumber nDec = new NaturalNumber2(n);
        nDec.decrement();

        powerMod(sqWModN, TWO, n);
        powerMod(wPowNDecModN, nDec, n);
        return sqWModN.compareTo(ONE) == 0 || wPowNDecModN.compareTo(ONE) != 0;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        boolean isPrime;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            /*
             * odd n >= 5: simply check whether 2 is a witness that n is
             * composite (which works surprisingly well :-)
             */
            isPrime = !isWitnessToCompositeness(new NaturalNumber2(2), n);
        }
        return isPrime;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        final int candidateCount = 50;

        /*
         * Use the ability to generate random numbers (provided by the
         * randomNumber method above) to generate several witness candidates --
         * say, 10 to 50 candidates -- guessing that n is prime only if none of
         * these candidates is a witness to n being composite (based on fact #3
         * as described in the project description); use the code for isPrime1
         * as a guide for how to do this, and pay attention to the requires
         * clause of isWitnessToCompositeness
         */

        boolean isPrime = true;

        /*
         * Manually set primeness for ≤ 4 since random number generation relies
         * on n > 4.
         */
        if (n.compareTo(FOUR) <= 0) {
            isPrime = !n.equals(FOUR);
        } else {
            NaturalNumber nDec4 = new NaturalNumber2(n);
            nDec4.subtract(FOUR);

            for (int i = 0; i < candidateCount; i++) {
                /*
                 * Generate random number within 1 < w < n-1; equivalent to 2 ≤
                 * w ≤ n-2; equivalent to 0 ≤ w-2 ≤ n-4.
                 */
                NaturalNumber w = randomNumber(nDec4);
                w.add(TWO);

                if (isWitnessToCompositeness(w, n) && isPrime) {
                    isPrime = false;
                }
            }
        }

        return isPrime;
    }

    /**
     * Reports whether n is a prime using Miller-Rabin algorithm with error
     * probability of at most 1/(4^k).
     *
     * @param n
     *            number to be checked
     * @param k
     *            number of rounds to perform
     * @return true means n is very likely prime with error of ≤ 1/(4^k); false
     *         means n is definitely composite
     * @requires n > 2
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error (≤ 1/(4^k))
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     *         </pre>
     */
    public static boolean isPrime3(NaturalNumber n, NaturalNumber k) {
        assert n.compareTo(TWO) > 0 : "Violation of: n > 2";

        boolean isPrime = true;

        if (n.compareTo(FOUR) <= 0) {
            isPrime = !n.equals(FOUR);
        } else {
            NaturalNumber nDec4 = new NaturalNumber2(n);
            nDec4.subtract(FOUR);

            /*
             * Calculate d and s vals based on n-1 = 2^s*d
             */
            NaturalNumber s = new NaturalNumber2(); // 0
            NaturalNumber d = new NaturalNumber2(n); // n-1
            d.decrement();

            while (new NaturalNumber2(d).divide(TWO).equals(ZERO)) {
                s.increment();
                NaturalNumber dMod = d.divide(TWO);
                d.transferFrom(dMod);
            }

            /*
             * Iterate k rounds checking different bases
             */
            for (NaturalNumber i = new NaturalNumber2(); i.compareTo(k) < 0; i
                    .increment()) {
                NaturalNumber a = randomNumber(nDec4);
                a.add(TWO);
                NaturalNumber x = new NaturalNumber2(a);
                NaturalNumber y = new NaturalNumber2();
                powerMod(x, d, n);

                /*
                 * Iterate s rounds checking compositeness(?)
                 */
                for (NaturalNumber j = new NaturalNumber2(); j
                        .compareTo(s) < 0; j.increment()) {
                    if (isWitnessToCompositeness(x, n) && isPrime) {
                        isPrime = false;
                    } else {
                        y = new NaturalNumber2(x);
                        powerMod(y, TWO, n);
                        x.transferFrom(y);
                    }
                }

                if (y.compareTo(ONE) != 0) {
                    isPrime = false;
                }

                /*
                 * If all composite checks passed implicitly prime
                 */
            }
        }

        return isPrime;
    }

    /**
     * Generates a likely prime number at least as large as some given number.
     *
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires n > 1
     * @ensures n >= #n and [n is very likely a prime number]
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use isPrime2 to check numbers, starting at n and increasing through
         * the odd numbers only (why?), until n is likely prime
         */

        while (!isPrime2(n)) {
            if (isEven(n)) {
                n.increment();
            } else {
                n.add(TWO);
            }
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */
        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println("  expected value = "
                + (double) testSamples / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */
        while (true) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                break;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
                }
            }
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
