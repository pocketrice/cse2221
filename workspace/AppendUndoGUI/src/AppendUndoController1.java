import components.stack.Stack;

/**
 * Controller class.
 *
 * @author Lucas Xie
 */
public final class AppendUndoController1 implements AppendUndoController {

    /**
     * Model object.
     */
    private final AppendUndoModel model;

    /**
     * View object.
     */
    private final AppendUndoView view;

    /**
     * Updates view to display model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     */
    private static void updateViewToMatchModel(AppendUndoModel model,
            AppendUndoView view) {
        /*
         * Get model info
         */
        String input = model.input();
        String output = model.output().top();

        view.updateUndoAllowed(!output.isEmpty());
        view.updateRedoAllowed(model.redoStack().length() >= 2);
        /*
         * Update view to reflect changes in model
         */
        view.updateInputDisplay(input);
        view.updateOutputDisplay(output);
    }

    /**
     * Constructor; connects {@code this} to the model and view it coordinates.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public AppendUndoController1(AppendUndoModel model, AppendUndoView view) {
        this.model = model;
        this.view = view;
        /*
         * Update view to reflect initial value of model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    /**
     * Processes reset event.
     */
    @Override
    public void processResetEvent() {
        /*
         * Update model in response to this event
         */
        this.model.setInput("");
        this.model.output().clear();
        this.model.output().push("");
        this.model.redoStack().clear();
        this.model.redoStack().push("");
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAppendEvent(String input) {
        if (!input.isEmpty()) {
            this.model.output().push(this.model.output().top() + input);
            this.model.setInput("");
            updateViewToMatchModel(this.model, this.view);
        }
    }

    @Override
    public void processUndoEvent() {
        String output1, output2;
        Stack<String> modelOutput = this.model.output();
        Stack<String> modelRedos = this.model.redoStack();

        output1 = modelOutput.pop();
        output2 = modelOutput.pop();
        modelOutput.push(output2);
        modelRedos.push(output1);

        this.model.setInput(output1.substring(output2.length()));

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processRedoEvent() {
        String output1, output2, outputDiff;
        Stack<String> modelOutput = this.model.output();
        Stack<String> modelRedos = this.model.redoStack();

        output1 = modelRedos.pop();
        output2 = modelRedos.pop();
        modelRedos.push(output2);
        modelOutput.push(output1);

        if (output2.isEmpty()) {
            outputDiff = output2;
        } else {
            outputDiff = output2.substring(output1.length());
        }

        this.model.setInput(outputDiff);

        updateViewToMatchModel(this.model, this.view);
    }
}
