package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;



public class ClientThread extends Thread {
   String name ;
   Socket socketOfclient ;
   StringTokenizer stringtokenizer ;
   static ClientThread instance ;
   Thread clientThread ;
   BufferedReader br ;
   BufferedWriter bw ;
   Socket nguoiGui ,nguoiNhan;
   static  boolean isConectServer  ;
   SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
   
     
   public ClientThread() {
	   isConectServer = false ;
	}


   
   public static ClientThread getInstance() {
	if(instance==null) {
		return instance = new ClientThread();
	}
	return instance;
}

public boolean Login(String username ,String password)
   {
	   
		  if(!isConectServer)
		  {
			  this.connecttoServer();
			  isConectServer=true;
		  }
	   
	   this.sentoServer("LOGIN|"+username+"|"+password);
	   String reponse = this.recieveToServer();
	   stringtokenizer = new StringTokenizer(reponse,"|");
	  
	   if(reponse !=null)
	   {
		   if(reponse.equals("LOGINfalse")){
			   Alert aler = new Alert(AlertType.INFORMATION);
			   aler.setHeaderText("tai khoan hoac mat khau khong dung");
			   aler.showAndWait();		   
		   }
		   else  {
			   Alert aler = new Alert(AlertType.INFORMATION);
			   aler.setHeaderText("dang nhap thanh cong");
			   aler.showAndWait();
			   stringtokenizer.nextToken();
			   this.name =stringtokenizer.nextToken();
			   return true  ;
		   }
	   }
	   return false ;
   }
public boolean resigter (String a, String b, String c)
{
	  if(!isConectServer)
	  {
		  this.connecttoServer();
		  isConectServer=true;
	  }
		this.sentoServer("RESIGTER|"+a+"|"+b+"|"+c);
	   String reponse = this.recieveToServer();
	   stringtokenizer = new StringTokenizer(reponse,"|");
	 if(reponse!=null) {
		 if(reponse.equals("RESIGTEROK"))
		 {
			 return true ;
		 }
		
	 }
	 return false ;
}
public void appMessage(TextArea a )
{	while(true)
{
	String reponse  = this.recieveToServer();
	stringtokenizer = new StringTokenizer(reponse,"|");
	String b=stringtokenizer.nextToken();
	if(b.equals("CHAT")){
	String nguoigui = stringtokenizer.nextToken();
	String message = stringtokenizer.nextToken();
	a.appendText("["+sdf.format(new Date())+"]"+nguoigui+":"+message+"\n");
}
}
}
   public void connecttoServer()
   {
	   try {
		socketOfclient =  new Socket("localhost", 12355);
		br = new BufferedReader(new InputStreamReader(socketOfclient.getInputStream()));
		bw = new BufferedWriter(new OutputStreamWriter(socketOfclient.getOutputStream()));
		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   public void sentoServer(String message)
   {
	   try {
		this.bw.write(message);
		   this.bw.newLine();
		   this.bw.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   public String recieveToServer()
   {
	   try {
		return this.br.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
   }
   public void disconnect() {
	   try {
		this.br.close();
		   this.bw.close();
		   this.socketOfclient.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
  
   
   @Override
	public void run() {
	   
	}
}
