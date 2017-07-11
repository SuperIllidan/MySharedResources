package com.hidy.hdoa6.caigou;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class PreRandomSelectPlanIdCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PreRandomSelectPlanIdCheck() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String fileName = URLDecoder.decode(request.getParameter("projectName"),"UTF-8");
		ServletContext context = this.getServletContext();
		String pathOfSelectResultFiles = context.getInitParameter("pathOfSelectResultFiles");
		File file = new File(pathOfSelectResultFiles);
		File[] files = null;
		if(file.isDirectory()){
			files = file.listFiles();
		}
		String[] arr = new String[files.length];
		for(int i = 0;i < files.length;i++){
			String name = files[i].getName();
			String prefixName = name.substring(0,name.indexOf("."));
			arr[i] = prefixName;
		}
		String confirmString = Arrays.binarySearch(arr, fileName) < 0 ? "1" : "0" ;
		response.setContentType("text/html;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Prama", "no-cache");
		PrintWriter out = response.getWriter();
		out.println(confirmString);
		out.flush();
		out.close();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
