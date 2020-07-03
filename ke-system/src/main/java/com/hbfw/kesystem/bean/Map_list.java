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
public class Map_list {

    private boolean belong_to_map;
    private String industry3_name;
    private int map_id;
    private List<Map_courses> map_courses;
    private int industry3;
    public void setBelong_to_map(boolean belong_to_map) {
         this.belong_to_map = belong_to_map;
     }
     public boolean getBelong_to_map() {
         return belong_to_map;
     }

    public void setIndustry3_name(String industry3_name) {
         this.industry3_name = industry3_name;
     }
     public String getIndustry3_name() {
         return industry3_name;
     }

    public void setMap_id(int map_id) {
         this.map_id = map_id;
     }
     public int getMap_id() {
         return map_id;
     }

    public void setMap_courses(List<Map_courses> map_courses) {
         this.map_courses = map_courses;
     }
     public List<Map_courses> getMap_courses() {
         return map_courses;
     }

    public void setIndustry3(int industry3) {
         this.industry3 = industry3;
     }
     public int getIndustry3() {
         return industry3;
     }

}