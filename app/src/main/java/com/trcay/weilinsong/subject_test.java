package com.trcay.weilinsong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tracy.bean.subtest;
import com.tracy.dao.DBManager;

import java.util.List;

/**
 * Created by trcay on 2017/5/24.
 * 主观题界面
 */
public class subject_test extends Activity implements View.OnClickListener {

    EditText ed_subScore;
    Button sub_btn;
    TextView tv_score;
    TextView tv_subcontent;
    String Single_Score;
    DBManager mydb;
    List<subtest> subjectTestList;
    int random_title = 0; //随机抽题
    //主观题界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjectest);
        mydb = new DBManager(getApplicationContext(), "students", null, 1);
        ed_subScore = (EditText) findViewById(R.id.ed_score);       //教师输入的主观题分数
        sub_btn = (Button) findViewById(R.id.btn_submit);
        tv_subcontent = (TextView) findViewById(R.id.sub_content);
        tv_score = (TextView) findViewById(R.id.tv_lastscore);
        //获得之前的分数
        Intent intent = getIntent();
        Single_Score = intent.getStringExtra("score");

        mydb.getSubTest();
        subjectTestList = mydb.getSubtests_list();
        while (true) {
            random_title = (int) (Math.random() * 10);
            if (random_title < subjectTestList.size())
                break;

        }

        tv_subcontent.setText(subjectTestList.get(random_title).getSub_content());
        sub_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                int score = Integer.parseInt(Single_Score); //获得之前单选总分数
                //获得主观题老师给的分数
                try {
                    if (!ed_subScore.getText().toString().equals("") && Integer.parseInt(ed_subScore.getText().toString()) <= 40) {

                        score += Integer.parseInt(ed_subScore.getText().toString());
                        tv_score.setText("您的测试已经完成，总成绩为: " + score);

                    } else {
                        Toast.makeText(this, "请您输入合理的分数后再尝试", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "请在分数输入框输入数字", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
