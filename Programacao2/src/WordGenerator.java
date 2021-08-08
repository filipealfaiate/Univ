import java.util.ArrayList;

public class WordGenerator implements LSystem {

    public String startWord;
    public String generatedWord;
    public ArrayList<Rule> rules;

    public WordGenerator(){
        rules = new ArrayList<>();
    }

    public void setStart(String start) { this.startWord = start; }

    public void addRule(Character symbol, String word) {
        Rule rule = new Rule(symbol, word);
        rules.add(rule);
    }

    public String iter(int n) {
        generatedWord = startWord;

        for(int i = 0; i < n; i++){
            StringBuilder newWord = new StringBuilder();

            for(int j = 0; j < generatedWord.length(); j++){
                char currentLetter = generatedWord.charAt(j);
                boolean found = false;

                for(int k = 0; k < rules.size(); k++){
                    if(rules.get(k).getStartLetter() == currentLetter){
                        newWord.append(rules.get(k).getRule());
                        found = true;
                        break;
                    }
                }

                if(!found){
                    newWord.append(currentLetter);
                }
            }

            generatedWord = newWord.toString();
            //System.out.println(generatedWord);
        }

        return generatedWord;
    }
}
