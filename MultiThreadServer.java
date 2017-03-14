package sdsmh_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
//Server class
public class MultiThreadServer implements Runnable {
	   ServerSocket server;// Server socket
	   Socket csocket; //Client socket
	   private ObjectOutputStream os;// client output stream
	   
	   
	   
	   MultiThreadServer(Socket csocket) {
	      this.csocket = csocket;
	   }
	   
	   public void run() {
		try {
			Response response = new Response();
			response.setSource("Server>>>>");
			os = new ObjectOutputStream(csocket.getOutputStream());
			os.writeObject(response);
			os.flush();
			
			
			

		} catch (IOException e) {
			System.out.println(e);
		}

	}
	   
	   public static void main(String args[]) throws Exception { 
	      ServerSocket server = new ServerSocket(1234);
	      ObjectInputStream in; // client input stream
	      System.out.println("Server started on port: " + server.getLocalPort());
	      System.out.println("Waiting on connections");
	      
	      while (true) {
	         Socket sock = server.accept();
	         System.out.println("Connected");
	         new Thread(new MultiThreadServer(sock)).start();
	         in = new ObjectInputStream(sock.getInputStream());
	         Response response = new Response();
	         response = (Response) in.readObject();
	         System.out.println("Response source " + response.getSource());
	         System.out.println("From client " + response.getMessage());
	      }
	   }
	   
	}
