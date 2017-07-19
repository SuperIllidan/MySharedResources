package com.hidy.hdoa6.weeklyDiet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.hidy.hdoa6.weeklyDiet.beans.Breakfast;
import com.hidy.hdoa6.weeklyDiet.beans.Dinner;
import com.hidy.hdoa6.weeklyDiet.beans.Lunch;
import com.hidy.hdoa6.weeklyDiet.beans.WeeklyDiet;
public class WeeklyDietUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public WeeklyDietUpload() {
        super();
    }
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			ServletContext context = this.getServletContext();
			String pathOfDietFiles = context.getInitParameter("pathOfDietFiles");
			String pathOfTempFiles = context.getInitParameter("pathOfTempFiles");
			File tempFile = new File(pathOfTempFiles);
			if(!tempFile.exists()){
				tempFile.mkdir();
			}
			String message = "";
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024*100);
			factory.setRepository(tempFile);
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setHeaderEncoding("UTF-8");
			if(!ServletFileUpload.isMultipartContent(request)){
				message = "表单格式错误！";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/weeklyDiet/message.jsp").forward(request, response);
				return;
			}
			fileUpload.setFileSizeMax(1024*1024);
			fileUpload.setSizeMax(1024*1024*10);
			InputStream fileIn = null;
			Workbook wb = null;
			Sheet sheet = null;
			WeeklyDiet weeklyDiet = new WeeklyDiet();
			Breakfast breakfast = new Breakfast();
			Lunch lunch = new Lunch();
			Dinner dinner = new Dinner();
			Map<String,Map<String,String>> dishesMap = null;
			Map<String,String> dayMap = null;
			String prefixName = "0";
			String suffixName = "0";
			try {
				List<FileItem> list = fileUpload.parseRequest(request);
				int fileCounts = 0;
				for(FileItem item : list){
					if(item.isFormField()){
						continue;
					}else{
						String fileName = item.getName();
						fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
						String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
						fileName = fileName.substring(0, fileName.lastIndexOf("."));
						if(fileName==null||"".equals(fileName.trim())){
							continue;
						}
						if(!("xls".equals(fileExtName.toLowerCase())||"xlsx".equals(fileExtName.toLowerCase()))){
							continue;
						}
						if("加餐模板".equals(fileName.trim())){
							continue;
						}
						fileIn = item.getInputStream();
						wb = WorkbookFactory.create(fileIn);
						sheet = wb.getSheetAt(0);
						DataFormatter dataFormatter = new DataFormatter();
						int rowCounts = sheet.getPhysicalNumberOfRows();
						int colCounts = sheet.getRow(2).getPhysicalNumberOfCells();
						DateTime commonDT = new DateTime();
						DateTimeFormatter dfr = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
						int year = commonDT.getYear();
						Row dateRow = sheet.getRow(0);
						//开始日期
						Cell dateCellStart = dateRow.getCell(1);
						String dateCellStartValue = dataFormatter.formatCellValue(dateCellStart).trim();
						if(dateCellStartValue.equals("")||dateCellStartValue==null){
							message = "未成功保存任何食谱！";
							request.setAttribute("message", message);
							request.getRequestDispatcher("/weeklyDiet/message.jsp").forward(request, response);
							return;
						}
						String beginTimePattern = year+"-"+dateCellStartValue+" 18:00:00";
						weeklyDiet.setDateStart(dateCellStartValue);
						DateTime beginDT = dfr.parseDateTime(beginTimePattern);
						prefixName = String.valueOf(beginDT.getMillis()); 
						//结束日期
						Cell dateCellEnd = dateRow.getCell(colCounts-1);
						String dateCellEndValue = dataFormatter.formatCellValue(dateCellEnd).trim();
						if(dateCellEndValue.equals("")||dateCellEndValue==null){
							for(int s=1;s<colCounts-1;s++){
								dateCellEnd = dateRow.getCell(colCounts-1-s);
								dateCellEndValue = dataFormatter.formatCellValue(dateCellEnd).trim();
								if(!dateCellEndValue.equals("")&&dateCellEndValue!=null){
									break;
								}
							}
						}
						colCounts = dateCellEnd.getColumnIndex()+1;
						String endTimePattern = year+"-"+dataFormatter.formatCellValue(dateCellEnd).trim()+" 18:00:00";
						weeklyDiet.setDateEnd(dateCellEndValue);
						DateTime endDT = dfr.parseDateTime(endTimePattern);
						suffixName = String.valueOf(endDT.getMillis());
						
						dishesMap = new HashMap<String, Map<String, String>>();
						for(int i = 2;i < rowCounts;i++){
							dayMap = new HashMap<String, String>();
							Row row = sheet.getRow(i);
							for(int j = 1;j < colCounts;j++){
								Cell cell = row.getCell(j);
								String value = dataFormatter.formatCellValue(dateRow.getCell(j)).trim();
								if(cell==null){
									dayMap.put(value, "");
									continue;
								}
								dayMap.put(value, dataFormatter.formatCellValue(cell));
							}
							dishesMap.put(dataFormatter.formatCellValue(row.getCell(0)), dayMap);
						}
						if("早餐模板".equalsIgnoreCase(fileName.trim())){
							breakfast.setDishesMap(dishesMap);
							fileCounts++;
						}else if("午餐模板".equalsIgnoreCase(fileName.trim())){
							lunch.setDishesMap(dishesMap);
							fileCounts++;
						}else if("晚餐模板".equalsIgnoreCase(fileName.trim())){
							dinner.setDishesMap(dishesMap);
							fileCounts++;
						}
						if(fileIn!=null){
							fileIn.close();
						}
						if(wb!=null){
							wb.close();
						}
					}
				}
				if(!(fileCounts>0)){
					message = "未成功保存任何食谱！";
					request.setAttribute("message", message);
					request.getRequestDispatcher("/weeklyDiet/message.jsp").forward(request, response);
				}
				weeklyDiet.setBreakfast(breakfast);
				weeklyDiet.setLunch(lunch);
				weeklyDiet.setDinner(dinner);
				ObjectMapper mapper = new ObjectMapper();
				String outFileName = prefixName+"-"+suffixName;
				String savePath = generateSavePath(outFileName,pathOfDietFiles);
				System.out.println("json文件名称:"+outFileName);
				System.out.println("json存放路径:"+savePath);
				File file = new File(savePath+outFileName+".json");
				if(file.exists()){
					boolean f = file.delete();
					System.out.println(outFileName+":存在，已删除"+f);
				}
				OutputStream fileOut = new FileOutputStream(file);
				mapper.writeValue(fileOut, weeklyDiet);
				if(fileOut!=null){
					fileOut.close();
				}
				message = "alert(\"上传成功！\")";
				response.setContentType("text/html;charset=GBK");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Prama", "no-cache");
				response.setCharacterEncoding("GBK");
				PrintWriter out = response.getWriter();
				out.println(message);
				out.flush();
				out.close();
			} catch (FileUploadException e) {
				e.printStackTrace();
				message = "文件上传出错！";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/weeklyDiet/message.jsp").forward(request, response);
				return;
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
				message = "文件解析出错！";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/weeklyDiet/message.jsp").forward(request, response);
				return;
			} catch (InvalidFormatException e) {
				e.printStackTrace();
				message = "文件解析出错！";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/weeklyDiet/message.jsp").forward(request, response);
				return;
			}
		}catch(Exception e){
			String message = "未知错误！";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/weeklyDiet/message.jsp").forward(request, response);
			return;
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	private String generateSavePath(String fileName,String rootPath){
		int hashCode = fileName.hashCode();
		int dir1 = hashCode & 0xf;
		int dir2 = (hashCode & 0xf0) >> 4;
		String dir = rootPath+dir1+"/"+dir2+"/";
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}
}
