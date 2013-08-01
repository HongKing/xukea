package com.xukea.common.util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 创建 Excel
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class CreateExcel {

	/**
	 * 创建Excel对象
	 * 
	 * @param title
	 * @param head
	 * @param data
	 * @return
	 */
	public static HSSFWorkbook create(String title, LinkedHashMap<String, String> head, List<Hashtable<String, String>> data) {
		HSSFWorkbook wb = new HSSFWorkbook(); // 建立新HSSFWorkbook对象
        return addSheet(wb, title, head, data);
	}

	/**
	 * 给Excel添加sheet
	 * @param wb
	 * @param title
	 * @param head
	 * @param data
	 * @return
	 */
	public static HSSFWorkbook addSheet(HSSFWorkbook wb, String title, LinkedHashMap<String, String> head, List<Hashtable<String, String>> data){
		// 设置单元名称
	    HSSFSheet  sheet= wb.createSheet();
	    int idx = wb.getNumberOfSheets()-1;
		wb.setSheetName(idx, title, HSSFWorkbook.ENCODING_UTF_16); 
		   
        HSSFRow row;
        HSSFCell cell;
        
        // 设置表头
        int i = (head.size()>0 && head.size()%2==0)? (head.size()-1)/2 : head.size()/2;// 居中
        row = sheet.createRow((short)0);
        cell = row.createCell((short) i );
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue( title );
        
        // 设置列名
        row = sheet.createRow((short)1);
        Iterator<String> it = head.keySet().iterator();
        i = 0;
        while (it.hasNext()){   
            String key = it.next();
	        cell = row.createCell( (short)i );          // 建立新cell
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setEncoding(HSSFCell.ENCODING_UTF_16); // 设置cell编码解决中文高位字节截断
	        cell.setCellValue( (String)head.get(key) );
	        i++;
        }

        // 添加内容
	    if(data!=null) {
	    	for(i=0;i<data.size();i++) {
	    		Hashtable<String, String> table = data.get(i);
	    		
	    		row = sheet.createRow((short)(2+i));
	    		
    	        it = head.keySet().iterator();
    	        int j = 0;
    	        while (it.hasNext()){   
    	            String key = (String)it.next();
    		        cell = row.createCell( (short) j );          // 建立新cell
    		        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    		        cell.setEncoding(HSSFCell.ENCODING_UTF_16); // 设置cell编码解决中文高位字节截断
    		        cell.setCellValue( (String) table.get( key ) );
    		        j++;
    	        }
	    	}
	    }else{
    		row = sheet.createRow( (short) 2 );
	        cell = row.createCell( (short) 0 );          // 建立新cell
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setEncoding(HSSFCell.ENCODING_UTF_16); // 设置cell编码解决中文高位字节截断
	        cell.setCellValue( "没有记录" );
	    }
		
		return wb;
	}
}



