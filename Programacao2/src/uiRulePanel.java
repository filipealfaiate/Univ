import javax.swing.*;

public class uiRulePanel extends JPanel {

    private JTextField letraInicial;
    private JTextField palavraSubstituicao;

    public uiRulePanel(){
        letraInicial = new JTextField(3);
        palavraSubstituicao = new JTextField(14);

        add(letraInicial);
        add(new JLabel(" > "));
        add(palavraSubstituicao);
    }

    public String getLetraInicialText() {
        return letraInicial.getText();
    }

    public String getPalavraSubstituicaoText() {
        return palavraSubstituicao.getText();
    }

    public void clear(){
        this.letraInicial.setText("");
        this.palavraSubstituicao.setText("");
    }
}
