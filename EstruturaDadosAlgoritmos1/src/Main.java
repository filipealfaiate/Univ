import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(new File("boogle.txt"))); //Ficheiro com os 16 caracteres

        String tabuleiro = reader.readLine();


        Boogle game = new Boogle();

        game.ler_boogle(tabuleiro);
        game.print();
        game.solve();


    }
}