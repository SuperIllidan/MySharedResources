package com.hidy.hdoa6.caigou;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
public class PreRandomSelectReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PreRandomSelectReview() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		response.setContentType("text/html;charst=GBK");
		response.setCharacterEncoding("GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Prama", "no-cache");
		PrintWriter out = response.getWriter();
		String forWhat = request.getParameter("forWhat");
		ServletContext context = this.getServletContext();
		String pathOfExcelFile = context.getInitParameter("pathOfExcelFile");
		String pathOfSelectResultFiles = context.getInitParameter("pathOfSelectResultFiles");
		String nameOfExcelFile = context.getInitParameter("nameOfExcelFile");
		InputStream fileIn = null;
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		String value = null;
		File file = null;
		File[] files = null;
		out.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+request.getContextPath()+"/zhaobiao/preSelect/bootstrap.min.css\">");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+request.getContextPath()+"/zhaobiao/preSelect/pre.css\">");
		out.println("<script type=\"text/javascript\" src=\""+request.getContextPath()+"/zhaobiao/preSelect/jquery-1.12.4.js\"></script>");
		out.println("<title>查看</title></head><body>");
		out.println("<div class=\"container-fluid\">");
		out.println("<h4 id=\"h4\" class=\"text-center text-primary\">结果查看</h4>");
		out.println("<div class=\"row\"><div class=\"col-md-4\"></div></div>");
		out.println("<div class=\"row\"><div class=\"col-md-4\">");
		out.println("<ul>");
		if("list".equals(forWhat)){
			int index = Integer.parseInt(request.getParameter("index"));
			fileIn = new FileInputStream(pathOfExcelFile+nameOfExcelFile);
			try {
				wb = WorkbookFactory.create(fileIn);
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			sheet = wb.getSheet("Sheet1");
			row = sheet.getRow(index);
			for(int i = 0;i < 6;i++){
				value = row.getCell(i).getStringCellValue();
				out.println("<li>"+value+"</li>");
			}
			if(fileIn!=null){
				fileIn.close();
			}
			if(wb!=null){
				wb.close();
			}
		}
		if("result".equals(forWhat)){
			String fileName = new String(request.getParameter("fileName").getBytes("ISO8859-1"),"GBK");
			out.println("<script>$(\"#h4\").text(\""+fileName+"\");</script>");
			fileIn = new FileInputStream(pathOfSelectResultFiles+fileName+".xlsx");
			try {
				wb = WorkbookFactory.create(fileIn);
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			sheet = wb.getSheet("Sheet1");
			int rowCounts = sheet.getPhysicalNumberOfRows();
			for(int j = 1;j < rowCounts;j++){
				out.println("<li>"+sheet.getRow(j).getCell(0).getStringCellValue()+"</li>");
			}
			
		}
		out.println("</ul>");
		out.println("</div></div>");
		out.println("<div class=\"row\"><div class=\"col-md-4\"></div></div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
