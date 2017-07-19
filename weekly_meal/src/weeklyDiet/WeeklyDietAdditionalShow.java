package com.hidy.hdoa6.weeklyDiet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

import com.hidy.hdoa6.weeklyDiet.beans.WeeklyDiet;
public class WeeklyDietAdditionalShow extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public WeeklyDietAdditionalShow() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Content-Type", "application/json;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		DateTime dt = new DateTime();
		long pointMillis = dt.getMillis();
		ServletContext context = this.getServletContext();
		String pathOfAdditionalFiles = context.getInitParameter("pathOfAdditionalFiles");
		File file = new File(pathOfAdditionalFiles);
		List<File> fileList = new ArrayList<File>();
		if(!file.isDirectory()||!file.exists()){
			out.println("{\"error\":\"未发现食谱信息！(Code:4)\"}");
			out.flush();
			out.close();
			return;
		}
		listFile(file,fileList);
		if(fileList.size()==0){
			out.println("{\"error\":\"未发现食谱信息！(Code:5)\"}");
			out.flush();
			out.close();
			return;
		}
		for(File f:fileList){
			String fileName = f.getName();
			long startMillis = Long.parseLong((fileName.substring(0, fileName.indexOf("-"))));
			long endMillis = Long.parseLong(fileName.substring(fileName.indexOf("-")+1, fileName.indexOf("$")));
			if(pointMillis>=startMillis&&pointMillis<endMillis){
				ObjectMapper mapper = new ObjectMapper();
				WeeklyDiet weeklyDiet = mapper.readValue(f, WeeklyDiet.class);
				String json = mapper.writeValueAsString(weeklyDiet);
				out.println(json);
				out.flush();
				out.close();
				return;
			}
			if(pointMillis<startMillis){
				ObjectMapper mapper = new ObjectMapper();
				WeeklyDiet weeklyDiet = mapper.readValue(f, WeeklyDiet.class);
				String json = mapper.writeValueAsString(weeklyDiet);
				out.println(json);
				out.flush();
				out.close();
				return;
			}
		}
		out.println("{\"error\":\"未发现食谱信息！(Code:6)\"}");
		out.flush();
		out.close();
		return;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private static void listFile(File file,List<File> list){
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f:files){
				listFile(f,list);
			}
		}else{
			list.add(file);
		}
	}
}
