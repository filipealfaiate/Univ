import javax.swing.*;
import java.awt.*;

public class uiCompilacaoRulePanel extends JPanel {

    private String[] listaComandos = { "Forward", "Turn", "Leap", "Save", "Restore" };
    private JTextField letraInicial;
    private JTextField valor;
    private JComboBox comandos;

    public uiCompilacaoRulePanel(){
        letraInicial = new JTextField(3);
        valor = new JTextField(3);
        comandos = new JComboBox(listaComandos);
        comandos.setPreferredSize(new Dimension(100, 25));
        comandos.setSelectedIndex(-1);

        add(letraInicial);
        add(new JLabel(" > "));
        add(comandos);
        add(valor);
    }

    public String getLetraInicialText() {
        return letraInicial.getText();
    }

    public TurtleStatement getComando(){

        int selectedIndex = comandos.getSelectedIndex();

        switch(selectedIndex){
            case 0: return new Forward(getValor());
            case 1: return new Turn(getValor());
            case 2: return new Leap(getValor());
            case 3: return new Save();
            case 4: return new Restore();
            default: return null;
        }
    }

    private double getValor(){
        if(valor.getText().equals("")){
            return 0.0;
        } else {
            return Double.parseDouble(valor.getText());
        }
    }

    public void clear(){
        this.letraInicial.setText("");
        this.comandos.setSelectedIndex(-1); //se fosse 0 metia tudo a forward
        this.valor.setText("");
    }
}
