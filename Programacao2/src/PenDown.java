public class PenDown extends TurtleStatement {

    public PenDown(){}

    public void run(Interpreter interpreter) {
        interpreter.run((PenDown)this);
    }
}
