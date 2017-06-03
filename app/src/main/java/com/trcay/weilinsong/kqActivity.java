package com.trcay.weilinsong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tracy.adapter.StuAdapter;
import com.tracy.bean.Students;
import com.tracy.tools.HttpUtils;
import com.tracy.tools.JsonTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trcay on 2017/4/17.
 * 考勤界面
 */
public class kqActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {


    ListView stu_list;
    List<Students> students_list;
    Button title_btn;   //显示时间和班级的按钮
    Button btn_submit;
    Button btn_selectAll;
    Button btn_random;

    int class_id;
    StuAdapter stuAdapter;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kq);

        title_btn = (Button) findViewById(R.id.title_button);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_selectAll = (Button) findViewById(R.id.all_sel);
        btn_random = (Button) findViewById(R.id.btn_random);
        Intent intent = getIntent();    //从MainActivity对话框中获得班级编号
        class_id = intent.getIntExtra("s_class", -1);
        FreshTitle(class_id);   //更新标题

        stu_list = (ListView) findViewById(R.id.stu_list);


        //使用异步任务从服务端解析json数据(云数据库RDS加载数据)，并在主线程设置Adapter
        new AsyncTask<Integer, Integer, String>() {

            ProgressDialog dialog = new ProgressDialog(getApplicationContext());

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ProgressDialog.show(context, "waitting", "查询正在进行..", false, true);

            }

            @Override
            protected String doInBackground(Integer... params) {    //运行在非UI线程，执行查询操作

                //通过服务端获得JSON并解析方法
                students_list = new ArrayList<Students>();
                String path = "http://47.93.96.122:8088/ConnectRDS/getjson?action_flag=students&class=" + (class_id + 1);
                String jsonString = HttpUtils.getJsonContent(path);
                students_list = JsonTools.getStudents("students", jsonString);
                stuAdapter = new StuAdapter(getApplicationContext(), students_list);
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String s) {        //主线程，用来更新UI
                super.onPostExecute(s);
                if (students_list.size() == 0) {
                    Toast toast = Toast.makeText(context, "您的网络好像开小差了，请返回并检查网络连接!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
                stu_list.setAdapter(stuAdapter);
                dialog.dismiss();
            }
        }.execute(0);


        stu_list.setOnItemClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_selectAll.setOnClickListener(this);
        btn_random.setOnClickListener(this);
    }

    private void FreshTitle(int class_id) {

        switch (class_id) {
            case 0:
                title_btn.setText(getYearMonthDate() + "     软件一班");
                break;
            case 1:
                title_btn.setText(getYearMonthDate() + "     软件二班");
                break;
            default:

                break;
        }
    }

    //获得当天日期
    private String getYearMonthDate() {
        String date = null;
        Time t = new Time();
        t.setToNow();
        date = t.year + "-" + (t.month + 1) + "-" + t.monthDay;
        return date;
    }


    @Override
    public void onClick(View v) {

        List<String> Notcomelist = new ArrayList<>();

        switch (v.getId()) {
            case R.id.btn_submit:
                //循环遍历选中的checkbox并获得其姓名
                for (int i = 0; i >= stu_list.getFirstVisiblePosition() && i < stu_list.getLastVisiblePosition(); i++) {

                    View view = stu_list.getChildAt(i);
                    CheckBox cb = (CheckBox) view.findViewById(R.id.check_stu);
                    TextView tv = (TextView) view.findViewById(R.id.tv_stuName);

                    if (!cb.isChecked()) {
                        Notcomelist.add(tv.getText().toString());   //把未到学生名单添加到list
                    }

                }


                //把装有未到学生名字的list传送给未到学生Activity界面
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("notcomestu", (ArrayList<String>) Notcomelist);
                Intent intent = new Intent(kqActivity.this, NotComeStuActivity.class);
                intent.putExtra("datalist_bundle", bundle);
                startActivity(intent);
                break;
            case R.id.all_sel:
                for (int i = 0; i >= stu_list.getFirstVisiblePosition() && i <= stu_list.getLastVisiblePosition(); i++) {
                    View view = stu_list.getChildAt(i - stu_list.getFirstVisiblePosition());
                    CheckBox cb = (CheckBox) view.findViewById(R.id.check_stu);
                    cb.toggle();    //设置全选与全不选

                }

                break;
            case R.id.btn_random:   //随机点名
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                if (students_list.size() != 0) {
                    b = new AlertDialog.Builder(context);
                    b.setTitle("随机点名");
                    b.setMessage(students_list.get((int) (Math.random() * students_list.size())).getName());    //随机选一个学生
                    b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    Dialog dialog = b.create();
                    dialog.show();
                } else {
                    b.setTitle("请检查网络连接!");
                    Dialog dialog = b.create();
                    dialog.show();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
//        StuListHolder holder = (StuListHolder) view.getTag();

        //实现点击listview把checkbox选中
        CheckBox cb = (CheckBox) view.findViewById(R.id.check_stu);     //view就是选中list的布局

        cb.toggle();

    }
}





/*
        //使用SqlliteOpenHelper来初始化数据  不会重复
        DBManager db = new DBManager(getApplicationContext(), "students", null, 1, students_list);
        mydb = db.getReadableDatabase();
        students_list = new ArrayList<>();
        db.queryDataStudentInfo((class_id + 1));  //查询 因为class_id是对话框中控件的位置（0和1），而class_id是1开始的
        students_list = db.getStudents_list();  //获得查询成功的学生列表

  */

        /*因为本身AsyncTask就是一个异步线程，所以不需要另启线程
                Runnable runnable = new Runnable() {

                    @Override
                    public void run() {
                        String path = "http://47.93.96.122:8088/ConnectRDS/getjson?action_flag=students&class=" + (class_id + 1);
                        String jsonString = HttpUtils.getJsonContent(path);
                        students_list = JsonTools.getStudents("students", jsonString);

                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
                try {
                    thread.join();
                    stuAdapter = new StuAdapter(getApplicationContext(), students_list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */

     /*        JDBC直接访问RDS方式
                RDS myRds = new RDS();
                students_list = new ArrayList<>();
                myRds.queryDataStudentInfo((class_id + 1));
                students_list = myRds.getStudents_list();
                stuAdapter = new StuAdapter(getApplicationContext(), students_list);

             */