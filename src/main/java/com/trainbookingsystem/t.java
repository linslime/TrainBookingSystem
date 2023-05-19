package com.trainbookingsystem;

import java.util.ArrayList;
import java.util.List;

public class t {
    public static void main(String[] args) {
        List<String> sites = new ArrayList<String>();
        sites.add("Google");
        sites.add("Runoob");
        sites.add("Taobao");
        sites.add("Weibo");
        sites.set(2, sites.get(0)); // 第一个参数为索引位置，第二个为要修改的值
        System.out.println(sites);
    }
}
