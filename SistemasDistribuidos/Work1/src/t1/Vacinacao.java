package t1;

import java.util.ArrayList;

/**
 *
 * @author José Santos, Filipe Alfaiate
 */

public interface Vacinacao extends java.rmi.Remote {
    
    public ArrayList<String> getCentrosVacinacao() throws java.rmi.RemoteException;
    
    public int getTamanhoFilaEspera(String centroV) throws java.rmi.RemoteException;
    
    public String inscricaoVacinacao(String dados) throws java.rmi.RemoteException;
    
    public boolean registarVacinacao(String dados) throws java.rmi.RemoteException;
    
    public boolean registarEfeitosSecundarios(String dados) throws java.rmi.RemoteException;
    
    public ArrayList<String> getDadosVacinados() throws java.rmi.RemoteException;
    
    public ArrayList<String> getDadosVacinados(String modeloVac) throws java.rmi.RemoteException;
    
    
}
