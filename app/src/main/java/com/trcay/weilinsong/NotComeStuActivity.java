package com.trcay.weilinsong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by trcay on 2017/5/22.
 * 未到学生界面
 */
public class NotComeStuActivity extends Activity {

    EditText ed_nocome;
    TextView tv_tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nocomestu);
        ed_nocome = (EditText) findViewById(R.id.ed_notcome);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        tv_tips.setText(getYearMonthDate() + " 缺勤的学生有:");
        StringBuffer sb = new StringBuffer();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("datalist_bundle");   //得到传过来的Bundle包
        List<String> list = bundle.getStringArrayList("notcomestu");    //从Bundle里获得list集合
        for (int i = 0; i < list.size(); i++) {     //遍历list集合追加到StringBuffer里
            sb.append(list.get(i) + "、");
        }
        if (sb.toString() != "")
            ed_nocome.setText(sb.toString());
        else {
            tv_tips.setText(getYearMonthDate() + " 没有缺勤学生!");
            ed_nocome.setVisibility(View.GONE);
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

}
