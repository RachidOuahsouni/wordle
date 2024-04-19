package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MonMenu {

    Parent root;
    Stage stage;
    Score scores = new Score();
    ParserMot parserMot = new ParserMot();
    List<String> motsFiltre = parserMot.par();
    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ChoixJeu.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
       
        stage.close();
    }
    
    public void afficherAide(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("aide");
        alert.setHeaderText("Comment jouer");
        alert.setContentText("Ce jeu reprend exactement le même concept que le Wordle, mais en français. \n" +
                "\n" +
                "Chaque jour, un mot de 5 lettres est choisi aléatoirement. Vous devez le deviner en 6 essais.\n" +
                "\n" +
                "À chaque essai, les lettres du mot que vous avez proposé changeront de couleur en fonction de à quel point vous êtes proche de le trouver");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        alert.show();
    }
    public void afficherScores(){
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("scores.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("score");
        alert.setHeaderText("Les 3 meilleurs scores");
        alert.setContentText(list.get(0)+ "\n" +list.get(1)+ "\n" +list.get(2));
        alert.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        alert.show();
    }
    
   
    public String Word2vecClient(String mot) {
        String motRecherche = mot.toLowerCase(); // Remplacez par le mot que on souhaite rechercher

        try {
            // Créez une URL pour la requête HTTP
            URL url = new URL("http://localhost:5000/get_similar_words?mot_recherche=" + motRecherche);

            // Ouvrez une connexion HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Définissez la méthode de la requête
            connection.setRequestMethod("GET");

            // Obtenez la réponse du serveur
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Affichez la réponse (la liste de mots similaires)
            System.out.println(response.toString());
            
            // Fermez la connexion
            connection.disconnect();
            return response.toString();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return motRecherche;
    }
    
    public void afficherIndi(String mot){
    	String similarWordsResponse = Word2vecClient(mot);
        Alert alt = new Alert(Alert.AlertType.CONFIRMATION);
        alt.setTitle("indice");
        alt.setHeaderText("Indic est :" + similarWordsResponse);
        alt.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        alt.show();
    }
}
