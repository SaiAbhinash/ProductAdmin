package purchaseservice;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import purchaseexception.FailedToCreateException;
import purchaseexception.FailedToPurchaseException;
import purchaseexception.ProductNotFoundException;
import purchaseexception.WrongChoiceException;
import purchasemodel.Product;
import purchasemodel.PurchaseOrder;
import purchaseproductcontroller.ProductController;
import purchaseproductcontroller.PurchaseProductController;


public class PurchaseProductServiceImpl implements PurchaseProductService
{
    private PurchaseProductController purchaseProductController = new PurchaseProductController();
    private ProductController productController = new ProductController();
    private static final Logger logger;
	 
	 static {
	 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
	 logger = Logger.getLogger(PurchaseProductServiceImpl.class.getName());
	 }
	 
	private Scanner scanner = new Scanner(System.in);
	public void createPurchaseOrder() throws ProductNotFoundException,FailedToCreateException
    {
    	logger.info("Enter Product Code: ");
    	String productcode = scanner.next();
    	Product product = productController.getProductByCode(productcode);
        if (product != null) 
        {
        	logger.info("Enter the Quantity : ");
        	int quantity = scanner.nextInt();
        	PurchaseOrder purchaseorder = new PurchaseOrder(productcode,quantity);
    		if(purchaseProductController.createPurchaseOrder(purchaseorder))
			{
				logger.info("Product Purchased Sucessfully.");
			}
			else
			{
				throw new FailedToPurchaseException();
			}
        }
        else 
        {
            throw new ProductNotFoundException();
        }   	
    }

    public void viewProductByCodeOrderByPurchaseDate() throws ProductNotFoundException 
    {
    	logger.info("Enter product code: ");
        String code = scanner.nextLine();
        Product product = productController.getProductByCode(code);
        if(product != null)
        {
        	logger.info("List of all products order by purchase date:");
	        List<PurchaseOrder> purchaseOrders = null;
			
			purchaseOrders = purchaseProductController.getPurchaseOrdersByProductCodeOrderedByDateOfPurchase(code);
			purchaseOrders.forEach(order -> logger.info(String.format("%-15s %-12s %-10s %-10s %-10s %n",order.getProductCode(),order.getQuantity(),order.getCategory(),order.getRate(),order.getDateOfPurchase())));
		    
        }
        else
        {
        	throw new ProductNotFoundException();
        }
    }
    
    
    public void viewProductByCodeOrderByTotalAmount() throws ProductNotFoundException
    {
    	logger.info("Enter product code: ");
        String code = scanner.nextLine();
        Product product = productController.getProductByCode(code);
        if(product != null)
        {
        	logger.info("List of all products order by total amount");
	        List<PurchaseOrder> purchaseOrderss;
	        purchaseOrderss = purchaseProductController.getPurchaseOrdersByProductCodeOrderByTotalAmount(code);
			purchaseOrderss.forEach(order -> logger.info(String.format("%-15s %-12s %-10s %-12s %-11s %-13s %n",order.getProductCode(),order.getQuantity(),order.getCategory(),order.getRate(),order.gettotalAmount(),order.getDateOfPurchase())));
		  
        }
        else
        {
        	throw new ProductNotFoundException(); 
        }
    }
    
    public void filterPurchaseProduct() throws WrongChoiceException
    {   
    	logger.info("Do you want to filter by category (y/n)? ");
        String choice = scanner.next();
        if(choice.equalsIgnoreCase("y"))
        {
        	filterPurchaseProductByCategory();
        }
        else if(choice.equalsIgnoreCase("n"))
        {
        	logger.info("Do you want to filter PurchaseProducts by date (y/n)? ");
        	String choice1 = scanner.next();
        	if(choice1.equalsIgnoreCase("y"))
        	{
        		filterPurchaseProductByDate();
        	}
        	else if(choice.equalsIgnoreCase("n"))
        	{
        		filterPurchaseProductByPrice();
        	}
        	else
        	{
        		throw new WrongChoiceException();
        	}
        }
        else
    	{
        	throw new WrongChoiceException();
    	}
    }
    
    
    public void filterPurchaseProductByCategory()
    {
    	logger.info("Enter the category: ");
    	String category = scanner.next();
    	List<PurchaseOrder> filterOrdersByCategory = purchaseProductController.filterOrdersByCategory(category);
    	if(!filterOrdersByCategory.isEmpty())
    	{
    	    filterOrdersByCategory.forEach(order -> logger.info(String.format("%-15s %-12s %-10s %-10s %-11s %n",order.getProductCode(),order.getQuantity(),order.getCategory(),order.getRate(),order.getDateOfPurchase())));
    	}
    	else
    	{
    		logger.info("No such Category");
    	}
    }
    
    public void filterPurchaseProductByDate()
    {
    	logger.info("Please enter from and to date in the format 'yyyy-mm-dd' \n");
		logger.info("Enter from date: ");
		String fromDate = scanner.next();
		logger.info("Enter to date: ");
		String toDate = scanner.next();
		List<PurchaseOrder> filterOrdersByDate = purchaseProductController.filterOrdersByDate(fromDate,toDate);
		filterOrdersByDate.forEach(order -> logger.info(String.format("%-15s %-12s %-10s %-11s %-10s %n",order.getProductCode(),order.getQuantity(),order.getCategory(),order.getRate(),order.getDateOfPurchase())));
		
    }

    public void filterPurchaseProductByPrice()
    {
    	logger.info("Filtering products by price..");
		double startPrice=0;
		double endPrice=0;
		try
		{
			logger.info("Select Range from :");
    		startPrice = scanner.nextDouble();
    		logger.info("Select Range to :");
    		endPrice = scanner.nextDouble();
    		List<PurchaseOrder> filterOrdersByPrice;
    		filterOrdersByPrice = purchaseProductController.filterOrdersByPrice(startPrice,endPrice);		
    		filterOrdersByPrice.forEach(order -> logger.info(String.format("%-15s %-12s %-11s %-10s %-10s %n",order.getProductCode(),order.getQuantity(),order.getCategory(),order.getRate(),order.getDateOfPurchase())));
    	   
		} 
		catch (InputMismatchException e)
		{
			logger.info("Invalid input. Please enter a valid Value.");
			scanner.nextLine();
		}
    }
}
