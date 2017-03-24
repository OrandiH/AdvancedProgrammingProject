import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame{
	private JTextField userText; //where you type
	private JTextArea chatWindow; //area where the chat shows up
	private ObjectOutputStream oos; //output stream, server's input
	private ObjectInputStream ois; //input stream, server's output
	private Socket connection; //connection itself
	private String message = ""; //the message
	private String serverIP = ""; //the IP address of the server

	//constructor
	public Client(String host){
		super("Client");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						sendMessage(e.getActionCommand());
						userText.setText("");
					}
				}
		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		setSize(300, 150);
		setVisible(true);
	}
	
	//connect to the server
	public void initializeClient(){
		try{
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(EOFException e){
			showMessage("\nClient terminated connection");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			closeCrap();
		}
	}
	
	//connect to server
	private void connectToServer() throws IOException{
		showMessage("Attempting connection...\n");
		connection = new Socket(InetAddress.getByName(serverIP), 8888); //passes in IPAddress
		showMessage("Connection to:" + connection.getInetAddress().getHostName() + " achieved!\n");
	}
	
	//setting up streams here
	private void setupStreams() throws IOException{
		oos = new ObjectOutputStream(connection.getOutputStream());
		oos.flush();
		ois = new ObjectInputStream(connection.getInputStream());
		showMessage("\nCommunications enabled.");
	}
	
	//while chatting with the server
	private void whileChatting() throws IOException{
		ableToType(true);
		do{
			try{
				message = (String) ois.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException e){
				showMessage("\n Unable to retrieve message..");
			}
		}while(!message.equals("SEVER - END"));
	}
	
	//closes the streams and sockets
	private void closeCrap(){
		showMessage("\n Closing connection..");
		ableToType(false);
		try{
			oos.close();
			ois.close();
			connection.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//sends messages to server
	private void sendMessage(String message) {
		try{
			oos.writeObject("CLIENT - " + message);
			oos.flush();
			showMessage("\nCLIENT - " + message);
		}catch(IOException e){
			//e.printStackTrace();
			chatWindow.append("\nMessage unable to send..");
		}
	}
	
	//changes/updates chatWindow
	private void showMessage(final String message){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(message);
				}
			}
		);
	}
	
	//gives user permission to type crap into the text box
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(tof);
				}
			}
		);
	}
}
