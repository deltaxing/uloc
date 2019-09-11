package uloc;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONStringer;

/**
 * Servlet implementation class GetAllPosition
 */
@WebServlet("/GetAllLocations")
public class GetAlLocations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAlLocations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//String LocID = request.getParameter("");
		
		String url = "jdbc:mysql://39.104.84.129:3306/uloc?characterEncoding=utf-8";
		String username = "deltaxing";
		String password = "!Xx@mariadb";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, username, password);
			String query = " select * from location";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();
			JSONStringer js = new JSONStringer();
			js.array();
			String str = ""; 
			while(rs.next()) {
				js.object();
				js.key("LocName");
				str = rs.getString("LocName");
				js.value(rs.getString("LocName"));
				js.key("Coordinate");
				js.value(rs.getString("Coordinate"));
				js.endObject();
			}
			js.endArray();
			
	        response.setCharacterEncoding("utf-8");
			response.getWriter().append(js.toString());
			
			conn.close();
		 
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
