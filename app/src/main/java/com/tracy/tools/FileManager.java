package com.tracy.tools;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trcay on 2017/5/10.
 * 用来遍历指定目录下后缀为xls文件
 */
public class FileManager {


    public List<File> XLSfiles;

    public FileManager() {
        XLSfiles = new ArrayList<File>();
    }

    public List<File> getXLSfile() {

        File home = new File("/mnt/sdcard/Download");   //存放xls文件的路径

        if (home.listFiles(new FileExtensionFilter()).length > 0) { //如果.xls的文件数大于0

            //遍历根目录
            for (File file : home.listFiles(new FileExtensionFilter())) { //遍历文件夹看有多少xls文件并添加到list集合中
                XLSfiles.add(file);
            }
        }
        return XLSfiles;
    }

    class FileExtensionFilter implements FilenameFilter {       //实现文件过滤接口

        @Override
        public boolean accept(File dir, String filename) {
            return (filename.endsWith(".xls") || filename.endsWith(".XLS"));       //通过一个类来判断是否为xls文件 ，是就返回真。
        }
    }


}
