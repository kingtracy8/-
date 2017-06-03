package com.tracy.bean;

/**
 * Created by trcay on 2017/4/24.
 */
public class singletest {

    public int id;
    public String content;
    public String answer1;
    public String answer2;

    public String getTrue_answer() {
        return true_answer;
    }

    public void setTrue_answer(String true_answer) {
        this.true_answer = true_answer;
    }

    public String true_answer;
    public singletest() {
    }

    public singletest( String content, String answer1, String answer2,String true_answer) {
        this.content = content;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.true_answer = true_answer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }


}
