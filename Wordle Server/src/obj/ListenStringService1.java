package obj;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.ServerWindow;

public class ListenStringService1 extends Service<String> {

	Server server = ServerWindow.server;

	BufferedReader netIn1;
	String msg1;

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {

			@Override
			protected String call() throws Exception {
				try {
					netIn1 = server.p1.netIn;
					msg1 = netIn1.readLine();
					// TODO Problème lors de la déconnexion du joueur 1 et lorsque le joueur 2 quitte
				} catch (IOException e) {
					e.printStackTrace();
				}
				return msg1;
			}

		};
	}
}