package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import java.util.Hashtable;
import java.util.StringTokenizer;

import javafx.scene.control.Label;


public class ServerThread extends Thread {
	Socket clietn ;
	BufferedReader inp ;
	BufferedWriter out ;
	String clientName , user , password;
	
	public static Hashtable<String, ServerThread> lisUser = new Hashtable<String, ServerThread>();
	StringTokenizer stringTokenizer ;
	String nguoiGui,nguoiNhan ;
	Label taServer ;
	static Socket socketGui,socketNhan ;
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	DatabaServer dbsv ;
	
	static boolean ktraFlie =false ; // kiem tra xem server co gui nhan file khong
	public ServerThread (Socket socket)
	{
		try {
			this.clietn = socket;
			this.inp = new BufferedReader(new InputStreamReader(clietn.getInputStream()));
			this.out =new BufferedWriter(new OutputStreamWriter(clietn.getOutputStream()));
			clientName ="";
			dbsv = new DatabaServer();
			dbsv.getConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void  appendMessage(String message)
//	{
//		
//	}
	// nhan tin nhan tu client
	public String revieveFromClient()
	{
		try {
			return inp.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//
	public void sendClient(String reponse)
	{
		try {
			out.write(reponse);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//gui tin nhan den 1 client cu the
	public void sendTo1CLient(ServerThread svThread,String reponse) {
		try {
			BufferedWriter  br  = svThread.out;
			br.write(reponse);
			br.newLine();
			br.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendToCLient(Socket socket ,String reponse) {
	
		try {
			BufferedWriter  br  =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			br.write(reponse);
			br.newLine();
			br.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	// khi 1 client gui thong tin thi se gui messeage den tat ca client dang ket noi
	public void senAllUser(String message)
	{				// lay cai phan tu tu list user cho vao clients
		Enumeration<ServerThread> clients = lisUser.elements();
		ServerThread st ;
		BufferedWriter writer ;
		
		while(clients.hasMoreElements())
		{
			st=clients.nextElement();
			writer = st.out;
			try {
				writer.write(message);
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void closeServerThread() {
		try {
			inp.close();
			out.close();
			clietn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public String getAllUser() {
	StringBuffer kq = new StringBuffer();
	String temp=null;
	// lay ra ten client tu listUser
	Enumeration<String> keys =  lisUser.keys();
	if(keys.hasMoreElements())
	{
		String str = keys.nextElement();
		kq.append(str);
	}
	while(keys.hasMoreElements())
	{
		temp=keys.nextElement();
		kq.append("|").append(temp);
	}
	return kq.toString();	
}
	
 /**
 *
 */
@Override
public void run() {
	 try {
		BufferedReader bf = new BufferedReader(new InputStreamReader(clietn.getInputStream()));
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(clietn.getOutputStream()));
		boolean isUserexit = true ;
		String messeage , userNhan ,userGui ,flileName;
		StringBuffer str ;
		String cmd ,  icon ;
		while(true)
		{	
			messeage = revieveFromClient();
			stringTokenizer = new StringTokenizer(messeage,"|");
			cmd = stringTokenizer.nextToken();
			switch (cmd) {
			case  "LOGIN" :
				user = stringTokenizer.nextToken();
				password=stringTokenizer.nextToken();
				isUserexit = lisUser.containsKey(user);
				if(isUserexit)
				{
					sendClient("LOGINexit");
				}
				else {
				boolean kq = dbsv.checkUser(user, password);
				if(kq)
				{	clientName = dbsv.getTenHienThi(user, password);
					sendClient("LOGINOK|"+this.clientName);
					
					lisUser.put(clientName, this);
				}else
				{
					sendClient("LOGINfalse");
				}
				
				}
				break ;
			case "RESIGTER" :
					String tenHienthi = stringTokenizer.nextToken();
					String taikhoan = stringTokenizer.nextToken();
					String matkhau = stringTokenizer.nextToken();
					int kq = dbsv.createUser(tenHienthi,taikhoan,matkhau);
					if(kq>0)
					{
						this.sendClient("RESIGTEROK");
					}
					else
					{
						this.sendClient("RESIGTERNo");
					}
			case "CHAT" :
				str = new StringBuffer(messeage);
				str.delete(0, 5);
				
				senAllUser("CHAT|"+this.clientName+"|"+str.toString());
				break ;
			
			default:
				throw new IllegalArgumentException("Unexpected value: " + cmd);
			}
		}
					
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		if(clietn.isClosed())
		{
			System.out.println(this.clientName +"da thoat ");
			ServerThread.removeUser(this.clientName);
		}
	}
}	
	
	private static void removeUser(String name) {
		lisUser.remove(name);
	}
	
	public void deletOnline(String name,ServerThread cli)
	{
		lisUser.replace(name, cli);
	}
	
	
	
	
	
}
