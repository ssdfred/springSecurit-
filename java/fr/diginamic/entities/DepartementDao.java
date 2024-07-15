package fr.diginamic.entities;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class DepartementDao {

    @PersistenceContext
    private EntityManager em;

    public List<Departement> findAll() {
        return em.createQuery("SELECT d FROM Departement d", Departement.class)
                 .getResultList();
    }

    public Departement findById(int idDepartement) {
        return em.find(Departement.class, idDepartement);
    }

    public Departement findByNom(String nom) {
        List<Departement> departements = em.createQuery("from Departement where nom = :nom", Departement.class)
                .setParameter("nom", nom)
                .getResultList();
        return departements.isEmpty() ? null : departements.get(0);
    }
    public Departement save(Departement departement) {
        em.persist(departement);
        return departement;
    }

    public Departement update(Departement departement) {
        return em.merge(departement);
    }

    public void delete(Departement departement) {
        em.remove(departement);
    }
    public List<Ville> findTopNVilles(int id, int n) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :departementId ORDER BY v.nbHabitants DESC", Ville.class);
        query.setParameter("departementId", id);
        query.setMaxResults(n);
        return query.getResultList();
    }

    public List<Ville> findVillesByPopulationRange(int id, int min, int max) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :departementId AND v.nbHabitants BETWEEN :min AND :max", Ville.class);
        query.setParameter("departementId", id);
        query.setParameter("min", min);
        query.setParameter("max", max);
        return query.getResultList();
    }
}