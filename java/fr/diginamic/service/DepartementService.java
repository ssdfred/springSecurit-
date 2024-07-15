package fr.diginamic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.dto.DepartementDto;
import fr.diginamic.dto.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.mappers.DepartementMapper;
import fr.diginamic.mappers.VilleMapper;
import fr.diginamic.repository.DepartementRepository;

@Service
@Transactional
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    public void ajouterDepartements(List<DepartementDto> departementDtos) {
        for (DepartementDto dto : departementDtos) {
            Departement departement = DepartementMapper.toEntity(dto);

            // Vérifier si le département existe déjà
            Departement existingDepartement = departementRepository.findByCode(departement.getCode());
            if (existingDepartement != null) {
                // Le département existe déjà, on pourrait choisir de le mettre à jour ou ignorer cette insertion
                // Ici, nous choisissons d'ignorer
                continue;
            }

            // Sauvegarder le département
            departement = departementRepository.save(departement);

            // Ajouter les villes associées au département
            for (VilleDto villeDto : dto.getVilles()) {
                Ville ville = VilleMapper.toEntity(villeDto);
                // Vérifier si la ville existe déjà dans ce département
                if (departement.getVilles().stream().anyMatch(v -> v.getNom().equals(ville.getNom()))) {
                    // La ville existe déjà dans ce département, on pourrait choisir de la mettre à jour ou ignorer cette insertion
                    // Ici, nous choisissons d'ignorer
                    continue;
                }
                ville.setDepartement(departement);
                departement.getVilles().add(ville);
            }
        }
    }
}