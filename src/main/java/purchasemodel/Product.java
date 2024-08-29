package purchasemodel;

import java.util.logging.Logger;

public class Product{
  private String productCode;
  private String productName;
  private String category;
  private double price;
  private double averagePrice;
  private int totalQuantity;
  private double averageStockAmount;
  

  public static final Logger logger = Logger.getLogger(Product.class.getName());
  public Product() {}

  public Product(String productCode, String productName, String category,double price,int quantity) 
  {
    this.productCode = productCode;
    this.productName = productName;
    this.category = category;
    this.price = price;
    this.totalQuantity = quantity;
  }



	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getAveragePrice() {
		return averagePrice;
	}
	
	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}
	
	public int gettotalQuantity() {
		return totalQuantity;
	}
	
	public void settotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	public double getAverageStockAmount() {
		return averageStockAmount;
	}
	
	public void setaverageStockAmount(double setAverageStockAmount) {
		this.averageStockAmount = setAverageStockAmount;
	}

	public void print() 
	{
		String fmt="%8s %13s %10s %10s %10s %12s \n";
        logger.info(String.format(fmt, getProductCode(), getProductName(), getCategory(), getAveragePrice(), gettotalQuantity(), getAverageStockAmount()));
    }

@Override
	public String toString() {
		
		return getProductCode()+ "\t" + getProductName()+ "\t" +getCategory()+ "\t" +getAveragePrice()+ "\t" +gettotalQuantity();
	}
}
