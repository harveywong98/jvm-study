package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassLoader extends ClassLoader {

    public JarFile jarFile;

    public ClassLoader parent;

    public JarClassLoader(JarFile jarFile) {
        super(Thread.currentThread().getContextClassLoader());
        this.parent = Thread.currentThread().getContextClassLoader();
        this.jarFile = jarFile;
    }


    public JarClassLoader(JarFile jarFile, ClassLoader parent) {
        super(parent);
        this.parent = parent;
        this.jarFile = jarFile;
    }

    public String classNameToJarEntry(String name) {
        String s = name.replaceAll("\\.", "\\/");
        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.append(".class");
        return stringBuilder.toString();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            Class c = null;
            if (null != jarFile) {
                String jarEntryName = classNameToJarEntry(name);
                JarEntry entry = jarFile.getJarEntry(jarEntryName);
                if (null != entry) {
                    InputStream is = jarFile.getInputStream(entry);
                    int availableLen = is.available();
                    int len = 0;
                    byte[] bt1 = new byte[availableLen];
                    while (len < availableLen) {
                        len += is.read(bt1, len, availableLen - len);
                    }
                    c = defineClass(name, bt1, 0, bt1.length);
                } else {
                    if (parent != null) {
                        return parent.loadClass(name);
                    }
                }
            }
            return c;
        } catch (IOException e) {
            throw new ClassNotFoundException("Class " + name + " not found.");
        }
    }

}