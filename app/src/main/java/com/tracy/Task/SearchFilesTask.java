package com.tracy.Task;

import android.os.AsyncTask;

import com.tracy.tools.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trcay on 2017/5/10.
 */
public class SearchFilesTask extends AsyncTask {

    //通过一个异步任务来实现对xls文件检索的耗时操作

        List<File> list = new ArrayList<>();



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        FileManager fileManager = new FileManager();
         list = fileManager.getXLSfile();
        System.out.println(list.get(0).getName());
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }



}
