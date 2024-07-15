package fr.diginamic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Integer> {
	 List<Ville> findByPopulationTotaleGreaterThanEqual(int population);
	List<Ville> findByPopulationTotaleGreaterThan(int minPopulation);
	List<Ville> findByDepartementCodeAndPopulationTotaleBetween(String code, int minPopulation, int maxPopulation);
	List<Ville> findByDepartement(Departement departement);
	//Optional<Ville> findByDepartementCode(String code, Departement departement);
	//Optional<Ville> findByDepartementCode(String code, Departement departement);
	Optional<Ville> findByNomAndDepartement(String nom, Departement departement);
	
	
	@Query("SELECT v FROM Ville v WHERE v.departement.id = :departementId AND v.populationTotale BETWEEN :min AND :max")
	List<Ville> findVillesByPopulationTotaleRange(@Param("departementId") int departementId, @Param("min") int min,
			@Param("max") int max);

	//List<Ville> findByPopulationTotaleGreaterThanDepartementAndIdOrderByDESC(@Param("departement_id") int departement_id,@Param("min") int min, @Param("max") int max);
	//List<Ville> findTopNVillesByDepartementId();
	
	//List<Ville> findVillesByPopulationRange(int id, int min, int max);
}