package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.Data;
import bean.Comment;
import bean.Good;
import bean.User;

public class GetGoodServlet extends HttpServlet {

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
		String id=request.getParameter("id");
		String userId=request.getParameter("userId");
		Data data=new Data();
		data.connect();
		Good good=data.getGood(id);
		StringBuffer buffer=new StringBuffer("{");
		List<Comment> comments=data.getComments(id);
		boolean ifCollect=false;
		if(!userId.equals("admin")){
		 ifCollect=data.ifCollect(id,userId);
		}
		buffer.append("\"comments\":[");
		for(int i=0;i<comments.size();i++){
			Comment comment=comments.get(i);
			User user=data.getUser(comment.getUserId());
			
			buffer.append("{");
			buffer.append("\"userName\":\""+user.getUserName()+"\"");
			buffer.append(",\"time\":\""+comment.getTime()+"\"");
			buffer.append(",\"type\":"+comment.getType());
			if(i==comments.size()-1){
				buffer.append(",\"content\":\""+comment.getContent()+"\"}");
			}
			else{
				buffer.append(",\"content\":\""+comment.getContent()+"\"},");
			}
		}
		buffer.append("],");
		buffer.append("\"id\":"+good.getId());
		buffer.append(",\"name\":\""+good.getName()+"\"");
		buffer.append(",\"num\":"+good.getNum());
		buffer.append(",\"img\":\""+good.getImg()+"\"");
		buffer.append(",\"price\":"+good.getPrice());
		buffer.append(",\"type\":"+good.getType());
		buffer.append(",\"time\":\""+good.getTime()+"\"");
		buffer.append(",\"good_comment\":"+good.getFavorable_comment());
		if(!userId.equals("admin"));
		buffer.append(",\"collect\":"+ifCollect);
		buffer.append(",\"bad_comment\":"+good.getBad_comment()+"}");
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
