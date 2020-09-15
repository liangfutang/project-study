package com.zjut.study.tomcat.startservlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class MyTomcat {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8090);
        tomcat.start();

        // 实现没该文件夹不会自动创建，会报找不到文件
        Context context = tomcat.addWebapp("/boot", "e:\\tomcat\\");

        IndexServlet indexServlet = new IndexServlet();
        tomcat.addServlet("/boot", "index", indexServlet);
        context.addServletMappingDecoded("/index.do", "index");
        tomcat.getServer().await();
    }
}
