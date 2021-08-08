public class Turn extends TurtleStatement {
    double angle;

    public Turn(double distance){
        this.angle = distance;
    }

    public double getAngle() {
        return angle;
    }

    public void run(Interpreter interpreter) {
        interpreter.run((Turn) this);
    }
}
