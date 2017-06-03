package com.trcay.weilinsong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tracy.bean.singletest;
import com.tracy.dao.DBManager;

import java.util.List;

/**
 * Created by trcay on 2017/4/24.
 * 单选题界面
 */
public class single_test2Activity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    TextView title;
    DBManager mydb;
    RadioGroup radioGroup;
    RadioButton radio_a1;
    RadioButton radio_a2;
    Button btn_next;
    List<singletest> test_list;
    String answer = "";
    String Single_Score = "";
    int random_title = 0;
    int last_random = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_test);
        mydb = new DBManager(getApplicationContext(), "students", null, 1);
        title = (TextView) findViewById(R.id.tv_content);
        btn_next = (Button) findViewById(R.id.btn_next);
        radioGroup = (RadioGroup) findViewById(R.id.single_radio);
        radio_a1 = (RadioButton) findViewById(R.id.radio_a1);
        radio_a2 = (RadioButton) findViewById(R.id.radio_a2);

        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        //获得之前的分数
        Intent intent = getIntent();
        Single_Score = intent.getStringExtra("score");
        //获得之前的题号
        last_random = Integer.parseInt(intent.getStringExtra("random_title"));
        radio_a1.setOnCheckedChangeListener(this);
        radio_a2.setOnCheckedChangeListener(this);


        //获得题目列表
        mydb.getTest();
        test_list = mydb.getSingletests_list();
        while (true) {
            random_title = (int) (Math.random() * 10);
            if (random_title < test_list.size() && random_title != last_random) {
//                Log.i("Assert", random_title + "----");
//                Toast.makeText(this, random_title + "----", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        title.setText(test_list.get(random_title).getContent());
        radio_a1.setText(test_list.get(random_title).getAnswer1());
        radio_a2.setText(test_list.get(random_title).getAnswer2());
        btn_next.setText("下一题");


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_next:


                RadioButton rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                if (rb != null) {
                    answer = rb.getText().toString();
                    int score = Integer.parseInt(Single_Score);
                    if (answer.equals(test_list.get(random_title).getTrue_answer())) {
                        score += 20;
                        Log.i("Assert", "答案正确,您的得分是：" + score);
                    } else Log.i("Assert", "答案错误,您的得分是：" + score);
                    Intent intent = new Intent(single_test2Activity.this, single_test3Activity.class);
                    intent.putExtra("score", score + "");
                    //为了不抽重复题
                    intent.putExtra("random_title", random_title + "");
                    startActivity(intent);
                    this.finish();  //结束掉当前Activity
                } else {
                    Toast.makeText(this, "请选择答案在进行操作！", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


    }
}
