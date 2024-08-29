package purchasecontroller;

import java.util.Scanner;
import java.util.logging.Logger;
import purchaseservice.ProductService;
import purchaseservice.ProductServiceImpl;
import purchaseservice.PurchaseProductService;
import purchaseservice.PurchaseProductServiceImpl;
import purchaseservice.UserService;

public class UserLogin {
	
	private static final Logger logger;
	UserService userservice = new UserService();
	PurchaseProductService purchaseproductservice = new PurchaseProductServiceImpl();
	ProductService productservice = new ProductServiceImpl(); 
	
	 static {
	 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
	 logger = Logger.getLogger(UserLogin.class.getName());
	 }
	 
    public void menu(String userName)
    {
    	logger.info("1.View All Products");
    	logger.info("2.Filter Product");
    	logger.info("3.Purchase Product");	
    	logger.info("4.Change Password");
    	logger.info("5.Edit Details");
    	logger.info("6.Delete User");
    	logger.info("7.Exit");
    	logger.info("Enter Your choice:");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 7) {
        	choice = scanner.nextInt();
            switch (choice) {
                case 1:
                	productservice.createProduct();
                    break;
                case 2:
                	productservice.filterProduct();
                    break;
                case 3:
                	purchaseproductservice.createPurchaseOrder();
                    break;
                case 4:
                	userservice.editPassword(userName);
                    break;
                case 5:
                	userservice.editDetails(userName);
                	break;
                case 6:
                	userservice.deleteUser(userName);
                    break;
                case 7:
                	logger.info("Thank You");
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

