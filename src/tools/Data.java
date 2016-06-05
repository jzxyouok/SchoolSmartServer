package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Cart;
import bean.Comment;
import bean.Good;
import bean.User;

import com.mysql.jdbc.PreparedStatement;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;


public class Data {
	/*
	 * 操作数据库的类
	 */
	Statement stmt;
	Connection conn;

	public void connect() {
		// 1.注册驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2.创建数据库的连接
		// useUnicode=true&characterEncoding=GBK：支持中文
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/school_smart?useUnicode=true&characterEncoding=GBK",
							"root", "root");
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}

	public void closeSql() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public int login(String phone, String password) {
		// TODO Auto-generated method stub
		int result=-1;
		String sql="select * from user where phone='"+phone+"' and password='"+password+"'";
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				result=res.getInt("id");
				return result;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}



	
	public List<Good> getGoods(String type) {
		// TODO Auto-generated method stub
		List<Good>goods=new ArrayList<Good>();
		String sql;
		if(type.equals("")){	
		 sql="select * from goods order by id desc limit 10";
		}
		else{
			if(type.equals("5")){
				sql="select * from goods order by id desc";
			}
			else{
			sql="select * from goods where type="+type+" order by id desc";
		}
		}
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				Good good=new Good();
				good.setId(res.getInt("id"));
				good.setName(res.getString("name"));
				good.setImg(res.getString("img"));
				good.setNum(res.getInt("num"));
				good.setPrice(res.getDouble("price"));
				good.setType(res.getInt("type"));
				goods.add(good);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return goods;
	}



	public boolean addCart(String userId, String goodId,String status) {
		// TODO Auto-generated method stub
		String sql="insert into cart(user_id,good_id,time,status) values ("+userId+","+goodId+",\""+getTime()+"\",\""+status+"\")";
		boolean result=false;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public List<Cart> getCart(String userId,String status) {
		// TODO Auto-generated method stub
		String sql="select * from cart where user_id ="+userId+" and status=\""+status+"\"";
		List<Cart> carts=new ArrayList<Cart>();
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				Cart cart=new Cart();
				cart.setId(res.getInt("id"));
				cart.setUserId(res.getInt("user_id"));
				cart.setGoodId(res.getInt("good_id"));
				cart.setTime(res.getString("time"));
				cart.setStatus(res.getString("status"));
				carts.add(cart);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return carts;
	}



	public Good getGoods(int goodId) {
		// TODO Auto-generated method stub
		String sql="select * from goods where id="+goodId;
		Good good=new Good();
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				good.setId(res.getInt("id"));
				good.setName(res.getString("name"));
				good.setImg(res.getString("img"));
				good.setPrice(res.getDouble("price"));
				good.setType(res.getInt("type"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return good;
	}



	public boolean submit(String id,String status) {
		// TODO Auto-generated method stub
		String sql="update cart set status= \""+status+"\" where user_id ="+id;
		boolean result=false;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public Good getGood(String id) {
		// TODO Auto-generated method stub
		String sql="select * from goods where id="+id;
		Good good=new Good();
		try {
			ResultSet res =stmt.executeQuery(sql);
			while(res.next()){
			good.setId(res.getInt("id"));
			good.setName(res.getString("name"));
			good.setNum(res.getInt("num"));
			good.setPrice(res.getDouble("price"));
			good.setType(res.getInt("type"));
			good.setTime(res.getString("time"));
			good.setImg(res.getString("img"));
			good.setFavorable_comment(res.getInt("favorable_comment"));
			good.setBad_comment(res.getInt("bad_comment"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return good;
	}



	public boolean ifCollect(String id, String userId) {
		// TODO Auto-generated method stub
		String sql="select * from collection where user_id="+userId+" and good_id="+id;
		boolean result=false;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				result=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public boolean collect(String userId, String goodId) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="insert into collection(user_id,good_id,time) values("+userId+","+goodId+",\""+getTime()+"\")";
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public boolean cancelCollect(String userId, String goodId) {
		// TODO Auto-generated method stub
		String sql="delete from collection where user_id="+userId+" and good_id="+goodId;
		boolean result=false;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}



	public boolean updateAddress(String userId, String address) {
		String sql="update user set address=\""+address+"\" where id="+userId;
		boolean result=false;
		try {
		
			stmt.executeUpdate(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return result;
	}



	public String getAddress(String userId) {
		// TODO Auto-generated method stub
		String sql="select * from user where id="+userId;
		String address="";
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				address=res.getString("address");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}



	public User getInfo(String userId) {
		// TODO Auto-generated method stub
		String sql="select * from user where id="+userId;
		User user=new User();
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				user.setId(res.getInt("id"));
				user.setUserName(res.getString("name"));
				user.setPhone(res.getString("phone"));
				user.setPassword(res.getString("password"));
				user.setMoney(res.getDouble("money"));
				user.setBirth(res.getString("birth"));
				user.setAddress(res.getString("address"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}



	public boolean updateInfo(String id, String userName, String phone,
			String birth) {
		// TODO Auto-generated method stub
		String sql="update user set name=\""+userName+"\",phone=\""+phone+"\",birth=\""+birth+"\" where id="+id;
		boolean result=false;
		try {
		
			stmt.executeUpdate(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return result;
	}



	public String selectPassword(String id) {
		// TODO Auto-generated method stub
		String selectPassword="select * from user where id="+id;
		String password="";
		try {
			ResultSet res=stmt.executeQuery(selectPassword);
			while(res.next()){
				password=res.getString("password");
			}
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return password;
	}



	public String updatePassword(String id, String newPassword) {
		// TODO Auto-generated method stub
		String sql="update user set password=\""+newPassword+"\" where id="+id;
		String result="fail";
		try {
			stmt.execute(sql);
			result="success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public List<Good> getCollect(String id) {
		
		// TODO Auto-generated method stub
		List<Good> goods=new ArrayList<Good>();
		String sql="select * from collection where user_id="+id;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				Good good=new Good();
				good.setId(res.getInt("good_id"));
				goods.add(good);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return goods;
	}



	public boolean comment(String id, String goodId, String comment,String type) {
		
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="insert into comment(user_id,good_id,content,time,type) values("+id+","+goodId+",\""+comment+"\",\""+getTime()+"\","+type+")";
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}



	public boolean updateCart(String cartId, String string) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="update cart set status=\""+string+"\" where id="+cartId;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}



	public boolean updateGoodComment(String goodId, String type, int goodComment, int badComment) {
		// TODO Auto-generated method stub
		String sql;
		boolean result=false;
		if(type.equals("1")){
			sql="update goods set favorable_comment="+(goodComment+1)+" where id="+goodId;
		}
		else{
			sql="update goods set bad_comment="+(badComment+1)+" where id="+goodId;
		}
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
			
	}



	public int selectGoodComment(String goodId) {
		// TODO Auto-generated method stub
		int num=0;
		String sql="select * from goods where id="+goodId;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				num=res.getInt("favorable_comment");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	public int selectBadComment(String goodId) {
		// TODO Auto-generated method stub
		int num=0;
		String sql="select * from goods where id="+goodId;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				num=res.getInt("bad_comment");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}



	public double selectUserMoney(String userId) {
		// TODO Auto-generated method stub
		double money=0;
		String sql="select * from user where id="+userId;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
			money=res.getDouble("money");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return money;
	}



	public double selectMartMoney() {
		// TODO Auto-generated method stub
		double money=0;
		String sql="select * from mart";
		ResultSet res;
		try {
			res = stmt.executeQuery(sql);
			while(res.next()){
				money=res.getDouble("money");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return money;
	}



	public boolean updateShopMoney(double string) {
		// TODO Auto-generated method stub
		String sql="update mart set money="+string;
		boolean result=false;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}



	public boolean updateUserMoney(String userId, double d) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="update user set money="+d+" where id="+userId;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public List<Comment> getComments(String id) {
		// TODO Auto-generated method stub
		List<Comment>comments=new ArrayList<Comment>();
		String sql="select * from comment where good_id="+id;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				Comment comment=new Comment();
				comment.setId(res.getInt("id"));
				comment.setGoodId(res.getInt("good_id"));
				comment.setUserId(res.getInt("user_id"));
				comment.setContent(res.getString("content"));
				comment.setTime(res.getString("time"));
				comment.setType(res.getInt("type"));
				comments.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}



	public User getUser(int userId) {
		// TODO Auto-generated method stub
		User user=new User();
		String sql="select * from user where id="+userId;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				user.setId(res.getInt("id"));
				user.setUserName(res.getString("name"));
				user.setPhone(res.getString("phone"));
				user.setBirth(res.getString("birth"));
				user.setMoney(res.getDouble("money"));
				user.setAddress(res.getString("address"));
				user.setPassword(res.getString("password"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}



	public User adminLogin(String userName) {
		// TODO Auto-generated method stub
		String sql="select * from mart where name=\""+userName+"\"";
		User user=new User();
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
			user.setUserName(res.getString("name"));
			user.setPassword(res.getString("password"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}



	public boolean getPhone(String phone) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="select *from user where phone=\""+phone+"\"";
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				result=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public String registe(String phone, String userName, String address,
			String password,String birth) {
		// TODO Auto-generated method stub
		String sql="insert into user(phone,name,password,birth,address,money)values(\""+phone+"\",\""+userName+"\",\""+password+"\",\""+birth+"\",\""+address+"\","+100+")";
		String result="fail";
		try {
			stmt.execute(sql);
			result="success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}



	public boolean deleteComments(String goodId) {
		// TODO Auto-generated method stub
		boolean result=true;
		String sql="delete from comment where good_id="+goodId;
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result=false;
		}
		return result;
	}



	public boolean deleteCollection(String goodId) {
		// TODO Auto-generated method stub
		boolean result=true;
		String sql="delete from collection where good_id="+goodId;
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result=false;
		}
		return result;
	}



	public boolean deleteCart(String goodId) {
		// TODO Auto-generated method stub
		boolean result=true;
		String sql="delete from cart where good_id="+goodId;
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result=false;
		}
		return result;
	}



	public boolean deleteGood(String goodId) {
		// TODO Auto-generated method stub
		boolean result=true;
		String sql="delete from goods where id="+goodId;
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result=false;
		}
		return result;
	}



	public int getImageName() {
		// TODO Auto-generated method stub
		int i=0;
		String sql="select * from goods";
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				i=res.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}



	public boolean addGood(String name, String price, String num, String img,
			String type, String string) {
		// TODO Auto-generated method stub
		String sql="insert into goods(name,num,img,price,type,time,favorable_comment,bad_comment) values (\""+name+"\","+num+",\""+img+"\","+price+","+type+",\""+getTime()+"\",0,0)";
		boolean result=false;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
