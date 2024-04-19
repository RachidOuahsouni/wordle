package application;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Player {

	public String name;
	public String ip;
	public int score;
	public BufferedReader netIn;
	public PrintWriter netOut;

	public Player(String name, String ip) {
		this.name = name;
		this.ip = ip;
		score = 0;
		netIn = null;
		netOut = null;
	}

	public Player(String name, String ip, BufferedReader netIn, PrintWriter netOut) {
		this.name = name;
		this.ip = ip;
		score = 0;
		this.netIn = netIn;
		this.netOut = netOut;
	}

	public void addFlux(BufferedReader netIn, PrintWriter netOut) {
		this.netIn = netIn;
		this.netOut = netOut;
	}
}