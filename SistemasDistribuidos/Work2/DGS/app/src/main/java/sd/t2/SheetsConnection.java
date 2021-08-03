package sd.t2;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SheetsConnection{
    private static Sheets sheetsService;
    private static String APPLICATION_NAME = "Google Sheets Example";
    private static String SPREADSHEET_ID = "1oiK4cz3LIqNYTk4lIQz8KuV2Gv1OFEBQNvBLE_cjwHI";

    private static Credential authorize() throws IOException, GeneralSecurityException{

        InputStream in = SheetsConnection.class.getResourceAsStream("/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
            JacksonFactory.getDefaultInstance(), new InputStreamReader(in)
        );
        
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
            clientSecrets, scopes)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
            .setAccessType("offline")
            .build();
        
            Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver())
                .authorize("user");
        
        return credential;
    }

    public static Sheets getSheetsService()throws IOException, GeneralSecurityException{
        
        Credential credential = authorize();

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();


    }
    
    // Devole um array do tipo [[nome,idade,e-mail,centro,data,codidog],...] a partir da linha i
    public ArrayList<String[]> getCidadaos(int i) throws Exception {

        ArrayList<String[]> arrCidadaos = new ArrayList<>();
        
        sheetsService = getSheetsService();

        // LER DATA
        String range = "Data!A"+i+":F";

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if(values == null || values.isEmpty()){
            System.out.println("\nJá estava atualizado");
        }
        else{
            for(List row: values){

                String[] arr = {row.get(0).toString(), row.get(1).toString(), row.get(2).toString(),row.get(3).toString(),row.get(4).toString(),row.get(5).toString()};
                
                arrCidadaos.add(arr);
    
            }
        }

        return arrCidadaos;
    }
}