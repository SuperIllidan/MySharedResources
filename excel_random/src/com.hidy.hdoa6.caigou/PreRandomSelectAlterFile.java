package com.hidy.hdoa6.caigou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;

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

public class PreRandomSelectAlterFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PreRandomSelectAlterFile() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Prama", "no-cache");
		PrintWriter out = response.getWriter();
		ServletContext context = this.getServletContext();
		String pathOfExcelFile = context.getInitParameter("pathOfExcelFile");
		String pathOfSelectResultFiles = context.getInitParameter("pathOfSelectResultFiles");
		String nameOfExcelFile = context.getInitParameter("nameOfExcelFile");
		String listForm = request.getParameter("list");
		if(listForm!=null){
			listForm = URLDecoder.decode(listForm, "UTF-8");
		}
		String resultForm = request.getParameter("result");
		if(resultForm!=null){
			resultForm = URLDecoder.decode(resultForm, "UTF-8");
		}
		File file = null;
		String[] arr = null;
		int index = -1;
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		InputStream fileIn = null;
		OutputStream fileOut = null;
		if(listForm!=null&&"list".equals(listForm)){
			arr = request.getParameterValues("agentName");
			fileIn = new FileInputStream(pathOfExcelFile+nameOfExcelFile);
			try {
				wb = WorkbookFactory.create(fileIn);
			} catch (EncryptedDocumentException e1) {
				e1.printStackTrace();
			} catch (InvalidFormatException e1) {
				e1.printStackTrace();
			}
			sheet = wb.getSheet("Sheet1");
			for(int i = 0;i < arr.length;i++){
				arr[i] = URLDecoder.decode(arr[i], "UTF-8");
				index = Integer.parseInt(arr[i]);
				row = sheet.getRow(index);
				sheet.removeRow(row);
			}
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
			out.println("1");
			out.flush();
			out.close();
		}else if(resultForm!=null&&"result".equals(resultForm)){
			arr = request.getParameterValues("resultName");
			file = new File(pathOfSelectResultFiles);
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(int j=0;j<arr.length;j++){
					arr[j] = URLDecoder.decode(arr[j],"UTF-8");
					for(int k=0;k<files.length;k++){
						if((arr[j]+".xlsx").equals(files[k].getName())){
							if(files[k].isFile()){
								files[k].delete();
							}
						}
					}
				}
			}
			out.println("1");
			out.flush();
			out.close();
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
