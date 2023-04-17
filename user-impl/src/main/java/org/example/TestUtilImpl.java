package org.example;

import cn.hutool.core.util.RandomUtil;
import org.example.util.TestUtil;

public class TestUtilImpl implements TestUtil {
    @Override
    public void test() {
        System.out.println("random is " + RandomUtil.randomInt(5));
    }
}
