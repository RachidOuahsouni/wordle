package main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import games.Morpion;
import games.Puissance4;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import obj.ListenPlayerService;
import obj.Player;
import obj.Server;

public class ServerWindow {

	Player p;
	Stage primaryStage;
	public static Server server = ServerStart.server;

	@FXML
	Label serverName, serverIP, serverGame, serverTimer, serverPlayerCount, serverStarted, gameStarted, title;

	@FXML
	public Label p1name, p1ip, p1score;

	@FXML
	public Label p2name, p2ip, p2score;

	@FXML
	GridPane gridp1, gridp2;

	@FXML
	Button startStop;
	
	@FXML
	TextArea textField;
	
	@FXML
	Tab tab1, tab2;

	public void getStage(Stage stage) {
		primaryStage = stage;
	}

	@FXML
	public void initialize() {
		// Affichage
		title.setText("Serveur '" + server.name + "'");
		serverName.setText(server.name);
		serverIP.setText(server.ip);
		serverGame.setText(server.game);
		serverTimer.setText("" + server.timer);
		serverPlayerCount.setText("" + server.playerCount);
		serverStarted.setText("" + server.isStarted);
		gameStarted.setText("" + server.isGameStarted);
		gridp1.setDisable(true);
		gridp2.setDisable(true);
		p1name.setText("-");
		p1ip.setText("-");
		p1score.setText("-");
		p2name.setText("-");
		p2ip.setText("-");
		p2score.setText("-");
		tab1.setDisable(true);
		tab2.setDisable(true);
		// Cr�ation bouton d�marrage
		startStop.setText("Démarrer le serveur");
		startStop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				serverStart();
			}
		});
		// Initialisation Console
		OutputStream out = new OutputStream() {
	        @Override
	        public void write(int b) throws IOException {
	            appendText(String.valueOf((char) b));
	        }
	    };
	    System.setOut(new PrintStream(out, true));
	}

	public void listen() {
		Service<Player> listen = new ListenPlayerService();
		listen.start();
		listen.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent e) {
				p = listen.getValue();
				if (p.name.equals("removed")){
					if(p.ip.equals("1")){
						gridp1.setDisable(true);
						p1name.setText("-");
						p1ip.setText("-");
						p1score.setText("-");
					}
					if(p.ip.equals("2")){
						if(p.ip.equals("1")){
							gridp2.setDisable(true);
							p2name.setText("-");
							p2ip.setText("-");
							p2score.setText("-");
						}
					}
				listen.restart();
				}else{
					if (!server.isFull) {
						tab1.setDisable(false);
						p1name.setText(p.name);
						p1ip.setText(p.ip);
						p1score.setText(p.score + "");
						gridp1.setDisable(false);
						listen.restart();
					} else {
						tab2.setDisable(false);
						p2name.setText(p.name);
						p2ip.setText(p.ip);
						p2score.setText(p.score + "");
						gridp2.setDisable(false);
						listen.cancel();
						startGame(server.game);
					}
				}
				serverPlayerCount.setText("" + server.playerCount);
				serverStarted.setText("" + server.isStarted);
				gameStarted.setText("" + server.isGameStarted);
			}
		});
	}

	public void serverStart() {
		startStop.setText("Arréter le serveur");
		startStop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				serverStop();
			}
		});
		server.startServer();
		serverStarted.setText("" + server.isStarted);
		System.out.println("[LOG] Serveur '" + server.name + "' (" + server.ip + ") demarre");
		listen();
	}

	public void serverStop() {
		System.exit(0);
	}

	public void startGame(String game) {
		server.p1.netOut.println("player-" + server.p2.name + "-" + server.p2.ip + "-2");
		server.p2.netOut.println("player-" + server.p1.name + "-" + server.p1.ip + "-1");
		if (game.equals("Wordle")) {
			Morpion mrp = new Morpion();
			mrp.start(server);
		}
		if (game.equals("Puissance 4")) {
			Puissance4 puiss4 = new Puissance4();
			puiss4.start(server);
		}
	}
	
	public void resetLabel(){
		server.p1 = null;
		gridp1.setDisable(true);
		p1name.setText("-");
		p1ip.setText("-");
		p1score.setText("-");
		server.p2 = null;
		gridp2.setDisable(true);
		p2name.setText("-");
		p2ip.setText("-");
		p2score.setText("-");
	}

	public void addWin(int win) {
		System.out.println("[LOG] Score actualise (Joueur " + win + ")");
		if(win == 1){
			server.p1.score++;
			p1score.setText("" + server.p1.score);
		}
		if(win == 2){
			server.p2.score++;
			p2score.setText("" + server.p2.score);
		}
	}
	
	public void appendText(String str) {
		Platform.runLater(() -> textField.appendText(str));
	}		
}
