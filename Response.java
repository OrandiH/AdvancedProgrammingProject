package sdsmh_server;

import java.io.Serializable;
//Response Object to be sent between clients and server
public class Response implements ResponseMethods,Serializable{
	//Attributes
	private static final long serialVersionUID = 1L;
	private String message;
	private String Action;
	private int status;
	private String source;
	//Credentials for login 
	private int id;


	private String password; 
	
	//Constructors
	//DEFAULT
	public Response()
	{
		this.message = "";
		this.Action = "";
		this.status = 0;
		this.source = "";
	}
	//PRIMARY
	public Response(String message,String action,int status,String source)
	{
		this.message = message;
		this.Action = action;
		this.status = status;
		this.source = source;
	}
	//COPY
	public Response(Response A)
	{
		this.message = A.message;
		this.Action = A.Action;
		this.source = A.source;
		this.status = A.status;
	}
	//GETTERS AND SETTERS
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSource()
	{
		return this.source;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	//METHODS FROM INTERFACE
	@Override
	public Response getResponseObject() {
		return this;
	}

	@Override
	public String getResponseAction() {
		String action;
		action = this.getAction();
		return action;
		
	}

	@Override
	public String getResponseMessage() {
		String message;
		message = this.getMessage();
		return message;
		
	}

	@Override
	public String getRepsonseSource() {
		String source;
		source = this.getSource();
		return source;
		
	}
	
	public String toString()
	{
		return new String("Response Source: " +
				this.source + "\n" + "Response status: " +
				this.status + "\n" + "Reponse Message: " + 
				this.message + "\n" + "Response action: " + 
				this.Action + "\n"
				);
				
				
				
	}

}
