import javax.swing.*;

public class uiIteracoesPanel extends JPanel {

    private JTextField numIteracoes;

    public uiIteracoesPanel(){
        add(new JLabel("Iterações"));
        numIteracoes = new JTextField(3);
        add(numIteracoes);
    }

    public String getNumIteracoeslText() {
        return numIteracoes.getText();
    }

    public void clear(){
        this.numIteracoes.setText("");
    }
}
