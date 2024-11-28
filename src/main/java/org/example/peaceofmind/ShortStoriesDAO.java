package org.example.peaceofmind;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ShortStoriesDAO {
    @PersistenceContext
    private EntityManager em;

    public void create(ShortStories s) {
        em.persist(s);
    }
    public ShortStories read(int id) {
        return em.find(ShortStories.class, id);
    }
    public void update(ShortStories s) {
        em.merge(s);
    }
    public void delete(int id) {
        em.remove(em.find(ShortStories.class, id));
    }
}
