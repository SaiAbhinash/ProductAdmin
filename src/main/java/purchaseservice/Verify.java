package purchaseservice;

import java.util.Scanner;
import java.util.logging.Logger;

import purchasecontroller.AdminLogin;
import purchasecontroller.AdminUserLogin;
import purchasecontroller.UserLogin;
import purchasedao.UserDao;
import purchasemodel.User;

public class Verify {
	private static final Logger logger;
	static {
		 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
		 logger = Logger.getLogger(Verify.class.getName());
		 }
	Scanner scanner=new Scanner(System.in);
	AdminLogin adminlogin=new AdminLogin();
	UserDao userdao=new UserDao();
	UserLogin userlogin = new UserLogin();
	UserService userservice = new UserService();
	AdminUserLogin adminuserlogin=new AdminUserLogin();
	
	public void adminCheck()
	 {
		 logger.info("Enter The UserName");
		 String username = scanner.next();
		 logger.info("Enter The Pasword");
		 String password = scanner.next();
		 if(username.equals("admin") && password.equals("admin"))
		 {
			 adminlogin.displaymenu();
		 }
		 else
		 {
			 logger.info("Incorect Credetainls");
			 
		 }
	 }
	 
	public boolean userCheck()
	{
		Boolean ans=false;
		logger.info("Enter User Name:");
		String userName = scanner.next();
		User user = userdao.verifyUser(userName);
		if(user == null)
		{
			logger.info("User Not Found!");
			logger.info("Do You Want To Create New User (y/n)");
			String choice = scanner.next();
			if(choice.equalsIgnoreCase("y"))
			{
				userservice.createUser();
			}
			else
			{
				adminuserlogin.mainMenu();
			}
		
			
		}	
		else
		{
			String userPassword = user.getPassword();
			logger.info("Enter Password:");
			String password = scanner.next();
			if(password.equals(userPassword))
			{
				userlogin.menu(userName);
			}
			else
			{
				logger.info("Incorrect Credentails!");
				
			}
		}
		return ans;
	}
	
	

}
