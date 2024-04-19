package obj;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.ServerWindow;

public class RestartService extends Service<String>{

	int timer = ServerWindow.server.timer * 1000;
	
	@Override
	protected Task<String> createTask() {
		return new Task<String>(){
			@Override
			protected String call() throws Exception {
				Thread.sleep(timer);
				System.out.println("[LOG] Jeu 'Morpion' redemarre");
				return null;
			}
		};
	}
}
