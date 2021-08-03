package sd.t2;


// import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
// import com.sun.jersey.api.core.PackagesResourceConfig;
// import com.sun.jersey.api.core.ResourceConfig;
// import org.glassfish.grizzly.http.server.HttpServer;


import javax.ws.rs.core.UriBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import java.sql.Date;

public class Main{

    private static Statement stmt;
    private static Date dataHoje;
    
    static ListaVacinas vacinasDistribuidas;

    private static javax.ws.rs.client.WebTarget webTarget;
    private static javax.ws.rs.client.Client client;
    private static final String BASE_URI = "http://localhost:9200/sd/";

    /**
     * @param requestEntity request data@return response object (instance of responseType class)
     */
    public static void putVacinas_XML(Object requestEntity) throws javax.ws.rs.ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }


    public static void startDB() throws Exception{

        String host, dbName, username, password;


        FileReader reader = new FileReader("./db.properties");
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

        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("listaVacinas");

        Scanner s = new Scanner(System.in);
        
        startDB();

        // Obter data atual no formato yyyy-mm-dd
        dataHoje = new java.sql.Date(System.currentTimeMillis()); 

        printMenuDGS();

        for(;;){

            System.out.print("\n$ "); // print estetico
            switch(s.next().toUpperCase()){
                
                case "ACT":
                    getCidadaosFromSheets();
                    System.out.println("\nAtualizado com sucesso");
                    break;

                case "DST":
                    int nNacVac = s.nextInt();
                    distribuirVacinas(nNacVac);
                    break;

                case "LST":
                    String listarDadosData = s.next();
                    listarVacinas(listarDadosData);
                    break;

                case "HLP":
                    printMenuDGS();
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

    public static void listarVacinas(String listarDadosData){
        
        

        try{

            ResultSet rs = stmt.executeQuery("SELECT nomeCentro, count(*) as c from vacinado where data='"+listarDadosData+"' group by nomeCentro;");

            System.out.println("\nDia - "+listarDadosData+"\nCentro - nº Vacinados\n");

            while(rs.next()){

                int numeroVacinados = rs.getInt("c");
                String nomeCentro = rs.getString("nomeCentro");

                System.out.println(nomeCentro + " - " + numeroVacinados +"\n");
                
            }
        }
        catch(Exception e){

            System.out.println("Erro na data inserida");
        }

    }

    public static void getCidadaosFromSheets() throws Exception{

        SheetsConnection sc = new SheetsConnection();

        try{

            ResultSet rs = stmt.executeQuery("Select count(codigo) as c from cidadao;");

            rs.next();

            int numeroCidadaosBD = rs.getInt("c");





            ArrayList<String[]> cidadaos = sc.getCidadaos(numeroCidadaosBD+2);

            for(String[]arr: cidadaos){
                
                String nome = arr[0];
                int idade = Integer.parseInt(arr[1]);
                String email = arr[2];
                String nomeCentro = arr[3];
                String data = arr[4];
                String codigo = arr[5];

                stmt.executeUpdate("insert into cidadao values('"+nome+"', "+idade+", '"+email+"', '"+codigo+"');");

                stmt.executeUpdate("insert into fila_vacinacao values('"+codigo+"','"+nomeCentro+"','"+data+"');");

            }
        }
        catch(Exception e){

            System.out.println("Erro a receber dados");
        } 
    }
    
    public static void distribuirVacinas(int nVacinas){

        // Guardar informação do tipo [[c1,CUF],[c2, Enfermaria],...]
        ArrayList<String[]> codigoCentro = new ArrayList<>();

        ArrayList<CidadaoCentro> cidadaoCentro = new ArrayList<>();
        

        try{

            // Buscar todos os codigos e centros que escolheram ser vacinados hoje
            ResultSet rs = stmt.executeQuery("SELECT codigo, nomeCentro from fila_vacinacao where data='"+dataHoje+"';");

            while (rs.next()) {

                // Inserir na lista de códigos<
                String codigo = rs.getString("codigo");
                String nomeCentro = rs.getString("nomeCentro");

                codigoCentro.add(new String[]{codigo,nomeCentro});
            }

            // Buscar todos os cidadões associados aos codigos
            for(String[]arr : codigoCentro){

                rs = stmt.executeQuery("SELECT * from cidadao where codigo='"+arr[0]+"';");

                while(rs.next()){

                    
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    String email = rs.getString("email");

                    rs = stmt.executeQuery("SELECT nmaxvac from centros where nomeCentro = '"+arr[1]+"';");

                    rs.next();
                    int nMaxVac = rs.getInt("nmaxvac");

                
                    cidadaoCentro.add(new CidadaoCentro(nome, idade, email, arr[0], arr[1], nMaxVac));
                }
            }

            // Ordenar esta lista por idades
            Collections.sort(cidadaoCentro, Collections.reverseOrder());

            // Inicializa a ListaVacinas, com 0 vacinas para cada centro existente
            vacinasDistribuidas = getArrayVacinas();


            // Distribuir vacinas pelos centros
            for(CidadaoCentro cc: cidadaoCentro){

                if(nVacinas>0){

                    // true se pode adicionar, false se não
                    if(adicionaVacina(cc)){
                        nVacinas--;
                    }
                }
                else{
                    break;
                }
            }
            
            for(Vacina v: vacinasDistribuidas.getVacinas()){
                System.out.println(v);
            }

            // Atualiza a ListaVacinas, de modo a que os centros de vacinação consigam obter a nova versão
            putVacinas_XML(vacinasDistribuidas);
        }
        catch(Exception e){
            System.out.println("Erro a distribuir vacinas");
        }
    }

    // Devolve true se puder adiconar uma vacina, false se o número máximo de vacinas diárias já tiver sido atingido
    public static boolean adicionaVacina(CidadaoCentro cc){

        for(Vacina v : vacinasDistribuidas.getVacinas()){

            if( v.nome.equals(cc.nomeCentro)){

                if(v.nVacinas >= cc.maxVac){

                    return false;
                }
                else{

                    v.nVacinas++;
                    return true;
                }
            }
        }

        return false;
    }

    // Inicializa a ListaVacinas, com 0 vacinas para cada centro existente
    public static ListaVacinas getArrayVacinas(){

        vacinasDistribuidas = new ListaVacinas();

        try{

            ResultSet rs = stmt.executeQuery("Select nomeCentro from centros");

            while(rs.next()){

                Vacina v = new Vacina(rs.getString("nomeCentro"), 0);

                vacinasDistribuidas.add(v);
            }
            
        }
        catch(Exception e){
            System.out.println("Erro a receber vacinas");
        }


        return vacinasDistribuidas;
    }

    // Print do menu
    public static void printMenuDGS(){

        System.out.println("\n\t\t\t\t--- Menu DGS ---\n"
                + "COMANDO ARGUMENTO(s) - DESCRICAO \n"
                + "ACT                                - Actualizar Base de Dados consoante os novos utilizadores inscritos pela Aplicação Web\n"
                + "DST NVacinas                       - Distribuir as vacinas pelos centros abrangendo as pessoas mais velhas, dado um número nacional de vacinas\n"
                + "LST yyyy-mm-dd                     - Listar o nº total de vacinados por centro num determinado dia ex. '2021-07-08'\n"
                + "HLP                                - Mostrar menu de Comandos\n"
                + "EXT                                - Sair\n");
    }


}