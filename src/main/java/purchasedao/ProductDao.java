package purchasedao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import purchasemodel.Product;

public class ProductDao
{
	private Connection connection;
	public static final String PRODUCT_CODE = "product_code";
	public static final String PRODUCT_NAME = "product_name";
	public static final String CATEGORY = "category";
	public static final String PRODUCT_QUANTITY = "product_quantity";
	public static final String PRODUCT_PRICE = "product_price";
	public static final String PASSWORD = "";
	public static final String USER = "root";
	
	private static final Logger logger;
	 
	 static {
	 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
	 logger = Logger.getLogger(ProductDao.class.getName());
	 }
	 
	private static final String PROPERTIES_FILE = "database.properties";
	private Properties properties;
	public ProductDao() 
	{	
		properties = new Properties();
        try 
        {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
            Class.forName(properties.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(properties.getProperty("jdbc.url"),
                                               properties.getProperty("jdbc.username"),
                                               properties.getProperty("jdbc.password"));
            Statement stmt = connection.createStatement();
			String query = "CREATE TABLE IF NOT EXISTS Product (product_code varchar(255) NOT NULL,product_name varchar(255) NOT NULL,category varchar(255) NOT NULL,product_price double,product_quantity int,PRIMARY KEY (product_code))";
			String query2 = "CREATE TABLE IF NOT EXISTS purchase_order (product_code varchar(255) NOT null,purchase_quantity int not null,category varchar(255) not null,product_price double not null,date_of_purchase date NOT NULL)";
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
			stmt.close();
        }
		catch (SQLException | ClassNotFoundException | IOException  e) 
        {
			e.printStackTrace();
			
		} 

	}

		


	public boolean createProduct(Product product) {
	    try (PreparedStatement preparedStatement = connection.prepareStatement(
	            "INSERT INTO product (product_code, product_name, category,product_price,product_quantity) VALUES (?, ?, ?, ?, ?) "))
	    {
	        preparedStatement.setString(1, product.getProductCode());
	        preparedStatement.setString(2, product.getProductName());
	        preparedStatement.setString(3, product.getCategory());
	        preparedStatement.setDouble(4, product.getPrice());
	        preparedStatement.setInt(5, product.gettotalQuantity());
	        preparedStatement.executeUpdate();
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}


	
	public List<Product> getAllProducts()
	{
		List<Product> products = new ArrayList<>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT product.product_code, product.product_name, product.category, AVG(product.product_price) AS average_price, product_quantity as total_quantity, AVG(product_quantity * product.product_price) AS average_stock_amount FROM product GROUP BY product_code, product_name");)
		{
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
			{
				Product product = new Product();
				product.setProductCode(resultSet.getString(PRODUCT_CODE));
				product.setProductName(resultSet.getString(PRODUCT_NAME));
				product.setCategory(resultSet.getString(CATEGORY));
				product.setAveragePrice(resultSet.getDouble("average_price"));
				product.settotalQuantity(resultSet.getInt("total_quantity"));
				product.setaverageStockAmount(resultSet.getDouble("average_stock_amount"));
				products.add(product);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return products;
	}
	
	
	public List<Product> filterProductsByCategory(String category) 
	{
		List<Product> products = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT product_code,product_name,category,product_quantity,product_price from product where category = ?"))
		{
			preparedStatement.setString(1, category);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
			{
				Product product = new Product();
				product.setProductCode(resultSet.getString(PRODUCT_CODE));
				product.setProductName(resultSet.getString(PRODUCT_NAME));
				product.setCategory(resultSet.getString(CATEGORY));
				product.settotalQuantity(resultSet.getInt(PRODUCT_QUANTITY));
				product.setPrice(resultSet.getDouble(PRODUCT_PRICE));
				products.add(product);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return products;
	}

	public List<Product> filterProductsByName(String name)
	{
		List<Product> products = new ArrayList<>();
		try(PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT product_code,product_name,category,product_quantity,product_price from product where product_name = ?"))
		{
			
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setProductCode(resultSet.getString(PRODUCT_CODE));
				product.setProductName(resultSet.getString(PRODUCT_NAME));
				product.setCategory(resultSet.getString(CATEGORY));
				product.settotalQuantity(resultSet.getInt(PRODUCT_QUANTITY));
				product.setPrice(resultSet.getDouble(PRODUCT_PRICE));
				products.add(product);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return products;
	}

	public List<Product> filterProductsByPrice(double startPrice, double endPrice)
	{
		List<Product> products = new ArrayList<>();
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT product_code,product_quantity,product_name,category,product_price from product where product_price between ? and ? order by product_price " + (startPrice < endPrice ? "ASC" : "DESC")))
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
				Product product = new Product();
				product.setProductCode(resultSet.getString(PRODUCT_CODE));
				product.setProductName(resultSet.getString(PRODUCT_NAME));
				product.setCategory(resultSet.getString(CATEGORY));
				product.settotalQuantity(resultSet.getInt(PRODUCT_QUANTITY));
				product.setPrice(resultSet.getDouble(PRODUCT_PRICE));
				products.add(product);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return products;
	}
	
	public boolean editProduct(Product product)
	{
		try(PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE product SET product_name = ?, category = ?, product_price = ?, product_quantity = ? WHERE product_code = ?"))
		{
			preparedStatement.setString(1, product.getProductName());
			preparedStatement.setString(2, product.getCategory());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.setInt(4, product.gettotalQuantity());
			preparedStatement.setString(5, product.getProductCode());
			preparedStatement.executeUpdate();
			return true;
		}
		catch (Exception e) 
		{
			return false;
		}
	}

	public boolean deleteProduct(String productCode)
	{
		try (PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM product WHERE product_code = ?");)
		{	
			preparedStatement.setString(1, productCode);
			preparedStatement.executeUpdate();
			return true;
		}
		catch (Exception e) 
		{
			logger.info("Error occurred while deleting product: " + e.getMessage());
			return false;
		}
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