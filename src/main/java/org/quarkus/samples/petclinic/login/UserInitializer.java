package org.quarkus.samples.petclinic.login;

import io.quarkus.runtime.Startup;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Initializes sample user data during application startup.
 */
@Startup
@ApplicationScoped
public class UserInitializer {

    @Inject
    EntityManager entityManager;

    /**
     * Initializes sample users in the database.
     */
    @PostConstruct
    @Transactional
    void init() {
        initializeUsers();
    }

    @Transactional
    public void initializeUsers() {
        String password = hashPassword();
        User user1 = new User();
        user1.email = "user1@example.com";
        user1.password = password;
        user1.role = "user";
        entityManager.persist(user1);

        User user2 = new User();
        user2.email = "user2@example.com";
        user2.password = password;
        user2.role = "user";
        entityManager.persist(user2);
    }

    private String hashPassword() {
        return BCrypt.hashpw("changeme", BCrypt.gensalt());
    }
}