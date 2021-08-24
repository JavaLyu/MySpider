package com.nsh.util;



import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * excel工具类
 */
public class ExcelUtil {
    public static List<List<Object>> getCourseListByExcel(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = new ArrayList<>();
        /**
         * 这个inputStream文件可以来源于本地文件的流，
         *  也可以来源与上传上来的文件的流，也就是MultipartFile的流，
         *  使用getInputStream()方法进行获取。
         */


        //创建excel工作薄
        Workbook workBook = getWorkBook(in, fileName);
        if (workBook == null) {
            throw new Exception("创建Excel为null");
        }

        Sheet sheet= null;
        Row row = null;
        Cell cell = null;

        //获取所有的工作表的的数量
        for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
            //获取一个sheet也就是一个工作本
            sheet = workBook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //获取一个sheet有多少Row
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }

                ArrayList<Object> l1 = new ArrayList <>();
                for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
                    cell = row.getCell(k);
                    l1.add(cell);
                }
                list.add(l1);
            }
        }

        workBook.close();
        return list;

    }

    private static Workbook getWorkBook(InputStream in, String fileName) throws Exception {
        Workbook workbook= null;

        /**
         * 然后再读取文件的时候，应该excel文件的后缀名在不同的版本中对应的解析类不一样
         * 要对fileName进行后缀的解析
         */
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(in);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(in);
        } else {
            throw new Exception("请上传Excel文件");
        }
        return workbook;
    }


}
