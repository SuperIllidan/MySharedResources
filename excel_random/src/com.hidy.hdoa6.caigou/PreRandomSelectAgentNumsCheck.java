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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
public class PreRandomSelectAgentNumsCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PreRandomSelectAgentNumsCheck() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int agentNums = Integer.valueOf(request.getParameter("agentNums"));
		ServletContext context = this.getServletContext();
		String pathOfExcelFile = context.getInitParameter("pathOfExcelFile");
		String nameOfExcelFile = context.getInitParameter("nameOfExcelFile");
		InputStream fileIn = null;
		Workbook wb = null;
		try {
			fileIn = new FileInputStream(pathOfExcelFile+nameOfExcelFile);
			wb = WorkbookFactory.create(fileIn);
			Sheet sheet = wb.getSheet("Sheet1");
			int rowCounts = sheet.getPhysicalNumberOfRows();
			int realRowCounts = rowCounts-1;
			if(fileIn!=null){
				fileIn.close();
			}
			if(wb!=null){
				wb.close();
			}
			String confirmString = agentNums > realRowCounts ? "0" : "1" ;
			response.setContentType("text/html;charset=GBK");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Prama", "no-cache");
			PrintWriter out = response.getWriter();
			out.println(confirmString);
			out.flush();
			out.close();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
