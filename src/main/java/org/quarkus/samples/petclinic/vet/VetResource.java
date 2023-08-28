package org.quarkus.samples.petclinic.vet;

import io.quarkus.qute.TemplateInstance;
import org.quarkus.samples.petclinic.system.TemplatesLocale;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/vets.html")
public class VetResource {

    @Inject
    TemplatesLocale templates;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showResourcesVetPage() {
        List<Vet> vets = Vet.listAll();
        System.out.println(vets);
        return templates.vetList(vets);
    }

    @GET
    @Path("/vets")
    public Vets showResourcesVetList() {
        Vets vets = new Vets();
        vets.getVetList().addAll(Vet.listAll());
        return vets;
    }

}
