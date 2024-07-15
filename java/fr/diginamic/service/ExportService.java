package fr.diginamic.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import fr.diginamic.dto.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.mappers.VilleMapper;
import fr.diginamic.repository.DepartementRepository;
import fr.diginamic.repository.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class ExportService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;

    public List<VilleDto> findVillesWithMinPopulation(int minPopulation) {
        List<Ville> villes = villeRepository.findByPopulationTotaleGreaterThanEqual(minPopulation);
        return villes.stream().map(VilleMapper::toDto).collect(Collectors.toList());
    }

    public ByteArrayInputStream exportVillesToCsv(List<VilleDto> villes) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write("Nom de la ville,Nombre d'habitants,Code département,Nom du département\n".getBytes());

        for (VilleDto ville : villes) {
            String nomDepartement = fetchDepartementName(ville.getCodeDepartement());
            out.write((ville.getNom() + "," + ville.getPopulationTotale() + "," + ville.getCodeDepartement() + "," + nomDepartement + "\n").getBytes());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public String fetchDepartementName(String codeDepartement) throws IOException {
        String apiUrl = "https://geo.api.gouv.fr/departements/" + codeDepartement + "?fields=nom";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }

        Scanner scanner = new Scanner(url.openStream());
        String inline = "";
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }
        scanner.close();

        // Parse JSON to get the name
        int startIndex = inline.indexOf("\"nom\":\"") + 7;
        int endIndex = inline.indexOf("\"", startIndex);
        return inline.substring(startIndex, endIndex);
    }

    public void addVilleDetailsToPdf(Document document, String codeDepartement, Font normalFont) throws DocumentException {
        Departement departement = departementRepository.findByCode(codeDepartement);
        if (departement != null) {
            List<Ville> villes = departement.getVilles();
            for (Ville ville : villes) {
                document.add(new Phrase("Ville: " + ville.getNom() + ", Population: " + ville.getPopulationTotale(), normalFont));
                document.add(new Phrase("\n"));
            }
        }
    }
}
