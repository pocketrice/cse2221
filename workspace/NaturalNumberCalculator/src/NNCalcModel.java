import components.naturalnumber.NaturalNumber;

/**
 * Model interface.
 *
 * The Natural Number Calculator model consists of the top/bottom operands and
 * memory value. (which have only getter methods).
 *
 * @author Lucas Xie
 *
 * @mathmodel <pre>
 * type NNCalcModel is modeled by
 *   (top: NATURAL_NUMBER,
 *    bottom: NATURAL_NUMBER)
 * </pre>
 * @initially <pre>
 * ():
 *  ensures
 *   this = (0, 0)
 * </pre>
 */
public interface NNCalcModel {

    /**
     * Reports top operand.
     *
     * @return this.top
     * @aliases reference returned by {@code top}
     * @ensures top = this.top
     */
    NaturalNumber top();

    /**
     * Reports bottom operand.
     *
     * @return this.bottom
     * @aliases reference returned by {@code bottom}
     * @ensures bottom = this.bottom
     */
    NaturalNumber bottom();

    /**
     * Reports memory value. Part of 'Additional Activities'.
     *
     * @return this.mem
     * @aliases reference returned by {@code mem}
     * @ensures mem = this.mem
     */
    NaturalNumber mem();
}
