package purchaseservice;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import purchasecontroller.AdminUserLogin;
import purchasedao.UserDao;
import purchasemodel.User;
import purchaseproductcontroller.AdminController;

public class UserService
{
	private static final Logger logger;
	UserDao userdao = new UserDao();
	Scanner scanner = new Scanner(System.in);
	 AdminController admincontroller = new AdminController();
	
	 static {
	 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
	 logger = Logger.getLogger(UserService.class.getName());
	 }
	 
	public void createUser()
	{
		logger.info("Enter UserName");
		String username = scanner.next();
		logger.info("Enter Name");
		String name = scanner.next();
		logger.info("Enter Password");
		String password = scanner.next();
		User user = new User(username,name,password);
		userdao.createUser(user);
	}
	
	
	public void viewAllUsers()
    {
        List<User> users = null;
		users = admincontroller.getAllUsers();		
		if(users != null) 
		{
		    for(User user : users) 
		    {
		    	logger.info(user.getUserName()+" "+user.getName());
		    }
		}
        else
    	{
    		logger.info("No Users to Display");
    	}
    }

	public void editPassword(String userName)
	{
		logger.info("Enter the New Password:");
		String password = scanner.next();
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		admincontroller.editPassword(user);
	}
	
	public void deleteUser(String userName)
	{
		logger.info("Do You Want To Delete? (y/n)");
		String choice = scanner.next();
		if(choice.equalsIgnoreCase("y"))
		{
			admincontroller.deleteUser(userName);
		}
	}
	
	public void editDetails(String userName)
	{
		logger.info("Enter the New Name:");
		String name = scanner.next();
		User user = new User();
		user.setUserName(userName);
		user.setName(name);
		admincontroller.editPassword(user);
	}
	
	public void editDetailsByAdmin()
	{
		logger.info("Enter UserName:");
		String userName = scanner.next();
		logger.info("Enter the New Name:");
		String name = scanner.next();
		User user = new User();
		user.setUserName(userName);
		user.setName(name);
		admincontroller.editPassword(user);
	}
}
