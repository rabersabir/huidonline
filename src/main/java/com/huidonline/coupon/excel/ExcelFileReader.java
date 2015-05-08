package com.huidonline.coupon.excel;

import com.huidonline.coupon.data.Address;
import com.huidonline.coupon.data.Article;
import com.huidonline.coupon.data.Customer;
import com.huidonline.coupon.data.Order;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;


import static com.huidonline.coupon.data.Constants.*;

public class ExcelFileReader {
    public static Set<Order> readExecelFile(final File excelFile) {
        try {

            Set<Order> orders = new HashSet<Order>();

            FileInputStream file = new FileInputStream(excelFile);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheet("ruwdata");

            //sheet.
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int index = 0;
            while (rowIterator.hasNext()) {

                index++;
                Row row = rowIterator.next();

                String newOrderId = getCellValue(row, COL_ORDER_ID);

                if (index != 1 && newOrderId != null) {

                    Order existingOrder = getOrderByOrderId(newOrderId, orders);
                    if (existingOrder != null) {
                        addArticles(existingOrder, row);
                    } else {
                        Order order = getOrder(row);
                        addArticles(order, row);
                        orders.add(order);
                    }

                }
            }
            file.close();

            return orders;

        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<Order>();
        }
    }

    private static void addArticles(Order order, Row row) {
        order.addArticles(new Article(getCellValue(row, COL_ARTIKELNUMMER), getCellValue(row, COL_PRODUCTNAAM), null));
    }

    private static Order getOrderByOrderId(String newOrderId, Set<Order> orders) {
        for (Order order : orders) {
            if (order.getOrderId().equalsIgnoreCase(newOrderId)) {
                return order;
            }
        }

        return null;
    }

    private static Order getOrder(Row row) {
        Order order = new Order(getCellValue(row, COL_ORDER_ID), getCellValue(row, COL_FACTUURNUMMER));
        order.setCustomer(getCustomer(row));
        return order;
    }

    private static Customer getCustomer(Row row) {

        Customer customer = new Customer();
        customer.setEmail(getCellValue(row, COL_EMAIL));
        customer.setName(getCellValue(row, COL_NAAM));
        customer.setPhoneNumber(getCellValue(row, COL_TELEFOON));
        customer.setAddress(getAddress(row));
        return customer;
    }

    private static Address getAddress(Row row) {
        Address address = new Address();

        address.setCity(getCellValue(row, COL_STAD));
        address.setCountry(getCellValue(row, COL_LAND));
        address.setHouseNumber(getCellValue(row, COL_HUISNUMMER));
        address.setPostalCode(getCellValue(row, COL_POSTCODE));
        address.setStreet(getCellValue(row, COL_STRAAT));

        return address;
    }

    private static String getCellValue(Row row, int index) {

        index = index - 1;
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(index);

        if (cell == null) {
            return null;
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }
}
