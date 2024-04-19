package games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import obj.ListenStringService1;
import obj.ListenStringService2;
import obj.RestartService;
import obj.Server;

public class Puissance4 {
	
	int[] cases;
	int win;
	int nb;
	int turn;
	
	Server server;
	
	PrintWriter netOut1, netOut2;
	BufferedReader netIn1, netIn2;
	
	Service<String> listen1, listen2, restart;

	public void start(Server s) {
		System.out.println("[LOG] Jeu 'Puissance 4' demarre");
		// Initialisation flux
		server = s;
		netOut1 = server.p1.netOut;
		netOut2 = server.p2.netOut;
		netIn1 = server.p1.netIn;
		netIn2 = server.p2.netIn;
		// Initialisation Service
		listen1 = new ListenStringService1();
		listen1.start();
		listen1.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		@Override
		public void handle(WorkerStateEvent e) {
			String msg = listen1.getValue();
				recieve(msg);
				if(!msg.split("-")[0].equals("quit"))
					listen1.restart();
			}
		});
		listen2 = new ListenStringService2();
		listen2.start();
		listen2.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent e) {
				String msg = listen2.getValue();
				recieve(msg);
				if(!msg.split("-")[0].equals("quit"))
					listen2.restart();
			}
		});
		restart = new RestartService();
		restart.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				init();
				restart.reset();
			}
		});
		init();
	}
	
	public void init(){
		Random rand = new Random();
		turn = rand.nextInt(2) + 1;
		send("start-" + turn);
		cases = new int[49];
		for (int i = 0; i < 49; i++) {
			cases[i] = 0;
		}
		win = 0;
		nb = 0;
	}

	public void recieve(String m){
		String[] msg = m.split("-");
		if(msg[0].equals("place")){
			int p = Integer.parseInt(msg[2]);
			int c = Integer.parseInt(msg[1]);
			if (cases[c] == 0 && win == 0) {
				for (int i = 6; i >= 0; i--) {
					int j = 7 * i + c;
					if (cases[j] == 0) {
						cases[j] = p;
						i = -1;
						nb++;
						System.out.println("[LOG] Le joueur " + p + " a joue sur la case " + j);
						send("place-" + j + "-" + p);
					}
				}
				detectWin();
			}
		}
		if(msg[0].equals("quit")){
			int p = Integer.parseInt(msg[1]);
//			ServerWindow w = new ServerWindow();
//			w.resetLabel();
			if(p==1) send("quit", 2);
			if(p==2) send("quit", 1);
			listen1.cancel();
			listen1.reset();
			listen2.cancel();
			listen2.reset();
			try {
				netIn1.close();
				netIn2.close();
				netOut1.close();
				netOut2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			w.listen();
		}
	}
	
	public void detectWin(){
		for (int p = 1; p < 3; p++) {
			// Détection verticale
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 7; j++) {
					if (cases[i * 7 + j] == p && cases[i * 7 + j + 7] == p && cases[i * 7 + j + 14] == p
							&& cases[i * 7 + j + 21] == p) {
						System.out.println("[LOG] Le joueur " + p + " a gagne !");
						win = p;
					}
				}
			}
			// Détection horizontale
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 4; j++) {
					if (cases[i * 7 + j] == p && cases[i * 7 + j + 1] == p && cases[i * 7 + j + 2] == p
							&& cases[i * 7 + j + 3] == p) {
						System.out.println("[LOG] Le joueur " + p + " a gagne !");
						win = p;
					}
				}
			}
			// Détection diagonale
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if ((cases[i * 7 + j] == p && cases[i * 7 + j + 8] == p && cases[i * 7 + j + 16] == p
							&& cases[i * 7 + j + 24] == p)
							|| (cases[i * 7 + j] == p && cases[i * 7 + j + 6] == p && cases[i * 7 + j + 12] == p
									&& cases[i * 7 + j + 18] == p)) {
						System.out.println("[LOG] Le joueur " + p + " a gagne !");
						win = p;
					}
				}
			}
		}
		if (nb == 49 && win == 0) {
			System.out.println("[LOG] Egalite !");
			win = 3;
		}
		if (win != 0) {
//			if (win==1) System.out.println("win1");
//			if (win==2) System.out.println("win2");
			send("end-" + win);
			restart.start();
		}
	}
	
	public void send(String msg) {
		netOut1.println(msg);
		netOut2.println(msg);
	}

	public void send(String msg, int i) {
		if(i==1) netOut1.println(msg);
		if(i==2) netOut2.println(msg);
	}
}
