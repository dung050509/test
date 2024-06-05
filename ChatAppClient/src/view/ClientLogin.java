package view;
	

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;



public class ClientLogin extends Application {
	static Stage rrimayStage ;
	@Override
	public void start(Stage primaryStage) {
	
		try {		
			Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
			primaryStage.getIcons().add(new Image("/image/iconchinh.png"));
			Scene scene = new Scene(root);	
			primaryStage.setScene(scene);		
			primaryStage.show();
			primaryStage.setTitle("Đăng Nhập");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
