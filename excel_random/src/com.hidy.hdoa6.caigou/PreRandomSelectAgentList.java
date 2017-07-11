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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
public class PreRandomSelectAgentList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PreRandomSelectAgentList() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Prama", "no-cache");
		ServletContext context = this.getServletContext();
		String pathOfExcelFile = context.getInitParameter("pathOfExcelFile");
		String nameOfExcelFile = context.getInitParameter("nameOfExcelFile");
		InputStream fileIn = null;
		Workbook wb = null;
		Sheet sheet = null;
		String[] arr = null;
		Row row = null;
		Cell cell = null;
		try {
			fileIn = new FileInputStream(pathOfExcelFile+nameOfExcelFile);
			wb = WorkbookFactory.create(fileIn);
			sheet = wb.getSheet("Sheet1");
			int lastRowNum = sheet.getLastRowNum();
			arr = new String[lastRowNum];
			for(int i = 1;i < lastRowNum+1;i++){
				row = sheet.getRow(i);
				if(row==null){
					arr[i-1] = "0";
				}else{
					cell = row.getCell(0);
					arr[i-1] = cell.getStringCellValue();
				}
			}
			if(fileIn!=null){
				fileIn.close();
			}
			if(wb!=null){
				wb.close();
			}
			PrintWriter out = response.getWriter();
			for(int j = 0;j < arr.length;j++){
				if(!arr[j].equals("0")){
					out.println("<li><input name=\"agentName\" type=\"checkbox\" value="+(j+1)+"><a href=\""+request.getContextPath()+"/PreRandomSelectReview?forWhat=list&index="+(j+1)+"\" target=\"_blank\">"+arr[j]+"</a></li>");
				}
			}
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
