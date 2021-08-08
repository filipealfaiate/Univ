public class Rule {

    private char startLetter;
    private String rule;

    public Rule(char startLetter, String rule){
        this.setStartLetter(startLetter);
        this.setRule(rule);
    }

    public char getStartLetter() {
        return startLetter;
    }

    public void setStartLetter(char startLetter) {
        this.startLetter = startLetter;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
