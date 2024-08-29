package purchasemodel;

import java.util.Date;
import java.util.logging.Logger;

public class PurchaseOrder
{
  private String productCode;
  private int quantity;
  private String category;
  private double rate;
  private Date dateOfPurchase;
  private String productName;
  private double totalAmount;
  
  

  public static final Logger logger = Logger.getLogger(PurchaseOrder.class.getName()); 

  public PurchaseOrder() {}

  public PurchaseOrder(String productCode,int quantity)
  {
    this.productCode = productCode;
    this.quantity = quantity;
  }
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getRate() {
		return rate;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}
	
	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	
	public String getproductName() {
		return productName;
	}
	
	public void setproductName(String productName) {
		this.productName = productName;
	}
	
	public double gettotalAmount() {
		return totalAmount;
	}
	
	public void settotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	  public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public void print() 
		{
	        logger.info(String.format("%5s %10s %10s %10s %10s %n", getProductCode(),getCategory(),getQuantity(),getRate(),getDateOfPurchase()));
	    }
	
	
	@Override
	public String toString()
	{
		return getDateOfPurchase()+" "+getQuantity()+" "+getRate()+" "+gettotalAmount();
	}
}
