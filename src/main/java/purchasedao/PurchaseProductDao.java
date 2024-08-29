package purchasedao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import purchasemodel.Product;
import purchasemodel.PurchaseOrder;

public class PurchaseProductDao
{
	private Connection connection;private static final Logger logger;
	 
	 static {
	 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
	 logger = Logger.getLogger(PurchaseProductDao.class.getName());
	 }
	 
	public static final String DATE_OF_PURCHASE = "date_of_purchase";
	public static final String PRODUCT_CODE = "product_code";
	public static final String PRODUCT_NAME = "product_name";
	public static final String CATEGORY = "category";
	public static final String PURCHASE_QUANTITY = "purchase_quantity";
	public static final String PRODUCT_PRICE = "product_price";
	private static final String PROPERTIES_FILE = "database.properties";
	private Properties properties;
	Statement stmt;
	public PurchaseProductDao()
	{
		properties = new Properties();
		
        try 
        {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
            Class.forName(properties.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(properties.getProperty("jdbc.url"),
                                               properties.getProperty("jdbc.username"),
                                               properties.getProperty("jdbc.password"));
            stmt = connection.createStatement();
			String query = "CREATE TABLE IF NOT EXISTS Product (product_code varchar(255) NOT NULL,product_name varchar(255) NOT NULL,category varchar(255) NOT NULL,product_price double,product_quantity int,PRIMARY KEY (product_code))";
			String query2 = "CREATE TABLE IF NOT EXISTS purchase_order (product_code varchar(255) NOT null,purchase_quantity int not null,category varchar(255) not null,product_price double not null,date_of_purchase date NOT NULL)";
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
			
        }
		catch (SQLException | ClassNotFoundException | IOException  e) 
        {
			e.printStackTrace();
		} 

	}
	
	public boolean createPurchaseOrder(PurchaseOrder purchaseOrder)
	{
		try(Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT product_quantity FROM product WHERE product_code = '" +purchaseOrder.getProductCode() + "'");
				PreparedStatement preparedStatement = connection.prepareStatement(
						"INSERT INTO purchase_order (product_code, purchase_quantity, date_of_purchase, product_price,category) SELECT ?, ?, ?, (SELECT product_price FROM product WHERE product_code = ?),(SELECT category FROM product WHERE product_code = ?)")
				)
		{
			if (rs.next())
			{
				int currentProductQuantity = rs.getInt("product_quantity");
				if (purchaseOrder.getQuantity() <= currentProductQuantity) {
					
					preparedStatement.setString(1, purchaseOrder.getProductCode());
					preparedStatement.setInt(2, purchaseOrder.getQuantity());
					java.util.Date utilDate = new java.util.Date();
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
					preparedStatement.setDate(3, sqlDate);
					preparedStatement.setString(4, purchaseOrder.getProductCode());
					preparedStatement.setString(5, purchaseOrder.getProductCode());
					int purchaseQuantity = purchaseOrder.getQuantity();
					String productCode = purchaseOrder.getProductCode();
					String updateQuery = "UPDATE product SET product_quantity = product_quantity-" + purchaseQuantity
							+ " WHERE product_code = '" + productCode + "'";
					String updateQuery1 = "DELETE FROM product where product_quantity = 0";
					stmt.executeUpdate(updateQuery);
					stmt.executeUpdate(updateQuery1);
					preparedStatement.executeUpdate();
					return true;
				}
				else 
				{
					logger.info("Not enough products");
					return false;
				}
			}
			else 
			{
				logger.info("Sorry Product is not available");
				return false;
			}
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			return false;
		}
	}




	public List<PurchaseOrder> getPurchaseOrdersByProductCodeOrderedByTotalAmount(String productCode)
	{
		List<PurchaseOrder> purchaseOrders = new ArrayList<>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT product_code,category,date_of_purchase, purchase_quantity, product_price, purchase_quantity * product_price AS total_amount FROM purchase_order WHERE product_code = ? ORDER BY total_amount DESC"))
		{
			preparedStatement.setString(1, productCode);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
			{
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				purchaseOrder.setDateOfPurchase(resultSet.getDate(DATE_OF_PURCHASE));
				purchaseOrder.setQuantity(resultSet.getInt(PURCHASE_QUANTITY));
				purchaseOrder.setRate(resultSet.getDouble(PRODUCT_PRICE));
				purchaseOrder.settotalAmount(resultSet.getDouble("total_amount"));
				purchaseOrder.setProductCode(resultSet.getString(PRODUCT_CODE));
				purchaseOrder.setCategory(resultSet.getString(CATEGORY));
				purchaseOrders.add(purchaseOrder);
			}
		} 
		catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		return purchaseOrders;
	}


	public List<PurchaseOrder> getPurchaseOrdersByProductCodeOrderedByDateOfPurchase(String productCode)
	{
		List<PurchaseOrder> purchaseOrders = new ArrayList<>();
		try(PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT purchase_order.product_code,date_of_purchase,category, purchase_quantity, product_price, purchase_quantity * product_price AS total_amount FROM purchase_order WHERE product_code = ? ORDER BY date_of_purchase"))
		{			
			preparedStatement.setString(1, productCode);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				purchaseOrder.setProductCode(resultSet.getString(PRODUCT_CODE));
				purchaseOrder.setDateOfPurchase(resultSet.getDate(DATE_OF_PURCHASE));
				purchaseOrder.setQuantity(resultSet.getInt(PURCHASE_QUANTITY));
				purchaseOrder.setRate(resultSet.getDouble(PRODUCT_PRICE));
				purchaseOrder.setCategory(resultSet.getString(CATEGORY));
				purchaseOrder.settotalAmount(resultSet.getDouble("total_amount"));
				purchaseOrders.add(purchaseOrder);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return purchaseOrders;
	}
	
	public Product getProductByCode(String productCode) 
	{
		Product product = null;
		try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE product_code = ?"))
		{
			statement.setString(1, productCode);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) 
			{
				product = new Product();
				product.setProductCode(resultSet.getString(PRODUCT_CODE));
				product.setProductName(resultSet.getString(PRODUCT_NAME));
				product.setCategory(resultSet.getString(CATEGORY));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return product;
	}

	public List<PurchaseOrder> filterordersByCategory(String category) 
	{
		List<PurchaseOrder> products = new ArrayList<>();
		try(PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT purchase_order.product_code,category,purchase_quantity,purchase_order.product_price,date_of_purchase from purchase_order where category = ?")) 
		{
			preparedStatement.setString(1, category);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				purchaseOrder.setProductCode(resultSet.getString(PRODUCT_CODE));
				purchaseOrder.setDateOfPurchase(resultSet.getDate(DATE_OF_PURCHASE));
				purchaseOrder.setCategory(resultSet.getString(CATEGORY));
				purchaseOrder.setQuantity(resultSet.getInt(PURCHASE_QUANTITY));
				purchaseOrder.setRate(resultSet.getDouble(PRODUCT_PRICE));
				products.add(purchaseOrder);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return products;
	}

	public List<PurchaseOrder> filterordersByDate(String fromDate, String toDate)
	{
		List<PurchaseOrder> products = new ArrayList<>();
		try(PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT purchase_order.product_code,purchase_quantity,category,purchase_order.product_price,date_of_purchase from purchase_order where date_of_purchase between ? and ?"))
		{
			java.util.Date from = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			java.sql.Date fromSqlDate = new java.sql.Date(from.getTime());
			preparedStatement.setDate(1, fromSqlDate);

			java.util.Date to = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
			java.sql.Date toSqlDate = new java.sql.Date(to.getTime());
			preparedStatement.setDate(2, toSqlDate);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
			{
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				purchaseOrder.setProductCode(resultSet.getString(PRODUCT_CODE));
				purchaseOrder.setDateOfPurchase(resultSet.getDate(DATE_OF_PURCHASE));
				purchaseOrder.setCategory(resultSet.getString(CATEGORY));
				purchaseOrder.setQuantity(resultSet.getInt(PURCHASE_QUANTITY));
				purchaseOrder.setRate(resultSet.getDouble(PRODUCT_PRICE));
				products.add(purchaseOrder);
			}
		}
		catch (SQLException | ParseException e) 
		{
			e.printStackTrace();
		}
		return products;
	}

	public List<PurchaseOrder> filterordersByPrice(double startPrice, double endPrice)
	{
		List<PurchaseOrder> products = new ArrayList<>();
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT purchase_order.product_code,purchase_quantity,category,purchase_order.product_price,date_of_purchase from purchase_order where product_price between ? and ? order by product_price " + (startPrice < endPrice ? "ASC" : "DESC")))
		{
			if(startPrice<endPrice)
			{
				preparedStatement.setDouble(1, startPrice);
				preparedStatement.setDouble(2, endPrice);
			}
			else
			{
				preparedStatement.setDouble(1, endPrice);
				preparedStatement.setDouble(2, startPrice);
			}
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				purchaseOrder.setProductCode(resultSet.getString(PRODUCT_CODE));
				purchaseOrder.setDateOfPurchase(resultSet.getDate(DATE_OF_PURCHASE));
				purchaseOrder.setCategory(resultSet.getString(CATEGORY));
				purchaseOrder.setQuantity(resultSet.getInt(PURCHASE_QUANTITY));
				purchaseOrder.setRate(resultSet.getDouble(PRODUCT_PRICE));
				products.add(purchaseOrder);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return products;
	}
	public void close()
	{
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
		e.printStackTrace();
		} 
	}

}
