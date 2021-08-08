import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        UI ui = new UI();

        ui.kockCurveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // A inicialização do gerador de palavras e do compilador tem de passar aqui
                // para dentro porque se estivesse fora quando adicionassemos uma nova regra
                // esta ia ser adicionada às regras já existentes e o que se quer é que o gerador de palavras
                // e o compilador estejam como novos, vazios
                LSystem wordGenerator = new WordGenerator();
                Compiler compiler = new Compiler();

                wordGenerator.setStart("F");
                wordGenerator.addRule('F', "F+F-F-F+F");

                compiler.addRule('F', new Forward(5.0));
                compiler.addRule('+', new Turn(90));
                compiler.addRule('-', new Turn(-90));

                String word = wordGenerator.iter(7);
                Vector<TurtleStatement> program = compiler.compile(word);

                Tartaruga tartaruga = new Tartaruga();
                tartaruga.run(program);
            }
        });


        ui.kochSnowflakeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LSystem wordGenerator = new WordGenerator();
                Compiler compiler = new Compiler();

                wordGenerator.setStart("F");
                wordGenerator.addRule('F', "F+F--F+F");

                compiler.addRule('F', new Forward(5.0));
                compiler.addRule('+', new Turn(60));
                compiler.addRule('-', new Turn(-60));

                String word = wordGenerator.iter(7);
                Vector<TurtleStatement> program = compiler.compile (word);

                Tartaruga tartaruga = new Tartaruga();
                tartaruga.run(program);
            }
        });


        ui.sierpinskiTriangleMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LSystem wordGenerator = new WordGenerator();
                Compiler compiler = new Compiler();

                wordGenerator.setStart(" F+G+G");
                wordGenerator.addRule('F', "F+G-F-G+F");
                wordGenerator.addRule('G', "GG");

                compiler.addRule('F', new Forward(2.0));
                compiler.addRule('G', new Forward(3.0));
                compiler.addRule('+', new Turn(120));
                compiler.addRule('-', new Turn(-120));

                String word = wordGenerator.iter(6);
                Vector<TurtleStatement> program = compiler.compile(word);

                Tartaruga tartaruga = new Tartaruga();
                tartaruga.run(program);
            }
        });


        ui.sierpinskiArrowheadMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LSystem wordGenerator = new WordGenerator();
                Compiler compiler = new Compiler();

                wordGenerator.setStart("A");
                wordGenerator.addRule('A', "B-A-B");
                wordGenerator.addRule('B', "A+B+A");

                compiler.addRule('A', new Forward(5.0));
                compiler.addRule('B', new Forward(10.0));
                compiler.addRule('+', new Turn(-60));
                compiler.addRule('-', new Turn(60));

                String word = wordGenerator.iter(7);
                Vector<TurtleStatement> program = compiler.compile(word);

                Tartaruga tartaruga = new Tartaruga();
                tartaruga.run(program);
            }
        });


        ui.dragonCurveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LSystem wordGenerator = new WordGenerator();
                Compiler compiler = new Compiler();

                wordGenerator.setStart("FX");
                wordGenerator.addRule('X', "X+YF+");
                wordGenerator.addRule('Y', "−FX−Y");

                compiler.addRule('F', new Forward(10.0));
                compiler.addRule('+', new Turn(-90));
                compiler.addRule('-', new Turn(90));

                String word = wordGenerator.iter(10);
                Vector<TurtleStatement> program = compiler.compile(word);

                Tartaruga tartaruga = new Tartaruga();
                tartaruga.run(program);
            }
        });


        ui.cantorSetMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LSystem wordGenerator = new WordGenerator();
                Compiler compiler = new Compiler();

                wordGenerator.setStart("A");
                wordGenerator.addRule('A', "ABA");
                wordGenerator.addRule('B', "BBB");

                compiler.addRule('A', new Forward(5.0));
                compiler.addRule('B', new Leap(10.0));

                String word = wordGenerator.iter(7);
                Vector<TurtleStatement> program = compiler.compile(word);

                Tartaruga tartaruga = new Tartaruga();
                tartaruga.run(program);
            }
        });


        ui.binaryTreeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LSystem wordGenerator = new WordGenerator();
                Compiler compiler = new Compiler();

                wordGenerator.setStart("0");
                wordGenerator.addRule('1', "11");
                wordGenerator.addRule('0', "1[+0]-0");

                compiler.addRule('0', new Forward(5.0));
                compiler.addRule('1', new Forward(2.0));
                compiler.addRule('+', new Turn(45));
                compiler.addRule('-', new Turn(-45));
                compiler.addRule('[', new Save());
                compiler.addRule(']', new Restore());

                String word7 = wordGenerator.iter(8);
                Vector<TurtleStatement> program = compiler.compile(word7);

                Tartaruga tartaruga = new Tartaruga();
                tartaruga.run(program);
            }
        });


        ui.anotherTreeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LSystem wordGenerator = new WordGenerator();
                Compiler compiler = new Compiler();

                wordGenerator.setStart("X");
                wordGenerator.addRule('X', "F+[[X]-X]-F[-FX]+X");
                wordGenerator.addRule('F', "FF");



                compiler.addRule('F', new Forward(5.0));
                compiler.addRule('+', new Turn(25));
                compiler.addRule('-', new Turn(-25));
                compiler.addRule('[', new Save());
                compiler.addRule(']', new Restore());

                String word = wordGenerator.iter(7);
                Vector<TurtleStatement> program = compiler.compile(word);

                Tartaruga tartaruga = new Tartaruga();
                tartaruga.run(program);
            }
        });


        //erros

        ui.executarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LSystem wordGenerator = new WordGenerator();
                Compiler compiler = new Compiler();

                // Letra Inical
                String letraInical = ui.inicioPanel.getInicioText();
                if(letraInical.equals("")){
                    ui.msgLabel.setText("Deve indicar a letra inicial");
                    ui.msgLabel.setVisible(true);
                    // Deu erro por não ter introduzido a letra inicial não deve continuar
                    return;
                } else {
                    wordGenerator.setStart(letraInical);
                }

                // Percorrer todas as regras de substuição
                String palavraSubstituicao;
                for(int i = 0; i < ui.regras.size(); i++){
                    letraInical = ui.regras.get(i).getLetraInicialText();
                    palavraSubstituicao = ui.regras.get(i).getPalavraSubstituicaoText();

                    // As duas caixas de texto estão preenchidas
                    if(!letraInical.equals("") && !palavraSubstituicao.equals("")){
                        wordGenerator.addRule(letraInical.charAt(0), palavraSubstituicao);
                    } else if(!letraInical.equals("") && palavraSubstituicao.equals("")){
                        ui.msgLabel.setText("Deve indicar a palavra de substituição");
                        ui.msgLabel.setVisible(true);
                        return;
                    } else if(letraInical.equals("") && !palavraSubstituicao.equals("")){
                        ui.msgLabel.setText("Deve indicar a letra inicial");
                        ui.msgLabel.setVisible(true);
                        return;
                    }
                }

                // Percorrer todas as regras de compilador
                TurtleStatement statement;
                for(int i = 0; i < ui.regrasCompilacao.size(); i++){
                    letraInical = ui.regrasCompilacao.get(i).getLetraInicialText();
                    statement = ui.regrasCompilacao.get(i).getComando();

                    if(!letraInical.equals("") && statement != null){
                        compiler.addRule(letraInical.charAt(0), statement);
                    } else if(!letraInical.equals("") && statement == null){
                        ui.msgLabel.setText("Deve indicar um comando e o respectivo valor");
                        ui.msgLabel.setVisible(true);
                        return;
                    } else if(letraInical.equals("") && statement != null){
                        ui.msgLabel.setText("Deve indicar a letra inicial do comando");
                        ui.msgLabel.setVisible(true);
                        return;
                    }
                }

                // Número de iterações
                String numIteracoes = ui.iteracoesPanel.getNumIteracoeslText();
                Vector<TurtleStatement> program;

                if(numIteracoes.equals("")){
                    ui.msgLabel.setText("Deve indicar o número de iterações");
                    ui.msgLabel.setVisible(true);
                    return;
                } else {
                    String word = wordGenerator.iter(Integer.parseInt(numIteracoes));
                    program = compiler.compile(word);
                }

                // esconde a mensagem de erro
                ui.msgLabel.setVisible(false);

                // inicia a tartaruga
                Tartaruga tartaruga = new Tartaruga();
                tartaruga.run(program);
            }
        });

        ui.limparBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ui.inicioPanel.clear();
                for(int i = 0; i < ui.regras.size(); i++){
                    ui.regras.get(i).clear();
                }
                for(int i = 0; i < ui.regrasCompilacao.size(); i++){
                    ui.regrasCompilacao.get(i).clear();
                }
                ui.iteracoesPanel.clear();

                ui.msgLabel.setText("");
                ui.msgLabel.setVisible(true);
            }
        });





        /*for(int i = 0; i < program.size(); i++){
            program.get(i).run(tartaruga);
        }*/
    }
}
