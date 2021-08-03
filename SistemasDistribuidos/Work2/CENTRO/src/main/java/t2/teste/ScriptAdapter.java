package t2.teste;

import com.google.api.services.script.Script;
import com.google.api.services.script.model.ExecutionRequest;
// import com.google.api.services.script.model.Operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptAdapter {

    private String scriptId;
    private Script service;

    public ScriptAdapter(String scriptId) {
        this.scriptId = scriptId;
        try {
            List<String> scopes = Arrays.asList(
                    "https://www.googleapis.com/auth/script.projects",
                    "https://www.googleapis.com/auth/drive",
                    "https://www.googleapis.com/auth/script.scriptapp",
                    "https://www.googleapis.com/auth/script.external_request",
                    "https://www.googleapis.com/auth/script.send_mail",
                    "https://www.googleapis.com/auth/spreadsheets"
            );
            this.service = GoogleCredential.getInstance(scopes)
                    .getScriptService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String email, String message) throws Exception {

        ExecutionRequest request = new ExecutionRequest().setFunction("sendEmail");
        List<Object> params = new ArrayList<>();
        params.add(email);
        params.add(message);
        request.setParameters(params);

        try {

            service.scripts().run(scriptId, request).execute();

            return;

        } catch (Exception e) {
            System.out.println("Erro a enviar e-mail\n");
        }


    }

    public void addCentro(String nomeCentro, String numeroMaxVacinas) throws Exception {

        ExecutionRequest request = new ExecutionRequest().setFunction("addCentro");

        List<Object> params = new ArrayList<>();
        params.add(nomeCentro);
        params.add(numeroMaxVacinas);
        request.setParameters(params);

        try{


            service.scripts().run(scriptId, request).execute();

        }
        catch(Exception e) {
            System.out.println("Erro a adicionar centro\n");
        }

    }

    public void addConfirmacao(String codigo) throws Exception {

        ExecutionRequest request = new ExecutionRequest().setFunction("addConfirmacao");
        List<Object> params = new ArrayList<>();
        params.add(codigo);
        request.setParameters(params);

        try{
            service.scripts().run(scriptId, request).execute();

        }
        catch(Exception e) {
            System.out.println("Erro a adicionar confirmação");
        }


    }

    public void addNegacao(String codigo) throws Exception {

        ExecutionRequest request = new ExecutionRequest().setFunction("addNegacao");
        List<Object> params = new ArrayList<>();
        params.add(codigo);
        request.setParameters(params);

        try{
            service.scripts().run(scriptId, request).execute();

        }
        catch(Exception e) {
            System.out.println("Erro a adicionar negação");
        }


    }
}
