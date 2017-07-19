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

import com.hidy.hdoa6.weeklyDiet.beans.Additional;
import com.hidy.hdoa6.weeklyDiet.beans.WeeklyDiet;
public class WeeklyDietUploadAdditional extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public WeeklyDietUploadAdditional() {
        super();
    }
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			ServletContext context = this.getServletContext();
			String pathOfAdditionalFiles = context.getInitParameter("pathOfAdditionalFiles");
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
			Additional additional = new Additional();
			Map<String,Map<String,String>> dishesMap = null;
			Map<String,String> dayMap = null;
			String jsonFileName = "0";
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
						if("早餐模板".equals(fileName.trim())||"午餐模板".equals(fileName.trim())||"晚餐模板".equals(fileName.trim())){
							continue;
						}
						fileIn = item.getInputStream();
						wb = WorkbookFactory.create(fileIn);
						sheet = wb.getSheetAt(0);
						DataFormatter dataFormatter = new DataFormatter();
						int rowCounts = sheet.getPhysicalNumberOfRows();
						DateTime commonDT = new DateTime();
						DateTimeFormatter dfr = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
						int year = commonDT.getYear();
						//区间date 开始日期
						Row dateSectionRow = sheet.getRow(0);
						Cell dateSectionCell = dateSectionRow.getCell(1);
						String dateSectionCellValue = dataFormatter.formatCellValue(dateSectionCell).trim();
						if(dateSectionCellValue.equals("")||dateSectionCellValue==null){
							continue;
						}
						String dateSectionCellSt = dateSectionCellValue.substring(0, dateSectionCellValue.indexOf("至")).trim();
						String dateSectionCellStPattern = year+"-"+dateSectionCellSt+" 18:00:00";
						DateTime startDT = dfr.parseDateTime(dateSectionCellStPattern);
						//区间date 结束日期
						String dateSectionCellEn = dateSectionCellValue.substring(dateSectionCellValue.indexOf("至")+1).trim();
						String dateSectionCellEnPattern = year+"-"+dateSectionCellEn+" 18:00:00";
						DateTime endDT = dfr.parseDateTime(dateSectionCellEnPattern);
						//某天date point
						Row dateRow = sheet.getRow(1);
						Cell datePointCell = dateRow.getCell(1);
						String dateCellValue = dataFormatter.formatCellValue(datePointCell).trim();
						if(dateCellValue.equals("")||dateCellValue==null){
							continue;
						}
						String dateCellTimePattern = year+"-"+dateCellValue+" 18:00:00";
						DateTime pointDT = dfr.parseDateTime(dateCellTimePattern);
						weeklyDiet.setDatePoint(dateCellValue);
						jsonFileName = String.valueOf(startDT.getMillis())+"-"+String.valueOf(endDT.getMillis())+"$"+String.valueOf(pointDT.getMillis()); 
						dishesMap = new HashMap<String, Map<String, String>>();
						for(int i = 3;i < rowCounts;i++){
							dayMap = new HashMap<String, String>();
							Row row = sheet.getRow(i);
							Cell cell = row.getCell(1);
							if(cell==null){
								dayMap.put(dateCellValue,"");
							}else{
								dayMap.put(dateCellValue, dataFormatter.formatCellValue(cell));
							}
							dishesMap.put(dataFormatter.formatCellValue(row.getCell(0)), dayMap);
						}
						additional.setDishesMap(dishesMap);
						fileCounts++;
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
				weeklyDiet.setAdditional(additional);;
				ObjectMapper mapper = new ObjectMapper();
				String savePath = generateSavePath(jsonFileName,pathOfAdditionalFiles);
				System.out.println("json文件名称:"+jsonFileName);
				System.out.println("json存放路径:"+savePath);
				File file = new File(savePath+jsonFileName+".json");
				if(file.exists()){
					boolean f = file.delete();
					System.out.println(jsonFileName+":存在，已删除"+f);
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
