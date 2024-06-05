package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientService implements Runnable{
	private Socket _socket ;
	private BufferedReader _in ;
	private PrintWriter _out;
	public static List<ClientService> listSocket= new ArrayList<ClientService>();
	private boolean isValidUser;
	private String name ;
	public ClientService(Socket _socket ,String name) {
		try {
			this.name=name ;
			this._socket=_socket;
			listSocket.add(this);
			_in= new BufferedReader(new InputStreamReader(_socket.getInputStream()));
			_out = new PrintWriter(_socket.getOutputStream(),true);
			
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
	public void sendMessage(String message)
	{
		_out.println(message);
		_out.flush();
	}
	public Socket get_socket() {
		return _socket;
	}
	public void set_socket(Socket _socket) {
		this._socket = _socket;
	}
	public BufferedReader get_in() {
		return _in;
	}
	public void set_in(BufferedReader _in) {
		this._in = _in;
	}
	public PrintWriter get_out() {
		return _out;
	}
	public void set_out(PrintWriter _out) {
		this._out = _out;
	}
	public static List<ClientService> getListSocket() {
		return listSocket;
	}
	public static void setListSocket(List<ClientService> listSocket) {
		ClientService.listSocket = listSocket;
	}
	public boolean isValidUser() {
		return isValidUser;
	}
	public void setValidUser(boolean isValidUser) {
		this.isValidUser = isValidUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String message = _in.readLine();
			System.out.println(message);
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

}
