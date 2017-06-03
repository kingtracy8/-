package com.tracy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tracy.ViewHolder.StuListHolder;
import com.tracy.bean.Students;
import com.trcay.weilinsong.R;

import java.util.List;

/**
 * Created by trcay on 2017/4/17.
 */
public class StuAdapter extends BaseAdapter {


    Context context;
    TextView tv_stu;
    CheckBox ch_stu;
    //测试数据

    StuListHolder holder;
    List<Students> students_list;


    public StuAdapter(Context context, List<Students> students_list) {
        this.context = context;

        //从sqlLite里获取数据

        this.students_list = students_list;

    }

    public StuAdapter(Context context) {
        this.context = context;


    }


    @Override
    public int getCount() {
        return students_list.size();
    }

    @Override
    public Object getItem(int position) {
        return students_list.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (true) {     // convertView==null 放弃使用缓存机制，使listview不重复(以后有空再研究解决办法)
            convertView = LayoutInflater.from(context).inflate(R.layout.check_list, null);
            tv_stu = (TextView) convertView.findViewById(R.id.tv_stuName);
            ch_stu = (CheckBox) convertView.findViewById(R.id.check_stu);
            holder = new StuListHolder(tv_stu, ch_stu);
            convertView.setTag(holder);     //进行listview的优化
        } else {
            holder = (StuListHolder) convertView.getTag();
            tv_stu = holder.getStu_Name();
            ch_stu = holder.getCh_stu();
        }

//        for (int i = 0; i < students_list.size(); i++) {   不需要for循环，谨以此注释，下次不要掉坑
        tv_stu.setText("学号:" + students_list.get(position).getPnumber() + "  姓名 " + students_list.get(position).getName());
//        }


        return convertView;
    }


}
