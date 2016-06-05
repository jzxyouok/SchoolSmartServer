package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.Data;

import bean.Cart;
import bean.Good;

public class GetCartServlet extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String userId=request.getParameter("userId");
		String status=request.getParameter("status");
		Data data=new Data();
		data.connect();
		List<Cart> carts=data.getCart(userId,status);
		StringBuffer buffer=new StringBuffer("{\"carts\":[");
		for(int i=0;i<carts.size();i++){
			Cart cart=carts.get(i);
			Good good=data.getGoods(cart.getGoodId());
			buffer.append("{\"cartId\":"+cart.getId());
			buffer.append(",\"goodId\":"+good.getId());
			buffer.append(",\"name\":\""+good.getName()+"\"");
			buffer.append(",\"price\":"+good.getPrice());
			buffer.append(",\"type\":"+good.getType());
			buffer.append(",\"img\":\""+good.getImg()+"\"");
			buffer.append(",\"time\":\""+cart.getTime()+"\"");
			buffer.append(",\"status\":\""+cart.getStatus()+"\"");
			if(i<carts.size()-1){
				buffer.append("},");
			}
			else{
				buffer.append("}");
			}
		}
		buffer.append("]}");
		out.print(buffer);
		out.flush();
		out.close();
		data.closeSql();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
