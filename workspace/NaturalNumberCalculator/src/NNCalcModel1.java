import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Model class.
 *
 * @author Lucas Xie
 */
public final class NNCalcModel1 implements NNCalcModel {

    /**
     * Model variables.
     */
    private final NaturalNumber top, bottom, mem;

    /**
     * No argument constructor.
     */
    public NNCalcModel1() {
        this.top = new NaturalNumber2();
        this.bottom = new NaturalNumber2();
        this.mem = new NaturalNumber2();
    }

    @Override
    public NaturalNumber top() {
        return top;
    }

    @Override
    public NaturalNumber bottom() {
        return bottom;
    }

    @Override
    public NaturalNumber mem() {
        return mem;
    }
}
