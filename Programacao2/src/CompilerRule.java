public class CompilerRule {

    private char letter;
    private TurtleStatement statement;

    public CompilerRule(char letter, TurtleStatement statement) {
        this.letter = letter;
        this.statement = statement;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public TurtleStatement getStatement() {
        return statement;
    }

    public void setStatement(TurtleStatement statement) {
        this.statement = statement;
    }
}
