import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Password validator based on OSU password requirements.
 *
 * @author Lucas Xie
 *
 */
public final class CheckPassword {
    /**
     * Minimum password length. Used for password validation.
     */
    static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CheckPassword() {
    }

    /**
     * Checks whether the given String satisfies the OSU criteria for a valid
     * password. Prints an appropriate message to the given output stream.
     *
     * @param passwordCandidate
     *            the String to check
     * @param out
     *            the output stream
     */
    private static void checkPassword(String passwordCandidate,
            SimpleWriter out) {
        boolean isValid = true;
        int invalidCriteriaCount = 0;

        if (passwordCandidate.length() < MIN_PASSWORD_LENGTH) {
            out.println("Warning: Password must be at least 8 characters.");
            isValid = false;
        }

        if (isValid && !containsUpperCaseLetter(passwordCandidate)) {
            out.println(
                    "Warning: Password must contain at least one uppercase letter.");
            invalidCriteriaCount++;
            if (invalidCriteriaCount >= 2) {
                isValid = false;
            }
        }

        if (isValid && !containsLowerCaseLetter(passwordCandidate)) {
            out.println(
                    "Warning: Password must contain at least one lowercase letter.");
            invalidCriteriaCount++;
            if (invalidCriteriaCount >= 2) {
                isValid = false;
            }
        }

        if (isValid && !containsDigit(passwordCandidate)) {
            out.println("Warning: Password must contain at least one digit.");
            invalidCriteriaCount++;
            if (invalidCriteriaCount >= 2) {
                isValid = false;
            }
        }

        if (isValid && !containsSpecialChar(passwordCandidate)) {
            out.println(
                    "Warning: Password must contain at least one special character.");
            invalidCriteriaCount++;
            if (invalidCriteriaCount >= 2) {
                isValid = false;
            }
        }

        if (isValid) {
            out.println("Valid password.");
        } else {
            String msg = ("Invalid password; did not meet either length or 3/4 criteria below:"
                    + "\n* At least 1 uppercase letter"
                    + "\n* At least 1 lowercase letter" + "\n* At least 1 digit"
                    + "\n* At least 1 special character");

            out.println(msg);
        }

    }

    // TA Note: prolly best to stick w/ what was taught; in this case it was supposed to teach you loop optimisation
    // (effectively loop through chars w/ while, and incrementer, terminate when found)

    /**
     * Checks if the given String contains an upper case letter.
     *
     * @param str
     *            the String to check
     * @return true if str contains an upper case letter, false otherwise
     */
    private static boolean containsUpperCaseLetter(String str) {
        return str.matches(".*[A-Z]+.*");
    }

    /**
     * Checks if the given String contains a lower case letter.
     *
     * @param str
     *            the String to check
     * @return true if str contains a lower case letter, false otherwise
     */
    private static boolean containsLowerCaseLetter(String str) {
        return str.matches(".*[a-z]+.*");
    }

    /**
     * Checks if the given String contains a digit.
     *
     * @param str
     *            the String to check
     * @return true if str contains a digit, false otherwise
     */
    private static boolean containsDigit(String str) {
        return str.matches(".*[0-9]+.*");
    }

    /**
     * Checks if the given String contains a special character.
     *
     * @param str
     *            the String to check
     * @return true if str contains a special character, false otherwise
     */
    private static boolean containsSpecialChar(String str) {
        return str.matches(".*[!@#$%^&*()_\\-\\+=\\{}\\[\\]:;,.?]+.*");
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
         * Repeatedly prompt user for passwords to check; close streams and
         * terminate when blank string received.
         */

        boolean isRunning = true;

        while (isRunning) {
            out.println("Input a password...  ");
            String candidate = in.nextLine();
            isRunning = !candidate.isBlank();

            if (isRunning) {
                checkPassword(candidate, out);
                out.print("\n\n\n");
            }
        }

        in.close();
        out.close();

        /*
         * while (true) { // TA Note: DON'T use System.exit(0). In fact, no
         * System.* at all. Plus, prolly violates "1-entrance, 1-exit"
         * philosophy out.println("Input a password...  "); String candidate =
         * in.nextLine(); if (candidate.isBlank()) { in.close(); out.close();
         * System.exit(0); }
         *
         * checkPassword(candidate, out); out.print("\n\n\n"); }
         */
    }

}
