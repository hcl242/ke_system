/**
  * Copyright 2020 bejson.com 
  */
package com.hbfw.kesystem.bean;
import java.util.List;

/**
 * Auto-generated: 2020-05-08 20:17:15
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Chapter_list {

    private long ch_id;
    private List<Sub_course_list> sub_course_list;
    private int ch_no;
    private String name;
    public void setCh_id(long ch_id) {
         this.ch_id = ch_id;
     }
     public long getCh_id() {
         return ch_id;
     }

    public void setSub_course_list(List<Sub_course_list> sub_course_list) {
         this.sub_course_list = sub_course_list;
     }
     public List<Sub_course_list> getSub_course_list() {
         return sub_course_list;
     }

    public void setCh_no(int ch_no) {
         this.ch_no = ch_no;
     }
     public int getCh_no() {
         return ch_no;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

}