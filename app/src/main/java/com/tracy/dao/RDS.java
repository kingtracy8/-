package com.tracy.dao;

import com.tracy.bean.Students;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trcay on 2017/5/8.
 * 已弃用，将此操作集成于服务端程序
 */
public class RDS {

    static String Driver = "com.mysql.jdbc.Driver";
    static String url = "";
    static String user = "";
    static String password = "";
    static Connection conn;
    static ResultSet rs;
    static Statement stmt;
    List<Students> students_list;

    public RDS() {
        students_list = new ArrayList<>();
    }

    public RDS(int notCreatestudentlist) {

    }

    public void queryDataStudentInfo(final int class_id) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName(Driver);
                    conn = DriverManager.getConnection(url, user, password);
                    if (!conn.isClosed()) {
                        System.out.println("Succeeded connecting to the Database!");
                    }
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT *" +
                            "  FROM students where class=" + class_id);
                    for (; rs.next(); ) {
                        Students stu = new Students(rs.getInt("stu_id"), rs.getString("stu_name"), rs.getString("stu_pnum"));
                        students_list.add(stu);
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread queryThread = new Thread(runnable);

        queryThread.start();
        try {
            queryThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<Students> getStudents_list() {

        return students_list;
    }


    public void InsertFormXLS(String sql) {
        try {
            Class.forName(Driver);
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
