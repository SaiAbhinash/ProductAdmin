package purchaseproductcontroller;

import java.util.List;
import purchasedao.PurchaseProductDao;
import purchasemodel.PurchaseOrder;

public class PurchaseProductController
{
	PurchaseProductDao purchaseproductdao = new PurchaseProductDao();
	public boolean createPurchaseOrder(PurchaseOrder purchaseOrder) {
		return purchaseproductdao.createPurchaseOrder(purchaseOrder);
	}

	public List<PurchaseOrder> getPurchaseOrdersByProductCodeOrderedByDateOfPurchase(String productCode){
		return purchaseproductdao.getPurchaseOrdersByProductCodeOrderedByDateOfPurchase(productCode);
	}

	public List<PurchaseOrder> getPurchaseOrdersByProductCodeOrderByTotalAmount(String productCode){
		return purchaseproductdao.getPurchaseOrdersByProductCodeOrderedByTotalAmount(productCode);
	}

	public List<PurchaseOrder> filterOrdersByCategory(String category){
		return purchaseproductdao.filterordersByCategory(category);
	}

	public List<PurchaseOrder> filterOrdersByDate(String fromDate, String toDate){
		return purchaseproductdao.filterordersByDate(fromDate, toDate);
	}

	public List<PurchaseOrder> filterOrdersByPrice(double startPrice, double endPrice){
		return purchaseproductdao.filterordersByPrice(startPrice, endPrice);
	}
	
}
