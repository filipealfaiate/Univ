package t1;


import java.sql.Statement;
import java.rmi.registry.*;

/**
 *
 * @author José Santos, Filipe Alfaiate
 */
public class T1_server {
    
    private int serverPort;
    private Statement stmt;
    
    /**
     * Construtor
     * 
     * @param serverPort
     * @param stmt 
     */
    public T1_server(int serverPort, Statement stmt){
        this.serverPort = serverPort;
        this.stmt = stmt;
    }
    
    public static void main(String [] args) throws Exception{
        
        // Variaves para ligar à base de dados
        String host, db, user, pwd;
       
        int regPort;
        
        if (args.length<5){
            System.err.println("Missing parameter: PORT HOST DB USER PWD");
            System.exit(0);
            
        }
        
        regPort = Integer.parseInt(args[0]);
        
        host = args[1];
        db = args[2];
        user = args[3];
        pwd = args[4];
        
        PostgresConnector pc = new PostgresConnector(host, db, user, pwd);        
        
        // estabelecer a ligacao ao SGBD
        pc.connect();
        Statement stmt = pc.getStatement();
        
        try{
            // criar um objeto remoto
            Vacinacao vac = new VacinacaoImpl(stmt);
            
            // usar o Registry local (mesma máquina) e porto regPort
            Registry registry = LocateRegistry.getRegistry(regPort);
            
            // Fazer o bind, com o nome do serviço e o objeto remoto
            registry.rebind("vacinacao", vac);

            System.out.println("RMI object bound to service in registry");
            System.out.println("servidor pronto");
        }
        catch(Exception e){
            System.out.println("Problema na conexão\n");
        }
        
    }
}
