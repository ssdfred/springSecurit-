package fr.diginamic.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.dto.DepartementDto;
import fr.diginamic.utils.ParseurVille;

@Service
public class CsvFileReaderService {

    private final ParseurVille parseurVille;

    @Autowired
    public CsvFileReaderService(ParseurVille parseurVille) {
        this.parseurVille = parseurVille;
    }

    @Transactional
    public void readAndSaveCsvData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            DepartementDto departementDto = new DepartementDto();
            while ((ligne = br.readLine()) != null) {
                parseurVille.ajoutLigne(departementDto, ligne);
            }
            parseurVille.ajoutDepartementEtVilles(departementDto);
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier CSV : " + e.getMessage());
            e.printStackTrace();
            // Relever l'exception pour ne pas masquer les erreurs graves
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("Erreur lors du traitement des données CSV : " + e.getMessage());
            e.printStackTrace();
            // Relever l'exception pour ne pas masquer les erreurs graves
            throw new RuntimeException(e);
        }
    }
}
