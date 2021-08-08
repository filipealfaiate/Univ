public class PenUp extends TurtleStatement {

    public PenUp() {}


    public void run(Interpreter interpreter) {
        interpreter.run((PenUp)this);
    }
}
