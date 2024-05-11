import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code NaturalNumber}.
 *
 * @author Lucas Xie
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        final NaturalNumber nnIntMax = new NaturalNumber2(Integer.MAX_VALUE);
        final NaturalNumber nnTwo = new NaturalNumber2(2);
        final NaturalNumber nnZero = new NaturalNumber2(0);

        NaturalNumber num1, num2;

        if (exp.child(0).label().equals("number")) {
            num1 = new NaturalNumber2(exp.child(0).attributeValue("value"));
        } else {
            num1 = evaluate(exp.child(0));
        }

        if (exp.child(1).label().equals("number")) {
            num2 = new NaturalNumber2(exp.child(1).attributeValue("value"));
        } else {
            num2 = evaluate(exp.child(1));
        }

        NaturalNumber res = nnZero.newInstance();
        String operator = exp.label();

        if (operator.equals("plus")) {
            res.add(num1);
            res.add(num2);
        } else if (operator.equals("minus")) {
            if (num1.compareTo(num2) < 0) {
                Reporter.fatalErrorToConsole("Cannot subtract if a < b!");
            }
            res.add(num1);
            res.subtract(num2);
        } else if (operator.equals("times")) {
            res.add(num1);
            res.multiply(num2);
        } else if (operator.equals("divide")) {
            if (num2.compareTo(nnZero) <= 0) {
                Reporter.fatalErrorToConsole("Cannot divide if b <= 0!");
            }
            res.add(num1);
            res.divide(num2);
        } else if (operator.equals("mod")) {
            if (num2.compareTo(nnZero) <= 0) {
                Reporter.fatalErrorToConsole("Cannot mod if b <= 0!");
            }
            res.add(num1.divide(num2));
        } else if (operator.equals("power")) {
            if (num2.compareTo(nnIntMax) >= 0) {
                Reporter.fatalErrorToConsole(
                        "Cannot power if b >= integer max value!");
            }
            num1.power(num2.toInt());
            res.add(num1);
        } else if (operator.equals("root")) {
            if (num2.compareTo(nnIntMax) >= 0 || num2.compareTo(nnTwo) < 0) {
                Reporter.fatalErrorToConsole(
                        "Cannot root if b not within range [2, Integer.MAX_VALUE]!");
            }
            num1.root(num2.toInt());
            res.add(num1);
        }

        return res;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
