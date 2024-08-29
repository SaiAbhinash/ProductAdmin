package purchaseproductcontroller;

import java.util.List;

import purchasedao.UserDao;
import purchasemodel.User;


public class AdminController 
{
	UserDao userdao = new UserDao();
	
	public boolean createProduct(User user){
		return userdao.createUser(user);
	}

	
	public List<User> getAllUsers(){
		return userdao.getAllUsers();
	}
	

	public boolean editPassword(User user){
		return userdao.editPassword(user);
	}

	public boolean deleteUser(String userName)
	{
		return userdao.deleteUser(userName);
	}
	
	public boolean editDetails(User user)
	{
		return userdao.editDetails(user);
	}
}
