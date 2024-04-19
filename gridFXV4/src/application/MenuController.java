package application;



import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {
	
	private Stage primaryStage;
	
    @FXML
    private Button PageJeuxButton;

    @FXML
    private Button parametresButton;

    @FXML
    private Button quitterButton;

    @FXML
    private void initialize() {
       

     
        PageJeuxButton.setOnAction(e -> ouvrirPageJeux());
        parametresButton.setOnAction(e -> afficherParametres());
        quitterButton.setOnAction(e -> quitterJeu());
    }

    private void ouvrirPageJeux() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChoixJeu.fxml"));
            StackPane pageJeuxRoot = loader.load();


            Stage nouvelleStage = new Stage();
            Scene nouvelleScene = new Scene(pageJeuxRoot, 1080, 720);
             
            ChoixJeuController controller = (ChoixJeuController) loader.getController();
			controller.setStage(nouvelleStage);
            nouvelleStage.setTitle("Nouvelle Page");
            nouvelleStage.setScene(nouvelleScene);
            nouvelleStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
    
    private void afficherParametres() {
    	 System.out.println("Afficher les paramètres");
       
    }

    private void quitterJeu() {
        System.exit(0); 
    }

    
}


