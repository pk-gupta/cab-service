package com.marlabs.cab.service.common.util;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class CabServiceUtil {
	
	private CabServiceUtil(){}

	public static boolean isNULL(Object object){
		if(object == null){
			return true;
		}
		return false;
	}
	
	public static String formatDate(Date date) {
		Format formatter = new SimpleDateFormat("dd MMM yyyy");
		String formattedDate = formatter.format(date);
		return formattedDate;
	} 
	
 
	public static java.util.Date parseDate(String date,String format) throws ParseException {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
		
		return  simpleDateFormat.parse(date);
	} 
		
	public static StringWriter getStackTrace(Exception exception){
		StringWriter stringWriter = new StringWriter();
		exception.printStackTrace(new PrintWriter(stringWriter));
		
		return stringWriter;
	}

}
