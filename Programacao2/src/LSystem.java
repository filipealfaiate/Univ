public interface LSystem {
    void setStart(String start);
    void addRule(Character symbol, String word);
    String iter(int n);
}
