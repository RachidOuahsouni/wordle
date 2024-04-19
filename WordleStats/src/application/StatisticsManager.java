package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;


public class StatisticsManager {
	
    private static final String CSV_FILE_PATH = "statistics.csv";
	
    //Charger les statistiques des joueurs depuis le fichier CSV au démarrage de l'application
    public static List<String[]> loadStatistics() {
        List<String[]> statistics = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Ajouter les statistiques de chaque joueur à la liste
                statistics.add(nextLine);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return statistics;
    }
    
	// Save player statistics to a CSV file
    public static void saveStatistics(List<String[]> statistics) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH))) {
            writer.writeAll(statistics);
            System.out.println("Statistics saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    // delete player statistics 
    public static void deleteStatistics(String Nom) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter("temp.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (!fields[0].equals(Nom)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Renommer temp.csv en statistics.csv pour remplacer l'ancien fichier
        File tempFile = new File("temp.csv");
        File file = new File(CSV_FILE_PATH);
        if (tempFile.renameTo(file)) {
            System.out.println("Statistiques de " + Nom + " supprimées avec succès.");
        } else {
            System.out.println("Erreur lors de la suppression des statistiques de " + Nom + ".");
        }
    }


}
