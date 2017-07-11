package com.hidy.hdoa6.caigou;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PreRandomSelectDoneProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PreRandomSelectDoneProject() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Prama", "no-cache");
		ServletContext context = this.getServletContext();
		String pathOfSelectResultFiles = context.getInitParameter("pathOfSelectResultFiles");
		File file = new File(pathOfSelectResultFiles);
		File[] files = null;
		if(file.isDirectory()){
			files = file.listFiles();
		}
		int len = files.length;
		String[] arr = new String[len];
		for(int i = 0;i < len;i++){
			String name = files[i].getName();
			arr[i] = name.substring(0,name.indexOf("."));
		}
		PrintWriter out = response.getWriter();
		for(int j = 0;j < len;j++){
			out.println("<li><input name=\"resultName\" type=\"checkbox\" value="+arr[j]+"><a href=\""+request.getContextPath()+"/PreRandomSelectReview?forWhat=result&fileName="+arr[j]+"\" target=\"_blank\">"+arr[j]+"</a></li>");
		}
		out.flush();
		out.close();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
