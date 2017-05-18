package com.base.apple.demo;


import java.util.ArrayList;

/**
 *公共基础类
 */
public class DemooBean {


    ArrayList<Data> list;


    public ArrayList<Data> getList() {
        return list;
    }

    public void setList(ArrayList<Data> list) {
        this.list = list;
    }

    /**
     * user : dd
     * name : dd
     */

    public class Data{
        private String user;
        private String name;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
