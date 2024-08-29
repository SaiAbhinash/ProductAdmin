package purchasecontroller;

import java.util.Scanner;
import java.util.logging.Logger;

import purchasedao.UserDao;
import purchasemodel.User;
import purchaseservice.UserService;
import purchaseservice.Verify;

public class AdminUserLogin 
{

	private static final Logger logger;
	UserLogin userlogin = new UserLogin();
	AdminLogin adminlogin = new AdminLogin();
	UserService userservice = new UserService();
	UserDao userdao = new UserDao();
	Scanner scanner = new Scanner(System.in);
	
	 static {
		 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
		 logger = Logger.getLogger(UserLogin.class.getName());
		 }
	
	public void mainMenu()
    {
    	logger.info("1.Admin");
    	logger.info("2.User");
    	logger.info("3.Exit");
    	logger.info("Enter Your choice:");
        Scanner scanner = new Scanner(System.in);
        Verify verify=new Verify();
        int choice = 0;
        choice = scanner.nextInt();
        while (choice > 0) {
            switch (choice) {
                case 1:
                	verify.adminCheck();
                    break;
                case 2:
                	verify.userCheck();
                    break;
                case 3:
                	System.exit(0);
                    break;
                default:
                	logger.info("Enter the correct choice");
					break;
            }
        }
        scanner.close();
    }
	
	
}
