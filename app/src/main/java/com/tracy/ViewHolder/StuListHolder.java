package com.tracy.ViewHolder;

import android.widget.CheckBox;
import android.widget.TextView;


/**
 * Created by trcay on 2017/4/17.
 */
public class StuListHolder {

    public static TextView stu_Name;
    public static CheckBox ch_stu;


    public StuListHolder(TextView stu_Name, CheckBox ch_stu){
        this.stu_Name = stu_Name;
        this.ch_stu = ch_stu;

    }

    public CheckBox getCh_stu() {
        return ch_stu;
    }

    public void setCh_stu(CheckBox ch_stu) {
        this.ch_stu = ch_stu;
    }


    public TextView getStu_Name() {
        return stu_Name;
    }

    public void setStu_Name(TextView stu_Name) {
        this.stu_Name = stu_Name;
    }



}
