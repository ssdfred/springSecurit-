package fr.diginamic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer> {

	Optional<Departement> findById(int id);

	Departement findByCode(String code);

	Departement findByNom(String nom);

	// List<Ville>
	// findTopNVillesByDepartementOrderByPopulationTotaleDesc(@Param("id") int
	// id,@Param("min") int min, @Param("max") int max);

	// Méthode pour récupérer les villes d'un département triées par population
	// totale dans une plage donnée
	@Query("SELECT v FROM Ville v WHERE v.departement.id = :departementId AND v.populationTotale BETWEEN :min AND :max ORDER BY v.populationTotale DESC")
	List<Ville> findVillesByPopulationTotaleRange(@Param("departementId") int departementId, @Param("min") int min,
			@Param("max") int max);

	// Méthode pour récupérer les N premières villes d'un département triées par
	// population totale
	@Query("SELECT v FROM Ville v WHERE v.departement.id = :departementId ORDER BY v.populationTotale DESC")
	List<Ville> findTopNVillesByDepartementOrderByPopulationTotaleDesc(@Param("departementId") int departementId);
	// List<Ville> findByCodeDepartement(String codeDepartement);
	// Departement findByDepartementCode(String code, Departement departement);

//    @Query("SELECT v FROM Ville v WHERE v.departement_id = :departement_Id ORDER BY v.populationTotale DESC")
//    List<Ville> findTopNVillesByDepartementId(@Param("departement_Id") int departementId);

}
