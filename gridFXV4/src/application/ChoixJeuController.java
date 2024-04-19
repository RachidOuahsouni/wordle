package application;



import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChoixJeuController {
	public static Player p;
    String pseudo;
    String ip;

    @FXML
    private Button jeu1Button;

    @FXML
    private Button jeu2Button;

    @FXML
    private Button jeu3Button;

    @FXML
    private Button retourButton;
    
	Stage mainStage;
	
	public void setStage(Stage stage) {
		mainStage = stage;
	}

    
   

    @FXML
    private void initialize() {
        jeu1Button.setOnAction(e -> lancerJeu(1));
        jeu2Button.setOnAction(e -> lancerJeu(2));
        jeu3Button.setOnAction(e -> lancerJeu(3));
        retourButton.setOnAction(e -> ((Stage) retourButton.getScene().getWindow()).close());
    }
    
    private void lancerJeu(int numeroJeu) {
    	if (numeroJeu == 1) {
    		System.out.println("Lancement jeu 1");
    		afficherJeu();
        } else if (numeroJeu == 2){
        	afficherJeu2();   
        }else  System.out.println("Numéro de jeu non pris en charge");
    }

    private void afficherJeu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gridFX.fxml"));
            VBox root = loader.load();
            Controller controller = loader.getController();
            Stage jeuStage = new Stage();
            Scene jeuScene = new Scene(root, 1080, 720);
            jeuStage.setScene(jeuScene);

            jeuStage.setTitle("Jeu");
            jeuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void afficherJeu2() {
        try {
        	 // Demande de pseudo
            TextInputDialog pseudoDialog = new TextInputDialog("Joueur");
            pseudoDialog.setTitle("Connexion");
            pseudoDialog.setHeaderText("Pseudo requis");
            pseudoDialog.setContentText("Entrez votre pseudo :");
            // Détection de données
            Optional<String> pseudoResult = pseudoDialog.showAndWait();
            if (pseudoResult.isPresent()) { // Si l'utilisateur appuie sur OK :
                // Récupération du pseudo
                pseudo = pseudoResult.get();
                if (!pseudo.equals("")) { // Un pseudo est entré
                    // Demande d'adresse IP du serveur
                    TextInputDialog ipDialog = new TextInputDialog("192.168.1.13");
                    ipDialog.setTitle("Connexion");
                    ipDialog.setHeaderText("Adresse IP du serveur requise");
                    ipDialog.setContentText("Entrez l'adresse IP du serveur :");
                    // Détection de données
                    Optional<String> ipResult = ipDialog.showAndWait();
                    if (ipResult.isPresent()) { // Si l'utilisateur appuie sur OK :
                        // Récupération de l'adresse IP
                        ip = ipResult.get();
                        if (!ip.equals("")) { // Une adresse IP est entrée
                            // Création du joueur
                            p = new Player(pseudo, ip);
                            System.out.println("[LOG] Joueur '" + p.name + "' (" + p.ip + ") connecté avec succès");
                        } else { // Aucune adresse IP n'est entrée
                            System.exit(0);
                        }
                    } else { // La personne a quitté
                        System.exit(0);
                    }
                } else { // Aucun pseudo n'est entré
                    System.exit(0);
                }
            } else { // La personne a quitté
                System.exit(0);
            }

            // Chargement de l'interface utilisateur principale
            PlayerListner multi = new PlayerListner();
			multi.serverJoin(ip, p, mainStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
}


