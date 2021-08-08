import java.io.*;

public class Boogle {

    Letra [][] matriz = new Letra[4][4];
    LinkedList<Letra> ll = new LinkedList<>();
    int nsolucoes;
    
    Hash<String> wordHash = new Hash<>(10000000);
    Hash<String> preHash  = new Hash<>(10000000);


    public Boogle() throws IOException {
        criarHash();
    }

    private void criarHash() throws IOException {

        File file = new File("allWords.txt"); //Ficheiro com as palavras do dicionario

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String st;

        while ((st = reader.readLine()) != null) {              //Criar as Hash com os prefixos e as Palavras

            wordHash.insere(st);

            for (int i = 1; i < st.length(); i++) {

                preHash.insere(st.substring(0, i));
            }
        }
    }

    public void ler_boogle(String file){

        int i = 0;

        for(int y=0; y<4; y++){
            for(int x =0; x<4; x++){
                matriz[x][y] = new Letra(String.valueOf(file.charAt(i)).toUpperCase() , new Position(y,x));        //Criar a matriz a partir da String
                i++;

            }
        }
    }

    public void print() {                   //Print do tabueleiro
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                System.out.print(matriz[x][y].w + " ");     
            }
            System.out.println();
        }
    }

    public void solve() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("BoogleAnswers.txt")); //Ficheiro onde coloca as respostas

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                respostas(x, y, ll, "", writer);

            }
        }
        writer.close();
        System.out.println("encontradas " + nsolucoes + "solucoes")
    }


    private void respostas(int x, int y, LinkedList ll, String s, BufferedWriter writer) throws IOException {


        if (x >= 0 && x < 4 && y >= 0 && y < 4 && !ll.contains(matriz[x][y])) { //ver se os valores de x e y estao dentro dos possiveis da matriz, e ver se a Letra já nao esta na LinkedList

            s += (matriz[x][y].w).toLowerCase();

            ll.add(matriz[x][y]);


            if(wordHash.procurar(s)!=null){//Se É Palavra

                writer.write(s + " " + ll.toString() +"\n");
                System.out.println(s +" "+ ll);
                nsolucoes++;
                

            }
            if (preHash.procurar(s) != null) {//Se É prefixo

                respostas(x - 1, y - 1, ll, s, writer);
                respostas(x, y - 1, ll, s, writer);
                respostas(x + 1, y - 1, ll, s, writer);
                respostas(x - 1, y, ll, s, writer);
                respostas(x + 1, y, ll, s, writer);
                respostas(x - 1, y + 1, ll, s, writer);
                respostas(x, y + 1, ll, s, writer);
                respostas(x + 1, y + 1, ll, s, writer);

            }
            ll.remove(ll.size-1);
        }
    }
}
