package Server;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaServer {
	private Connection con ;
	private PreparedStatement pstm ;
	private Statement stm ;
	private ResultSet rs ;
	
	public Connection getConnection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				 con=DriverManager.getConnection("jdbc:mysql://localhost:33006/serverchat","root","");
				System.out.println("ket noi thanh cong");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	public ResultSet getDaTa() {
		try {
			stm = con.createStatement();
			rs =stm.executeQuery("SELECT * FROM user");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs ;
	}
	private void showDaTa()
	{
		rs=getDaTa();
		try {
			while(rs.next())
			{
				System.out.printf("%15s%15s%15s",rs.getString(1),rs.getString(2),rs.getString(3));
				System.out.println("");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public int createUser(String tenHienThi, String username, String password)
	{
		try {
			pstm =con.prepareStatement("INSERT INTO user VALUES (?,?,?)");
			pstm.setString(1,tenHienThi);
			pstm.setString(2,username);
			pstm.setString(3,password);
			
			
			int kq = pstm.executeUpdate();
			if(kq>0)
			{
				System.out.println("them thanh cong");
			}
			return kq ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public boolean checkUser(String user , String password)
	{
		try {
			pstm=con.prepareStatement("SELECT * FROM user WHERE taikhoan = ? AND matkhau =? ");
			pstm.setString(1, user);
			pstm.setString(2, password);
			rs = pstm.executeQuery();
			while(rs.next())
			{
				return true ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false ;
	}
	public void closeConnection()
	{
		try {
			if(rs!=null) rs.close();
			if(stm!=null) stm.close();
			if(pstm!=null) pstm.close();
			if(con!=null) con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public String getTenHienThi(String username ,String password)
	{
		try {
			pstm=con.prepareStatement("SELECT tenhienthi FROM user WHERE taikhoan = ? AND matkhau =? ");
			pstm.setString(1, username);
			pstm.setString(2, password);
			rs=pstm.executeQuery();
			while(rs.next())
			{
				String tenhienthi = rs.getString(1);
				return tenhienthi;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}
	public static void main(String[] args) {
		DatabaServer db = new DatabaServer();
		db.getConnection();
		db.showDaTa();
		String tenhienthi = db.getTenHienThi("hoanganh", "2192003");
		System.out.println(tenhienthi);
		
		
	}
}
