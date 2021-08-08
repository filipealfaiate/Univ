public class Position {

    int posX;
    int posY;

    public Position(int posX, int posY){
        this.posX=posX;
        this.posY=posY;
    }

    @Override
    public boolean equals(Object o) {
        Position position =  (Position) o;
        return posX == position.posX && posY == position.posY;
    }

    @Override
    public String toString() {
        return "(" + posX + "," + posY + ")";
    }

}
