public class Letra{

    Position pos;
    String w;

    public Letra(String w, Position p) {
        this.pos = p;
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        Letra l = (Letra) o;

        return pos.equals(l.pos);
    }

    public String toString() {
        return "(" + w.toUpperCase() + ":" + pos.toString()+")";
    }

}
