package com.tracy.bean;

/**
 * Created by trcay on 2017/5/24.
 */
public class subtest {

    public int sub_id;
    public String sub_content;

    public int getSub_id() {
        return sub_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    public String getSub_content() {
        return sub_content;
    }

    public void setSub_content(String sub_content) {
        this.sub_content = sub_content;
    }

    public subtest(String sub_content) {

        this.sub_content = sub_content;
    }


}
