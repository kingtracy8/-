package com.tracy.tools;

import com.tracy.bean.Students;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trcay on 2017/5/22.
 */
public class JsonTools {

    public JsonTools() {

    }

    //解析从HttpUtils中获得的String类型的json

    public static List<Students> getStudents(String key, String jsonString) {

        List<Students> studentsList = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);     //key 就是students
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonArrayObj = jsonArray.getJSONObject(i);
                Students student = new Students();
                student.setId(jsonArrayObj.getInt("id"));
                student.setName(jsonArrayObj.getString("name"));
                student.setPnumber(jsonArrayObj.getString("pnumber"));
                studentsList.add(student);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return studentsList;
    }
}
