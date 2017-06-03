package com.tracy.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tracy.bean.Students;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trcay on 2017/4/17.
 */
public class SqlUtils {

    SQLiteDatabase mydb;
    List<Students> students_list;


    public SqlUtils(SQLiteDatabase mydb, List<Students> students_list) {
        this.mydb = mydb;
        this.students_list = new ArrayList<>();
    }

    public void CreateTable() {

        String sql = "CREATE TABLE IF NOT EXISTS student" +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT,name VCHAR(50),class INTEGER,Pnumber VAHCAR(50));";
        mydb.execSQL(sql);

        queryDataStudentInfo();
    }


    public void insertData() {


        ContentValues cv = new ContentValues();

        cv.put("name", "WEi");
        cv.put("class","1");
        cv.put("Pnumber", "P14475");
        mydb.insert("student", null, cv);



        cv.put("name", "Qin");
        cv.put("class","1");
        cv.put("Pnumber", "P14475");
        mydb.insert("student", null, cv);



        cv.put("name", "Li");
        cv.put("class","1");
        cv.put("Pnumber", "P14475");
        mydb.insert("student", null, cv);

        //二班


        cv.put("name", "赵");
        cv.put("class","2");
        cv.put("Pnumber", "P14475");
        mydb.insert("student", null, cv);


        cv.put("name", "钱");
        cv.put("class","2");
        cv.put("Pnumber", "P14475");
        mydb.insert("student", null, cv);



        cv.put("name", "孙");
        cv.put("class","2");
        cv.put("Pnumber", "P14475");
        mydb.insert("student", null, cv);




    }

    public void queryDataStudentInfo() {

        Cursor cs = mydb.query("student", null, "class=?", new String[]{"1"}, null, null, null, null);
        cs.moveToFirst();

        for (int i = 0; i < cs.getCount(); i++) {
            Log.i("Error", "" + " name= " + cs.getString(1)
                    + " id=" + cs.getString(0) + " number=" + cs.getString(2));
            Students student = new Students(new Integer(cs.getString(0)), cs.getString(1), cs.getString(2));
            students_list.add(student);
            cs.moveToNext();
        }

    }

    public List<Students> getStudents_list() {



        return students_list;
    }


}
