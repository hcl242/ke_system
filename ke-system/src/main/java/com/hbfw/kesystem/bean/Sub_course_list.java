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
public class Sub_course_list {

    private long endtime;
    private List<Task_list> task_list;
    private int sub_id;
    private String name;
    private int complement_degree;
    private long csid;
    private int complete_score;
    private long bgtime;
    public void setEndtime(long endtime) {
         this.endtime = endtime;
     }
     public long getEndtime() {
         return endtime;
     }

    public void setTask_list(List<Task_list> task_list) {
         this.task_list = task_list;
     }
     public List<Task_list> getTask_list() {
         return task_list;
     }

    public void setSub_id(int sub_id) {
         this.sub_id = sub_id;
     }
     public int getSub_id() {
         return sub_id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setComplement_degree(int complement_degree) {
         this.complement_degree = complement_degree;
     }
     public int getComplement_degree() {
         return complement_degree;
     }

    public void setCsid(long csid) {
         this.csid = csid;
     }
     public long getCsid() {
         return csid;
     }

    public void setComplete_score(int complete_score) {
         this.complete_score = complete_score;
     }
     public int getComplete_score() {
         return complete_score;
     }

    public void setBgtime(long bgtime) {
         this.bgtime = bgtime;
     }
     public long getBgtime() {
         return bgtime;
     }

}