import javax.swing.*;

public class uiLetraInicialPanel extends JPanel {

    public JTextField letraInicial;

    public uiLetraInicialPanel(){
        add(new JLabel("Letra Inical"));
        letraInicial = new JTextField(3);

        add(letraInicial);
    }

    public JTextField getLetraInicial() {
        return letraInicial;
    }
}
