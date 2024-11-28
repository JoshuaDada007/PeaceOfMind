package org.example.peaceofmind;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class QuotesDAO {
    @PersistenceContext
    private EntityManager em;

    public void create(Quotes quotes){
        em.persist(quotes);
    }
    public Quotes get(int id){
        return em.find(Quotes.class, id);
    }

    public void update(Quotes quotes){
        em.merge(quotes);
    }

    public void delete(int id){
         em.remove(em.find(Quotes.class, id));
    }
    public List<Integer> getAll(){
        //this is the correct way to retrieve all ids from the table
        return em.createNativeQuery("SELECT id FROM Quotes").getResultList();

    }

}
