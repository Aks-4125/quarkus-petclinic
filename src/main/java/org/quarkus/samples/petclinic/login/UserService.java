package org.quarkus.samples.petclinic.login;

import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public User getUserByEmailAndPassword(String email, String password) {
        try {
            User userInfo = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if (BCrypt.checkpw(password, userInfo.password)) {
                return userInfo;
            } else {
                return null;
            }
        } catch (NoResultException ex) {
            return null;
        }
    }
}
