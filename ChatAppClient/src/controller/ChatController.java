package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController implements Initializable {
	@FXML
	private TextArea txtAmessgae;

	@FXML
	private TextField txtMessage;

	@FXML
	private ListView<String> listUser;
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	StringTokenizer stk ;
	String [] message = {};
	String[] ten = { "hoang anh", "hiep", "nam", "cuong" };

	@FXML
	void sendMessage(ActionEvent  event) {
		
		String message =txtMessage.getText().trim();
		ClientThread.instance.sentoServer("CHAT|"+message);
		txtMessage.setText("");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		listUser.getItems().addAll(ten);
		new Thread() {
			@Override
			public void run() {
				ClientThread.getInstance().appMessage(txtAmessgae);
			}
		}.start();
	

	}
	public void addMessage(String message) {
		txtAmessgae.appendText("["+sdf.format(new Date())+"] you: "+message+"\n");
	}
	
}
