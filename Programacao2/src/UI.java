import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class UI extends JFrame {

    public JMenuItem kockCurveMenuItem;
    public JMenuItem kochSnowflakeMenuItem;
    public JMenuItem sierpinskiTriangleMenuItem;
    public JMenuItem sierpinskiArrowheadMenuItem;
    public JMenuItem dragonCurveMenuItem;
    public JMenuItem cantorSetMenuItem;
    public JMenuItem binaryTreeMenuItem;
    public JMenuItem anotherTreeMenuItem;
    public Vector<uiRulePanel> regras;
    public Vector<uiCompilacaoRulePanel> regrasCompilacao;
    public uiInicioPanel inicioPanel;
    public uiIteracoesPanel iteracoesPanel;
    public JButton executarBtn;
    public JButton limparBtn;
    public JLabel msgLabel;

    public UI(){
        super("LSystem");
        setSize(new Dimension(400, 630));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Centra a janela no centro do ecrã
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel);

        setJMenuBar(setMenuBar());
        mainPanel.add(initFormUI());

        setVisible(true);
    }

    private JMenuBar setMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("L-Systems Predefinidos");

        // Items de Menu
        kockCurveMenuItem = new JMenuItem("Kock Curve");
        menu.add(kockCurveMenuItem);

        kochSnowflakeMenuItem = new JMenuItem("Koch Snowflake");
        menu.add(kochSnowflakeMenuItem);

        sierpinskiTriangleMenuItem = new JMenuItem("Sierpinski Triangle");
        menu.add(sierpinskiTriangleMenuItem);

        sierpinskiArrowheadMenuItem = new JMenuItem("Sierpinski Arrowhead");
        menu.add(sierpinskiArrowheadMenuItem);

        dragonCurveMenuItem = new JMenuItem("Dragon Curve");
        menu.add(dragonCurveMenuItem);

        menu.addSeparator();

        cantorSetMenuItem = new JMenuItem("Cantor Set");
        menu.add(cantorSetMenuItem);

        menu.addSeparator();

        JMenu submenu = new JMenu("Fractal Plant");

        binaryTreeMenuItem = new JMenuItem("Binary Tree");
        submenu.add(binaryTreeMenuItem);

        anotherTreeMenuItem = new JMenuItem("Another Tree");
        submenu.add(anotherTreeMenuItem);

        menu.add(submenu);

        menuBar.add(menu);
        return menuBar;
    }

    private JPanel initFormUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        regras = new Vector<uiRulePanel>();
        regrasCompilacao = new Vector<uiCompilacaoRulePanel>();

        contentPanel.add(Box.createRigidArea(new Dimension(10, 10)));

        inicioPanel = new uiInicioPanel();
        contentPanel.add(inicioPanel);

        contentPanel.add(Box.createRigidArea(new Dimension(10, 20)));

        contentPanel.add(new JLabel("Regras"));

        // Inicializa e adicionar os campos das regras
        for(int i = 0; i < 3; i++){
            regras.add(new uiRulePanel());
        }

        // Coloca os campos no UserInterface
        for(int i = 0; i < regras.size(); i++){
            contentPanel.add(regras.get(i));
        }

        contentPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        contentPanel.add(new JLabel("Regras Compilador"));

        // Inicializa e adicionar os campos das regras de compilação
        for(int i = 0; i < 6; i++){
            regrasCompilacao.add(new uiCompilacaoRulePanel());
        }

        // Coloca os campos no UserInterface
        for(int i = 0; i < regrasCompilacao.size(); i++){
            contentPanel.add(regrasCompilacao.get(i));
        }

        contentPanel.add(Box.createRigidArea(new Dimension(10, 20)));

        iteracoesPanel = new uiIteracoesPanel();
        contentPanel.add(iteracoesPanel);

        contentPanel.add(Box.createRigidArea(new Dimension(10, 20)));

        JPanel btnsPanel = new JPanel();
        executarBtn = new JButton("Executar");
        btnsPanel.add(executarBtn);
        btnsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        limparBtn = new JButton("Limpar");
        btnsPanel.add(limparBtn);
        contentPanel.add(btnsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(10, 15)));

        msgLabel = new JLabel("");
        msgLabel.setForeground(Color.RED);
        msgLabel.setVisible(false);
        contentPanel.add(msgLabel);

        return contentPanel;
    }
}
