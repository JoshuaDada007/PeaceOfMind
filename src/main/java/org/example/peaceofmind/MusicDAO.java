package org.example.peaceofmind;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class MusicDAO {
    @PersistenceContext
    private EntityManager em;
    public void create(Music music) {
        em.persist(music);
    }
    public Music get(int id){
        return em.find(Music.class, id);
    }
    public void update(Music music){
        em.merge(music);
    }
    public void delete(int id){
        em.remove(em.find(Music.class, id));
    }

    public List<Music> getAll(){
        return em.createQuery("select id from Music m").getResultList();
    }
}
