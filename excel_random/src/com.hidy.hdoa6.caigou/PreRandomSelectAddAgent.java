package com.hidy.hdoa6.caigou;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
public class PreRandomSelectAddAgent extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PreRandomSelectAddAgent() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Prama", "no-cache");
		PrintWriter out = response.getWriter();
		String agentName = request.getParameter("agentName");
		String agentAddress = request.getParameter("agentAddress");
		String agentContact = request.getParameter("agentContact");
		String contactPosition = request.getParameter("contactPosition");
		String contactMobile = request.getParameter("contactMobile");
		String contactTelephone = request.getParameter("contactTelephone");
		ServletContext context = this.getServletContext();
		String pathOfExcelFile = context.getInitParameter("pathOfExcelFile");
		String nameOfExcelFile = context.getInitParameter("nameOfExcelFile");
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		InputStream fileIn = null;
		OutputStream fileOut = null;
		int lastRowNum = -1;
		try{
			fileIn = new FileInputStream(pathOfExcelFile+nameOfExcelFile);
			wb = WorkbookFactory.create(fileIn);
			sheet = wb.getSheet("Sheet1");
			lastRowNum = sheet.getLastRowNum();
			row = sheet.createRow(lastRowNum+1);
			row.createCell(0).setCellValue(agentName==null?"":agentName);
			row.createCell(1).setCellValue(agentAddress==null?"":agentAddress);
			row.createCell(2).setCellValue(agentContact==null?"":agentContact);
			row.createCell(3).setCellValue(contactPosition==null?"":contactPosition);
			row.createCell(4).setCellValue(contactMobile==null?"":contactMobile);
			row.createCell(5).setCellValue(contactTelephone==null?"":contactTelephone);
			if(fileIn!=null){
				fileIn.close();
			}
			fileOut = new FileOutputStream(pathOfExcelFile+nameOfExcelFile);
			wb.write(fileOut);
			if(fileOut!=null){
				fileOut.close();
			}
			if(wb!=null){
				wb.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		out.println("1");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
