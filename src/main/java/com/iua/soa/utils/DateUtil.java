package com.iua.soa.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static String getFechaActual(){
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = dateFormat.format(date);
        return str;
	}
	
	public static String getHoraActual() {
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String str = dateFormat.format(date);
        return str;		 
	}
	
	public static boolean esFechaValidaAñoMes(String fecha) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		
		try{
			dateFormat.parse(fecha);
		}catch (ParseException e) {
			return false;
		}		
		return true;
	}
}
