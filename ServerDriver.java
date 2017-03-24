import javax.swing.JFrame;

public class ServerDriver {
	public static void main(String[] args){
		Server David = new Server();
		David.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		David.initiateServer();
	}
}
