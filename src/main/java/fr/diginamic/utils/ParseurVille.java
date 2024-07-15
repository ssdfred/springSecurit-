package fr.diginamic.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.diginamic.dto.DepartementDto;
import fr.diginamic.dto.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.mappers.VilleMapper;
import fr.diginamic.repository.DepartementRepository;
import fr.diginamic.repository.VilleRepository;
import fr.diginamic.service.DepartementService;

@Component
public class ParseurVille {

    private final VilleRepository villeRepository;
    private final DepartementRepository departementRepository;

    @Autowired
    public ParseurVille(VilleRepository villeRepository, DepartementRepository departementRepository) {
        this.villeRepository = villeRepository;
        this.departementRepository = departementRepository;
    }

    
    
    /**
     * Ajoute une entité Ville au département spécifié.
     *
     * @param departement Le département auquel la ville doit être ajoutée.
     * @param villeDto    Le DTO Ville contenant les données à ajouter.
     */
    public void ajoutVille(Departement departement, VilleDto villeDto) {
        // Valider la longueur du nom de la ville
        if (villeDto.getNom().length() < 2 || villeDto.getNom().length() > 100) {
            System.out.println("Longueur de nom de ville invalide : " + villeDto.getNom());
            return;
        }

        Ville ville = VilleMapper.toEntity(villeDto);
        ville.setDepartement(departement);
        villeRepository.save(ville);
        //System.out.println("Ville enregistrée : " + ville.getNom() + " dans le département : " + departement.getCode());
    }
    /**
     * Ajoute un département et ses villes à partir d'un DTO du département.
     *
     * @param departementDto Le DTO du département contenant les données à ajouter.
     */
    public void ajoutDepartementEtVilles(DepartementDto departementDto) {
        try {
            Departement departement = new Departement();
            departement.setCode(departementDto.getCode());
            departement.setNom(departementDto.getNom());
            departement = departementRepository.save(departement);
            System.out.println("Département enregistré : " + departement.getCode());

            for (VilleDto villeDto : departementDto.getVilles()) {
                ajoutVille(departement, villeDto);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'enregistrement du département " + departementDto.getCode() + " : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ajoute une ligne représentant une ville au DTO du département spécifié.
     *
     * @param departementDto Le DTO du département auquel la ville doit être ajoutée.
     * @param ligne          La ligne à partir de laquelle nous extrayons les données de la ville.
     */
    public void ajoutLigne(DepartementDto departementDto, String ligne) {
        String[] morceaux = ligne.split(";");
        if (morceaux.length < 8) {
            System.err.println("Ligne incorrecte : " + ligne);
            return;
        }
        String codeRegion = morceaux[0];
        String nomRegion = morceaux[1];
        String codeDepartement = morceaux[2];
        String codeCommune = morceaux[5];
        String nomCommune = morceaux[6];
        String population = morceaux[7];

        try {
            int populationTotale = Integer.parseInt(population.replace(" ", "").trim());

            // Créer le VilleDto avec toutes ses données
            VilleDto villeDto = new VilleDto();
            villeDto.setCodeRegion(codeRegion);
            villeDto.setNomRegion(nomRegion);
            villeDto.setCodeDepartement(codeDepartement);
            villeDto.setCodeVille(codeCommune);
            villeDto.setNom(nomCommune);
            villeDto.setPopulationTotale(populationTotale);

            // Ajouter le VilleDto au DepartementDto
            departementDto.setCode(codeDepartement);
            departementDto.setNom(nomRegion);
            departementDto.addVille(villeDto);

            // Log des détails de la ville extraite
           // System.out.println("Ville extraite : " + nomCommune + " dans le département : " + codeDepartement);

        } catch (NumberFormatException e) {
            System.err.println("Erreur de parsing de la population pour la ligne : " + ligne);
            e.printStackTrace();
        }
    }
}
