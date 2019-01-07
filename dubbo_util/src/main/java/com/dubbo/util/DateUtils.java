package com.dubbo.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {
	 
	 /** 
     * ��ʱ���ַ���ת��ΪDate���� 
     * @param dateStr 
     * @return Date 
     */  
    public static Date toDate(String dateStr) {  
        Date date = null;  
        SimpleDateFormat formater = new SimpleDateFormat();  
        formater.applyPattern("yyyy-MM-dd");  
        try {
			date = formater.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}  
        return date;  
    }  
    
    /** 
     * ����java.sql.Date��ʽ��
     * */  
    public static java.sql.Date strToDate(String strDate) {  
        String str = strDate;  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        Date d = null;
        try {  
            d = format.parse(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        java.sql.Date date = new java.sql.Date(d.getTime());  
        return date;  
    }  
    
}
