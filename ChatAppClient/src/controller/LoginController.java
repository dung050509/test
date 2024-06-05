package controller;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;


public class LoginController implements Initializable{
	
	@FXML
	private TextField txtUser ;
	@FXML
	private PasswordField txtPassword ;
	@FXML 
	private Button btDangNhap;
	@FXML
	private Label btDangKy;
	static Stage stage ;
	static Parent root ;
	
	
	 
	@FXML
	public void LoginUser(ActionEvent event) {
		
		String user =txtUser.getText().trim();
		String password =txtPassword.getText().trim();
		if(user.equals("")||password.equals("")||user.length()<4||password.length()<4)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("ten dang nhap hoac mat khau khong duoc trong");
			alert.setContentText("tai khoan va mat khau phai tu 4 ky tu tro len");
			alert.showAndWait();
		}
		else
		{		
			boolean kq = ClientThread.getInstance().Login(user, password);
			if(kq)
			{
				Stage stage=(Stage)	((Node)event.getSource()).getScene().getWindow();
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/view/chat.fxml"));
					Scene scene = new Scene(root);
				
					stage.setScene(scene);
					stage.setTitle("Chào "+ClientThread.getInstance().name);
					stage.show();
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	@FXML
	public void dangkyUser(MouseEvent event) throws IOException
	{	
		
		Stage stage=(Stage)	((Node)event.getSource()).getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Resigter.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.isResizable();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
		}
	}

	public static Stage getStage() {
		return stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
	
		
	}
	
	
}
