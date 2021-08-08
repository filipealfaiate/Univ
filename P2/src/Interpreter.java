import java.util.List;

public interface Interpreter {
    void run(List<TurtleStatement> program);
    void run(TurtleStatement statement);
    void run(Forward statement);
    void run(Turn statement);
    void run(PenUp statement);
    void run(PenDown statement);
    void run(Leap statement);
    void run(Save statement);
    void run(Restore statement);
}
