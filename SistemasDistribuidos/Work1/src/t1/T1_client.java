package t1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author José Santos, Filipe Alfaiate
 */
public class T1_client {

    public static void main(String [] args){
        
        String regHost, regPort;
        
        // Scanner utilizado para Input
        Scanner s = new Scanner(System.in);
  
        if (args.length != 2) {
        	
            System.err.println("Missing Parameter: registryHost registryPort");
            System.exit(1);
        }
        
        regHost= args[0];
	regPort= args[1];
        
        try{
            
            // Mostrar o menu ao utilizador
            showMenu();
            
            // rmi
            Vacinacao vac = 
                  (Vacinacao) java.rmi.Naming.lookup("rmi://" 
                      + regHost +":"+regPort +"/vacinacao");
            
            // ciclo
            for(;;){
                
                System.out.print("$ ");
                switch(s.next().toUpperCase()){
                    
                    case "CCV": // Listar centros de vacinação existentes
                        
                        // Guardar todos os centros recebidos do método getCentrosVacinacao
                        ArrayList<String> listaCentros = vac.getCentrosVacinacao();
                        
                        
                        if(! listaCentros.isEmpty()){ 
                            
                            System.out.println(); // print estético
                            
                            // Printar cada nome dentro do ArrayList
                            for(String nomeCentro: listaCentros){
                                
                                System.out.println(nomeCentro);
                            }
                            
                            System.out.println(); // print estético
                        }
                        else{   
                            System.out.println("\nNão existem centros de vacinação\n");
                        }

                        break;
                        
                    case "CFE": // Listar lista de espera de um dado centro de vacinação
                        
                        String centro = s.nextLine(); // receber o nome do centro de vacinação
                        
                        // Guardar tamanha da fila de espera recebido do método getTamanhoFilaEspera
                        int fila_size = vac.getTamanhoFilaEspera(centro);
                        
                        if (fila_size!=-1){ // Se o centro inserido existir
                            System.out.println("\n"+ fila_size + ((fila_size==1) ? " pessoa ": " pessoas") + " na fila de espera no "+centro+"\n");
                        }
                        else{
                            System.out.println("\nCentro inserido não existe, utilize CCV para ver os centros de vacinação existentes. (tem de colocar o nome entre 'plicas')\n");
                        }
                        
                        break;
                    
                    case "INV": // Inscrição para vacinação
                        
                        String dados = s.nextLine(); // ler do input os dados necessários
                        
                        // Recebe do método inscricaoVacinacao o código associado ao utilizador
                        String cod = vac.inscricaoVacinacao(dados);
                        
                        System.out.println(cod.equals("erro") ? "\nProblema com os dados inseridos\n" : "\nInserido na fila, com o codigo "+cod+"\n");
                        
                        break;
                    
                    case "RGV": // Registar vacinação
                        
                        dados = s.nextLine(); // ler do input os dados necessários
 
                        System.out.println(vac.registarVacinacao(dados)? "\nVacinado com sucesso\n":"\nProblema com os dados inseridos\n");
                        
                        break;
                    
                    case "EFS": // Introduzir efeito(s) secundários
                        
                        dados = s.nextLine(); // ler do input os dados necessários
                       
                        System.out.println(vac.registarEfeitosSecundarios(dados) ? "\nEfeito secundário registado com sucesso\n": "\nProblema com os dados inseridos\n");
                        
                        break;

                    case "LST": // Listar informações referentes às vacinas
                        
                        // Array utilizado para guardar os dados das vacinas
                        ArrayList<String> dadosVacinas = new ArrayList<>();
                        
                        String modeloVac = s.nextLine(); // ler do input o modelo da vacina
                        
                        dadosVacinas = (modeloVac.length()>0) ? vac.getDadosVacinados(modeloVac) : vac.getDadosVacinados();
                        
                        if(dadosVacinas.isEmpty()){
                            System.out.println("\nProblema com os dados inseridos, coloque o tipo de vacina entre plicas\n");
                        
                        }
                        else{
                            printLista(dadosVacinas);
                        }
                            
                        break;
                    
                    case "HLP": // Listar o menu de comandos
                        
                        showMenu();
                        break;
                    
                    case "EXT": // Sair do programa
                        
                        s.close();
                        System.exit(1);
                        break;
                    
                    default: // Caso o comando inserido nao exista
                        
                        s.nextLine(); // Limpar o scanner
                        System.out.println("\nComando não válido - HLP para mostrar comandos\n\n");
                        break;
                        
                }// fim switch
            }// fim ciclo
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // Print auxiliar no output dos dados relativos às vacinas (Comando LST)
    public static void printLista(ArrayList<String> l){
             
        for(int i = 0; i<l.size(); i+=3){
            
            System.out.println("Vacina - "+l.get(i)+" Nº Vacinados - "+l.get(i+1)+" Nº Efeitos Secundarios - "+l.get(i+2));
        
        }
        System.out.println("");
    }
    
    // Print do menu de comandos (Comando HLP)
    public static void showMenu(){
        System.out.println("\t\t\t\t--- Menu ---\n"
                + "COMANDO ARGUMENTO(s) - DESCRICAO \n"
                + "CCV                                - Consultar centros de vacinacao\n"
                + "CFE 'Centro'                       - Consultar fila de espera num centro\n"
                + "INV 'Centro' CC Nome Idade Genero  - Inscricao da vacinacao\n"
                + "RGV  Codigo 'Modelo Vac'           - Registar a realizacao de vacinacao prevista para a inscricao com o codigo X\n"
                + "EFS  Codigo 'Efeito'               - Registar efeitos secundarios na vacinacao\n"
                + "LST                                - Listar nº total de vacinados e nº de casos com efeito secundario\n"
                + "LST 'Modelo Vac'                   - Listar nº total de vacinados e nº de casos com efeito secundario por modelo de vacina\n"
                + "HLP                                - Mostrar menu de Comandos\n"
                + "EXT                                - Sair\n\n"
                + "Nota: Argumentos que estão entre plicas, têm de estar entre plicas\n\n");
    }       
}
