package t1;

import java.sql.ResultSet;
import java.sql.Statement;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author José Santos, Filipe Alfaiate
 */
public class VacinacaoImpl extends UnicastRemoteObject implements Vacinacao, java.io.Serializable  {
    
    Statement stmt;
    
    /**
     * Construtor
     * 
     * @param stmt
     * @throws java.rmi.RemoteException 
     */
    public VacinacaoImpl(Statement stmt) throws java.rmi.RemoteException{

        this.stmt = stmt;
    }
    
    /**
     * Método devolve uma ArrayList com o nome dos centros de vacinação existentes na BD
     * 
     * @return
     * @throws java.rmi.RemoteException 
     */
    public ArrayList<String> getCentrosVacinacao() throws java.rmi.RemoteException {
        
        ArrayList<String> centrosVac = new ArrayList<>();
        
        try {
            
            // Vai guardar todos os nomes da tabela centros no rs
            ResultSet rs = stmt.executeQuery("SELECT * from centros");
            
            // Equanto ainda existirem nomes (linhas)
            while (rs.next()) {

                // Inserir na ArrayList o nome referente a fila atual
                centrosVac.add(rs.getString("NomeC"));
            }
            rs.close();
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        
        return centrosVac;
    }
    
    /**
     * Devolte o número de pessoas na fila de espera num determinado centro
     * 
     * @param centroV
     * @return
     * @throws java.rmi.RemoteException 
     */
    public int getTamanhoFilaEspera(String centroV) throws java.rmi.RemoteException {
        
        int fila_sz = 0;
        
        
        
        try {
            // Vai guardar o número de pessoas na fila de espera no centro centroV
            ResultSet rs = stmt.executeQuery("SELECT count(NomeC) as c from fila where NomeC like "+centroV+";");
            
            // Passar para a proxima linha
            rs.next();
            
            // Buscar o inteiro referente à coluna c (count)
            fila_sz = rs.getInt("c");
            
            
            rs.close();

            return fila_sz;
            
        } catch (Exception e) {
            
            // No caso de o centro não existir
            return -1; 
        }
    }
    
    /**
     * Inscrever um utilizador para a vacinação num centro
     * 
     * @param dados
     * @return
     * @throws java.rmi.RemoteException 
     */
    public String inscricaoVacinacao(String dados) throws java.rmi.RemoteException {
        
        
        
        try{
            // código
            String cod_utilizador ="c";
            
            // Dividir o input por plicas 
            String [] auxNomeC = dados.split("'");

            
            // Dividir o resto do input (sem o Nome do Centro) por espaços
            String [] auxResto = auxNomeC[2].split(" ");

            String nomeC = auxNomeC[1];
            String cc = auxResto[1];
            String nomeP = auxResto[2];
            int idade = Integer.parseInt(auxResto[3]);
            String genero =auxResto[4];

            
            // código atual
            int current_cod = 0;
                      

            // Verificar quantas pessoas estão na fila
            ResultSet rs = stmt.executeQuery("SELECT count(codigo) as c from fila");

            // Passar para a proxima linha
            rs.next();   
            current_cod = rs.getInt("c");
            
            
            // Verificar quantas pessoas foram vacinadas
            rs = stmt.executeQuery("SELECT count(codigo) as c from vacinados");

            // Passar para a proxima linha
            rs.next();
            current_cod += rs.getInt("c");
            
            rs.close();
            
            // Novo código irá ser o numero de pessoas vacinadas + pessoas fila + 1
            cod_utilizador += current_cod + 1;
            
            // Verificar se o nome do centro inserido existe
            ArrayList<String> listaCentros = getCentrosVacinacao();
            if (!listaCentros.contains(nomeC)){
                throw new Exception("Centro não existeñ");
            }
            
            // Inserir a pessoa na tabela pessoa
            stmt.executeUpdate("insert into pessoa values('"+cc+"','"+nomeP+"',"+idade+",'"+genero+"');");
            
            // Inserir a pessoa na fila
            stmt.executeUpdate("insert into fila values('"+nomeC+"','"+cc+"','"+cod_utilizador+"');");
            

            
            return cod_utilizador;
        
        }
        catch(Exception e){
            
            // Caso algum dos dados inseridos não estejam corretos
            return "erro";
        }
    }

    /**
     * Registar uma vacinação dado um código e o tipo/modelo de vacina
     * 
     * @param dados
     * @return
     * @throws java.rmi.RemoteException 
     */
    public boolean registarVacinacao(String dados) throws java.rmi.RemoteException{
        
        try{
            // Dividir o input por plicas 
            String [] auxPlicas = dados.split("'");
            
            // Remover o espaço inicial do codigo
            String [] auxEspaco = auxPlicas[0].split(" ");
            
            String codigo = auxEspaco[1];  
            String modeloVac = auxPlicas[1];
          
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            
            // Remover o utilizador com da fila
            int removido = stmt.executeUpdate("delete from fila where codigo='"+codigo+"';");
            
            if(removido!=1){
                throw new Exception ("\ncódigo não existe\n");
            }
            stmt.executeUpdate("insert into vacinados values ('"+codigo+"','"+sdf.format(new java.util.Date())+"','"+modeloVac+"');");
            // Inserir o utilizador na tabela de vacinados
            
            
            return true;
        }
        catch(Exception e){
            
            // Caso algum dos dados inseridos não estejam corretos
            return false;
        }
        
        
    }
    
    /**
     * Registar efeitos secundários dado um codigo e uma descricao
     * 
     * @param dados
     * @return
     * @throws java.rmi.RemoteException 
     */
    public boolean registarEfeitosSecundarios(String dados) throws java.rmi.RemoteException {
        
        try{
            // Dividir o input por plicas 
            String [] auxPlicas = dados.split("'");
            
            // Remover o espaço inicial do codigo
            String [] auxEspaco = auxPlicas[0].split(" ");

            String codigo = auxEspaco[1];  
            String efeito = auxPlicas[1];
            
            // Guardar o tipo/modelo da vacina
            String modeloVac = "";
           
            // Procurar qual o tipo/modelo da vacina com que o utilizador o dado codigo foi vacinado
            ResultSet rs = stmt.executeQuery("select modeloVac from vacinados where codigo='"+codigo+"';");
            
            // Passar para a proxima linha
            rs.next();
            modeloVac = rs.getString("modeloVac");
            
            rs.close();
                  
            // Inserir efeito Secundário
            stmt.executeUpdate("insert into efeitoS values ('"+codigo+"','"+efeito+"','"+modeloVac+"');");
            
            return true;
        }
        catch(Exception e){
            
            // Caso algum dos dados inseridos não estejam corretos
            return false;
        }
    }
    
   /**
     * Devolve uma ArrayList com os dados associados às vacinas existentes
     * 
     * @return
     * @throws java.rmi.RemoteException 
     */
    public ArrayList<String> getDadosVacinados() throws java.rmi.RemoteException {
        
        // Guardar os dados para o return
        ArrayList<String> dadosVacinas = new ArrayList<>();
        
        try{
            // Guardar todos os tipos de vacinas existentes
            ArrayList<String> modelosVacinas = new ArrayList<>();
            // Guardar os dados referentes a um tipo de vacina
            ArrayList<String> dadosModelo = new ArrayList<>();
            
            // Busca todos os modelos que existem
            ResultSet rs = stmt.executeQuery("select distinct modeloVac as m from vacinados;");
            
            while(rs.next()){
                
                // adicionados aos tipos de vacinas
                modelosVacinas.add(rs.getString("m"));
            }
            
            rs.close();
            
            for(String vacinaStr : modelosVacinas){
                
                // Por cada tipo de vacina vamos buscar os dados associados
                dadosModelo = getDadosVacinados(" '"+vacinaStr+"'");
                
                for( String dado: dadosModelo){
                    
                    // Adicionamos esses dados à lista final
                    dadosVacinas.add(dado);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return dadosVacinas;
    }

    /**
     * Devolve os dados de vacinaçao referentes a um modelo de vacina
     * 
     * @param modeloVac
     * @return
     * @throws java.rmi.RemoteException 
     */
    public ArrayList<String> getDadosVacinados(String modeloVac) throws java.rmi.RemoteException {
        
        ArrayList<String> dadosVacina = new ArrayList<>();
        String nVacinados;
        String nEfeitoS;
    
        try{
            
            modeloVac = modeloVac.split("'")[1];
            
            // Adicionar o modelo da vacina aos dados
            dadosVacina.add(modeloVac);
            
            ResultSet rs = stmt.executeQuery("select count(codigo) as c from vacinados where modeloVac='"+modeloVac+"';");
            
            if(rs.next()){
                nVacinados = rs.getString("c");
                
                // Adicionar o numero de vacinados aos dados
                dadosVacina.add(nVacinados);
            }
            
            rs = stmt.executeQuery("select count(codigo) as c from efeitos where modeloVac='"+modeloVac+"';");
            
            if(rs.next()){
                nEfeitoS = rs.getString("c");
                
                // Adicionar o numero de efeitos secundarios aos dados
                dadosVacina.add(nEfeitoS);
            }
            
            rs.close();
        }
        catch(Exception e){
           
        }
        
        return dadosVacina;
    }
}
