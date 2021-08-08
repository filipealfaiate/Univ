import java.util.Vector;

public class Compiler {

    Vector<CompilerRule> rules;

    public Compiler() {
        rules = new Vector<>();
    }

    public void addRule(char letter, TurtleStatement statement){
        CompilerRule rule = new CompilerRule(letter, statement);
        rules.add(rule);
    }

    protected TurtleStatement compile(Character c){

        for(int i = 0; i < rules.size(); i++){
            if(rules.get(i).getLetter() == c){
                return rules.get(i).getStatement();
            }
        }

        return null;
    }

    protected Vector<TurtleStatement> compile(String word) {
        Vector<TurtleStatement> result = new Vector<>();
        for (int i = 0; i < word.length(); i++) {
            result.add(compile(word.charAt(i)));
        }
        return result;
    }
}
