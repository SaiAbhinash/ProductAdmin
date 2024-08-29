package purchaseservice;

import purchaseexception.FailedToCreateException;
import purchaseexception.ProductNotFoundException;
import purchaseexception.WrongChoiceException;

public interface ProductService 
{
	
	void createProduct() throws FailedToCreateException;

	void viewAllProducts() throws ProductNotFoundException,WrongChoiceException;
	
	void editProduct() throws ProductNotFoundException;

	void deleteProduct() throws ProductNotFoundException;

	void filterProduct() throws WrongChoiceException;	

}
