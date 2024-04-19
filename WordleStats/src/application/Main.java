package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger les statistiques
            List<String[]> statistics = StatisticsManager.loadStatistics();

            // Afficher les statistiques chargées dans la console
            for (String[] stats : statistics) {
                for (String stat : stats) {
                    System.out.print(stat + "\t");
                }
                System.out.println();
            }

            // Enregistrer de nouvelles statistiques
            String[] newStats = {"Player3", "10", "5"}; // Exemple de nouvelles statistiques
            //statistics.add(newStats);
            //StatisticsManager.saveStatistics(statistics);

            // Supprimer les statistiques d'un joueur
            String playerNameToDelete = "Player1"; // Nom du joueur à supprimer
            StatisticsManager.deleteStatistics(playerNameToDelete);

            // Charger à nouveau les statistiques après suppression
            statistics = StatisticsManager.loadStatistics();
            System.out.println("Statistiques après suppression :");
            for (String[] stats : statistics) {
                for (String stat : stats) {
                    System.out.print(stat + "\t");
                }
                System.out.println();
            }

         // Charger le fichier FXML de la vue des statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
            Parent root = loader.load();

            // Créer la scène avec la vue chargée
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            // Afficher la scène dans la fenêtre principale
            primaryStage.setScene(scene);
            primaryStage.setTitle("Statistiques des joueurs");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
