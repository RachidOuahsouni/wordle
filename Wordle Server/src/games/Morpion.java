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

public class Morpion {

	int[] cases;
	int win;
	int nb;
	int turn;
	Server server;

	PrintWriter netOut1, netOut2;
	BufferedReader netIn1, netIn2;
	
	Service<String> listen1, listen2, restart;

	public void start(Server s) {
		System.out.println("[LOG] Jeu 'Morpion' demarre");
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
		// Initialisation jeu
		Random rand = new Random();
		turn = rand.nextInt(2) + 1;
		send("start-" + turn);
		cases = new int[9];
		for (int i = 0; i < 9; i++) {
			cases[i] = 0;
		}
		win = 0;
		nb = 0;
	}

	public void recieve(String m) {
		String[] msg = m.split("-");
		if (msg[0].equals("place")) {
			int p = Integer.parseInt(msg[2]);
			int c = Integer.parseInt(msg[1]);
			System.out.println("[LOG] Le joueur " + p + " a joue dans la case " + c);
			cases[c] = p;
			nb++;
			if(p == 1) send("place-" + c, 2);
			if(p == 2) send("place-" + c, 1);
			detectWin();
		}
		if (msg[0].equals("quit")){
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

	public void detectWin() {
		int i, j;
		for (i = 1; i <= 2; i++) {
			// D�tection horizontale
			for (j = 0; j <= 6; j = j + 3) {
				if (cases[j] == i && cases[j + 1] == i && cases[j + 2] == i) {
					System.out.println("[LOG] Le joueur " + i + " a gagne !");
					win = i;
				}
			}
			// D�tection verticale
			for (j = 0; j <= 2; j++) {
				if (cases[j] == i && cases[j + 3] == i && cases[j + 6] == i) {
					System.out.println("[LOG] Le joueur " + i + " a gagne !");
					win = i;
				}
			}
			// D�tection diagonale
			if ((cases[0] == i && cases[4] == i && cases[8] == i)
					|| (cases[2] == i && cases[4] == i && cases[6] == i)) {
				System.out.println("[LOG] Le joueur " + i + " a gagne !");
				win = i;
			}
		}
		if (nb == 9 && win == 0) {
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
		System.out.println("baynaaaaaaa : "+msg);
		netOut1.println(msg);
		netOut2.println(msg);
	}

	public void send(String msg, int i) {
		if(i==1) netOut1.println(msg);
		if(i==2) netOut2.println(msg);
	}
}
