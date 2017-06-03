package com.tracy.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by trcay on 2017/5/10.
 * 已弃用，解析xls文件并更新数据库已经整合到服务端程序
 */
public class ExtralXls {


    List<HashMap<String, String>> xlsdatalist;

    public ExtralXls() {
        xlsdatalist = new ArrayList<HashMap<String, String>>();
    }

    public void test() {


    }

    public List<HashMap<String, String>> readExcel(String path) {
//        System.out.println();
//        System.out.print("--------开始读取---------");
        Workbook readwb = null;            //构建Workbook对象
        String data = "";
        try {

            InputStream is = new FileInputStream(path);
            //获得xls文件输入流 以构建Workbook的对象
            readwb = Workbook.getWorkbook(is);
            Sheet readsheet = readwb.getSheet(0);        //读取第一张工作表
            int rsColumns = readsheet.getColumns();        //获得列数
            int rsRows = readsheet.getRows();            //获得行数

            for (int i = 1; i < rsRows; i++) {        //两层循环遍历
                for (int j = 0; j < rsColumns; j++) {
                    Cell cell = readsheet.getCell(j, i);            //获取指定单元格的对象引用
                    HashMap<String, String> map = new HashMap<String, String>();
                    if (cell.getColumn() == 0)
                        map.put("stu_pnum", cell.getContents());
                    if (cell.getColumn() == 1)
                        map.put("stu_name", cell.getContents());
                    if (cell.getColumn() == 2)
                        map.put("class", cell.getContents());
                    xlsdatalist.add(map);

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return xlsdatalist;
    }
}
