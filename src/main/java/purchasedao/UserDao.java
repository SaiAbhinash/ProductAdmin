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

import purchasemodel.User;

public class UserDao 
{
	private Connection connection;
	private static final Logger logger;
	 
	 static {
	 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
	 logger = Logger.getLogger(ProductDao.class.getName());
	 }
	 
		private static final String PROPERTIES_FILE = "database.properties";
		private Properties properties;

		public UserDao() {
			properties = new Properties();
			try {
				properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
				Class.forName(properties.getProperty("jdbc.driver"));
				connection = DriverManager.getConnection(properties.getProperty("jdbc.url"),
						properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
				Statement stmt = connection.createStatement();
				String query = "CREATE TABLE IF NOT EXISTS User (UserName varchar(255) NOT NULL,Name varchar(255) NOT NULL,Password varchar(255) NOT NULL,PRIMARY KEY (UserName))";
				stmt.executeUpdate(query);
				stmt.close();
			} catch (SQLException | ClassNotFoundException | IOException e) {
				e.printStackTrace();

			}

		}
	
		public boolean createUser(User user) {
		    try (PreparedStatement preparedStatement = connection.prepareStatement(
		            "INSERT INTO user (UserName, Name, Password) VALUES (?, ?, ?) "))
		    {
		        preparedStatement.setString(1, user.getUserName());
		        preparedStatement.setString(2, user.getName());
		        preparedStatement.setString(3, user.getPassword());
		        preparedStatement.executeUpdate();
		        return true;
		    } catch (Exception e) {
		        return false;
		    }
		}
	

		


	public User verifyUser(String userName) 
	{
		User user = null;
		try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username = ?"))
		{
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) 
			{
				user = new User();
				user.setUserName(resultSet.getString("UserName"));
				user.setName(resultSet.getString("Name"));
				user.setPassword(resultSet.getString("Password"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return user;
	}
	
	
	public List<User> getAllUsers()
	{
		List<User> users = new ArrayList<User>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USER"))
		{
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
			{
				User user = new User();
				user.setUserName(resultSet.getString("UserName"));
				user.setName(resultSet.getString("Name"));
				users.add(user);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return users;
	}
	
	
	
	public boolean deleteUser(String userName)
	{
		try(PreparedStatement preparedstatement = connection.prepareStatement("Delete from user where UserName='"+userName+"'"))
		{
			preparedstatement.executeUpdate();
			return true;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean editDetails(User user)
	{
		try(PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE user SET Name = ? WHERE UserName = ?"))
		{
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.executeUpdate();
			return true;
		}
		catch (Exception e) 
		{
			return false;
		}
	}

	public boolean editPassword(User user)
	{
		try(PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE user SET Password = ? WHERE UserName = ?"))
		{
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.executeUpdate();
			return true;
		}
		catch (Exception e) 
		{
			return false;
		}
	}
}

