package com.tracy.bean;

/**
 * Created by trcay on 2017/4/17.
 */
public class Students {

    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPnumber() {
        return Pnumber;
    }

    public void setPnumber(String pnumber) {
        Pnumber = pnumber;
    }

    public String name;
    public String Pnumber;

    public Students(){

    }
    public Students(int id,String name,String Pnumber){
        this.id = id;
        this.name = name;
        this.Pnumber = Pnumber;
    }

}
