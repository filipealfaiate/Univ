package galapagos;

public class TurtleState {

    private double x;
    private double y;
    private double angle;

    public TurtleState() {
        this(0.0,0.0,0.0);
    }

    public TurtleState(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
