package com.tracy.Task;

import android.os.AsyncTask;

import com.tracy.dao.RDS;
import com.tracy.tools.FileManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by trcay on 2017/5/10.
 * <p/>
 * 已弃用,数据库操作代码我已封装到服务器端     Edit by trcay on 2017/5/25.
 */
public class UpdateStudentsTask extends AsyncTask {

    String whichXLSfile;
    List<HashMap<String, String>> xlscontentlist;
    int witchXls;
    FileManager fileManager;
    List<File> list;

    public UpdateStudentsTask(String whichXLSfile, List<HashMap<String, String>> xlscontentlist, int witchXls, FileManager fileManager, List<File> list) {

        this.whichXLSfile = whichXLSfile;
        this.xlscontentlist = xlscontentlist;
        this.witchXls = witchXls;
        this.fileManager = fileManager;
        this.list = list;
    }


    @Override
    protected Object doInBackground(Object[] params) {

        //在查询xls文件显示对话框创建的那个异步任务里新创建这个异步任务，用来进行数据库的更新操作

        for (int i = 1; i < xlscontentlist.size(); i += 3) {
            System.out.println(xlscontentlist.get(i - 1).get("stu_pnum"));
            System.out.println(xlscontentlist.get(i).get("stu_name"));
            System.out.println(xlscontentlist.get(i + 1).get("class"));
            String sql = "INSERT INTO `students`(`stu_pnum`,`stu_name` ,`class` )values"
                    + "(" + "'" + xlscontentlist.get(i - 1).get("stu_pnum") + "'" + "," + "'" + xlscontentlist.get(i).get("stu_name")
                    + "'" + "," + Integer.parseInt(xlscontentlist.get(i + 1).get("class")) + ")";
            System.out.println(sql);
            RDS rds = new RDS(0);
            rds.InsertFormXLS(sql);     //执行插入操作
        }
        return null;
    }
}
