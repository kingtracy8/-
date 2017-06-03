package com.trcay.weilinsong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tracy.dao.DBManager;

/**
 * Created by trcay on 2017/4/24.
 * 测试代码，无用
 */
public class stuManagerActivity extends Activity implements View.OnClickListener {


    Button btn_insert;
    Button btn_delete;
    EditText edit_class;
    EditText edit_name;
    EditText edit_Pnum;

    DBManager mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_manager);


        btn_insert = (Button) findViewById(R.id.insert_stu);
        btn_delete = (Button) findViewById(R.id.delete_stu);

        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        edit_class = (EditText) findViewById(R.id.edit_class);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_Pnum = (EditText) findViewById(R.id.edit_Pnum);

        mydb = new DBManager(getApplicationContext(), "students", null, 1);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.insert_stu:
                mydb.insertStudents(edit_class.getText().toString(), edit_name.getText().toString(), edit_Pnum.getText().toString());
                Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.delete_stu:
                mydb.deleteStudents(edit_class.getText().toString(), edit_name.getText().toString(), edit_Pnum.getText().toString());
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }
}
