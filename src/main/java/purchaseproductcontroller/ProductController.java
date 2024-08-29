package purchaseproductcontroller;

import java.util.List;
import purchasedao.ProductDao;
import purchasemodel.Product;

public class ProductController {

  private ProductDao productDao;

  public ProductController() {
    this.productDao = new ProductDao();
  }

	public boolean createProduct(Product product){
		return productDao.createProduct(product);
	}

	
	public List<Product> getAllProducts(){
		return productDao.getAllProducts();
	}
	

	public boolean editProduct(Product product){
		return productDao.editProduct(product);
	}

	public boolean deleteProduct(String product){
		return productDao.deleteProduct(product);
	}
	
	public Product getProductByCode(String productCode) {
		return productDao.getProductByCode(productCode);
	}
	
	public List<Product> filterProductsByCategory(String name){
		return productDao.filterProductsByCategory(name);
	}

	public List<Product> filterProductsByName(String name){
		return productDao.filterProductsByName(name);
	}

	public List<Product> filterProductsByPrice(double startPrice, double endPrice){
		return productDao.filterProductsByPrice(startPrice, endPrice);
	}
}
