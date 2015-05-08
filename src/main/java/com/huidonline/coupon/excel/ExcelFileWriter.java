package com.huidonline.coupon.excel;

import com.huidonline.coupon.data.Constants;
import com.huidonline.coupon.data.Order;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class ExcelFileWriter {
    public static void export(File file, Set<Order> orders) {

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Vichy_Coupon");

        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", Constants.HEADERS);
       // CellStyle style= ;
   
        int index = 2;
        for (Order order : orders) {
            index++;
            data.put("" + index, new Object[]{order.getOrderId()+"",
                    order.getCustomer().getName(),
                    order.getCustomer().getAddress().toString(),
                    order.getArticlesAsString(),
                    order.getCustomer().getEmail(),
                    order.getCustomer().getPhoneNumber()+"",
                    "",
                    "",
                    ""});
        }

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);

            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
               // if (obj instanceof String)
                    cell.setCellValue((String) obj);
            }
        }

        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();

            System.out.println("Vichy export is afgerond.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
