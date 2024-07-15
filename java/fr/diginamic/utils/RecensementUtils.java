package fr.diginamic.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.diginamic.dto.DepartementDto;

@Component
public class RecensementUtils {
    private final ParseurVille parseurVille;

    @Autowired
    public RecensementUtils(ParseurVille parseurVille) {
        this.parseurVille = parseurVille;
    }

    public DepartementDto lire(String cheminFichier) {
        DepartementDto departementDto = new DepartementDto();

        List<String> lignes;
        try {
            File file = new File(cheminFichier);
            lignes = FileUtils.readLines(file, "UTF-8");

            // Supprimer la ligne d'en-tête avec les noms des colonnes
            if (!lignes.isEmpty()) {
                lignes.remove(0);
            }

            for (String ligne : lignes) {
                System.out.println("Traitement de la ligne : " + ligne);
                parseurVille.ajoutLigne(departementDto, ligne);
            }
            return departementDto;

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la lecture du fichier", e);
        }
    }
}
