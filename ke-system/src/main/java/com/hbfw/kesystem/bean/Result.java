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
public class Result {

    private int ts;
    private int page;
    private List<Map_list> map_list;
    private int end;
    public void setTs(int ts) {
         this.ts = ts;
     }
     public int getTs() {
         return ts;
     }

    public void setPage(int page) {
         this.page = page;
     }
     public int getPage() {
         return page;
     }

    public void setMap_list(List<Map_list> map_list) {
         this.map_list = map_list;
     }
     public List<Map_list> getMap_list() {
         return map_list;
     }

    public void setEnd(int end) {
         this.end = end;
     }
     public int getEnd() {
         return end;
     }

}