package com.huidonline.coupon.excel;

import com.huidonline.coupon.data.Order;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class ExcelFileWriter
{
	public static void export(File file, Set<Order> orders)
	{
		//Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		//Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Vichy_Coupon");
		 
		//This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] {"OrderId", "FactuurId", "Naam", "Adres", "Articles"});
		int index=2;
		for(Order order:orders ){
			index++;
			data.put(""+index, new Object[] {order.getOrderId(), order.getFactuurId(), order.getCustomer().getName(), order.getCustomer().getAddress().toString(), order.getArticlesAsString()});
		}

		//Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset)
		{
		    Row row = sheet.createRow(rownum++);
		    Object [] objArr = data.get(key);
		    int cellnum = 0;
		    for (Object obj : objArr)
		    {
		       Cell cell = row.createCell(cellnum++);
		       if(obj instanceof String)
		            cell.setCellValue((String)obj);
		        else if(obj instanceof Integer)
		            cell.setCellValue((Integer)obj);
		    }
		}
		try 
		{
			//Write the workbook in file system
		    FileOutputStream out = new FileOutputStream(file);
		    workbook.write(out);
		    out.close();
		    
		    System.out.println("Vichy export is afgerond.");

		} 
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
	}
}
