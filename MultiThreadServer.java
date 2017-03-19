package sdsmh_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Server class
public class MultiThreadServer implements Runnable {
	   private static Socket sock; //Client socket
	   private static ObjectOutputStream os;// client output stream
	   private static Connection con = null;//connection for database
	   private static ResultSet res;
	   private static Statement stt;
	   static ServerSocket server;   // Server socket
	   
	   MultiThreadServer(Socket csocket) {
	      this.sock = csocket;
	   }
	   
	   public void run() {
		try {
			 
		      ObjectInputStream in; // client input stream
		      String url = "jdbc:mysql://localhost:3306/ARDS";//URL for database
		      String username = "root";
		      Class.forName("com.mysql.jdbc.Driver");
		      con = DriverManager.getConnection(url,username,"");
				if(con != null)
				{
					
				    System.out.println("Waiting on connections");
					System.out.println("Connected to resources");
				}
				
				 in = new ObjectInputStream(sock.getInputStream());
		         os = new ObjectOutputStream(sock.getOutputStream());
		         Response response = new Response();
		         response = (Response) in.readObject();
		         System.out.println("Response source " + response.getSource());
		         System.out.println("From client " + response.getMessage());
		         String password = getSHA_256Hash(response.getPassword());
		         int stud_ID = response.getId();
		         //Possible actions from clients
		         if(response.getAction().equals("Login"))
		         {
					try {
						String query = "SELECT * FROM student_password WHERE stud_ID = '" + stud_ID
								+ "' and password = '"+password+"'";
						stt = con.createStatement();
						res = stt.executeQuery(query);

						if(res.next())
						{
							response.setMessage("Authenticated");
							os.writeObject(response);
							os.flush();
						}
						else
						{
							response.setMessage("Not Authenticated");
							os.writeObject(response);
							os.flush();
						}
					} catch (SQLException e) {
						System.out.println("Error in SQL");
						e.printStackTrace();
					}
		         }
		         if(response.getAction().equals("Exit"))
		         {
		        	 sock.close();
		        	 System.out.println("Client connection closed!");
		         }
		         
		         
		
		     
			
			
			
	
		
		} catch (IOException e) {
			System.out.println(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	   
	   public static void main(String args[]) throws Exception { 
		   server = new ServerSocket(1234);   // Server socket
		   
		   System.out.println("Server started on port: " + server.getLocalPort());
		   while (true) {
		         Socket sock = server.accept();
		         System.out.println("Connected");
	        new Thread(new MultiThreadServer(sock)).start();
		   }
	   }
	   
	   private static String getSHA_256Hash(String password){
			String hash = null;
			try{
				  MessageDigest md = MessageDigest.getInstance("SHA-256");
			        md.update(password.getBytes());

			        byte byteData[] = md.digest();

			        //convert the byte to hex format method 1
			        StringBuffer sb = new StringBuffer();
			        for (int i = 0; i < byteData.length; i++) {
			        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			        }
			        hash = sb.toString();
				// Now we need to zero pad it if you actually want the full 32 chars.
				while(hash.length() <  64){
				  hash = "0"+hash;
				}
				
			}catch(NoSuchAlgorithmException e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			return hash;
		}
	   
	}
