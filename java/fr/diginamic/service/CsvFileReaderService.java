package fr.diginamic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.dto.DepartementDto;
import fr.diginamic.dto.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.mappers.DepartementMapper;
import fr.diginamic.repository.DepartementRepository;
import fr.diginamic.utils.ParseurVille;
import fr.diginamic.utils.RecensementUtils;

import java.io.File;
import java.util.List;

@Service
public class CsvFileReaderService {

    private final DepartementRepository departementRepository;
    private final RecensementUtils recensementUtils;
    private final ParseurVille parseurVille;

    @Autowired
    public CsvFileReaderService(DepartementRepository departementRepository,
                                RecensementUtils recensementUtils, ParseurVille parseurVille) {
        this.departementRepository = departementRepository;
        this.recensementUtils = recensementUtils;
        this.parseurVille = parseurVille;
    }

    @Transactional
    public void readAndSaveCsvData(String csvFileName) {
        File csvFile = new File(csvFileName);
        String filePath = csvFile.getAbsolutePath();

        try {
            DepartementDto departementDto = recensementUtils.lire(filePath);

            if (departementDto == null) {
                System.out.println("Erreur lors de la lecture du fichier CSV.");
                return;
            }

            // Log des détails du département
            System.out.println("Traitement du département : " + departementDto.getCode());

            // Rechercher si le département existe déjà en base
            Departement existingDepartement = departementRepository.findByCode(departementDto.getCode());

            if (existingDepartement != null) {
                System.out.println("Département existant trouvé : " + existingDepartement.getCode());

                // Mettre à jour les villes du département existant avec les nouvelles villes du DTO
                List<VilleDto> villesDto = departementDto.getVilles();
                for (VilleDto villeDto : villesDto) {
                    System.out.println("Ajout de la ville " + villeDto.getNom() + " au département existant " + existingDepartement.getCode());
                    parseurVille.ajoutVille(existingDepartement, villeDto); // Utilisation du parseur pour ajouter la ville
                }

                // Mettre à jour le nom du département si nécessaire
                existingDepartement.setNom(departementDto.getNom());
                existingDepartement = departementRepository.save(existingDepartement);
                System.out.println("Département mis à jour : " + existingDepartement);
            } else {
                // Enregistrer le nouveau département en base
                Departement newDepartement = DepartementMapper.toEntity(departementDto);
                newDepartement = departementRepository.save(newDepartement);
                System.out.println("Nouveau département enregistré : " + newDepartement);

                // Enregistrer les villes du nouveau département
                List<VilleDto> villesDto = departementDto.getVilles();
                for (VilleDto villeDto : villesDto) {
                    System.out.println("Ajout de la ville " + villeDto.getNom() + " au nouveau département " + newDepartement.getCode());
                    parseurVille.ajoutVille(newDepartement, villeDto); // Utilisation du parseur pour ajouter la ville
                }
            }

            System.out.println("Les données du fichier CSV ont été enregistrées avec succès.");
        } catch (Exception e) {
            // Logguer l'exception
            System.err.println("Erreur lors de l'enregistrement des données du fichier CSV : " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-lancer l'exception pour assurer le rollback de la transaction
        }
    }
}
