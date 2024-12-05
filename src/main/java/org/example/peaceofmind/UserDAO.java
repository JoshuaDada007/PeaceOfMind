package org.example.peaceofmind;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserDAO {
    @PersistenceContext
    private EntityManager em;

    public void create(User user){
         em.persist(user);
    }
    public User get(int id){
        return em.find(User.class, id);
    }
    public void update(User user){
        em.merge(user);
    }
    public void delete(int id){
        em.remove(em.find(User.class, id));
    }
    public User getByToken(String token) {
        return (User) em.createNativeQuery("SELECT * FROM User WHERE token = ?", User.class)
                .setParameter(1, token)
                .getSingleResult();
    }



    public List<Integer> getAllUserIds(){
        return em.createNativeQuery("select id from User").getResultList();
    }



      }




