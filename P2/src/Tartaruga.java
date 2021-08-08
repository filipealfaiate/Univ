import galapagos.Turtle;

import java.awt.*;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class Tartaruga implements Interpreter{

    private Turtle turtle;
    private Stack<TurtlePosition> pilha;

    public Tartaruga() {
        turtle = new Turtle();
        turtle.speed(800);
        turtle.penColor(Color.black);
        turtle.getPlayground().setOrigin(300, 0);
        pilha = new Stack<TurtlePosition>();
    }

    public void run(List<TurtleStatement> program) {
        for(int i=0; i< program.size(); i++){
            if(program.get(i) != null) {
                program.get(i).run(this);
            }
        }
    }

    public void run(TurtleStatement statement) {
        statement.run(this);
    }

    public void run(Forward statement) {
        turtle.forward(statement.getDistance());
    }

    public void run(Turn statement) {
        turtle.turn(statement.getAngle());
    }

    public void run(PenUp statement) { turtle.penUp(); }

    public void run(PenDown statement) { turtle.penDown(); }

    public void run(Leap statement) {
        Vector<TurtleStatement> leap = new Vector<>();
        leap.add(new PenUp());
        leap.add(new Forward(2.0));
        leap.add(new PenDown());
        this.run(leap);
    }

    public void run(Save statement) { turtle.save(); }

    public void run(Restore statement) { turtle.restore(); }
}
