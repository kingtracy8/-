package com.tracy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tracy.bean.Students;
import com.tracy.bean.singletest;
import com.tracy.bean.subtest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trcay on 2017/4/22.
 */
public class DBManager extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    List<Students> students_list;


    List<singletest> singletests_list;
    List<subtest> subtests_list;

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        db = getWritableDatabase();
        this.singletests_list = new ArrayList<>();  //题库List集合
        this.subtests_list = new ArrayList<>();
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, List<Students> students_list) {
        super(context, name, factory, version);
        db = getWritableDatabase();
        this.students_list = new ArrayList<>();
        this.subtests_list = new ArrayList<>();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
//        String sql = "CREATE TABLE IF NOT EXISTS student" +
//                " (id INTEGER PRIMARY KEY AUTOINCREMENT,name VCHAR(50),class INTEGER,Pnumber VAHCAR(50));";
//        db.execSQL(sql);

        String sql_test = "CREATE TABLE IF NOT EXISTS test_single (t_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content VCHAR(50),answer1 VCHAR(20),answer2 VACHAR(20),t_answer VCHAR(20));";
        db.execSQL(sql_test);
        String sql_subtest = "CREATE TABLE IF NOT EXISTS test_subject (sub_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "sub_content VCHAR(50));";
        db.execSQL(sql_subtest);        //创建主观题表
        insertTestData();
        insertSubTestData();
//        insertData();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //获得题库的list集合
    public void getTest() {
        Cursor cs = db.query("test_single", null, null, null, null, null, null, null);
        cs.moveToFirst();
        for (int i = 0; i < cs.getCount(); i++) {
            singletest single = new singletest(cs.getString(1), cs.getString(2), cs.getString(3), cs.getString(4));
            singletests_list.add(single);
            cs.moveToNext();
        }
    }

    //获得题库的主观题list集合
    public void getSubTest() {
        Cursor cs = db.query("test_subject", null, null, null, null, null, null, null);

        cs.moveToFirst();
        for (int i = 0; i < cs.getCount(); i++) {
            subtest sub = new subtest(cs.getString(1));
            subtests_list.add(sub);
            cs.moveToNext();
        }


    }


    public void insertTestData() {
        ContentValues cv = new ContentValues();
        cv.put("content", "Activity是四大组件之一?");
        cv.put("answer1", "正确");
        cv.put("answer2", "错误");
        cv.put("t_answer", "正确");
        db.insert("test_single", null, cv);

        cv.put("content", "安卓客户端能否访问网络?");
        cv.put("answer1", "能");
        cv.put("answer2", "不能");
        cv.put("t_answer", "能");
        db.insert("test_single", null, cv);

        cv.put("content", "Android是否是开源的?");
        cv.put("answer1", "是");
        cv.put("answer2", "不是");
        cv.put("t_answer", "是");
        db.insert("test_single", null, cv);

    }

    public void insertSubTestData() {
        ContentValues cv = new ContentValues();
        //往主观题表里插入数据
        cv.put("sub_content", "请简述Activity的生命周期。");
        db.insert("test_subject", null, cv);

        cv.put("sub_content", "简述Android程序的构成");
        db.insert("test_subject", null, cv);
    }


    public int InsertSinTK(String sin_content, String answer1, String answer2, String t_answer) {
        ContentValues cv = new ContentValues();
        cv.put("content", sin_content);
        cv.put("answer1", answer1);
        cv.put("answer2", answer2);
        cv.put("t_answer", t_answer);
        int result = (int) db.insert("test_single", null, cv);
        return result;  //如果为-1则表示插入异常
    }

    public int InsertSubTK(String sub_content) {
        ContentValues cv = new ContentValues();
        cv.put("sub_content", sub_content);
        int result = (int) db.insert("test_subject", null, cv);
        return result;
    }

    //删除题库
    public void DeletSinTK(int id) {
        db.delete("test_single", "t_id=?", new String[]{id + ""});
    }

    public void DeletSubTK(int id) {
        db.delete("test_subject", "sub_id=?", new String[]{id + ""});
    }


    public List<singletest> getSingletests_list() {
        return singletests_list;
    }


    public List<subtest> getSubtests_list() {
        return subtests_list;
    }


    //插入学生
    public void insertStudents(String s_class, String name, String Pnum) {

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("class", s_class);
        cv.put("Pnumber", Pnum);
        db.insert("student", null, cv);
    }

    //删除学生
    public void deleteStudents(String s_class, String name, String Pnum) {

        db.delete("student", "name=?", new String[]{name});

    }


}


/*


    public List<Students> getStudents_list() {
        return students_list;
    }

    //获得题库
    public String gettestContent() {
        Cursor cs = db.query("test_single", null, null, null, null, null, null, null);
        cs.moveToFirst();
        return cs.getString(1);
    }

    public String gettesta1() {
        Cursor cs = db.query("test_single", null, null, null, null, null, null, null);
        cs.moveToFirst();
        return cs.getString(2);
    }

    public String gettesta2() {
        Cursor cs = db.query("test_single", null, null, null, null, null, null, null);
        cs.moveToFirst();
        return cs.getString(3);
    }




  public void queryDataStudentInfo() {

        Cursor cs = db.query("student", null, "class=?", new String[]{"2"}, null, null, null, null);
        cs.moveToFirst();

        for (int i = 0; i < cs.getCount(); i++) {
//            Log.i("Error", "" + " name= " + cs.getString(1)
//                    + " id=" + cs.getString(0) + " number=" + cs.getString(2));
            Students student = new Students(new Integer(cs.getString(0)), cs.getString(1), cs.getString(2));
            students_list.add(student);
            cs.moveToNext();
        }

    }


  public void queryDataStudentInfo(int class_id) {


        Cursor cs = db.query("student", null, "class=?", new String[]{class_id + ""}, null, null, null, null);
        cs.moveToFirst();

        for (int i = 0; i < cs.getCount(); i++) {
            Log.i("Error", "" + " name= " + cs.getString(1)
                    + " id=" + cs.getString(0) + " number=" + cs.getString(2));
            Students student = new Students(new Integer(cs.getString(0)), cs.getString(1), cs.getString(2));
            students_list.add(student);
            cs.moveToNext();
        }

    }



    public void insertData() {


        ContentValues cv = new ContentValues();

        cv.put("name", "韦");
        cv.put("class", "1");
        cv.put("Pnumber", "P14475");
        db.insert("student", null, cv);


        cv.put("name", "覃");
        cv.put("class", "1");
        cv.put("Pnumber", "P14476");
        db.insert("student", null, cv);


        cv.put("name", "李");
        cv.put("class", "1");
        cv.put("Pnumber", "P14477");
        db.insert("student", null, cv);

        cv.put("name", "田");
        cv.put("class", "1");
        cv.put("Pnumber", "P14478");
        db.insert("student", null, cv);

        cv.put("name", "毛");
        cv.put("class", "1");
        cv.put("Pnumber", "P14479");
        db.insert("student", null, cv);

        cv.put("name", "王");
        cv.put("class", "1");
        cv.put("Pnumber", "P14480");
        db.insert("student", null, cv);

        //二班


        cv.put("name", "赵");
        cv.put("class", "2");
        cv.put("Pnumber", "P14475");
        db.insert("student", null, cv);


        cv.put("name", "钱");
        cv.put("class", "2");
        cv.put("Pnumber", "P14479");
        db.insert("student", null, cv);


        cv.put("name", "孙");
        cv.put("class", "2");
        cv.put("Pnumber", "P14476");
        db.insert("student", null, cv);

        cv.put("name", "杨");
        cv.put("class", "2");
        cv.put("Pnumber", "P14478");
        db.insert("student", null, cv);


        cv.put("name", "叶");
        cv.put("class", "2");
        cv.put("Pnumber", "P14480");
        db.insert("student", null, cv);


    }
*/