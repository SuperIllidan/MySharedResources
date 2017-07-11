package com.hidy.hdoa6.caigou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class PreRandomSelectAgentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PreRandomSelectAgentServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String fileName = URLDecoder.decode(request.getParameter("projectName"),"UTF-8");
		int agentNums = Integer.valueOf(request.getParameter("agentNums"));
		ServletContext context = this.getServletContext();
		String pathOfExcelFile = context.getInitParameter("pathOfExcelFile");
		String pathOfSelectResultFiles = context.getInitParameter("pathOfSelectResultFiles");
		String nameOfExcelFile = context.getInitParameter("nameOfExcelFile");
		InputStream fileIn = null;
		OutputStream fileOut = null;
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		String[] arr = null;
		ArrayList<String[]> list = null;
		Set<String[]> resultSet = null;
		try {
			fileIn = new FileInputStream(pathOfExcelFile+nameOfExcelFile);
			wb = WorkbookFactory.create(fileIn);
			sheet = wb.getSheet("Sheet1");
			int lastRowNum = sheet.getLastRowNum();
			int colCounts = sheet.getRow(0).getPhysicalNumberOfCells();
			list = new ArrayList<String[]>();
			for(int i = 1;i <= lastRowNum;i++){
				arr = new String[6];
				row = sheet.getRow(i);
				for(int j = 0;j < colCounts;j++){
					 if(row!=null){
						 cell = row.getCell(j);
						 if(cell!=null){
			                arr[j] = cell.getStringCellValue();
						 }
					 }
				}
				if(arr[0]!=null){
					list.add(arr);
				}
			}
			if(fileIn!=null){
				fileIn.close();
			}
			if(wb!=null){
				wb.close();
			}
			//随机抽取机构
			Random rnd = new Random();
			resultSet = new HashSet<String[]>();
			while(resultSet.size()<agentNums){
				resultSet.add(list.get(rnd.nextInt(list.size())));
			}
			//将本次抽取结果生成excel文件存档
			wb = new XSSFWorkbook();
			sheet = wb.createSheet("Sheet1");
			Row row0 = sheet.createRow(0);
			row0.createCell(0).setCellValue("单位名称");
			row0.createCell(1).setCellValue("单位地址");
			row0.createCell(2).setCellValue("联系人");
			row0.createCell(3).setCellValue("职务");
			row0.createCell(4).setCellValue("手机");
			row0.createCell(5).setCellValue("座机");
			Iterator<String[]> iterator = resultSet.iterator();
			int rowNum = 1;
			while(iterator.hasNext()){
				Row row1 = sheet.createRow(rowNum);
				rowNum++;
				String[] arr1 = iterator.next();
				for(int n = 0;n < 6;n++){
					row1.createCell(n).setCellValue(arr1[n]);
				}
			}
			File selectResultFile = new File(pathOfSelectResultFiles+fileName+".xlsx");
			if(!selectResultFile.exists()){
				selectResultFile.createNewFile();
			}else{
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			fileOut = new FileOutputStream(selectResultFile);
			wb.write(fileOut);
			if(fileOut!=null){
				fileOut.close();
			}
			if(wb!=null){
				wb.close();
			}
			//返回html数据
			response.setContentType("text/html;charset=GBK");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Prama", "no-cache");
			PrintWriter out = response.getWriter();
			Iterator<String[]> it = resultSet.iterator();
			while(it.hasNext()){
				String[] arr2 = it.next();
				out.println("<li>"+arr2[0]+"</li>");
			}
			out.flush();
			out.close();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/zhaobiao/preRandomSelectAgent.jsp");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/zhaobiao/preRandomSelectAgent.jsp");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
