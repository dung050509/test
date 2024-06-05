package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResigterController {
	Alert alert ;
	Parent root ;
	Stage stage ;
	@FXML
    private Button btDangKy;

    @FXML
    private Button btQuayLai;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPasswordnl;

    @FXML
    private TextField txtTenHienThi;

    @FXML
    private TextField txtUser;
	
	public void ResigterUser(ActionEvent event)
	{	String tenHienThi = txtTenHienThi.getText().trim();
		String user = txtUser.getText().trim();
		String password =txtPassword.getText().trim();
		String passwordcf = txtPasswordnl.getText().trim();
		if(!tenHienThi.equals("") || !user.equals("") || !password.equals("")|| !passwordcf.equals("")
			|| tenHienThi.length()>=4 || (user.length()>=4) || password.length()>=4 || passwordcf.length()>=4 	
				) {
			if(checkPasswordConfirm(password, passwordcf))
			{
				
				if(ClientThread.getInstance().resigter(tenHienThi, user, password)) {
					ShowAlert("Tạo tài khoản thành công");
					Resetinfor();
					
				}
				else {
					ShowAlert("Khôn thể kết nối đến cơ sở dữ liệu");
				}
			}
			else
			{
				ShowAlert("mật chưa trùng khớp");
			}
		}else {
			ShowAlert("tên hiển thị , tài khoản,mật khẩu không được trống và phải từ 4 ký tự trở lên ");
		}
 	}
	public void BackHome(ActionEvent event)
	{
		Stage stage=(Stage)	((Node)event.getSource()).getScene().getWindow();
		try {
			Parent dangky = FXMLLoader.load(getClass().getResource("/Login.fxml"));
			Scene scene = new Scene(dangky);
			stage.setScene(scene);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void ShowAlert (String message)
	{
		alert = new Alert(AlertType.ERROR);
		
		alert.setContentText(message);
		alert.showAndWait();
	}
	public boolean checkPasswordConfirm(String a , String b)
	{
		if(a.trim().equals(b.trim())) return true ;
		return false ;
	}
	public void Resetinfor()
	{
		txtTenHienThi.setText("");
		txtUser.setText("");
		txtPassword.setText("");
		txtPasswordnl.setText("");
	}
}
