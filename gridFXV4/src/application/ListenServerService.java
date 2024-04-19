package application;


import java.io.BufferedReader;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.MultiLAN;

public class ListenServerService extends Service<String> {

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {

			@Override
			protected String call() throws Exception {
				BufferedReader netIn = PlayerListner.netIn;
				String msg = netIn.readLine();
				return msg;
			}

		};
	}

}
