import javax.swing.JFrame;

public class ClientTest {
	public static void main(String[] args){
		Client DavidClient = new Client("127.0.0.1");
		DavidClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DavidClient.initializeClient();
		
		Client WembzClient = new Client("127.0.0.1");
		WembzClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WembzClient.initializeClient();
	}
}
