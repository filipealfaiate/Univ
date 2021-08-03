package t2.teste;

import java.util.*;

// import org.eclipse.persistence.config.SystemProperties;

import java.io.*;
import java.sql.Statement;
import java.sql.ResultSet;

public class Main {

    private static final String scriptId = "AKfycbxuo6nhdB4zUvTO0v8eXc7QByUWFdnIFNgtF1NBDg-QS3-ZzE4qcDhymyjPHL050ds";
    static ScriptAdapter scriptAdapter;
    static Statement stmt;
    static Scanner s;

    public static void startDB() throws Exception {

        String host, dbName, username, password;

        s = new Scanner(System.in);
        scriptAdapter = new ScriptAdapter(scriptId);

        FileReader reader = new FileReader("db.properties");
        Properties p = new Properties();
        p.load(reader);

        host = p.getProperty("host");
        dbName = p.getProperty("dbName");
        username = p.getProperty("username");
        password = p.getProperty("password");
        
        PostgresConnector pc = new PostgresConnector(host, dbName, username, password);        
        
        // estabelecer a ligacao ao SGBD
        pc.connect();
        stmt = pc.getStatement();
    }
    
    public static void main(String[] args) throws Exception {


        s = new Scanner(System.in);
        scriptAdapter = new ScriptAdapter(scriptId);

        startDB();

        printMenu();

        for(;;){
            
            System.out.print("\n$ "); // print estetico
            switch(s.next().toUpperCase()){

                case "CVA":

                    printCentros();
                    break;

                case "ENT":
                    String nomeCentro = s.nextLine();
                    entrarCentro(nomeCentro);
                    break;

                case "REG":
                    // Nome centro e número maximo de vacinas
                    String dados = s.nextLine();
                    registarCentro(dados);

                    System.out.println("Centro inserido com sucesso\n\n");
                    break;

                case "HLP":
                    printMenu();
                    break;

                case "EXT":
                    s.close();
                    System.exit(0);
                    break;

                default:
                    s.nextLine(); // Limpar o scanner
                    System.out.println("\nComando não válido - HLP para mostrar comandos\n\n");
                    break;
            }
        }
    }


    public static ArrayList<String> getCentros() throws Exception {

        ArrayList<String> centrosVac = new ArrayList<>();
        
        try {
            
            // Vai guardar todos os nomes da tabela centros no rs
            ResultSet rs = stmt.executeQuery("SELECT * from centros");
            
            // Equanto ainda existirem nomes (linhas)
            while (rs.next()) {

                // Inserir na ArrayList o nome referente a fila atual
                centrosVac.add(rs.getString("NomeCentro"));
            }
            rs.close();
            
        } catch (Exception e) {
            
            System.out.println("Erro a obter os centros existentes\n");
        }
        
        return centrosVac;

    }

    public static void printCentros() throws Exception{

        ArrayList<String> centros = getCentros();

        System.out.println(); // print estético
        for(int i = 0; i<centros.size(); i++){
            
            System.out.printf(" %s\n",centros.get(i));
        
        }     
    }

    // Devolve true se pode adicionar, false se for duplicado
    public static boolean centroExiste(String nomeCentro) throws Exception{

        ArrayList<String> centros = getCentros();

        for(String centro: centros){
            if(centro.equals(nomeCentro)){
                return true;
            }
        }

        return false;
    }
    

    public static void registarCentro(String dados) throws Exception{


        try{

            // Separar os dados em, NomeCentro e nº máximo
            String[] arr = dados.split("'");

            String nomeCentro = arr[1];
            String numeroMaxVacinas = arr[2];

            if(!centroExiste(nomeCentro)){

                // Adicionar o novo centro o Google Sheets
                scriptAdapter.addCentro(nomeCentro, numeroMaxVacinas);

                // Adicionar ao PostGres
                stmt.executeUpdate("insert into centros values('"+nomeCentro+"',"+numeroMaxVacinas+");");
            }
            else{

                System.out.println("Centro inserido já existe\n");
            }
        
        }
        catch(Exception e){
            System.out.println("Erro a registar centro\n");
        }

    }

    public static void printMenu(){

        System.out.println("\n\t\t\t\t--- Menu ---\n"
                + "COMANDO ARGUMENTO(s) - DESCRICAO \n"
                + "CVA                                - Consultar centros de vacinacao\n"
                + "ENT 'Centro'                       - Entrar na conta do Centro\n"
                + "REG 'Centro' numeroMaxVacinas      - Registar um novo centro\n"
                + "HLP                                - Mostrar menu de Comandos\n"
                + "EXT                                - Sair\n");
    }
    
    public static void entrarCentro(String nomeCentroRec) throws Exception {

        String nomeCentro = nomeCentroRec.split("'")[1];

        if(centroExiste(nomeCentro)){
            
            Centro centro = new Centro(nomeCentro,stmt, scriptAdapter);

            boolean loggedIn = true;

            centro.printComandosCentro();

            while(loggedIn){

                System.out.print("\n$ "); // print estetico
                switch(s.next().toUpperCase()){

                    case "GET":
                        centro.showCidadaosASerVacinados();
                        break;

                    case "INF":

                        centro.informarCidadaos();
                        break;

                    case "VCN":

                        String codigo = s.next();
                        centro.vacinar(codigo);
                        break;

                    case "LOG":
                        loggedIn = false;
                        printMenu();
                        break;
                    
                    case "HLP":
                        centro.printComandosCentro();
                        break;
                    
                    case "EXT":
                        s.close();
                        System.exit(0);
                        break;
                    
                    default:

                        s.nextLine(); // limpar o scanner
                        System.out.println("\nComando não válido - HLP para mostrar comandos\n\n");
                        break;
                }
            }
        }
        else{
            System.out.println("Centro não existe");
        }
    }

    
}
