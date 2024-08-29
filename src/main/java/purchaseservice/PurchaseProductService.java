package purchaseservice;

import purchaseexception.FailedToCreateException;
import purchaseexception.ProductNotFoundException;
import purchaseexception.WrongChoiceException;

public interface PurchaseProductService 
{

	void createPurchaseOrder() throws FailedToCreateException;
	
	void viewProductByCodeOrderByTotalAmount() throws ProductNotFoundException;

	void viewProductByCodeOrderByPurchaseDate() throws ProductNotFoundException;

	void filterPurchaseProduct() throws WrongChoiceException;

}
