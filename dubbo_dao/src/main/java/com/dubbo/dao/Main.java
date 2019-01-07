package com.dubbo.dao;

public class Main {
    public static void main(String[] args) {
        //第一种方式：
        //该类是dubbo框架提供，作用是启动dubbo服务，dubbo会在启动服务时，读取classpath下一个名为dubbo.properties文件的属性值。
        com.alibaba.dubbo.container.Main.main(args);
    }
}