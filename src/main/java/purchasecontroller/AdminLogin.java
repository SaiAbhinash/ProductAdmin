package purchasecontroller;

import java.util.Scanner;
import java.util.logging.Logger;

import purchaseservice.ProductService;
import purchaseservice.ProductServiceImpl;
import purchaseservice.PurchaseProductService;
import purchaseservice.PurchaseProductServiceImpl;
import purchaseservice.UserService;

public class AdminLogin {
	private static final Logger logger;
	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
		logger = Logger.getLogger(AdminLogin.class.getName());
	}

	Scanner scanner = new Scanner(System.in);
	UserService userservice = new UserService();
	ProductService productService = new ProductServiceImpl();
	PurchaseProductService purchaseProductService = new PurchaseProductServiceImpl();

	public void displaymenu() 
	{
		int choice =0;
		while(choice != 3)
		{
			logger.info("1.View Users Menu");
			logger.info("2.View Products Menu");
			logger.info("Enter Your Choice");
			choice = scanner.nextInt();
			switch(choice)
			{
				case 1:
					usermenu();
					break;
				case 2:
					productmenu();
					break;
				case 3:
					System.exit(0);
					break;
				default:
					logger.info("Enter Correct Choice");
					
			}
			
		}
		
	}

	public void productmenu()
	{
		int choice = 0;
		while(choice != 10)
		{
			logger.info("1.Create Product");
	    	logger.info("2.View All Products");
	    	logger.info("3.Edit Product");
	    	logger.info("4.Delete Product");
	    	logger.info("5.Filter Product");
	    	logger.info("6.Sell Product");
	    	logger.info("7.View Product by Product code order by Purchase date");
	    	logger.info("8.View Product by Product code order by Amount");
	    	logger.info("9.Filter Purchase Product");
	    	logger.info("10.Exit");
	    	logger.info("Enter Your choice:");
			choice = scanner.nextInt();
			switch(choice)
			{
				case 1:
					productService.createProduct();
					break;
				case 2:
					productService.viewAllProducts();
					break;
				case 3:
					productService.editProduct();
					break;
				case 4:
					productService.deleteProduct();
					break;
				case 5:
					productService.filterProduct();
					break;
				case 6:
					purchaseProductService.createPurchaseOrder();
					break;
				case 7:
					purchaseProductService.viewProductByCodeOrderByPurchaseDate();
					break;
				case 8:
					purchaseProductService.viewProductByCodeOrderByTotalAmount();
					break;
				case 9:
					purchaseProductService.filterPurchaseProduct();
					break;
				case 10:
					displaymenu();
					break;
				default:
					logger.info("Enter The Correct Deatils");
					break;
				
					
			}
		}
	}
	
	
	public void usermenu()
	{
		int choice = 0;
		while(choice != 4)
		{
			logger.info("1.Create User");
	    	logger.info("2.View All Users");
	    	logger.info("3.Edit User");
	    	logger.info("4.Exit");
	    	logger.info("Enter Your choice:");
			choice = scanner.nextInt();
			switch(choice)
			{
				case 1:
					userservice.createUser();
					break;
				case 2:
					userservice.viewAllUsers();
					break;
				case 3:
					userservice.editDetailsByAdmin();
					break;
				case 4:
					displaymenu();
					break;
				default:
					logger.info("Enter The Correct Deatils");
					break;		
			}
		}
	}
}
