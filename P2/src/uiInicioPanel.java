import javax.swing.*;

public class uiInicioPanel extends JPanel {

    private JTextField inicio;

    public uiInicioPanel(){
        add(new JLabel("Inicio"));
        inicio = new JTextField(10);
        add(inicio);
    }

    public String getInicioText() {
        return inicio.getText();
    }

    public void clear(){
        this.inicio.setText("");
    }
}
