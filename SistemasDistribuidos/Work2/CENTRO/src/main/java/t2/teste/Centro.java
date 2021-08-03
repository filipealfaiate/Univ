package t2.teste;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import java.sql.Date;

// import javax.sound.midi.Soundbank;
// import javax.ws.rs.client.*;

// import org.apache.ws.commons.schema.constants.Constants.SystemConstants;
// import org.glassfish.jersey.client.ClientConfig;

public class Centro {

    private String nomeCentro;
    private Statement stmt;
    private int nVacinas;
    private Date dataHoje;

    private ArrayList<Cidadao> filaEspera;

    private ScriptAdapter scriptAdapter;

    private javax.ws.rs.client.WebTarget webTarget;
    private javax.ws.rs.client.Client client;
    private static final String BASE_URI = "http://localhost:9200/sd/";
    

    public Centro(String nomeCentro, Statement stmt, ScriptAdapter scriptAdapter) throws Exception {

        this.nomeCentro = nomeCentro;
        this.stmt = stmt;
        this.scriptAdapter = scriptAdapter;

        // REST
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("listaVacinas");
        
        // Obter data atual no formato yyyy-mm-dd
        dataHoje = new java.sql.Date(System.currentTimeMillis());  

        // Obter o número de vacinas para o dia
        nVacinas = getVacinasDia();

        // Obter, da db, a fila de espera para este centro de vacinacao para o dia (dataHoje)
        filaEspera = getFilaEspera();
        ordenarIdade();
    }

    /**
     * @param responseType Class representing the response
     * @return response object (instance of responseType class)
     */
    public <T> T getVacinas_XML(Class<T> responseType) throws javax.ws.rs.ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    
    public void close() {
        client.close();
    }

    // Obtém os cidadãos que marcaram ser vacinados, neste Centro, no presente dia
    public ArrayList<Cidadao> getFilaEspera(){
        
        ArrayList<Cidadao> listaCidadoes = new ArrayList<>();
        ArrayList<String> listaCodigos = new ArrayList<>();
        
        try{

            // Vai guardar todos os codigos que estão associados a este centro de vacinação
            ResultSet rs = stmt.executeQuery("SELECT codigo from fila_vacinacao where nomeCentro like '"+nomeCentro+"' and data='"+dataHoje+"';");

            while (rs.next()) {

                // Inserir na lista de códigos<
                listaCodigos.add(rs.getString("codigo"));

            }
            
            for(String codigo : listaCodigos){
                
                rs = stmt.executeQuery("Select * from cidadao where codigo like '"+codigo+"';");
                
                while(rs.next()){
                    
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    String email = rs.getString("email");

                    // System.out.println(nome + "\t" + idade + "\t" + email);
                    listaCidadoes.add(new Cidadao(nome,idade,email, codigo));
                }   
            }

            // System.out.println(listaCidadoes.size());  
            rs.close();
        }
        catch(Exception e){
            System.out.println("Erro a obter a fila de espera\n");
        }


        return listaCidadoes;
    }

    // Obtém o número de vacinas diárias para o centro
    public int getVacinasDia() throws Exception{
        
        ListaVacinas lv = getVacinas_XML(ListaVacinas.class);

        for(Vacina v : lv.getVacinas()){

            if(v.getNome().equals(nomeCentro)){
                return v.nVacinas;
            }
        }
        
        return -1;
    }
    
    // Ordena a filaEspera por ordem descrescente de idade
    public void ordenarIdade(){
        
        Collections.sort(filaEspera, Collections.reverseOrder());
    }
    
    public void informarCidadaos(){
        
        String corpo_email_confirmacao = "A sua vacina marcada para  "+dataHoje +" pode ser realizada, apareça quando lhe for mais conveniente no centro de vacinação "+nomeCentro;
        String corpo_email_nao_confirmacao = "A sua vacina marcada para "+dataHoje+" no centro " + nomeCentro+ " não pode ser realizada, entre no link (https://script.google.com/macros/s/AKfycbxuo6nhdB4zUvTO0v8eXc7QByUWFdnIFNgtF1NBDg-QS3-ZzE4qcDhymyjPHL050ds/exec) com o seu código ";

        try{

            for(int i=0; i<filaEspera.size(); i++){

                if(i+1 <= nVacinas){ // Significa que podemos vacinar a pessoa

                    scriptAdapter.addConfirmacao(filaEspera.get(i).getCodigo());
                    scriptAdapter.sendEmail(filaEspera.get(i).getEmail(),corpo_email_confirmacao);
                }
                else{ // as vacinas acabaram, informar impossibilidade de vacinação

                    scriptAdapter.addNegacao(filaEspera.get(i).getCodigo());
                    scriptAdapter.sendEmail(filaEspera.get(i).getEmail(),corpo_email_nao_confirmacao + filaEspera.get(i).getCodigo());
                }
            }
        }
        catch(Exception e){
            System.out.println("Erro a enviar o e-mail");
        }
    }

    public void vacinar(String codigo) throws Exception{

        try{
            int removido = stmt.executeUpdate("delete from fila_vacinacao where codigo='"+codigo+"';");

            if(removido!=1){
                throw new Exception ("\ncódigo não existe\n");
            }
            stmt.executeUpdate("insert into vacinado values('"+codigo+"', '"+nomeCentro+"','"+dataHoje+"');");
        }
        catch(Exception e){
            System.out.println("Erro no código inserido\n");
        }
        
    }

    public void showCidadaosASerVacinados(){

        System.out.println(); // print estético
        

        int i = 0;
        for(Cidadao c : filaEspera){

            if(i>=nVacinas){
                break;
            }

            System.out.println(c);
            i++;
        }

    }

    public void printComandosCentro(){
        System.out.println("\n\t\t\t\t--- Menu Centro "+nomeCentro+" ---\n"
                + "COMANDO ARGUMENTO(s) - DESCRICAO \n"
                + "GET                                - Mostra a informação de todos os cidadãos a ser vacinados no presente dia\n"
                + "INF                                - Informar os cidadões que podem ou não fazer a vacina consoante o número recebido\n"
                + "VCN codigo                         - Regista a vacinação do cidadão com o código dado\n"
                + "LOG                                - Faser Log Out da conta do centro\n"
                + "HLP                                - Mostrar menu de Comandos\n"
                + "EXT                                - Sair\n");
    }
}