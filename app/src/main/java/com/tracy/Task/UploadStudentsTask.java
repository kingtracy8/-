package com.tracy.Task;

import android.os.AsyncTask;

import com.tracy.tools.HttpUpload;

import java.io.File;


/**
 * Created by trcay on 2017/6/3.
 * 将xls文件上传到服务器的异步任务
 */
public class UploadStudentsTask extends AsyncTask {

    public String FilePath = "";

    public UploadStudentsTask(String FilePath) {

        this.FilePath = FilePath;

    }


    @Override
    protected Object doInBackground(Object[] params) {

        HttpUpload hup = new HttpUpload();
        hup.uploadFile(new File(FilePath));

        return null;
    }
}

