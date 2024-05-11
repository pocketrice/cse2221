import components.stack.Stack;
import components.stack.Stack1L;

/**
 * Model class.
 *
 * @author Bruce W. Weide
 * @author Paolo Bucci
 */
public final class AppendUndoModel1 implements AppendUndoModel {

    /**
     * Model input.
     */
    private String input;
    /**
     * Model output.
     */
    private Stack<String> output, redoStack;

    /**
     * Default constructor.
     */
    public AppendUndoModel1() {
        /*
         * Initialize model; both variables start as empty strings
         */
        this.input = "";
        this.output = new Stack1L<>();
        this.output.push("");
        this.redoStack = new Stack1L<>();
        this.redoStack.push("");
    }

    @Override
    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public Stack<String> output() {
        return this.output;
    }

    @Override
    public Stack<String> redoStack() {
        return this.redoStack;
    }

    @Override
    public String input() {
        return this.input;
    }

}
