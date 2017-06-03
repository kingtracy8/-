package com.trcay.weilinsong;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tracy.Task.UploadStudentsTask;
import com.tracy.adapter.MyVPadapter;
import com.tracy.tools.FileManager;

import java.io.File;
import java.util.List;

/*
*   主界面
* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager imgViewPager;
    ImageButton btn_kq, btn_select, btn_callus, btn_test, btn_tk, btn_student;
    Context context = this;
    Handler handler = new Handler();
    int i = 1;
    TextView tv_blog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_blog = (TextView) findViewById(R.id.tv_blog);        //设置超链接
        String html = "";
        html += "<font color='red'><big><i>访问开发者博客请点击超链接</i></big></font>";
        html += "<big><a href='http://blog.csdn.net/kingtracy8'>@Tracy</a></big>";

        CharSequence charSequence = Html.fromHtml(html);
        tv_blog.setText(charSequence);
        tv_blog.setMovementMethod(LinkMovementMethod.getInstance());  //点击的时候产生超链接的方法

        btn_kq = (ImageButton) findViewById(R.id.btn_kq);
        btn_select = (ImageButton) findViewById(R.id.btn_select);
        btn_callus = (ImageButton) findViewById(R.id.btn_callus);
        btn_test = (ImageButton) findViewById(R.id.btn_test);
        btn_tk = (ImageButton) findViewById(R.id.btn_tk);
        btn_student = (ImageButton) findViewById(R.id.btn_student);

        btn_kq.setOnClickListener(this);
        btn_select.setOnClickListener(this);
        btn_callus.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        btn_tk.setOnClickListener(this);
        btn_student.setOnClickListener(this);

        imgViewPager = (ViewPager) findViewById(R.id.imgVP);
        MyVPadapter myVPadapter = new MyVPadapter(getApplicationContext());
        imgViewPager.setAdapter(myVPadapter);


        Runnable changeVPrun = new Runnable() {     //实现ViewPager轮播的Runnable

            @Override
            public void run() {

                int currentItem = 0;
                currentItem = (i++) % 2;
                imgViewPager.setCurrentItem(currentItem);
                handler.postDelayed(this, 3000);
            }
        };

        handler.postDelayed(changeVPrun, 3000);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_kq:
                Dialog dialog = onCreateDialog();
                dialog.show();
                break;
            case R.id.btn_callus:

                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle("联系我们");
                b.setMessage("感谢您的使用，如果在使用过程中出现了问题，或者有更好的建议，请点击底部超链接，与开发者联系。");
                b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog dialog1 = b.create();
                dialog1.show();

                break;
            case R.id.btn_select:

                break;
            case R.id.btn_test:
                Intent intent1 = new Intent(MainActivity.this, single_testActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_student:

                //启用一个异步任务来进行查询指定文件下的xls文件，并构建选择对话框
                AsyncTask task = new AsyncTask() {

                    Dialog dialog = null;
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    int witchXls = 0;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object[] params) {      //进行查询指定目录下的xls文件并让用户选择，然后读取xls文件更新数据库


                        final FileManager fileManager = new FileManager();
                        final List<File> list = fileManager.getXLSfile();

                        b.setTitle("请选择xls文件:");

                        final String[] xlslist = new String[list.size()];     //！！ 有多少个文件创建多少个对象
                        for (int index = 0; index < list.size(); index++) {
                            xlslist[index] = list.get(index).getName();
                        }
                        if (xlslist.length != 0) {  //当有xls文件，才设置单选按钮
                            b.setSingleChoiceItems(xlslist, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    witchXls = which;
                                }
                            });
                        } else {
                            b.setMessage("对不起，在指定目录下找不到xls的文件，请检查！");
                        }

                        b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (xlslist.length != 0) {  //如果有xls文件才进行读取并更新

                                    //判断单选对话框选中的文件名，并用其来构建Path  为了兼容4.1以上版本，加了一个getpath
                                    String whichXLSfile = Environment.getExternalStorageDirectory()
                                            .getPath() + "/Download/" + list.get(witchXls).getName();
                                    UploadStudentsTask uploadStudentsTask = new UploadStudentsTask(whichXLSfile);
                                    uploadStudentsTask.execute(0);
                                } else { //如果没有xls文件，提示信息，啥也不干

                                }
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "上传学生名单成功!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return null;
                    }


                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        dialog = b.create();        //ui线程进行显示对话框
                        dialog.show();
                    }
                };
                task.execute(0);

                break;
            case R.id.btn_tk:       //管理题库
                Intent intent = new Intent(MainActivity.this, tkManager.class);
                startActivity(intent);
                break;
        }


    }


    //创建选择班级的对话框
    public Dialog onCreateDialog() {
        //判断网络连接 如果网络无连接，则Toast提示，不做任何操作。若连接，查询云数据库RDS
        Dialog dialog = null;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        if (isNetworkAvailable(context)) {

            final int[] n_class = new int[1];   //获得对话框选择的班级编号
            final String[] s_class = new String[]{"软件一班", "软件二班"};

            b.setTitle("请选择班级:");
            b.setSingleChoiceItems(s_class, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    n_class[0] = which;
                }
            });
            b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, kqActivity.class);
                    intent.putExtra("s_class", n_class[0]);
                    startActivity(intent);
                    dialog.dismiss();   //为了节省内存
                    dialog = null;  //只有把这个dialog对象置空，kqActivity的ProgressDialog才能正常显示，而dismiss方法不行。
                }
            });

        } else {

            b.setTitle("请连接网络!");
        }
        dialog = b.create();
        return dialog;
    }


    public Dialog onCreateListFiledialog() {
        Dialog dialog = null;
        return dialog;
    }


    private boolean checkNetWork() {

        boolean flags = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();

        if (net.getState() == NetworkInfo.State.CONNECTED) {        //!!!检测网络状态一直空指针!!!
            flags = true;
        }


        return flags;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {

        } else {
            //如果仅仅是用来判断网络连接
//            Log.i("Assert",cm.toString());


        }

        return true;
    }

}

/*
*
  if (xlslist.length != 0) {  //如果有xls文件才进行读取并更新
                                    //进行读xls文件并更新数据库操作
                                    ExtralXls extral = new ExtralXls();
                                    //判断单选对话框选中的文件名，并用其来构建Path  为了兼容4.1以上版本，加了一个getpath
                                    String whichXLSfile = Environment.getExternalStorageDirectory().getPath() + "/Download/" + list.get(witchXls).getName();
                                    List<HashMap<String, String>> xlscontentlist = extral.readExcel(whichXLSfile);

                                    //   新创建这个异步任务，用来进行数据库的更新操作
                                    UpdateStudentsTask task1 = new UpdateStudentsTask(whichXLSfile, xlscontentlist, witchXls, fileManager, list);    //调用此线程来进行更新操作
                                    task1.execute(0);
                                    Toast.makeText(context, "成功将学生名单插入数据库!", Toast.LENGTH_SHORT).show();
                                } else { //如果没有xls文件，提示信息，啥也不干

                                }
*
* */