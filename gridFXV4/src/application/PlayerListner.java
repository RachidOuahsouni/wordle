package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;


import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PlayerListner {

	public static Player pl = ChoixJeuController.p;
	public static Server server;
	Player p1, p2;
	Stage primaryStage;
	String serverIP;

	public static Socket commSocket;
	Alert alert;

	public static BufferedReader netIn;
	public PrintWriter netOut;

	public void serverJoin(String ip, Player p, Stage mainStage) {
		try {
			primaryStage = mainStage;
			serverIP = ip;
			commSocket = new Socket(serverIP, Reference.SOCKET);
			netIn = new BufferedReader(new InputStreamReader(commSocket.getInputStream()));
			netOut = new PrintWriter(commSocket.getOutputStream(), true);
			p.addFlux(netIn, netOut);
			System.out.println("premiiiierrr : ");
			netOut.println("add-" + p.name + "-" + p.ip);
			listen();

		} catch (IOException e) {
			alert = new Alert(AlertType.ERROR, "L'addresse IP est �ronn�e.", ButtonType.CLOSE);
			alert.setTitle(Reference.TITLE + "Erreur");
			alert.setHeaderText("Erreur");
			alert.show();
		}

	}

	public void listen() {
		System.out.println("deuxieeeemeee : ");
		Service<String> listen = new ListenServerService();
		listen.start();
		System.out.println("ERRRRRRRRRRR");
		listen.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent e) {
				System.out.println("troisieeeeeme : ");
				String msg[] = listen.getValue().split("-");
				 System.out.println(msg[0]);
				if (msg[0].equals("server")) {
					server = new Server(serverIP, msg[1], msg[2]);
					System.out.println("[LOG] Connect� au serveur '" + server.name + "' (" + server.ip + ") [Jeu : "
							+ server.game + "]");
					listen.restart();
				}
				if (msg[0].equals("player")) {
					System.out.println("[LOG] Joueur '" + msg[1] + "' (" + msg[2] + ") re�u");
					server.addPlayer(new Player(msg[1], msg[2]));
					if (!server.isFull)
						server.addPlayer(pl);
					System.out.println("22222222222");
					listen.restart();
				}
				if (msg[0].equals("start")) {
					server.isGameStarted = true;
					System.out.println("[LOG] Le jeu d�marre...");
					startGame(server.game, Integer.parseInt(msg[1]));
					if (alert != null)
						alert.close();
				}
				if (msg[0].equals("wait")) {
					server.addPlayer(pl);
					alert = new Alert(AlertType.INFORMATION, "En attente d'un adversaire... veuillez patienter !",
							ButtonType.CANCEL);
					alert.setTitle(Reference.TITLE + "Attente d'un adversaire");
					alert.setHeaderText("Attente d'un adversaire");
					System.out.println("[LOG] En attente d'un adversaire...");
					listen.restart();
					Optional<ButtonType> result = alert.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.CANCEL && server.isGameStarted == false) {
						try {
							commSocket = new Socket(serverIP, Reference.SOCKET);
							netOut = new PrintWriter(commSocket.getOutputStream(), true);
							netOut.println("remove-" + pl.name);
							commSocket.close();
							netIn.close();
							netOut.close();
						} catch (UnknownHostException ex) {
							ex.printStackTrace();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						System.out.println("[LOG] Déconnect� du serveur");
					}
				}
			}
		});
	}

	public void startGame(String game, int p) {
		server.setInfo(p);
		p1 = server.p1;
		p2 = server.p2;
		System.out.println("quatrieeeeemeee : ");
		if (game.equals("Wordle")) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("gridFX.fxml"));
	            VBox root = loader.load();
	            Controller controller = loader.getController();
	            Stage jeuStage = new Stage();
	            Scene jeuScene = new Scene(root, 1080, 720);
	            jeuStage.setScene(jeuScene);

	            jeuStage.setTitle("Jeu");
	            jeuStage.show();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}
}
