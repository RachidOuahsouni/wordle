package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class StatisticsController implements Initializable {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChart;
    
    @FXML
    private LineChart<String, Number> lineChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadStatistics();
    }

    private void loadStatistics() {
        // Chargement des données à partir du fichier CSV
        ObservableList<XYChart.Data<String, Number>> barChartData = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<String, Number>> lineChartData = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("statistics.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String playerName = parts[0];
                    int gamesPlayed = Integer.parseInt(parts[1]);
                    int gamesWon = Integer.parseInt(parts[2]);
                    // Ajout des données au graphique à barres
                    barChartData.add(new XYChart.Data<>(playerName, gamesWon));
                    // Ajout des données au graphique en secteurs
                    pieChartData.add(new PieChart.Data(playerName, gamesWon));
                    // Ajout des données au graphique de ligne
                    lineChartData.add(new XYChart.Data<>(playerName, gamesPlayed));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        // Création des séries de données pour le graphique à barres
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setData(barChartData);
        barChart.getData().add(barSeries);

        // Ajout des données au graphique en secteurs
        pieChart.setData(pieChartData);

        // Création des séries de données pour le graphique de ligne
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setData(lineChartData);
        lineChart.getData().add(lineSeries);
    }
}
