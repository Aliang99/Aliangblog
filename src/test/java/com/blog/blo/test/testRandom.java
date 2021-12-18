package com.blog.blo.test;

import java.util.HashMap;
import java.util.Random;

/**
 * @Author: ALiang
 * @Date: 2021/5/1 18:08
 * @Description:
 */
public class testRandom {
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int j = random.nextInt(2)+1;
            System.out.println(j);
        }

        //HashMap;
    }
}
