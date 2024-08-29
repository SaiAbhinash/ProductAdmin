package purchaseservice;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import purchaseexception.FailedToCreateException;
import purchaseexception.ProductNotFoundException;
import purchaseexception.WrongChoiceException;
import purchasemodel.Product;
import purchasemodel.PurchaseOrder;
import purchaseproductcontroller.ProductController;



public class ProductServiceImpl implements ProductService
{
    private ProductController productController = new ProductController();
    private static final Logger logger;
	 
	 static {
	 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
	 logger = Logger.getLogger(ProductServiceImpl.class.getName());
	 }
	 
    
    private Scanner scanner = new Scanner(System.in);
    List<PurchaseOrder> purchaseOrders = null;
   
    public void createProduct() throws FailedToCreateException {
        String code = readCode();
        Product checkProduct = productController.getProductByCode(code);
        if(checkProduct != null)
        {
        	logger.log(Level.INFO,"The Product with this ProductCode:{0} is alredy available..",code);
        }
        else
        {
	        String name = readName();
	        String category = readCategory();
	        double price = readPrice();
	        int quantity = readQuantity();
	        Product product = new Product(code, name, category, price, quantity);
	        if (productController.createProduct(product)) {
	            logger.info("Product created successfully!");
	        } else {
	            throw new FailedToCreateException();
	        }
        }
    }

    public void editProduct() throws ProductNotFoundException, WrongChoiceException {
        String code = readCode();
        Product product = productController.getProductByCode(code);
        if (product != null) {
            if (confirm("Do you want to edit (y/n)? ")) {
                String name = readName();
                String category = readCategory();
                Double price = readPrice();
                Integer quantity = readQuantity();
                product.setProductName(name);
                product.setCategory(category);
                if (price != null) {
                    product.setPrice(price);
                }
                if (quantity != null) {
                    product.settotalQuantity(quantity);
                }
                if (confirm("Do you want to update (y/n)? ")) {
                    productController.editProduct(product);
                } else {
                    logger.info("Update failed.");
                }
            } else {
                logger.info("Update cancelled.");
            }
        } else {
            throw new ProductNotFoundException();
        }
    }

    private String readCode() {
        String code;
        while (true) {
            logger.info("Enter product code: ");
            code = scanner.nextLine().trim();
            if (!code.isEmpty()) {
                return code;
            }
            logger.info("Product code cannot be empty.");
        }
    }

    private String readName() {
        String name;
        while (true) {
            logger.info("Enter product name: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                return name;
            }
            logger.info("Product name cannot be empty.");
        }
    }

    private String readCategory() {
        String category;
        while (true) {
            logger.info("Enter product category: ");
            category = scanner.nextLine().trim();
            if (!category.isEmpty()) {
                return category;
            }
            logger.info("Product category cannot be empty.");
        }
    }

    private Double readPrice() {
        while (true) {
            logger.info("Enter product price: ");
            String priceString = scanner.nextLine().trim();
            if (priceString.isEmpty()) {
                return null;
            }
            try {
                return Double.parseDouble(priceString);
            } catch (NumberFormatException e) {
                logger.info("Invalid price input.");
            }
        }
    }

    private Integer readQuantity() {
        while (true) {
            logger.info("Enter product quantity: ");
            String quantityString = scanner.nextLine().trim();
            if (quantityString.isEmpty()) {
                return null;
            }
            try {
                return Integer.parseInt(quantityString);
            } catch (NumberFormatException e) {
                logger.info("Invalid quantity input.");
            }
        }
    }

    private boolean confirm(String prompt) {
        while (true) {
            logger.info(prompt);
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("y")) {
                return true;
            } else if (response.equals("n")) {
                return false;
            }
            else {
            	logger.info("Enter the Correct Choice");
            }
        }
    }
    

    public void viewAllProducts()
    {
        List<Product> products = null;
		products = productController.getAllProducts();		
		if(products != null) {
			String fmt = "%-10s %-10s %-10s %-10s %-10s %-10s \n";
		    logger.info(String.format(fmt, "product_code", "product_name", "category",
		        "Price", "Quantity", "StockAmount"));
		    for(Product order : products) {
		        if (order.gettotalQuantity() > 0) {
		            order.print();
		        }
		    }
		}
        else
    	{
    		logger.info("No Products to Display");
    	}
    }

    

    
    public void deleteProduct() throws ProductNotFoundException
    {
    	logger.info("Enter product code :");
        String code = scanner.nextLine();
        Product product = productController.getProductByCode(code);
        if (product != null) 
        {
			productController.deleteProduct(code);		
            logger.info("Deleted Product Sucessfully.");
        }
        else 
        {
        	throw new ProductNotFoundException();   
        }
    }    
    
    public void filterProduct() throws WrongChoiceException
    {   
    	logger.info("Do you want to filter by category (y/n)? ");
        String choice = scanner.next();
        if(choice.equalsIgnoreCase("y"))
        {
        	filterProductByCategory();
        }
        else if(choice.equalsIgnoreCase("n"))
        {
        	logger.info("Do you want to filter PurchaseProducts by Name (y/n)? ");
        	String choice1 = scanner.next();
        	if(choice1.equalsIgnoreCase("y"))
        	{
        		filterProductByName();
        	}
        	else if(choice1.equalsIgnoreCase("n"))
        	{
        		filterProductByPrice();
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
    
    
    
    public void filterProductByCategory()
    {
    	logger.info("Enter the category: ");
    	String category = scanner.next();
    	List<Product> filterProductsByCategory = null;	
		filterProductsByCategory = productController.filterProductsByCategory(category);
    	if(!filterProductsByCategory.isEmpty())
    	{
    		logger.info("Filtering Products By Category:");
    		filterProductsByCategory.forEach(order -> logger.info(String.format("%-15s %-12s %-11s %-10s %-10s %n",order.getProductCode(),order.getProductName(),order.gettotalQuantity(),order.getCategory(),order.getPrice())));
    	    
    	}
    	else
    	{
    		logger.info("No such Category");
    	}
    }
    
    public void filterProductByName()
    {
    	logger.info("Enter the Product Name: ");
    	String name = scanner.next();
		List<Product> filterProductsByName;
		filterProductsByName = productController.filterProductsByName(name);	
    	if(!filterProductsByName.isEmpty())
    	{
    		logger.info("Filtering Products By Model");
    		filterProductsByName.forEach(order -> logger.info(String.format("%-15s %-12s %-10s %-11s %-10s %n",order.getProductCode(),order.getProductName(),order.gettotalQuantity(),order.getCategory(),order.getPrice())));
    	    
    	}
    	else
    	{
    		logger.info("No such Model");
    	}
    }
    
    public void filterProductByPrice()
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
    		List<Product> filterProductsByPrice = productController.filterProductsByPrice(startPrice,endPrice);
    		logger.info("Filtering Products By Price:");
    		filterProductsByPrice.forEach(order -> logger.info(String.format("%-15s %-12s %-10s %-10s %-11s %n",order.getProductCode(),order.getProductName(),order.gettotalQuantity(),order.getCategory(),order.getPrice())));
    	    
		} 
		catch (InputMismatchException e)
		{
			logger.info("Invalid input. Please enter a valid Value.");
			scanner.nextLine();
		}
    }    
}
