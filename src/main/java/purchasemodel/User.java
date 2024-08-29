package purchasemodel;

public class User 
{
	private String UserName;
	private String Name;
	private String Password;

	public User() {}

	public User(String userName, String name, String password) 
	{
		this.UserName = userName;
		this.Name = name;
		this.Password = password;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	
	
	
	
}
