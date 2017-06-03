package com.trcay.weilinsong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tracy.dao.DBManager;

/**
 * Created by trcay on 2017/5/25.
 * 管理题库界面
 */
public class tkManager extends Activity implements View.OnClickListener {

    EditText ed_sin_content;
    EditText ed_answer1;
    EditText ed_answer2;
    EditText ed_true_answer;
    EditText ed_sub_content;
    EditText ed_delete_sincontent;
    EditText ed_delete_subcontent;
    Button btn_submitSin;
    Button btn_submitSub;
    Button btn_deleteSin;
    Button btn_deleteSub;
    DBManager mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tk_manager);
        ed_sin_content = (EditText) findViewById(R.id.ed_sin_content);
        ed_answer1 = (EditText) findViewById(R.id.ed_answer1);
        ed_answer2 = (EditText) findViewById(R.id.ed_answer2);
        ed_true_answer = (EditText) findViewById(R.id.ed_true_answer);
        ed_sub_content = (EditText) findViewById(R.id.ed_sub_content);
        ed_delete_sincontent = (EditText) findViewById(R.id.ed_delete_sincontent);
        ed_delete_subcontent = (EditText) findViewById(R.id.ed_delete_subcontent);
        btn_submitSin = (Button) findViewById(R.id.btn_submitSin);
        btn_submitSub = (Button) findViewById(R.id.btn_submitSub);
        btn_deleteSin = (Button) findViewById(R.id.btn_deleteSin);
        btn_deleteSub = (Button) findViewById(R.id.btn_deleteSub);
        btn_submitSin.setOnClickListener(this);
        btn_submitSub.setOnClickListener(this);
        btn_deleteSin.setOnClickListener(this);
        btn_deleteSub.setOnClickListener(this);
        mydb = new DBManager(getApplicationContext(), "students", null, 1);
    }

    @Override
    public void onClick(View v) {

        String sin_content = ed_sin_content.getText().toString();
        String answer1 = ed_answer1.getText().toString();
        String answer2 = ed_answer2.getText().toString();
        String t_answer = ed_true_answer.getText().toString();
        String sub_content = ed_sub_content.getText().toString();
        String delete_sinId = ed_delete_sincontent.getText().toString();
        String delete_subId = ed_delete_subcontent.getText().toString();
        switch (v.getId()) {

            case R.id.btn_submitSin:    //如果三个不都不为空，而且有一个是正确答案
                if (!sin_content.equals("") && !answer1.equals("") && !answer2.equals("") && TitleTrue(answer1, answer2, t_answer)) {
                    int result = mydb.InsertSinTK(sin_content, answer1, answer2, t_answer);
                    if (result == -1)   //返回-1表示出异常了
                        Toast.makeText(getApplicationContext(), "插入题库失败,请稍后尝试!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "插入题库成功!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "您的输入不合法，请重新检查!", Toast.LENGTH_SHORT).show();
                break;
            //插入主观题
            case R.id.btn_submitSub:
                if (!sub_content.equals("")) {
                    int result = mydb.InsertSubTK(sub_content);
                    if (result == -1)
                        Toast.makeText(getApplicationContext(), "插入题库失败,请稍后尝试!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "插入题库成功!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "您的输入不合法，请重新检查!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_deleteSin:
                //判断输入是否合法
                try {
                    if (!delete_sinId.equals("") && Integer.parseInt(delete_sinId) >= 0) {
                        mydb.DeletSinTK(Integer.parseInt(delete_sinId));
                        Toast.makeText(getApplicationContext(), "输入合法，进行删除!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "输入不能为空！", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "输入不合法，请检查输入!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_deleteSub:
                try {
                    if (!delete_sinId.equals("") && Integer.parseInt(delete_sinId) >= 0) {
                        mydb.DeletSubTK(Integer.parseInt(delete_sinId));
                        Toast.makeText(getApplicationContext(), "输入合法，进行删除!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "输入不能为空！", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "输入不合法，请检查输入!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //判断两个是否有一个为正确答案的方法
    public boolean TitleTrue(String answer1, String answer2, String t_answer) {
        if (answer1.equals(t_answer) || answer2.equals(t_answer))
            return true;
        return false;
    }
}
