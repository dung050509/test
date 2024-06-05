package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		ServerSocket severSocket ;
		ServerThread st ;
		Socket client ;
		try {
			severSocket = new ServerSocket(12355);
			
			while(true)
			{
				client =severSocket.accept();
				System.out.println(client.getInetAddress().getHostName()+"ket noi den den luc["+sdf.format(new Date())+"]");
				st = new ServerThread(client);				
				st.start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
