package org.quarkus.samples.petclinic.login;

import io.quarkus.qute.TemplateInstance;
import org.quarkus.samples.petclinic.system.TemplatesLocale;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collections;

@Path("/")
public class LoginResource {

    @Inject
    TemplatesLocale templates;

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    /**
     * Renders the login.html
     *
     * @return
     */
    public TemplateInstance loginTemplate() {
        return templates.login(Collections.EMPTY_LIST);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    /**
     * Process the findOwners form
     */
    public TemplateInstance processLoginForm(@FormParam("email") String email, @FormParam("password") String password) {
        User user = userService.getUserByEmailAndPassword(email, password);
        if (user != null) {
            // Successful login, redirect to welcome page
            return templates.welcome();
        } else {
            // Invalid credentials, show an error message or redirect to login page
            return templates.login(Arrays.asList("Invalid email or password"));
        }
    }

}
