package uloc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONStringer;

import util.QRCodeCreator;
import util.QRCodeModel;
/**
 * Servlet implementation class GetAllQrCodes
 */
@WebServlet("/GetAllQrCodes")
public class GetAllQrCodes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllQrCodes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "jdbc:mysql://39.104.84.129:3306/uloc?characterEncoding=utf-8";
		String username = "deltaxing";
		String password = "!Xx@mariadb";



		
        
            ZipOutputStream zos = null;
            try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			Connection conn = DriverManager.getConnection(url, username, password);
    			String query = " select * from location";
    			PreparedStatement preparedStmt = conn.prepareStatement(query);
    			ResultSet rs = preparedStmt.executeQuery();
            	
            	
            	String downloadFilename = "����λ�ö�ά��";//ѹ���ļ�������
                //downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");//ת�����ķ�����ܻ��������
                downloadFilename = new String(downloadFilename.getBytes(), "ISO-8859-1");//ת�����ķ�����ܻ�������룬������firefoxҲ��Ч
                response.setContentType("application/octet-stream");// ָ��response�ķ��ض������ļ��� 
                response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename+".zip");// ���������ؿ�Ĭ����ʾ���ļ���
                zos = new ZipOutputStream(response.getOutputStream());
                
    			while(rs.next()) {
    				long LocID = rs.getLong("LocID");
    				String LocName = rs.getString("LocName");
    				String Coordinate = rs.getString("Coordinate");
    				
                    zos.putNextEntry(new ZipEntry(LocName + ".png"));//����
                    String LocUrl = "http://gocoxing.cn/uloc/oluser.html?LocID="+LocID;  //ƴ��url
                    QRCodeCreator creator = new QRCodeCreator();  
                    QRCodeModel info1 = new QRCodeModel();  
                    info1.setWidth(400);  
                    info1.setHeight(400);  
                    info1.setFontSize(24);  
                    info1.setContents(LocUrl);  
                    info1.setDesc(LocName);  
                    creator.createCodeImage(info1, zos);

    			}
                zos.flush();     
                zos.close();//����
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if(zos != null){
                    try {
                        zos.flush();
                        zos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }     

                }

            }
        
//		response.setCharacterEncoding("utf-8"); 
//		response.setHeader("Content-Disposition","attachment; filename=����λ�õĶ�ά�롣zip"); 
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
