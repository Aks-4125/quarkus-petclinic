package org.quarkus.samples.petclinic.register;

import io.quarkus.qute.TemplateInstance;
import org.quarkus.samples.petclinic.system.TemplatesLocale;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/register")
public class RegisterResource {
    
    @Inject
    TemplatesLocale templates;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return templates.register();
    }

}
