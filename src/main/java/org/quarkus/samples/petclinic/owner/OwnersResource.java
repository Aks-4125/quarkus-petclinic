package org.quarkus.samples.petclinic.owner;

import org.quarkus.samples.petclinic.system.Templates;
import org.quarkus.samples.petclinic.visit.Visit;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.TemplateInstance;

@Path("/owners")
public class OwnersResource {

    @GET
    @Path("/find")
    @Produces(MediaType.TEXT_HTML)
    /**
     * Renders the findOwners.html
     * 
     * @return
     */
    public TemplateInstance findTemplate() {
        return Templates.findOwners(Collections.EMPTY_LIST);
    }

    @GET
    @Path("new")
    @Produces(MediaType.TEXT_HTML)
    /**
     * Renders the createOrUpdateOwnerForm.html
     * 
     * @return
     */
    public TemplateInstance createTemplate() {
        return Templates.createOrUpdateOwnerForm(null);
    }

    @GET
    @Path("{ownerId}/edit")
    @Produces(MediaType.TEXT_HTML)
    /**
     * Renders the createOrUpdateOwnerForm.html
     * 
     * @return
     */
    public TemplateInstance editTemplate(@PathParam("ownerId") Long ownerId) {
        return Templates.createOrUpdateOwnerForm(Owner.findById(ownerId));
    }

    @GET
    @Path("{ownerId}")
    @Produces(MediaType.TEXT_HTML)
    /**
     * Renders the createOrUpdateOwnerForm.html
     * 
     * @return
     */
    public TemplateInstance showOwner(@PathParam("ownerId") Long ownerId) {
        return Templates.ownerDetails(Owner.findById(ownerId));
    }

    @POST
    @Path("new")
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    /**
     * Renders the createOrUpdateOwnerForm.html
     * 
     * @return
     */
    public TemplateInstance processCreationForm(@BeanParam Owner owner) {
        owner.persist();
        return Templates.ownerDetails(owner);
    }
    
    @POST
    @Path("{ownerId}/edit")
    @Transactional
    @Produces(MediaType.TEXT_HTML)
    /**
     * Renders the createOrUpdateOwnerForm.html
     * 
     * @return
     */
    public TemplateInstance processUpdateOwnerForm(@BeanParam Owner owner, @PathParam("ownerId") Long ownerId) {
        // We need to reattach the Owner object. Since method is transactional, the update occurs automatically.
        return Templates.ownerDetails(owner.attach());
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    /**
     * Process the findOwners form
     */
    public TemplateInstance processFindForm(@QueryParam("lastName") String lastName) {

        Collection<Owner> owners = null;

        // allow parameterless GET request for /owners to return all records
        if (lastName == null || "".equals(lastName.trim())) {
            owners = Owner.listAll(); // empty string signifies broadest possible search
        } else {
            owners = Owner.findByLastName(lastName);
        }

        // find owners by last name
        if (owners.isEmpty()) {
            // no owners found
            return Templates.findOwners(Arrays.asList("lastName not found"));
        }
        if (owners.size() == 1) {
            // 1 owner found
            Owner owner = owners.iterator().next();
            return Templates.ownerDetails(setVisits(owner));
        }
        
        return Templates.ownersList(owners);

    }


    protected Owner setVisits(Owner owner) {
        for (Pet pet : owner.pets) {
            pet.setVisitsInternal(Visit.findByPetId(pet.id));
        }
        return owner;
    }

}
