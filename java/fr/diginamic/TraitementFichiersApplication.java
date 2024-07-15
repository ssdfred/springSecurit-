package fr.diginamic;




import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import fr.diginamic.service.CsvFileReaderService;



@SpringBootApplication

public class TraitementFichiersApplication implements CommandLineRunner {

    private final CsvFileReaderService csvFileReaderService;

    public TraitementFichiersApplication(CsvFileReaderService csvFileReaderService) {
        this.csvFileReaderService = csvFileReaderService;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TraitementFichiersApplication.class);
       // application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        String csvFilePath = "E:/CDA Curs/hello/src/main/resources/recensement.csv";
        csvFileReaderService.readAndSaveCsvData(csvFilePath);
    }
}
