package fr.diginamic.entities;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Repository
@Transactional
public class VilleDao {
    @PersistenceContext
    private EntityManager em;
    
    public List<Ville> findAll() {
        return em.createQuery("SELECT v FROM Ville v", Ville.class)
                 .getResultList();
    }
    public List<Ville> findByDepartementId(int departementId) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :departementId", Ville.class);
        query.setParameter("departementId", departementId);
        return query.getResultList();
    }

    public Ville findById(int id) {
        return em.find(Ville.class, id);
    }

    public Ville save(Ville ville) {
        em.persist(ville);
        return ville;
    }



    public Ville update(Ville ville) {
        return em.merge(ville);
    }

    public void delete(Ville ville) {
        em.remove(ville);
    }
}
