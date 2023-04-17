package org.example;

import org.example.util.TestUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.f();
    }
    public void f() {
        String path = System.getProperty("user.dir") + File.separator + "common" + File.separator + "src" + File.separator + "lib";
        File classpath = new File(path);
        URL[] urls = new URL[1];
        URLClassLoader loader = null;
        try {
            String repository = new URL("file", null, classpath.getCanonicalPath() + File.separator + "user-impl-1.0-SNAPSHOT-jar-with-dependencies.jar").toString();
            URLStreamHandler streamHandler = null;
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Class clazz = null;
        try {
            clazz = loader.loadClass("org.example.TestUtilImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TestUtil testUtil = null;
        try {
            testUtil = (TestUtil) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        testUtil.test();
    }
}