package sd.rest;

import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Singleton
@Path(value = "/listaVacinas")
public class ListaVacinasResource {
    
    private ListaVacinas listaVacinas;
    
    public ListaVacinasResource(){ 
    }
    
    @GET
    @Produces({"application/json","application/xml"})
    public synchronized ListaVacinas getVacinas() {
        return listaVacinas;
    }
    
    @PUT
    @Consumes({"application/json", "application/xml"})
    public synchronized void putVacinas(ListaVacinas listaVacinas) {
        this.listaVacinas = listaVacinas;
    }
    
    @Path("/add")
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public synchronized ListaVacinas addVacina(@QueryParam("nome") String nome,
                        @QueryParam("nVacinas") int nVacinas
					) {
        listaVacinas.add(new Vacina(nome,nVacinas));
	return listaVacinas;
    }
    
}