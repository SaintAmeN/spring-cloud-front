package com.aps.services.ui.util.ordering;

import com.aps.services.model.dto.ordering.summary.InvoiceSummaryDto;
import com.aps.services.model.dto.ordering.summary.OrderSummaryDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author ajarmolkowicz
 */
public class ExcelWriter {
    private static final String[] COLUMN_NAMES = {"Order target", "Invoice number", "Price", "Shop name"};

    public static XSSFWorkbook prepareOrderSummary(OrderSummaryDto orderSummary) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("OrderSummary");

        int rowCount = 0, columntCount = 0;
        Row row;
        Cell cell;
        row = sheet.createRow(rowCount++);
        for (String columnName : COLUMN_NAMES) {
            sheet.setColumnWidth(columntCount, 4000);
            cell = row.createCell(columntCount++);
            cell.setCellStyle(columnName.equalsIgnoreCase(COLUMN_NAMES[0]) ? targetStyleTitle(sheet) : inputColumnStyle(sheet));
            cell.setCellValue(columnName);
        }

        for (InvoiceSummaryDto simpleSummary : orderSummary.getSummaryList()) {
            columntCount = 0;
            row = sheet.createRow(rowCount++);

            cell = row.createCell(columntCount++);
            cell.setCellStyle(targetStyle(sheet));
            cell.setCellValue(orderSummary.getOrderTarget());

            cell = row.createCell(columntCount++);
            cell.setCellStyle(inputStyle(sheet));
            cell.setCellValue(simpleSummary.getNumber());

            cell = row.createCell(columntCount++);
            cell.setCellStyle(inputStyle(sheet));
            cell.setCellValue(simpleSummary.getPrice());

            cell = row.createCell(columntCount++);
            cell.setCellStyle(inputStyle(sheet));
            cell.setCellValue(simpleSummary.getShopName());
        }
        return workbook;
    }

    private static CellStyle targetStyleTitle(XSSFSheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
        cellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
        return cellStyle;
    }

    private static CellStyle targetStyle(XSSFSheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
        cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        return cellStyle;
    }

    private static CellStyle inputColumnStyle(XSSFSheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
        cellStyle.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        return cellStyle;
    }

    private static CellStyle inputStyle(XSSFSheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
        return cellStyle;
    }
}
